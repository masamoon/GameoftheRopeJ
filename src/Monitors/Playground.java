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
    private int team1vic;
    private int team2vic;
    private Global global;

    public Playground( Global global, int team1vic, int team2vic){
        this.team1vic = team1vic;
        this.team2vic = team2vic;
        this.global = global;

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
           team1vic+=1; //team 1 wins

       }
        else if (flagPos < 0){
           team2vic+=1; //team 2 wins

       }
    }

    /**
     *   Referee begins the trial
     */
    public synchronized  void startTrial(){
        global.setRefereeState(RefereeState.WAIT_FOR_TRIAL_CONCLUSION);
    }

    /**
    * Contestant operation
    * @param contestantID contestant's ID
     */
    public synchronized  void getReady(int contestantID) {
        global.setContestantState(contestantID,ContestantState.DO_YOUR_BEST);
    }

   /**
    * Contestant operation
    * @param contestantID contestant's ID
    */
    public synchronized void done(int contestantID){
        notifyAll();
    }

    /** Coach informs Referee of the chosen team
    *@param teamID team's ID
    */
    public synchronized  void informReferee(int teamID){
        global.setCoachState(teamID, CoachState.WATCH_TRIAL);
    }

   /**
    * Coach operation
    * @param teamID team's ID
    */
    public synchronized void reviewNotes(int teamID){


    }

    public synchronized void callTrial(){
        global.setRefereeState(RefereeState.TEAMS_READY);
    }



    public synchronized boolean followCoachAdvice (int teamID, int contestantID, Global global) {

        // todo: in progress by jonnybel @quintafeira

        return true;
    }

}
