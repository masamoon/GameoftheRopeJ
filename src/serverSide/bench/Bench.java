package serverSide.bench;

import common.entityStates.CoachState;
import common.VectorClock;
import interfaces.GlobalInterface;
import interfaces.RefereeSiteInterface;
import common.entityStates.ContestantState;

import java.rmi.RemoteException;
import java.util.Collections;
import java.util.HashMap;
import java.util.Random;
import java.util.stream.IntStream;


public class Bench {

    /**
     *  General Information Repository object
     */
    private final GlobalInterface global;

    /**
     *  RefereeSiteRemote object
     */
    private final RefereeSiteInterface refereeSite;

    /**
     *  Number of contestants sitting on the bench.
     */
    private int numSitting;

    /**
     * True if a new trial has been called by the RefereeThread
     */
    private boolean trialCalled;

    /**
     * Whether a team on the bench has been called for a trial
     */
    private boolean [] benchCalled;

    /**
     * Selected contestants of each team to participate in the next trial
     */
    private int [] [] selectedTeam;

    /**
     * Contestants strengths
     */
    private int [] [] contestantStrengths;

    /**
     * true if the contestant strength needs to be updated (on it's object)
     */
    private boolean [] [] strUpdate;


    private final int NUMBER_OF_TEAMS = 2;
    private final int TEAM_SIZE = 5;


    /**
     * Constructor for this Shared Region.
     * @param global
     */
    public Bench(GlobalInterface global, RefereeSiteInterface refereeSite){

        this.global = global;
        this.refereeSite = refereeSite;
        this.numSitting=0;

        this.trialCalled = false;
        this.benchCalled = new boolean [] {false, false};
        this.selectedTeam = new int [] [] {{-1,-1,-1} , {-1,-1,-1}};

        this.contestantStrengths = new int [NUMBER_OF_TEAMS] [TEAM_SIZE];
        this.strUpdate = new boolean[NUMBER_OF_TEAMS] [TEAM_SIZE];
    }

    public synchronized void reviewNotes (VectorClock vc, int teamID) throws RemoteException{

        if(refereeSite.getTrialNum()>1){

            for(int i = 0; i< TEAM_SIZE; i++){
                final int finalI = i;
                boolean contains = IntStream.of(selectedTeam[teamID]).anyMatch(x -> x == finalI);

                if(contains){
                    int str = contestantStrengths[teamID][i];
                    if(str > 0) {
                        contestantStrengths[teamID][i] = (str - 1);

                        global.setStrength(i, teamID,--str);
                    }
                }
                else{
                    int str = contestantStrengths[teamID][i];
                    if(str < 10) {
                        contestantStrengths[teamID][i] = (str + 1);
                        global.setStrength(i, teamID, ++str);
                    }
                }
            }
        }
        global.setCoachState(vc, teamID, CoachState.WAIT_FOR_REFEREE_COMMAND);
        while(!trialCalled)
        {
            try {
                wait();
            } catch (InterruptedException e) {}
        }
    }


    /**
     * The ContestantThread sits on the bench. He changes its state to to SIT_AT_THE_BENCH and waits until he is called by the coach.
     * When the last contestant (the 10th) sits, he enables a flag to signal this event and notifies the RefereeThread at the PlaygroundRemote so that a trial can be called.
     * The conditions for the contestant to leave his waiting cycle are: a trial being called AND being selected by the coach of his team. He is also freed from the waiting cycle if the match has been finished.
     *
     * @param teamID Team ID the contestant
     * @param contestantID contestant's ID
     */
    public synchronized void sitDown(VectorClock vc, int contestantID, int teamID) throws RemoteException{

        global.setContestantState(vc, contestantID, teamID, ContestantState.SIT_AT_THE_BENCH);

        this.numSitting++;

        if(numSitting==10){
            refereeSite.setReadyForTrial(true);
            refereeSite.benchWakeRef();
        }
        while ((!benchCalled[teamID] || !imSelected(contestantID, teamID)) && global.matchInProgress()){
            try
            {
                wait ();
            }
            catch (InterruptedException e) {}
        }

        this.numSitting--;

        // todo: do this only once (the first to stand up)
        refereeSite.setReadyForTrial(false);
    }
    /**
     * Checks if this contestant has been selected by his coach to stand up for the next trial.
     * Internal Operation.
     * @param contestantID contestant's id
     * @param teamID Team of the contestant
     * @return true if selected
     */
    private boolean imSelected (int contestantID, int teamID)
    {
        for(int id : selectedTeam[teamID]){
            if(contestantID == id)
                return true;
        }
        return false;
    }

