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
     @param playground reference to playground
     */
    public synchronized void sitDown(int contestantID, int teamID, Global global, Playground playground) {

        global.setContestantState(contestantID, teamID, ContestantState.SIT_AT_THE_BENCH,logger);
        this.numSitting++;

        if(numSitting==10){
            global.setBenchReady(true);
            playground.benchWakeRef();
        }
        while ((!global.benchCalled(teamID) || !imSelected(contestantID, teamID)) && global.matchInProgress()){
            try
            {
                wait ();
            }
            catch (InterruptedException e) {}
        }

        this.numSitting--;

        global.setBenchReady(false);

    }

    /**
     * Checks if contestant is selected for trial
     * @param contestantID contestant's id
     * @param teamID team's id
     * @return true if selected
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
     * @param teamID team's id
     *
     */
    public synchronized void callContestants (int teamID){

        Random r = new Random();
        int strategy = r.nextInt(2);

        int team[];
        //System.out.println("Coach from team " + teamID + " rolled the strategy: "+strategy);
        if(strategy == 0)
            team = selectRandom();
        else
            team = selectTopteam(teamID);

        global.selectTeam(teamID, team[0],team[1],team[2]);

        //System.out.println("Coach " + teamID + "picked:" + team[0]+team[1]+team[2]);
        global.setBenchCalled(teamID, true);

        notifyAll();

    }

    /**
     *  wakes all the contestants: this is only used at the match to free the contestants that staying in the bench.
     */
    public synchronized void wakeContestants(){
        notifyAll();
    }

    /**
     * team building strategy that consists on randomly choosing 3 team elements
     * @return array containing the selected team for trial
     */
    public int[] selectRandom(){
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

    /**
     * team building strategy that consists on choosing the 3 elements with more strength left
     * @param teamID team's id
     * @return array containing the selected team for trial
     */
    public int[] selectTopteam(int teamID){
        int[] str;
        if(teamID ==0)
            str=global.getStrength_t1();
        else
            str = global.getStrength_t2();

        HashMap<Integer,Integer> map = new HashMap<>();

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
