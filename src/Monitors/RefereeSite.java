package Monitors;

import Logging.Logger;

/**
 * Created by jonnybel on 3/8/16.
 */
public class RefereeSite {

    /*
    *   Referee Operations
     */

    /**
     * Announces new game
     */
    public synchronized void announceGame(){}

    /**
     * Calls new Trial
     */
    public synchronized void callTrial(){}

    /**
     * Announces new match
     */
    public synchronized void announceMatch(){}


    /**
     * Checks what team is the game's winner
     * @param playground reference to the playground
     */
    public synchronized void declareGameWinner (Playground playground, Global global,Logger logger){
        if(playground.getTrial_no() >= 6){
            if(playground.getFlagPos() > 0){
                //team1 winner
                global.incGamescore_t1();
                logger.gameWinnerLinePoints(global.getGamescore_t1()+global.getGamescore_t2(),1,playground.getTrial_no());
            }
            else if(playground.getFlagPos() < 0){
                //team 2 winner
                global.incGamescore_t2();
                logger.gameWinnerLinePoints(global.getGamescore_t1()+global.getGamescore_t2(),2,playground.getTrial_no());
            }
            else{
                //tie
                global.incGamescore_t1();
                global.incGamescore_t2();
                logger.gameTieLine();
            }
        }
        else{
            if(playground.getFlagPos() >= 4){
                //team1 knockout
                global.incGamescore_t1();
                logger.gameWinnerLineKO(global.getGamescore_t1()+global.getGamescore_t2(),1,playground.getTrial_no());
            }
            else if(playground.getFlagPos() <= -4){
                //team2 knockout
                global.incGamescore_t2();
                logger.gameWinnerLineKO(global.getGamescore_t1()+global.getGamescore_t2(),2,playground.getTrial_no());
            }
        }
    }

    /**
     * Checks what team is the match's winner
     * @param global reference to the global repository
     */
    public synchronized void declareMatchWinner (Global global, Logger logger){
        if(global.getGamescore_t1()> global.getGamescore_t2()){
            //team1 takes match
            logger.matchWinnerLine(global.getGamescore_t1(),global.getGamescore_t2(),"team1");
        }
        else if(global.getGamescore_t1() < global.getGamescore_t2()){
            //team2 takes match
            logger.matchWinnerLine(global.getGamescore_t1(),global.getGamescore_t2(),"team2");
        }
        else{
            //draw
            logger.matchTieLine();
        }
    }

}
