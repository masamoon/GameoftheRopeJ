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

    public Logger(String path){
        f = new TextFile();
        f.openForWriting(path,"log");
        this.path = path;
    }

    public void firstLine(){

        String str =  "Ref Coa 1 Cont 1 Cont 2 Cont 3 Cont 4 Cont 5 Coa 2 Cont 1 Cont 2 Cont 3 Cont 4 Cont 5 Trial";
        String str2 = "Sta Stat Sta SG Sta SG Sta SG Sta SG Sta SG Stat Sta SG Sta SG Sta SG Sta SG Sta SG 3 2 1 . 1 2 3 NB PS";
    }

    public void insertLine(Global global, Playground playground){

        String ref_state = global.getRefereeState().toString();
        String coach_state_1 = global.getCoachState(0).toString();
        String coach_state_2 = global.getCoachState(1).toString();

        StringBuilder team1 = new StringBuilder();
        StringBuilder team2 = new StringBuilder();

        for (int i = 0; i <= 4 ; i++) {
            team1.append(global.getContestantState(0,i));
        }

        for (int i = 0; i <= 4 ; i++) {
            team2.append(global.getContestantState(1,i));
        }


        int rope_pos = playground.getFlagPos();
        int trial_no = playground.getTrial_no();

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

        String line = ref_state + coach_state_1 + team1.toString() + coach_state_2 + team2.toString() + t1_str_score.toString() + t2_str_score.toString() + trial_no + rope_pos;

       f.writelnString(line);



    }
}
