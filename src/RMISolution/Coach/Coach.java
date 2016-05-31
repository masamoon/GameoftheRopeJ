package RMISolution.Coach;

import RMISolution.Bench.Bench;
import RMISolution.Global.Global;
import RMISolution.Playground.Playground;

/**
 * Created by jonnybel on 5/31/16.
 */
public class Coach extends Thread {

    /**
     * Team ID of this Coach: either 0 (team1) or 1 (team2);
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
     * Current state of this Coach
     */
    private CoachState coachState;

    /**
     * Coach object Constructor
     * @param teamID
     * @param benchMon
     * @param playgroundMon
     * @param global
     */
    public Coach(int teamID, Bench benchMon, Playground playgroundMon, Global global) {
        this.teamID = teamID;
        this.benchMon = benchMon;
        this.playgroundMon = playgroundMon;
        this.global = global;
        this.coachState = CoachState.INIT;
    }

    /**
     *  Coach Thread life cycle.
     */
    @Override
    public void run()
    {
        while(global.matchInProgress()){

            //System.out.println("coach " + teamID + " status: reviewing");
            benchMon.reviewNotes(teamID);

            System.out.println("coach " + teamID + " status: calling");
            benchMon.callContestants(teamID);

            System.out.println("coach " + teamID + " status: waiting");
            playgroundMon.waitForContestants(teamID);

            System.out.println("coach " + teamID + " status: informing");
            playgroundMon.informReferee(teamID);
        }
    }
    public void setCoachState(CoachState coachState) {
        this.coachState = coachState;
    }
}
