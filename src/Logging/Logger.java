package Logging;

import Monitors.Global;
import Monitors.Playground;

/**
 * Created by Andre on 18/03/2016.
 */
public class Logger {



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


        String rope_pos;
        String trial_no;



    }
}
