package Nondistributedsolution.Entities;

import Nondistributedsolution.Monitors.Bench;
import Nondistributedsolution.Monitors.Global;
import Nondistributedsolution.Monitors.Playground;

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

            benchMon.sitDown(contestantID, teamID,playgroundMon);

            if(global.matchInProgress()){
                playgroundMon.followCoachAdvice(contestantID, teamID); //// TODO: 02/04/2016 verify the location of operations  

                playgroundMon.getReady(contestantID, teamID);
                pullRope();
                playgroundMon.done(teamID);

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
