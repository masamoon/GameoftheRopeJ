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
        this. refereeRefereeSiteStub = refereeRefereeSiteStub;
    }

    /** Life Cycle of the Referee Thread
    */
    @Override
    public void run(){
        refereeRefereeSiteStub.announceMatch();
        GenericIO.writelnString("match announced ");
        do{
            refereeRefereeSiteStub.announceGame();
            while(!refereeGlobalStub.gameFinished()){

                // TODO: method in stub for this
                refereeRefereeSiteStub.waitForBench();

                refereePlaygroundStub.callTrial();
                refereePlaygroundStub.startTrial();
                refereePlaygroundStub.assertTrialDecision();
            }
            refereeRefereeSiteStub.declareGameWinner ();
        }while(refereeGlobalStub.matchInProgress());
        refereeRefereeSiteStub.declareMatchWinner();
        //logger.closeFile();
    }


}
