package DistributedSolution.ClientSide.Referee;


import Nondistributedsolution.Monitors.Global;
import Nondistributedsolution.Monitors.Playground;
import Nondistributedsolution.Monitors.RefereeSite;
import genclass.GenericIO;

/**
 * Created by jonnybel on 3/8/16.
 */

public class Referee extends Thread {

    /**
     * PlaygroundRemote Monitor object
     */
    private RefereePlaygroundStub refereePlaygroundStub;

    /**
     * RefereeSiteRemote Monitor object
     */
    private RefereeRefereeSiteStub refereeRefereeSiteStub;

    /**
     * General Informational Repository object
     */
    private RefereeGlobalStub refereeGlobalStub;

    /**
     * Referee Current State
     */
    private RefereeState refereeState;


    /**
     * Referee Object Constructor
     * @param refereePlaygroundStub
     * @param refereeRefereeSiteStub
     * @param refereeGlobalStub
     */
    public Referee(RefereePlaygroundStub refereePlaygroundStub, RefereeRefereeSiteStub refereeRefereeSiteStub, RefereeGlobalStub refereeGlobalStub){
        this.refereePlaygroundStub = refereePlaygroundStub;
        this.refereeGlobalStub = refereeGlobalStub;
        this.refereeRefereeSiteStub = refereeRefereeSiteStub;
    }

    /** Life Cycle of the Referee Thread
    */
    @Override
    public void run(){
        setRefereeState(RefereeState.START_OF_THE_MATCH);
        refereeRefereeSiteStub.announceMatch();
        GenericIO.writelnString("match announced ");
        do{
            setRefereeState(RefereeState.START_OF_A_GAME);
            refereeRefereeSiteStub.announceGame();
            while(!refereeGlobalStub.gameFinished()){

                // TODO: method in stub for this
                refereeRefereeSiteStub.waitForBench();

                setRefereeState(RefereeState.TEAMS_READY);
                refereePlaygroundStub.callTrial();
                setRefereeState(RefereeState.WAIT_FOR_TRIAL_CONCLUSION);
                refereePlaygroundStub.startTrial();

                refereePlaygroundStub.assertTrialDecision();
            }
            setRefereeState(RefereeState.END_OF_A_GAME);
            refereeRefereeSiteStub.declareGameWinner ();
        }while(refereeGlobalStub.matchInProgress());
        setRefereeState(RefereeState.END_OF_THE_MATCH);
        refereeRefereeSiteStub.declareMatchWinner();

        // TODO: kill referee site
        refereeGlobalStub.closeFile();
        // TODO: kill global repository
    }

    public RefereeState getRefereeState() {
        return refereeState;
    }

    public void setRefereeState(RefereeState refereeState) {
        this.refereeState = refereeState;
    }
}
