package Nondistributedsolution.Monitors;

import Nondistributedsolution.Logging.Logger;
import Nondistributedsolution.States.ContestantState;

import java.util.Collections;
import java.util.HashMap;
import java.util.Random;

/**
 * Created by jonnybel on 3/8/16.
 *
 * This Class implements the shared region for the Bench, with synchronization based on monitors
 */
public class Bench {

    /**
     *  General Information Repository object
     */
    private final Global global;

    /**
     * Logger object
     */
    private final Logger logger;

    /**
     *  Number of contestants sitting on the bench.
     */
    private int numSitting;


    /**
     *  Constructor for this Shared Region.
     * @param global
     * @param logger
     */
    public Bench(Global global, Logger logger){

        this.global = global;
        this.numSitting=0;
        this.logger = logger;
    }

    /**
     * The Contestant sits on the bench. He changes its state to to SIT_AT_THE_BENCH and waits until he is called by the coach.
     * When the last contestant (the 10th) sits, he enables a flag to signal this event and notifies the Referee at the Playground so that a trial can be called.
     * The conditions for the contestant to leave his waiting cycle are: a trial being called AND being selected by the coach of his team. He is also freed from the waiting cycle if the match has been finished.
     *
     * @param teamID Team ID the contestant
     * @param contestantID contestant's ID
     * @param playground reference to playground
     */
    public synchronized void sitDown(int contestantID, int teamID, Playground playground) {

        global.setContestantState(contestantID, teamID, ContestantState.SIT_AT_THE_BENCH,logger);
        this.numSitting++;

        if(numSitting==10){
            global.setBenchReady(true);
            playground.benchWakeRef();
        }
        while ((!global.benchCalled(teamID) || !imSelected(contestantID, teamID)) && global.matchInProgress()){
            try
            {
                wait ();
            }
            catch (InterruptedException e) {}
        }

        this.numSitting--;

        global.setBenchReady(false);
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
        for(int id : global.getSelection(teamID)){
            if(contestantID == id)
                return true;
        }
        return false;
    }

    /**
     *  Call contestants for the next trial based on a selection.
     *  He makes a selection and notifies the Bench.
     * @param teamID Team of the Coach
     *
     */
    public synchronized void callContestants (int teamID){

        Random r = new Random();
        int strategy = r.nextInt(2);

        int team[];
        //System.out.println("Coach from team " + teamID + " rolled the strategy: "+strategy);
        if(strategy == 0)
            team = selectRandom();
        else
            team = selectTopteam(teamID);

        global.selectTeam(teamID, team[0],team[1],team[2]);

        //System.out.println("Coach " + teamID + "picked:" + team[0]+team[1]+team[2]);
        global.setBenchCalled(teamID, true);

        notifyAll();
    }

    /**
     *  This method is used by the Referee Entity exclusively at the end of the match to free the contestants that stayed on the bench for the last trial.
     *  @see Playground#assertTrialDecision()
     */
    public synchronized void wakeContestants(){
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
        if(teamID ==0)
            str=global.getStrength_t1();
        else
            str = global.getStrength_t2();

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
}
