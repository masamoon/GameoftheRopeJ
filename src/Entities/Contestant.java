package Entities;

import Monitors.Bench;
import Monitors.Global;
import Monitors.Playground;
import States.ContestantState;

/**
 * Created by jonnybel on 3/8/16.
 */

public class Contestant extends Thread {


    private int contestantID;

    private int teamID;

    private Bench benchMon;

    private Playground playgroundMon;

    private Global global;

    public Contestant(int contestantID, int teamID,  Bench benchMon, Playground playgroundMon, Global global) {
        this.contestantID = contestantID;
        this.teamID = teamID;
        this.benchMon = benchMon;
        this.playgroundMon = playgroundMon;
        this.global = global;
    }

    @Override
    public void run() {

        while(global.matchInProgress()){

            benchMon.sitDown(contestantID, teamID, playgroundMon);

            if(global.matchInProgress()){
                playgroundMon.followCoachAdvice(contestantID, teamID);

                playgroundMon.getReady(contestantID, teamID);
                pullRope();
                playgroundMon.done(contestantID, teamID);

            }
        }
    }

    /**
    * pulls the rope
     */
    private void pullRope ()
    {
        try
        { sleep ((long) (1 + 10 * Math.random ()));
        }
        catch (InterruptedException e) {}
    }


}
