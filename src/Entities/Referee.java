package Entities;

import Logging.Logger;
import Monitors.Global;
import Monitors.Playground;
import Monitors.RefereeSite;
import States.RefereeState;

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

    /**
     *sets Referee State to new state
     * @param state new Referee State
     */
    public void setRefstate(RefereeState state){
        refstate = state;
    }





}
