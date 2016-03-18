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
    private Contestant[] team1;
    private Contestant[] team2;
    private Global global;

    public Playground(Contestant[] team1, Contestant[] team2, Global global){
        team1 = new Contestant[teamSize];
        team2 = new Contestant[teamSize];
        this.global = global;
    }

    /*
    *   Referee calls the teams to assemble
    */
    public synchronized void assertTrialDecision(){
       if(flagPos > 0){
           //team 1 wins
           if(flagPos > 4) {
               //knockout for team1
           }
       }
        else if (flagPos < 0){
           //team 2 wins
           if(flagPos < -4){
               //knockout for team2
           }
       }
    }

    /*
     *   Referee begins the trial
    */
    public synchronized  void startTrial(){
        global.setRefereeState(RefereeState.WAIT_FOR_TRIAL_CONCLUSION);
    }

    /*
    * Contestant operation
     */
    public synchronized  void getReady(int contestantID) {
        global.setContestantState(contestantID,ContestantState.DO_YOUR_BEST);
    }

     /*
    * Contestant operation
     */
    public synchronized void done(int contestantID){
        notifyAll();
    }

    /* Coach operation
    *
     */
    public synchronized  void informReferee(int teamID){
        global.setCoachState(teamID, CoachState.WATCH_TRIAL);
    }

    /*
    * Coach operation
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
