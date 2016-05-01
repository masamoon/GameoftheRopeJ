package Nondistributedsolution.Contestant;

import Nondistributedsolution.Monitors.Bench;
import Nondistributedsolution.Monitors.Global;
import Nondistributedsolution.Monitors.Playground;
import Nondistributedsolution.Monitors.RefereeSite;

import java.util.Random;

/**
 * Created by jonnybel on 3/8/16.
 */

public class Contestant extends Thread {


    /**
     * ID of this Contestant
     */
    private int contestantID;

    /**
     * Team ID of this contestant: either 0 (team1) or 1 (team2);
     */
    private int teamID;

    /**
     * BenchRemote Monitor Object
     */
    private Bench benchMon;

    /**
     *  PlaygroundRemote Monitor Object
     */
    private Playground playgroundMon;

    /**
     * General Information Repository Object
     */
    private Global global;

    /**
     * Strength level of this Contestant
     */
    private int strength;

    /**
     * Current State of this Contestant
     */
    private ContestantState contestantState;


    /**
     * Contestant Object Constructor
     * @param contestantID
     * @param teamID
     * @param benchMon
     * @param playgroundMon
     * @param global
     */
    public Contestant(int contestantID, int teamID,  Bench benchMon, Playground playgroundMon , Global global) {
        this.contestantID = contestantID;
        this.teamID = teamID;
        this.benchMon = benchMon;
        this.playgroundMon = playgroundMon;
        this.global = global;
        this.contestantState = ContestantState.INIT;

        Random r = new Random();
        this.strength = r.nextInt(6) + 5;
    }

    /** Life Cycle of the Contestant Thread
     */
    @Override
    public void run() {
        // initialize contestant strength at the shared zones
        benchMon.setStrength(contestantID, teamID, strength);
        global.setStrength(contestantID,teamID,strength);
        while(global.matchInProgress()){

            benchMon.sitDown(contestantID, teamID);


            if(global.matchInProgress()){

                playgroundMon.followCoachAdvice(contestantID, teamID);

                playgroundMon.getReady(contestantID, teamID, strength);
                pullRope();
                playgroundMon.done(contestantID, teamID);

            }
        }
    }

    /**
     *  Internal function for the Contestant thread where he pulls the rope, sleeping for a randomly generated time interval.
     */
    private void pullRope ()
    {
        try
        { sleep ((long) (1 + 10 * Math.random ()));
        }
        catch (InterruptedException e) {}
    }

    public int getStrength() {
        return strength;
    }

    public void setStrength(int strength){
        this.strength = strength;
    }

    public void setContestantState(ContestantState contestantState) {
        this.contestantState = contestantState;
    }
}
