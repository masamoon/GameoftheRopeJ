package serverSide.refereeSite;

import common.VectorClock;
import interfaces.GlobalInterface;
import common.entityStates.RefereeState;

import java.rmi.RemoteException;


public class RefereeSite {
    private final int NUMBER_OF_TEAMS = 2;
    private final int NUMBER_OF_PLAYERS_PER_TEAM = 5;

    private GlobalInterface global;

    private int trialNum;

    private boolean readyForTrial;

    private int gamesNum;

    private int [] teamScore;




    public RefereeSite(GlobalInterface global) {

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
     * RefereeThread Announces new game
     */
    public synchronized void announceGame(VectorClock vc) throws RemoteException{


        gamesNum++;
        global.incrementGamesNum();

        global.resetFlagPos();

        trialNum = 0;
        global.resetTrialNum();

        global.setRefereeState(vc, RefereeState.START_OF_A_GAME);

        //global.newGame(vc);
    }

    /**
     * RefereeThread Announces a new Match
     */
    public synchronized void announceMatch(VectorClock vc) throws RemoteException{
        global.setRefereeState(vc, RefereeState.START_OF_THE_MATCH);
    }

    /**
     *   The last contestant to sit wakes the Ref
     */
    public synchronized void benchWakeRef(){

        notifyAll();
    }

    public synchronized void waitForBench() throws RemoteException{
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
    public synchronized void declareGameWinner (VectorClock vc) throws RemoteException{

        global.setRefereeState(vc, RefereeState.END_OF_A_GAME);

        int flagPos = global.getFlagPos();

        if(trialNum>= 6){
            if(flagPos < 0){
                //team1 winner
                teamScore[0]++;
                global.gameWinnerLinePoints(vc, 1);
            }
            else if(flagPos > 0){
                //team 2 winner
                teamScore[1]++;
                global.gameWinnerLinePoints(vc, 2);
            }
            else{
                //tie
                global.gameTieLine(vc);
            }
        }
        else{
            if(flagPos <= -4){
                //team1 knockout
                teamScore[0]++;
                global.gameWinnerLineKO(vc, 1);
            }
            else if(flagPos >= 4){
                //team2 knockout
                teamScore[1]++;
                global.gameWinnerLineKO(vc, 2);
            }
        }
    }

    /**
     * Checks what team is the match's winner
     */
    public synchronized void declareMatchWinner (VectorClock vc) throws RemoteException{
        //((RefereeThread)Thread.currentThread()).setRefereeState(RefereeState.END_OF_THE_MATCH);
        global.setRefereeState(vc, RefereeState.END_OF_THE_MATCH);

        if(teamScore[0]> teamScore[1]){
            //team1 takes match
            global.matchWinnerLine(vc, teamScore[0],teamScore[1],1);
        }
        else if(teamScore[0] < teamScore[1]){
            //team2 takes match
            global.matchWinnerLine(vc, teamScore[0],teamScore[1],2);
        }
        else{
            //draw
            global.matchTieLine(vc);
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
