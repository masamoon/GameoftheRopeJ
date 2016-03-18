package Monitors;

import Entities.Contestant;
import States.CoachState;
import States.ContestantState;
import States.RefereeState;

import java.util.Random;

/**
 * Created by jonnybel on 3/8/16.
 */
public class Bench {


    private Contestant[] team1;
    private Contestant[] team2;

    /*
    *   COACH OPERATIONS
    */


    /**
     *  call contestants to the rope
     *  @param selection chosen contestants to pull rope
     *
     */
    public synchronized void callContestants (int [] selection, Global global){
        for(int id: selection){
            global.setContestantState(id,ContestantState.STAND_IN_POSITION);
        }
    }

   /**
    * execute the selection of the next contestant's to pull the rope
    * @param teamID team's id
    * @return selected contestants
    */
    public int [] selectContestants(int teamID){
        /* todo: algorithm of selection is used here */
        Random r = new Random();
        int first = r.nextInt(4);
        int second;
        do {
            second = r.nextInt(4);
        }while(second == first);

        int third;

        do{
           third = r.nextInt(4);
        }while( third == first || third == second);


        int team [] = {first,second,third};


        return team;
    }


    /**
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
