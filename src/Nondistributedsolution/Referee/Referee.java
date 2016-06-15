package Nondistributedsolution.Referee;

import Nondistributedsolution.Monitors.Bench;
import Nondistributedsolution.Monitors.Global;
import Nondistributedsolution.Monitors.Playground;
import Nondistributedsolution.Monitors.RefereeSite;

/**
 * Created by jonnybel on 3/8/16.
 */

public class Referee extends Thread {

    /**
     * PlaygroundRemote Monitor object
     */
    private Playground playground;

    /**
     * RefereeSiteRemote Monitor object
     */
    private RefereeSite refereeSite;

    /**
     * General Informational Repository object
     */
    private Global global;


    /**
     * RefereeThread Current State
     */
    private RefereeState refereeState;

    /**
     * RefereeThread Object Constructor
     * @param playground
     * @param refereeSite
     * @param global
     */
    public Referee(Playground playground, RefereeSite refereeSite, Global global){
        this.playground = playground;
        this.refereeSite = refereeSite;
        this.global = global;
    }

    /** Life Cycle of the RefereeThread Thread
    */
    @Override
    public void run(){
        refereeSite.announceMatch();
        do{
            refereeSite.announceGame();
            while(!global.gameFinished()){
                refereeSite.waitForBench();
                System.out.println("CALLING NEW TRIAL");

                playground.callTrial();
                System.out.println("waking people.....");

                playground.startTrial();
                playground.assertTrialDecision();
            }
            refereeSite.declareGameWinner ();
        }while(global.matchInProgress());

        refereeSite.declareMatchWinner();
        global.closeFile();
    }

    public void setRefereeState(RefereeState refereeState) {
        this.refereeState = refereeState;
    }

}
