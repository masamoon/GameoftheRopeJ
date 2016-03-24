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


    //private Contestant [] team1;
    //private Contestant [] team2;
    private int[] team1;
    private int[] team2;


    private Global global;

    private int contestantsSitting;



    public Bench(Global global){
       /* this.team1 = new Contestant[5];
        this.team2 = new Contestant[5];*/

        this.team1 = new int[5];
        this.team2 = new int[5];

        this.global = global;
    }

    /*
    *   COACH OPERATIONS
    */


    /**
     *  call contestants to the rope
     *  coach changes the state of the selected contestants to SELECTED
     *  coach wakes the contestants
     *
     *  @param selection chosen contestants to pull rope
     *
     */
    public synchronized void callContestants (int teamID, int [] selection){
        for(int id: selection){
            System.out.println("coach "+ teamID + "calling: "+id);
            global.setContestantState(teamID, id, ContestantState.SELECTED);
        }
        notifyAll();

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

        System.out.println("coach " + teamID + " selected: "+first+second+third);
        return team;
    }


    /**
        Contestant Operations
     */


    /**
        sitDown:
        the contestant's state is changed to SIT_AT_THE_BENCH and he waits until he is called by the coach

        @param teamID team ID of both the contestant and his coach
        @param contestantID contestant's ID
        @param global reference to global repository
     */
    public synchronized void sitDown(int contestantID, int teamID, Global global) {

        global.setContestantState(teamID, contestantID, ContestantState.SIT_AT_THE_BENCH);
        System.out.println("Contestant "+contestantID+" from team "+teamID+" sitting down");
        global.incrementSittingAtBench(teamID);

        do{
            try
                { System.out.println("Contestant "+contestantID+" from team "+teamID+" waiting");
                    wait ();
                }
            catch (InterruptedException e) {}
            System.out.println("Contestant "+contestantID+" from team "+teamID+" was woken up!");
        }while(global.getContestantState(contestantID, teamID) != ContestantState.SELECTED);
        System.out.println("Contestant "+contestantID+" from team "+teamID+": exited the waiting cycle and standing up because im selected");

        global.decrementSittingAtBench(teamID);

    }


    /**
     * decrements the strength of a contestant
     * @param teamID
     * @param contestantID
     */
    public void tireOut(int teamID, int contestantID){


    }

    /**
     * increments the strength of a contestant
     * @param teamID
     * @param contestantID
     */
    public void restUp(int teamID, int contestantID){

    }



}
