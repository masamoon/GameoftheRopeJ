package Entities;

import Monitors.Playground;
import Monitors.RefereeSite;
import States.RefereeState;

/**
 * Created by jonnybel on 3/8/16.
 */
public class Referee extends Thread {

    /* todo: add comments */

    private RefereeState refstate;
    private Playground playground;
    private RefereeSite refereeSite;

    public Referee(Playground playground, RefereeSite refereeSite){
        this.playground = playground;
        this.refereeSite = refereeSite;
    }

    /* Life Cycle of the Referee Thread
    */


    @Override
    public void run(){

        while(true){
            refereeSite.callTrial();
            waitingForTeamsReady();
            playground.startTrial();
            waitingForTrialEnd();
            playground.assertTrialDecision();
        }

    }


    public RefereeState getrefstate() {
        return refstate;
    }

    public void setrefstate(RefereeState state) {
        this.refstate = state;
    }



    /*
     *Waiting for the last member of the teams to sit up */

    public void waitingForTeamsReady(){
        try
        { sleep ((long) (1 + 40 * Math.random ()));
        }
        catch (InterruptedException e) {}
    }

    /*
    * Waiting for the trial's end
     */
    public void waitingForTrialEnd(){
        try
        { sleep ((long) (1 + 40 * Math.random ()));
        }
        catch (InterruptedException e) {}
    }


}
