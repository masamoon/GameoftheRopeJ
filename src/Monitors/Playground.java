package Monitors;

import Entities.Contestant;
import Entities.Referee;
import States.CoachState;
import States.ContestantState;
import States.RefereeState;

/**
 * Created by jonnybel on 3/8/16.
 */
public class Playground {

    private int flagPos;
    private int teamSize;
    private int[] team1;
    private int[] team2;
   // private int team1vic;
    //private int team2vic;
    private int trial_no;
    private Global global;

    private int teamsReady;
    boolean trialDecided;
    private int contestantsDone;

    public Playground( Global global){
        //this.team1vic = 0;
        //this.team2vic = 0;
        this.global = global;

        this.teamsReady = 0;
        this.trialDecided = false;

        this.team1 = new int[3];
        this.team2 = new int[3];
    }

    /*
    *   REFEREE OPERATIONS
     */

   /**
    *   Referee decides who's the winner
    */
    public synchronized void assertTrialDecision(){
       if(flagPos > 0){
           global.incGamescore_t1(); //team 1 wins

       }
        else if (flagPos < 0){
           global.incGamescore_t2(); //team 2 wins

       }

        trialDecided = true;
        trial_no+=1;
    }

    /**
     *   Referee begins the trial
     *   waits for the all the contestants to be done
     */
    public synchronized  void startTrial(){

        contestantsDone=0;
        global.setRefereeState(RefereeState.WAIT_FOR_TRIAL_CONCLUSION);

        notifyAll();

        while(contestantsDone<6){
            try {
                wait();
            } catch (InterruptedException e) {}
        }


    }

    /**
    * Contestant operation
    * @param contestantID contestant's ID
     */
    public synchronized  void getReady( int contestantID, int teamID) {
        global.setContestantState(teamID,contestantID,ContestantState.DO_YOUR_BEST);

        // todo!!!
    }


    /**
     * Contestant operation
     * @param contestantID contestant's ID
     * @param teamID contestant's team ID
     */
    public synchronized void followCoachAdvice (int contestantID, int teamID) {

        global.setContestantState(teamID, contestantID, ContestantState.STAND_IN_POSITION);

        notifyAll();

        while(global.getRefereeState() != RefereeState.WAIT_FOR_TRIAL_CONCLUSION)
        {
            try {
                wait();
            } catch (InterruptedException e) {}
        }

    }


   /**
    * Contestant operation
    * @param contestantID contestant's ID
    */
    public synchronized void done(int contestantID, int teamID){

        // todo !!!
        notifyAll();
    }

    /** Coach informs Referee of the readiness of his team
    *@param teamID team's ID
    */
    public synchronized  void informReferee(int teamID){

        global.setCoachState(teamID, CoachState.WATCH_TRIAL);

        teamsReady++;

        notifyAll();


        while(!trialDecided) {
            try {
                wait();
            } catch (InterruptedException e) {}
        }

    }

   /**
    * Coach operation
    * @param teamID team's ID
    */
    public synchronized void reviewNotes(int teamID) {

        global.setCoachState(teamID, CoachState.WAIT_FOR_REFEREE_COMMAND);

        while(global.getRefereeState() != RefereeState.TEAMS_READY || global.matchFinished()) {
            try {
                wait();
            } catch (InterruptedException e) {}
        }

    }

    /**
     *  coach changes state to blocking state ASSEMBLE_TEAM
     *  waits for the selected contestants to be standing in position
     *
     *  @param selection chosen contestants to pull rope
     *
     */
    public synchronized void waitForContestants(int teamID, int [] selection){

        global.setCoachState(teamID, CoachState.ASSEMBLE_TEAM);

        boolean contestantsStanding=false;
        do{
            { try
                { wait ();
            }
            catch (InterruptedException e) {}
            }
            for(int id: selection){
                contestantsStanding = global.getContestantState(teamID, id) == ContestantState.STAND_IN_POSITION;
                if(!contestantsStanding) break;
            }
        }while(!contestantsStanding);
    }


    /**
     *  Referee calls the trial: wakes the coaches and waits for them to have their teams ready
     *
     */


    public synchronized void callTrial(){

        teamsReady=0;

        global.setRefereeState(RefereeState.TEAMS_READY);

        notifyAll();

        while(teamsReady<2)
        {
            try{
                wait ();
            }
            catch (InterruptedException e) {}
        }
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
