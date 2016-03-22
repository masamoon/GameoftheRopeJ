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

    /* todo: add comments */

    private RefereeState refstate;
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

    /* Life Cycle of the Referee Thread
    */


    @Override
    public void run(){


        refereeSite.announceMatch(); // state -> START_OF_THE_MATCH

        // talvez seja desnecessário:
        makeArrangements(); // prepare for the beginning of the match (sleep, while the other threads get in wait states)
        do{
            refereeSite.announceGame(); // state -> START_OF_THE_GAME
            while(!global.gameFinished()){
                playground.callTrial(); //  waitingForTeamsReady();
                playground.startTrial(); // waitingForTrialEnd();
                playground.assertTrialDecision();
            }
            refereeSite.declareGameWinner (playground,logger);

        }while(!global.matchFinished());
        refereeSite.declareMatchWinner(logger);




    }

    public void setRefstate(RefereeState state){
        refstate = state;
    }

    private void makeArrangements(){
        try
        {
            sleep ((long) (1 + 10 * Math.random ()));
        }
        catch (InterruptedException e) {}
    }



}
