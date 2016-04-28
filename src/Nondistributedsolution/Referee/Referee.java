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
            setRefereeState(RefereeState.START_OF_A_GAME);
            refereeSite.announceGame();
            while(!global.gameFinished()){
                refereeSite.waitForBench();
                System.out.println("CALLING NEW TRIAL");
                setRefereeState(RefereeState.TEAMS_READY);
                playground.callTrial();
                System.out.println("waking people.....");
                setRefereeState(RefereeState.WAIT_FOR_TRIAL_CONCLUSION);
                playground.startTrial();
                playground.assertTrialDecision();
            }
            setRefereeState(RefereeState.END_OF_A_GAME);
            refereeSite.declareGameWinner ();
        }while(global.matchInProgress());
        setRefereeState(RefereeState.END_OF_THE_MATCH);
        refereeSite.declareMatchWinner();
        global.closeFile();
    }

    public RefereeState getRefereeState() {
        return refereeState;
    }

    public void setRefereeState(RefereeState refereeState) {
        this.refereeState = refereeState;
        global.setRefereeState(refereeState);
    }

}
