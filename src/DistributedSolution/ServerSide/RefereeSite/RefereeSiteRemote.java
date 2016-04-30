package DistributedSolution.ServerSide.RefereeSite;

import DistributedSolution.ClientSide.Referee.RefereeState;
import DistributedSolution.ServerSide.Bench.BenchRemote;
import DistributedSolution.ServerSide.Global.GlobalRemote;


/**
 * Created by jonnybel on 3/8/16.
 */
public class RefereeSiteRemote {

    private RefereeSiteGlobalStub global;

    private int trialNum;

    private boolean readyForTrial;



    public RefereeSiteRemote(RefereeSiteGlobalStub global) {

        this.global = global;
        this.readyForTrial = false;
    }
    /**
     * Referee Announces new game
     */
    public synchronized void announceGame(){
        global.incrementGamesNum();

        global.resetFlagPos();
        global.resetTrialNum();
    }

    /**
     * Referee Announces a new Match
     */
    public synchronized void announceMatch(){
        global.setRefereeState(RefereeState.START_OF_THE_MATCH);
    }

    /**
     *   The last contestant to sit wakes the Ref
     */
    public synchronized void benchWakeRef(){

        notifyAll();
    }

    public synchronized void waitForBench(){
        global.incrementTrialNum();
        System.out.println(global.getTrialNum());

        while(!readyForTrial){
            try {
                wait();
            } catch (InterruptedException e) {}
        }
    }

    /**
     * Checks what team is the game's winner
     */
    public synchronized void declareGameWinner (){
        int flagPos = global.getFlagPos();
        if(global.getTrialNum() >= 6){
            if(flagPos < 0){
                //team1 winner
                global.incTeamScore(0);
                global.gameWinnerLinePoints(global.getTeamScore(0)+global.getTeamScore(1),1,global.getTrialNum());
            }
            else if(flagPos > 0){
                //team 2 winner
                global.incTeamScore(1);
                global.gameWinnerLinePoints(global.getTeamScore(0)+global.getTeamScore(1),2,global.getTrialNum());
            }
            else{
                //tie
                global.incTeamScore(0);
                global.incTeamScore(1);
                global.gameTieLine();
            }
        }
        else{
            if(flagPos <= -4){
                //team1 knockout
                global.incTeamScore(0);
                global.gameWinnerLineKO(global.getTeamScore(0)+global.getTeamScore(1),1,global.getTrialNum());
            }
            else if(flagPos >= 4){
                //team2 knockout
                global.incTeamScore(1);
                global.gameWinnerLineKO(global.getTeamScore(0)+global.getTeamScore(1),2,global.getTrialNum());
            }
        }
    }

    /**
     * Checks what team is the match's winner
     */
    public synchronized void declareMatchWinner (){
        if(global.getTeamScore(0)> global.getTeamScore(1)){
            //team1 takes match
            global.matchWinnerLine(global.getTeamScore(0),global.getTeamScore(1),1);
        }
        else if(global.getTeamScore(0) < global.getTeamScore(1)){
            //team2 takes match
            global.matchWinnerLine(global.getTeamScore(0),global.getTeamScore(1),2);
        }
        else{
            //draw
            global.matchTieLine();
        }
    }

    public synchronized void setReadyForTrial(boolean readyForTrial) {
        this.readyForTrial = readyForTrial;
    }

    public int getTrialNum() {
        return trialNum;
    }

    public void setTrialNum(int trialNum) {
        this.trialNum = trialNum;
    }
}
