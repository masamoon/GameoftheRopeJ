package Monitors;

/**
 * Created by jonnybel on 3/8/16.
 */
public class RefereeSite {

    /*
    *   Referee Operation
     */


    public synchronized void announceGame(){}

    /*
     *   Referee Operation
     */
    public synchronized void callTrial(){}


    public synchronized void announceMatch(){}


    public synchronized void declareGameWinner (Playground playground){
        if(playground.getTrial_no() >= 6){
            if(playground.getFlagPos() > 0){
                //team1 winner
            }
            else if(playground.getFlagPos() < 0){
                //team 2 winner
            }
            else{
                //tie
            }
        }
        else{
            if(playground.getFlagPos() >= 4){
                //team1 knockout
            }
            else if(playground.getFlagPos() <= -4){
                //team2 knockout
            }
        }
    }

    public synchronized void declareMatchWinner (Global global){
        if(global.getGamescore_t1()> global.getGamescore_t2()){
            //team1 takes match
        }
        else if(global.getGamescore_t1() < global.getGamescore_t2()){
            //team2 takes match
        }
        else{
            //draw
        }
    }

}
