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

    private int strategyID;

    private Bench benchMon;

    private Playground playgroundMon;

    private Global global;

    // todo: state

    public Coach(int teamID, Bench benchMon, Playground playgroundMon, Global global) {
        this.strategyID = 0;
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
        int [] selection;

        while(global.matchInProgress()){

            playgroundMon.reviewNotes(teamID); // enter WAIT_FOR_REFEREE_COMMAND   blocking state
            // no reviewNotes é que se actualizam as forças dos contestants

            // todo: fazer isto no banco:
            selection = benchMon.selectContestants(teamID);

            benchMon.callContestants(teamID,selection,global);
            playgroundMon.informReferee(teamID); // enter WATCH_TRIAL blocking state
        }
    }

    public int getTeamID() {
        return teamID;
    }

    public int getStrategyID() {
        return strategyID;
    }





}
