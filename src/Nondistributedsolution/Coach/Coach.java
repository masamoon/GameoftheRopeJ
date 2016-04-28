package Nondistributedsolution.Coach;

import Nondistributedsolution.Monitors.Global;
import Nondistributedsolution.Monitors.Playground;
import Nondistributedsolution.Monitors.Bench;

/**
 * Created by jonnybel on 3/8/16.
 */
public class Coach extends Thread{

    /**
     * Team ID of this Coach: either 0 (team1) or 1 (team2);
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

            System.out.println("coach " + teamID + " status: reviewing");
            benchMon.reviewNotes(teamID);

            System.out.println("coach " + teamID + " status: calling");
            setCoachState(CoachState.WAIT_FOR_REFEREE_COMMAND);
            benchMon.callContestants(teamID);

            setCoachState(CoachState.ASSEMBLE_TEAM);
            System.out.println("coach " + teamID + " status: waiting");
            playgroundMon.waitForContestants(teamID);

            setCoachState(CoachState.WATCH_TRIAL);
            System.out.println("coach " + teamID + " status: informing");
            playgroundMon.informReferee(teamID);
        }
    }
    public CoachState getCoachState() {
        return coachState;
    }

    public void setCoachState(CoachState coachState) {
        this.coachState = coachState;
        global.setCoachState(teamID, coachState);
    }

}
