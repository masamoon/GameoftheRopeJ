package Nondistributedsolution.Entities;

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
    }

    /**
     *  Coach Thread life cycle.
     */
    @Override
    public void run()
    {

        while(global.matchInProgress()){

            playgroundMon.waitForCalling(teamID);

            benchMon.callContestants(teamID);
            playgroundMon.waitForContestants(teamID);

            playgroundMon.informReferee(teamID);

            playgroundMon.reviewNotes(teamID, benchMon);

        }
    }

}
