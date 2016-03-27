package Entities;

import Monitors.Global;
import Monitors.Playground;
import States.CoachState;
import Monitors.Bench;

/**
 * Created by jonnybel on 3/8/16.
 */
public class Coach extends Thread{


    private int teamID;

    private Bench benchMon;

    private Playground playgroundMon;

    private Global global;

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
