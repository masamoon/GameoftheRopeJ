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

            playgroundMon.waitForCalling(teamID); // enter WAIT_FOR_REFEREE_COMMAND   blocking state

            benchMon.callContestants(teamID); // seleciona os contestants e acorda-os
            playgroundMon.waitForContestants(teamID); // entra no estado ASSEMBLE_TEAM e espera no playground que todos os contestants estejam STANDING

            playgroundMon.informReferee(teamID); // enter WATCH_TRIAL blocking state

            playgroundMon.reviewNotes(teamID);

            System.out.println("COACH MATCH IN PROGRESS?: "+ global.matchInProgress());
        }
        System.out.println("COACH FINISHED");
    }

}
