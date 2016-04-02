package Nondistributedsolution.Entities;

import Nondistributedsolution.Monitors.Bench;
import Nondistributedsolution.Monitors.Global;
import Nondistributedsolution.Monitors.Playground;

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
     * Contestant Object Constructor
     * @param contestantID
     * @param teamID
     * @param benchMon
     * @param playgroundMon
     * @param global
     */
    public Contestant(int contestantID, int teamID,  Bench benchMon, Playground playgroundMon, Global global) {
        this.contestantID = contestantID;
        this.teamID = teamID;
        this.benchMon = benchMon;
        this.playgroundMon = playgroundMon;
        this.global = global;
    }

    /** Life Cycle of the Contestant Thread
     */
    @Override
    public void run() {

        while(global.matchInProgress()){

            benchMon.sitDown(contestantID, teamID, playgroundMon);

            if(global.matchInProgress()){
                playgroundMon.followCoachAdvice(contestantID, teamID); //// TODO: 02/04/2016 verify the location of operations

                playgroundMon.getReady(contestantID, teamID);
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


}
