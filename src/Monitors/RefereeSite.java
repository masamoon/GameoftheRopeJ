package Monitors;

import Logging.Logger;
import States.RefereeState;

/**
 * Created by jonnybel on 3/8/16.
 */
public class RefereeSite {

    private Global global;
    private Logger logger;

    public RefereeSite( Global global, Logger logger){

        this.global = global;
        this.logger = logger;
    }
    /*
    *   Referee Operations
     */

    /**
     * Announces new game
     */
    public synchronized void announceGame(){
        //System.out.println("RefereeState: START_OF_A_GAME");
        global.setRefereeState(RefereeState.START_OF_A_GAME,logger);


        global.incrementGamesNum();

        global.setFlagPos(0);
        global.resetTrialNum();
    }

    public synchronized void announceMatch(){
       // System.out.println("RefereeState: START_OF_THE_MATCH");
        global.setRefereeState(RefereeState.START_OF_THE_MATCH,logger);

    }



    /**
     * Checks what team is the game's winner
     * @param logger
     */
    public synchronized void declareGameWinner (Logger logger){
        if(global.getTrialNum() >= 6){
            if(global.getFlagPos() < 0){
                //team1 winner
                global.incGamescore_t1();
                logger.gameWinnerLinePoints(global.getGamescore_t1()+global.getGamescore_t2(),1,global.getTrialNum());
                System.out.println("TEAM 1 WINNER BY POINTS");
            }
            else if(global.getFlagPos() > 0){
                //team 2 winner
                global.incGamescore_t2();
                logger.gameWinnerLinePoints(global.getGamescore_t1()+global.getGamescore_t2(),2,global.getTrialNum());
                System.out.println("TEAM 2 WINNER BY POINTS");
            }
            else{
                //tie
                global.incGamescore_t1();
                global.incGamescore_t2();
                logger.gameTieLine();
                System.out.println("'TIS A TIE, FOLKS!");
            }
        }
        else{
            if(global.getFlagPos() <= -4){
                //team1 knockout
                global.incGamescore_t1();
                logger.gameWinnerLineKO(global.getGamescore_t1()+global.getGamescore_t2(),1,global.getTrialNum());
                System.out.println("TEAM 1 WINS BY GENOCIDE");
            }
            else if(global.getFlagPos() >= 4){
                //team2 knockout
                global.incGamescore_t2();
                logger.gameWinnerLineKO(global.getGamescore_t1()+global.getGamescore_t2(),2,global.getTrialNum());
                System.out.println("TEAM 2 WINS BY TOTAL ANIHILATION");
            }
        }
    }

    /**
     * Checks what team is the match's winner
     */
    public synchronized void declareMatchWinner (Logger logger){
        if(global.getGamescore_t1()> global.getGamescore_t2()){
            //team1 takes match
            logger.matchWinnerLine(global.getGamescore_t1(),global.getGamescore_t2(),"team1");
            System.out.println("MATCH WINNER: TEAM 1");
        }
        else if(global.getGamescore_t1() < global.getGamescore_t2()){
            //team2 takes match
            logger.matchWinnerLine(global.getGamescore_t1(),global.getGamescore_t2(),"team2");
            System.out.println("MATCH WINNER: TEAM 2");
        }
        else{
            //draw
            logger.matchTieLine();
            System.out.println("HOLY SHIT NO ONE FUCKING WINS LOL!");
        }
    }

}
