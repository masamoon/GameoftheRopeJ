package Nondistributedsolution.Monitors;

import Nondistributedsolution.Referee.Referee;
import Nondistributedsolution.Referee.RefereeState;

/**
 * Created by jonnybel on 3/8/16.
 */
public class RefereeSite {

    private final int NUMBER_OF_TEAMS = 2;
    private final int NUMBER_OF_PLAYERS_PER_TEAM = 5;

    private Global global;

    private int trialNum;

    private boolean readyForTrial;

    private int gamesNum;

    private int [] teamScore;




    public RefereeSite(Global global) {

        this.global = global;
        this.readyForTrial = false;

        this.trialNum = 0;
        this.gamesNum = 0;

        teamScore = new int [NUMBER_OF_TEAMS];
        for(int i : teamScore) {
            teamScore [i] = 0;
        }
    }
    /**
     * Referee Announces new game
     */
    public synchronized void announceGame(){

        ((Referee)Thread.currentThread()).setRefereeState(RefereeState.START_OF_A_GAME);
        global.setRefereeState(RefereeState.START_OF_A_GAME);

        gamesNum++;
        global.incrementGamesNum();

        global.resetFlagPos();

        trialNum = 0;
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
        trialNum++;
        global.incrementTrialNum();
        System.out.println(trialNum);

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

        ((Referee)Thread.currentThread()).setRefereeState(RefereeState.END_OF_A_GAME);
        global.setRefereeState(RefereeState.END_OF_A_GAME);

        int flagPos = global.getFlagPos();

        if(trialNum>= 6){
            if(flagPos < 0){
                //team1 winner
                teamScore[0]++;
                global.gameWinnerLinePoints(1);
            }
            else if(flagPos > 0){
                //team 2 winner
                teamScore[1]++;
                global.gameWinnerLinePoints(2);
            }
            else{
                //tie
                global.gameTieLine();
            }
        }
        else{
            if(flagPos <= -4){
                //team1 knockout
                teamScore[0]++;
                global.gameWinnerLineKO(1);
            }
            else if(flagPos >= 4){
                //team2 knockout
                teamScore[1]++;
                global.gameWinnerLineKO(2);
            }
        }
    }

    /**
     * Checks what team is the match's winner
     */
    public synchronized void declareMatchWinner (){
        ((Referee)Thread.currentThread()).setRefereeState(RefereeState.END_OF_THE_MATCH);
        global.setRefereeState(RefereeState.END_OF_THE_MATCH);

        if(teamScore[0]> teamScore[1]){
            //team1 takes match
            global.matchWinnerLine(teamScore[0],teamScore[1],1);
        }
        else if(teamScore[0] < teamScore[1]){
            //team2 takes match
            global.matchWinnerLine(teamScore[1],teamScore[1],2);
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


    /**
     * Gets the number of games played
     * @return number of games
     */
    public int getGamesNum() {
        return gamesNum;
    }
}
