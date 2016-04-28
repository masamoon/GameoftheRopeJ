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
     * Bench Monitor Object
     */
    private Bench benchMon;

    /**
     *  Playground Monitor Object
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
     * Referee Site Object
     */
    private RefereeSite refereeSite;


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
    public Contestant(int contestantID, int teamID,  Bench benchMon, Playground playgroundMon, RefereeSite refereeSite, Global global) {
        this.contestantID = contestantID;
        this.teamID = teamID;
        this.benchMon = benchMon;
        this.playgroundMon = playgroundMon;
        this.global = global;
        this.refereeSite = refereeSite;
        this.contestantState = ContestantState.INIT;

        Random r = new Random();
        this.strength = r.nextInt(6) + 5;
    }

    /** Life Cycle of the Contestant Thread
     */
    @Override
    public void run() {
        // initialize contestant at the bench
        benchMon.setStrength(contestantID, teamID, strength);
        global.setStrength(contestantID,teamID,strength);
        while(global.matchInProgress()){

            setContestantState(ContestantState.SIT_AT_THE_BENCH);
            benchMon.sitDown(contestantID, teamID, refereeSite);

            if(global.matchInProgress()){

                setContestantState(ContestantState.STAND_IN_POSITION);
                playgroundMon.followCoachAdvice(contestantID, teamID);

                setContestantState(ContestantState.DO_YOUR_BEST);
                playgroundMon.getReady(teamID);
                pullRope();
                playgroundMon.done(teamID);

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

    public ContestantState getContestantState() {
        return contestantState;
    }

    public void setContestantState(ContestantState contestantState) {
        this.contestantState = contestantState;
        global.setContestantState(contestantID, teamID, contestantState);
    }
}
