package DistributedSolution.ClientSide.Referee;

import Nondistributedsolution.Logging.Logger;
import Nondistributedsolution.Monitors.Global;
import Nondistributedsolution.Monitors.Playground;
import Nondistributedsolution.Monitors.RefereeSite;

/**
 * Created by jonnybel on 3/8/16.
 */

public class Referee extends Thread {

    /**
     * Playground Monitor object
     */
    private RefereePlaygroundStub refereePlaygroundStub;

    /**
     * RefereeSite Monitor object
     */
    private RefereeRefereeSiteStub refereeRefereeSiteStub;

    /**
     * Logger object
     */
    private Logger logger;

    /**
     * General Informational Repository object
     */
    private RefereeGlobalStub refereeGlobalStub;


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
        do{
            refereeRefereeSiteStub.announceGame();
            while(!refereeGlobalStub.gameFinished()){
                refereePlaygroundStub.callTrial();
                refereePlaygroundStub.startTrial();
                refereePlaygroundStub.assertTrialDecision();
            }
            refereeRefereeSiteStub.declareGameWinner ();
        }while(refereeGlobalStub.matchInProgress());
        refereeRefereeSiteStub.declareMatchWinner();
        logger.closeFile();
    }


}
