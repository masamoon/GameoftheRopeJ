package Logging;

import Monitors.Global;
import Monitors.Playground;
import genclass.TextFile;
import genclass.TextFile.*;

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
        String str =  "Ref Coa1 Cont 1 Cont 2 Cont 3 Cont 4 Cont 5 Coa2 Cont 1 Cont 2  Cont 3 Cont 4 Cont 5 Trial";
        String str2 = "Sta Stat Sta SG Sta SG Sta SG Sta SG Sta SG Stat Sta SG Sta SG Sta SG Sta SG Sta SG 3 2 1 . 1 2 3 NB PS";
        f.writelnString(str);
        f.writelnString(str2);

        this.path = path;
        this.global = global;
    }



    /**
     *
     *
     *
     */
    public synchronized void insertLine(){

       // f.openForWriting(null,"log.txt");
        String ref_state = global.getRefereeState().getAcronym();
        String coach_state_1 = global.getCoachState(0).getAcronym();
        String coach_state_2 = global.getCoachState(1).getAcronym();

        StringBuilder team1 = new StringBuilder();
        StringBuilder team2 = new StringBuilder();

        for (int i = 0; i <= 4 ; i++) {
            team1.append(global.getContestantState(0,i).getAcronym());
        }

        for (int i = 0; i <= 4 ; i++) {
            team2.append(global.getContestantState(1,i).getAcronym());
        }


        int rope_pos = global.getFlagPos();
        int trial_no = global.getTrial_no();

        int team1_score = global.getGamescore_t1();
        int team2_score = global.getGamescore_t2();




        StringBuilder t1_str_score = new StringBuilder();
        StringBuilder t2_str_score = new StringBuilder();

        for (int i = 0; i < team1_score ; i++) {
            t1_str_score.append("*");
        }

        for (int i = 0; i < team2_score ; i++) {
            t2_str_score.append("*");
        }

        String line = ref_state +" " + coach_state_1+" " +  team1.toString()+" " + coach_state_2 + team2.toString()+" " + t1_str_score.toString()+" " + t2_str_score.toString()+" " + trial_no+" " + rope_pos;

       f.writelnString(line);



    }

    public void matchWinnerLine(int score1, int score2, String winner){
      //  f.openForWriting(null,"log.txt");
        f.writelnString("Match was won by team "+winner+ "("+score1+"-"+score2+").");
       // f.close();
    }

    public void matchTieLine(){
      //  f.openForWriting(null,"log.txt");
        f.writelnString("match was a draw");
      //  f.close();
    }

    public void gameWinnerLinePoints(int ngame, int nteam, int ntrials){
      //  f.openForWriting(null,"log.txt");
       f.writelnString("Game "+ngame+" was won by team "+nteam+" by points in "+ntrials+" trials");
     //  f.close();
    }

    public void gameWinnerLineKO(int ngame, int nteam, int ntrials){
      //  f.openForWriting(null,"log.txt");
        f.writelnString("Game "+ngame+" was won by team "+nteam+" by knock-out in "+ntrials+" trials");
      //  f.close();
    }

    public void gameTieLine(){
     //   f.openForWriting(null,"log.txt");
        f.writelnString("game was a draw");
       // f.close();
    }

    public void closeFile(){
        f.endOfFile();
        f.close();
    }
}
