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
    /*
        call contestants to the rope
        @param selection chosen contestants to pull rope

     */
    public synchronized void callContestants (int [] selection){

    }

    /*
    * execute the selection of the next contestant's to pull the rope
    * @param teamID team's id
    * @return selected contestants
     */
    public int [] selectContestants(int teamID){
        /* todo: algorithm of selection is used here */
        int team [] = {1,2,3};
        return team;
    }


    /*
        Contestant Operations
        @param teamID team's ID
        @param contestantID contestant's ID
        @param global reference to global repository
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
