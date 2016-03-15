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

    public Coach(int teamID, int strategyID, Bench benchMon, Playground playgroundMon, Global global) {
        this.strategyID = strategyID;
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
            selection = selectContestants(teamID);

            benchMon.callContestants(selection);
            playgroundMon.informReferee(teamID); // enter WATCH_TRIAL blocking state
            playgroundMon.reviewNotes(teamID); // enter WAIT_FOR_REFEREE_COMMAND   blocking state
        }
    }

    public int getTeamID() {
        return teamID;
    }

    public int getStrategyID() {
        return strategyID;
    }

    public int [] selectContestants(int teamID){
        /* todo: algorithm of selection is used here */
        int team [] = {1,2,3};
        return team;
    }



}
