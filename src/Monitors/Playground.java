package Monitors;

import States.CoachState;
import States.ContestantState;
import States.RefereeState;

import java.util.stream.IntStream;

import static java.lang.Thread.sleep;

/**
 * Created by jonnybel on 3/8/16.
 */
public class Playground {

    private int flagPos;

    private int[] team1;
    private int[] team2;

    private int trial_no;
    private Global global;

    private int teamsReady;

    private int contestantsDone;


    private boolean trialCalled;

    private boolean trialStarted;

    public Playground( Global global){
        this.global = global;

        this.teamsReady = 0;
        this.contestantsDone = 0;

        this.team1 = new int[3];
        this.team2 = new int[3];

        this.trialStarted = false;
        this.trialCalled = false;
    }


    public synchronized void waitForCalling(int teamID){

        global.setCoachState(teamID, CoachState.WAIT_FOR_REFEREE_COMMAND);
        System.out.println("coach "+teamID+" state: WAIT_FOR_REFEREE_COMMAND ");

        while(!trialCalled)
        {
            System.out.println("Coach " +teamID+ " is now waiting");
            System.out.println(global.getSittingAtBench(teamID) + " " + trialCalled);

            try {
                wait();
            } catch (InterruptedException e) {}
            System.out.println("Coach "+teamID+" was woken up!");
        }
    }


    /**
    *   The last contestant to sit wakes the Ref
     */
    public synchronized void benchWakeRef(){
        notifyAll();
    }



    /**
     *  Referee calls the trial:
     *
     */

    public synchronized void callTrial(){

        while(!global.getBenchReady()){
            try {
                wait();
            } catch (InterruptedException e) {}
        }

        System.out.println("Referee calling trial");

        teamsReady=0;

        global.setRefereeState(RefereeState.TEAMS_READY);
        global.eraseTeamSelections();
        trialCalled = true;

        notifyAll();


    }

    public synchronized void waitForContestants(int teamID){

        global.setCoachState(teamID, CoachState.ASSEMBLE_TEAM);
        System.out.println("coach "+teamID+"state: ASSEMBLE_TEAM ");

        boolean contestantsStanding=false;
        do{
            try
            {
                System.out.println("Coach " +teamID+ " is now waiting");
                wait ();
            }
            catch (InterruptedException e) {}

            System.out.println("Coach "+teamID+" was woken up, checking selection:");
            for(int id: global.getSelection(teamID)){
                contestantsStanding = global.getContestantState(id, teamID) == ContestantState.STAND_IN_POSITION;
                if(!contestantsStanding){ System.out.println("Coach "+teamID+": my whole team is not yet ready!"); break; }
            }
        }while(!contestantsStanding);
        System.out.println("Coach "+teamID+" exited the waiting cycle in waitForContestants! (which means my team is ready");
    }


    /**
     * Contestant operation
     * @param contestantID contestant's ID
     * @param teamID contestant's team ID
     */
    public synchronized void followCoachAdvice (int contestantID, int teamID) {

        System.out.println("Contestant "+contestantID+" from team "+teamID+" standing in position");
        global.setContestantState(contestantID, teamID, ContestantState.STAND_IN_POSITION);

        notifyAll();


        while(!trialStarted)
        {
            try {
                wait();
            } catch (InterruptedException e) {}
        }

    }


    /** Coach informs Referee of the readiness of his team
     *@param teamID team's ID
     */
    public synchronized  void informReferee(int teamID){

        System.out.println(teamID+" informing referee");
        global.setCoachState(teamID, CoachState.WATCH_TRIAL);

        System.out.println("Coach " + teamID + " is now watching Trial...");
        teamsReady++;

        notifyAll();


        while(trialCalled || trialStarted) {
            System.out.println("Coach " + teamID + " is waiting.");
            try {
                wait();
            } catch (InterruptedException e) {}
            System.out.println("Coach " + teamID + " woke up!");
        }

    }

    /**
     *   Referee begins the trial
     *   waits for the all the contestants to be done
     */
    public synchronized  void startTrial(){

        while(teamsReady<2)
        {
            try{
                wait ();
            }
            catch (InterruptedException e) {}
        }

        global.setBenchCalled(false);
        System.out.println("starting trial");
        contestantsDone=0;

        global.setRefereeState(RefereeState.WAIT_FOR_TRIAL_CONCLUSION);

        trialStarted = true;
        trialCalled = false;

        notifyAll();

    }


    /**
     * Contestant operation
     * @param contestantID contestant's ID
     */
    public synchronized  void getReady( int contestantID, int teamID) {
        System.out.println("Contestant "+contestantID+" from team "+ teamID+ " getting ready");
        global.setContestantState(contestantID,teamID,ContestantState.DO_YOUR_BEST);

    }


    /**
     * Contestant operation
     */
    public synchronized void done(){

        contestantsDone++;

        if(contestantsDone==6)
            notifyAll();

        while(trialStarted)
            try{
                wait();
            } catch (InterruptedException e){}

    }

   /**
    *   Referee decides who's the winner
    */
    public synchronized void assertTrialDecision(){

        while(contestantsDone<6){
            try {
                wait();
            } catch (InterruptedException e) {}
        }

        System.out.println("trial decision:");
        if(flagPos > 0){
            global.incGamescore_t1(); //team 1 wins
              System.out.println("team 1 wins trial ");
        }
        else if (flagPos < 0) {
            global.incGamescore_t2(); //team 2 wins
            // System.out.println("team 2 wins trial ");

        }
        else System.out.println("draw!");

        trialStarted = false;

        notifyAll();
        trial_no+=1;
    }

   /**
    * Coach operation
    * @param teamID team's ID
    */
    public synchronized void reviewNotes(int teamID) {


        System.out.println(teamID +" team reviewing notes ");


        for(int i=0; i<5; i++){
            final int finalI = i;
            boolean contains = IntStream.of(global.getSelection(teamID)).anyMatch(x -> x == finalI);

            if(contains){
                int str = global.getStrength(teamID,i);
                global.setStrength(teamID,i,--str);
            }
            else{
                int str = global.getStrength(teamID,i);
                global.setStrength(teamID,i,++str);
            }


        }

        System.out.println("Coach "+teamID+" exited the waiting cycle in reviewNotes!");

    }



    /**
     *
     * @return
     */
    public int getFlagPos(){ return this.flagPos; }

    /**
     *
     * @return
     */
    public int getTrial_no(){ return this.trial_no; }





}