    /**
     *  Call contestants for the next trial based on a selection.
     *  He makes a selection and notifies the BenchRemote.
     * @param teamID Team of the CoachThread
     *
     */
    public synchronized void callContestants (int teamID){

        Random r = new Random();
        int strategy = r.nextInt(2);

        int team[];
        //System.out.println("CoachThread from team " + teamID + " rolled the strategy: "+strategy);
        if(strategy == 0)
            team = selectRandom();
        else
            team = selectTopteam(teamID);

        selectTeam(teamID, team[0],team[1],team[2]);

        System.out.println("CoachThread " + teamID + " picked: " + team[0]+team[1]+team[2]);

        benchCalled[teamID] = true;

        notifyAll();
    }

    /**
     *  This method is used by the RefereeThread in two occasions: when calling a trial to wake contestants and coach OR at the end of the match to free the contestants that stayed on the bench for the last trial.
     */
    public synchronized void wakeBench(){
        notifyAll();
    }

    /**
     * Team building strategy that consists on randomly choosing 3 team elements.
     * Internal Operation
     * @return array containing the selected team for trial
     */
    private int[] selectRandom(){
        Random r = new Random();
        int first = r.nextInt(5);
        int second;
        do {
            second = r.nextInt(5);
        }while(second == first);

        int third;

        do{
            third = r.nextInt(5);
        }while( third == first || third == second);

        return new int[]{first,second,third};
    }

    /**
     * Team building strategy that consists on choosing the 3 elements with most strength.
     * Internal Operation
     * @param teamID Team of the coach
     * @return Array containing the IDs of the contestants selected for the next trial
     */
    private int[] selectTopteam(int teamID){
        int[] str;

        str= contestantStrengths[teamID];

        HashMap<Integer,Integer> map = new HashMap<>();

        for (int i = 0; i <5 ; i++) {
            map.put(i,str[i]);
        }

        int first = Collections.max(map.entrySet(), (entry1, entry2) -> entry1.getValue() > entry2.getValue() ? 1 : -1).getKey();
        map.remove(first);
        int second = Collections.max(map.entrySet(), (entry1, entry2) -> entry1.getValue() > entry2.getValue() ? 1 : -1).getKey();
        map.remove(second);
        int third = Collections.max(map.entrySet(), (entry1, entry2) -> entry1.getValue() > entry2.getValue() ? 1 : -1).getKey();

        return new int[]{first,second,third};
    }

    /**
     * Sets if the contestants have been called for a trial
     * @param teamID team's id
     * @return true if bench is called
     */
    public synchronized void setBenchCalled (int teamID, boolean called){
        benchCalled [teamID] = called;
    }

    public void setTrialCalled(boolean trialCalled) {
        this.trialCalled = trialCalled;
    }

    private synchronized void selectTeam(int teamID, int first, int second, int third) {
        selectedTeam [teamID] = new int [] {first,second,third};
    }

    public synchronized void setStrength (int contestantID, int teamID, int strength) throws RemoteException{
        contestantStrengths[teamID][contestantID] = strength;
        global.setStrength(contestantID, teamID, strength);
    }

    public synchronized int getStrength (int contestantID, int teamID){
        return contestantStrengths[teamID][contestantID];
    }
}
