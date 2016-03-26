package Monitors;

import States.ContestantState;

import java.util.Random;

/**
 * Created by jonnybel on 3/8/16.
 */
public class Bench {

    private Global global;

    private int numSitting;



    public Bench(Global global){

        this.global = global;
        this.numSitting=0;
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
    public synchronized void sitDown(int contestantID, int teamID, Global global, Playground playground) {

        global.setContestantState(contestantID, teamID, ContestantState.SIT_AT_THE_BENCH);
        System.out.println("Contestant "+contestantID+" from team "+teamID+" sitting down");
        //global.incrementSittingAtBench(teamID);
        this.numSitting++;
        System.out.println("Sitting on bench: "+ numSitting);

        //if(global.getSittingAtBench(teamID)==5){
        if(numSitting==10){
            // this is in case a contestant is sitting after the coach began waiting for the next trial
            System.out.println("*** Last man Sitting***");
            global.setBenchReady(true);
            playground.benchWakeRef();
        }

        System.out.println(imSelected(contestantID, teamID) + " " + global.benchCalled());
        while (!global.benchCalled() || !imSelected(contestantID, teamID)){
            try
            { System.out.println("Contestant "+contestantID+" from team "+teamID+" waiting");
                System.out.println(imSelected(contestantID, teamID) + " " + global.benchCalled());

                wait ();
            }
            catch (InterruptedException e) {}
            System.out.println("Contestant "+contestantID+" from team "+teamID+" was woken up!");
        }
        System.out.println("Contestant " + contestantID + " from team "+teamID+": I'm Selected!! Standing up...");

        //global.decrementSittingAtBench(teamID);
        this.numSitting--;

        System.out.println("Sitting on bench: "+ numSitting);
        global.setBenchReady(false);

    }

    /**
     * Checks if contestant is selected for trial
     * @param contestantID
     * @param teamID
     * @return
     */
    private boolean imSelected (int contestantID, int teamID)
    {
        for(int id : global.getSelection(teamID)){
            if(contestantID == id)
                return true;
        }
        return false;
    }

    /** COACH
     *  select and call contestants to the rope
     *  coach changes the state of the selected contestants to SELECTED
     *  coach wakes the contestants
     * @param teamID
     *
     */
    public synchronized void callContestants (int teamID){
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

        global.selectTeam(teamID, first,second,third);

        global.setBenchCalled(true);
        System.out.println("coach " + teamID + " selected: "+first+second+third);

        notifyAll();

    }


}
