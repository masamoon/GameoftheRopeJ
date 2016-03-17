package Monitors;

import States.CoachState;
import States.ContestantState;
import States.RefereeState;

/**
 * Created by jonnybel on 3/8/16.
 */
public class Bench {

    /*
        Coach Operations
     */
    public synchronized void callContestants (int [] selection){

    }

    public int [] selectContestants(int teamID){
        /* todo: algorithm of selection is used here */
        int team [] = {1,2,3};
        return team;
    }


    /*
        Contestant Operations
     */
    public synchronized void sitDown(int teamID, int contestantID, Global global) {

        global.setContestantState(contestantID, ContestantState.SIT_AT_THE_BENCH);

        while(global.getCoachState(teamID) != CoachState.ASSEMBLE_TEAM)
        { try
            { wait ();
            }
        catch (InterruptedException e) {}
        }
    }



}
