package RMISolution.Referee;

import RMISolution.Global.Global;
import RMISolution.Playground.Playground;
import RMISolution.RefereeSite.RefereeSite;

/**
 * Created by jonnybel on 5/31/16.
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
     * Referee Current State
     */
    private RefereeState refereeState;

    /**
     * Referee Object Constructor
     * @param playground
     * @param refereeSite
     * @param global
     */
    public Referee(Playground playground, RefereeSite refereeSite, Global global){
        this.playground = playground;
        this.refereeSite = refereeSite;
        this.global = global;
    }

    /** Life Cycle of the Referee Thread
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
