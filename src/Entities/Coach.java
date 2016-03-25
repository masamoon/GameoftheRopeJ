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

        while(global.matchInProgress()){

            playgroundMon.waitForCalling(teamID); // enter WAIT_FOR_REFEREE_COMMAND   blocking state

            benchMon.callContestants(teamID); // seleciona os contestants e acorda-os
            playgroundMon.waitForContestants(teamID); // entra no estado ASSEMBLE_TEAM e espera no playground que todos os contestants estejam STANDING

            playgroundMon.informReferee(teamID); // enter WATCH_TRIAL blocking state

            // todo: no fim disto é preciso actualizar as forças:
            playgroundMon.reviewNotes(teamID);

        }
    }

    public int getTeamID() {
        return teamID;
    }

    public int getStrategyID() {
        return strategyID;
    }





}
