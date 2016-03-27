package Logging;

import Monitors.Global;
import genclass.TextFile;

/**
 * Created by Andre on 18/03/2016.
 */
public class Logger {

    private TextFile f;
    private String path;
    private Global global;

    public Logger(String path, Global global){
        f = new TextFile();
        f.openForWriting(null,"log.txt");
        String str =  "Ref Coa1 Cont 1 Cont 2 Cont 3 Cont 4 Cont 5 Coa2 Cont 1 Cont 2 Cont 3 Cont 4 Cont 5      Trial";
        String str2 = "Sta Stat Sta SG Sta SG Sta SG Sta SG Sta SG Stat Sta SG Sta SG Sta SG Sta SG Sta SG 3 2 1 . 1 2 3 NB PS";
        f.writelnString(str);
        f.writelnString(str2);

        this.path = path;
        this.global = global;
    }



    /**
     *
     * Inserts new line into logger
     *
     */
    public synchronized void insertLine(){

        String ref_state = global.getRefereeState().getAcronym();
        String coach_state_1 = global.getCoachState(0).getAcronym();
        String coach_state_2 = global.getCoachState(1).getAcronym();

        StringBuilder team1 = new StringBuilder();
        StringBuilder team2 = new StringBuilder();

        for (int i = 0; i <= 4 ; i++) {
            team1.append(global.getContestantState(i,0).getAcronym());
            team1.append(" ");
            if(global.getStrength(i,0)<10)
                team1.append(" ");
            team1.append(global.getStrength(i,0));
            team1.append(" ");
        }

        for (int i = 0; i <= 4 ; i++) {
            team2.append(global.getContestantState(i,1).getAcronym());
            team2.append(" ");
            if(global.getStrength(i,1)<10)
                team2.append(" ");
            team2.append(global.getStrength(i,1));
            team2.append(" ");
        }


        int rope_pos = global.getFlagPos();
        int trial_no = global.getTrialNum();


        int[] sel1 = global.getSelection(0);
        int[] sel2 = global.getSelection(1);

        StringBuilder selection1 = new StringBuilder();
        StringBuilder selection2 = new StringBuilder();

        for(int sel: sel1){
            if(sel==-1){
                selection1.append("- ");
            }
            else{
                sel++;
                selection1.append(sel + " ");
            }

        }

        for(int sel: sel2){
            if(sel==-1){
                selection2.append("- ");
            }
            else{
                sel++;
                selection2.append(sel+ " ");
            }

        }

        String line = ref_state +" " + coach_state_1+" " +  team1.toString() + coach_state_2 + " "+ team2.toString()
                +selection1.toString() +". "+selection2.toString() + " " + trial_no+"  " + rope_pos;

        f.writelnString(line);

    }

    public void matchWinnerLine(int score1, int score2, int winner) {
        f.writelnString("Match was won by team " + winner + " (" + score1 + "-" + score2 + ").\n");
    }
    /**
     * writes line on log file indicating the winner team and score
     * @param score1 score of team1
     * @param score2 score of team2
     * @param winner winner team
     */
    public void matchWinnerLine(int score1, int score2, String winner){
      //  f.openForWriting(null,"log.txt");
        f.writelnString("Match was won by team "+winner+ "("+score1+"-"+score2+").");
       // f.close();
    }

    /**
     * writes line on log file indicating a draw
     */
    public void matchTieLine(){
        f.writelnString("Match was a draw.\n");
    }

    /**
     * writes line on log file indicating the game's winner
     * @param ngame game's number
     * @param nteam winner team
     * @param ntrials number of trials needed to decide the winner
     */
    public void gameWinnerLinePoints(int ngame, int nteam, int ntrials){
       f.writelnString("Game "+ngame+" was won by team "+nteam+" by points in "+ntrials+" trials.\n");
    }

    /**
     *writes line on log file indicating a knock-out win by a team
     * @param ngame game number
     * @param nteam winner team
     * @param ntrials number of trials needed to decide the winner
     */
    public void gameWinnerLineKO(int ngame, int nteam, int ntrials){
        f.writelnString("Game "+ngame+" was won by team "+nteam+" by knock-out in "+ntrials+" trials.\n");
    }

    /**
     *writes line on log file indicating a draw game
     */
    public void gameTieLine(){
        f.writelnString("Game was a draw.\n");
    }

    /**
     * closes log file
     */
    public void closeFile(){
        f.endOfFile();
        f.close();
    }
}
