package Monitors;

/**
 * Created by jonnybel on 3/8/16.
 */
public class Playground {


    /*
    *   Referee calls the teams to assemble
    */
    public synchronized void assertTrialDecision(){

    }

    /*
     *   Referee begins the trial
    */
    public synchronized  void startTrial(){

    }

    /*
    * Contestant operation
     */
    public synchronized  void getReady(int contestantID) {

    }

     /*
    * Contestant operation
     */
    public synchronized void done(int contestantID){

    }

    /* Coach operation
    *
     */
    public synchronized  void informReferee(int teamID){

    }

    /*
    * Coach operation
     */
    public synchronized void reviewNotes(int teamID){


    }

    public synchronized void callTrial(){}



    public synchronized boolean followCoachAdvice (int teamID, int contestantID, Global global) {

        // todo: in progress by jonnybel @quintafeira

        return true;
    }

}
