package Entities;

import States.RefereeState;

/**
 * Created by jonnybel on 3/8/16.
 */
public class Referee extends Thread {

    /* todo: add comments */

    private RefereeState refstate;

    /* Life Cycle of the Referee Thread
    */

    @Override
    public void run(){

        while(true){
            callTrial();
            waitingForTeamsReady();
            startTrial();
            waitingForTrialEnd();
            assertTrialDecision();
        }

    }


    public RefereeState getrefstate() {
        return refstate;
    }

    public void setrefstate(RefereeState state) {
        this.refstate = state;
    }

    /*
    *   Referee calls the teams to assemble
    */
    public void callTrial(){

    }

    /*
     *   Referee begins the trial
    */
    public void startTrial(){

    }

    /*
     *Waiting for the last member of the teams to sit up */

    public void waitingForTeamsReady(){
        try
        { sleep ((long) (1 + 40 * Math.random ()));
        }
        catch (InterruptedException e) {}
    }

    public void waitingForTrialEnd(){
        try
        { sleep ((long) (1 + 40 * Math.random ()));
        }
        catch (InterruptedException e) {}
    }

    public void assertTrialDecision(){

    }
}
