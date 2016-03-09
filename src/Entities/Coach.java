package Entities;

import Monitors.Global;
import Monitors.Playground;
import States.CoachState;
import Monitors.Bench;

/**
 * Created by jonnybel on 3/8/16.
 */
public class Coach extends Thread{

    /* todo : comments */

    private int teamID;

    private int strategyID;

    private Bench benchMon;

    private Playground playgroundMon;

    private Global globalMon;

    public Coach(int teamID, int strategyID, Bench benchMon, Playground playgroundMon, Global globalMon) {
        this.strategyID = strategyID;
        this.teamID = teamID;
        this.benchMon = benchMon;
        this.playgroundMon = playgroundMon;
        this.globalMon = globalMon;
    }

    /**
     *  Coach Thread life cycle.
     */

    @Override
    public void run()
    {
        /* todo
        while(matchInProgress()){
            Bench.callContestants();
            Playground.InformReferee(); // enter WATCH_TRIAL blocking state
            Playground.reviewNotes(); // enter WAIT_FOR_REFEREE_COMMAND   blocking state
        }
        */
    }

    public int getTeamID() {
        return teamID;
    }

    public int getStrategyID() {
        return strategyID;
    }

}
