package Monitors;

import Logging.Logger;
import States.ContestantState;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.Random;

/**
 * Created by jonnybel on 3/8/16.
 */
public class Bench {

    private Global global;

    private int numSitting;

    private Logger logger;



    public Bench(Global global, Logger logger){

        this.global = global;
        this.numSitting=0;
        this.logger = logger;
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

        global.setContestantState(contestantID, teamID, ContestantState.SIT_AT_THE_BENCH,logger);
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

        System.out.println(imSelected(contestantID, teamID) + " " + global.benchCalled(teamID));
        while (!global.benchCalled(teamID) || !imSelected(contestantID, teamID)){
            try
            { System.out.println("Contestant "+contestantID+" from team "+teamID+" waiting");
                System.out.println(imSelected(contestantID, teamID) + " " + global.benchCalled(teamID));

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
        int strategy = r.nextInt(2);

        int team[];
        System.out.println("ROLLED:"+strategy);
        if(strategy == 0)
            team = selectRandom(teamID);
        else
            team = selectTopteam(teamID);

        global.selectTeam(teamID, team[0],team[1],team[2]);

        global.setBenchCalled(teamID, true);
        System.out.println("coach " + teamID + " selected: "+team[0]+team[1]+team[2]);

        notifyAll();

    }

    public int[] selectRandom(int teamID){
        Random r = new Random();
        int first = r.nextInt(5);
        int second;
        do {
            second = r.nextInt(5);
        }while(second == first);

        int third;

        do{
            third = r.nextInt(5);
        }while( third == first || third == second);

        return new int[]{first,second,third};
    }


    public int[] selectTopteam(int teamID){
        int[] str;
        if(teamID ==0)
            str=global.getStrength_t1();
        else
            str = global.getStrength_t2();
        int[] tmp = new int[5];
        tmp = Arrays.copyOf(str,5);

        HashMap<Integer,Integer> map = new HashMap<Integer,Integer>();

        for (int i = 0; i <5 ; i++) {

            map.put(i,str[i]);

        }

        int first = Collections.max(map.entrySet(), (entry1, entry2) -> entry1.getValue() > entry2.getValue() ? 1 : -1).getKey();
        map.remove(first);
        int second = Collections.max(map.entrySet(), (entry1, entry2) -> entry1.getValue() > entry2.getValue() ? 1 : -1).getKey();
        map.remove(second);
        int third = Collections.max(map.entrySet(), (entry1, entry2) -> entry1.getValue() > entry2.getValue() ? 1 : -1).getKey();

        return new int[]{first,second,third};
    }


}
