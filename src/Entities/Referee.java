package Entities;

import Monitors.Global;
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

    private Global global;

    public Referee(Playground playground, RefereeSite refereeSite, Global global){
        this.playground = playground;
        this.refereeSite = refereeSite;
        this.global = global;
    }

    /* Life Cycle of the Referee Thread
    */


    @Override
    public void run(){


        refereeSite.announceMatch();
        do{
            refereeSite.announceGame();
            while(!gameFinished()){
                playground.callTrial(); //  waitingForTeamsReady();
                playground.startTrial(); // waitingForTrialEnd();
                playground.assertTrialDecision();

            }

        }while(!global.matchFinished());




    }


    public RefereeState getrefstate() {
        return refstate;
    }

    public void setrefstate(RefereeState state) {
        this.refstate = state;
    }

    public boolean gameFinished(){
        return true;
    }

}
