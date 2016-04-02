package Nondistributedsolution.Entities;

import Nondistributedsolution.Logging.Logger;
import Nondistributedsolution.Monitors.Global;
import Nondistributedsolution.Monitors.Playground;
import Nondistributedsolution.Monitors.RefereeSite;

/**
 * Created by jonnybel on 3/8/16.
 */

public class Referee extends Thread {

    private Playground playground;
    private RefereeSite refereeSite;
    private Logger logger;
    private Global global;

    public Referee(Playground playground, RefereeSite refereeSite, Global global, Logger logger){
        this.playground = playground;
        this.refereeSite = refereeSite;
        this.global = global;
        this.logger = logger;
    }

    /** Life Cycle of the Referee Thread
    */
    @Override
    public void run(){
        refereeSite.announceMatch();
        do{
            refereeSite.announceGame();
            while(!global.gameFinished()){
                playground.callTrial();
                playground.startTrial();
                playground.assertTrialDecision();
            }
            refereeSite.declareGameWinner (logger);
        }while(global.matchInProgress());
        refereeSite.declareMatchWinner(logger);
        logger.closeFile();
    }







}
