package Entities;

import Monitors.Bench;
import Monitors.Global;
import Monitors.Playground;
import States.ContestantState;

/**
 * Created by jonnybel on 3/8/16.
 */
public class Contestant extends Thread {

    /* todo: add comments */

    private int contestantID;

    private int teamID;

    private int strength;

    private Bench benchMon;

    private Playground playgroundMon;

    private Global global;

    public Contestant(int contestantID, int teamID, int strength, Bench benchMon, Playground playgroundMon, Global global) {
        this.contestantID = contestantID;
        this.teamID = teamID;
        this.strength = strength;
        this.benchMon = benchMon;
        this.playgroundMon = playgroundMon;
        this.global = global;
    }

    @Override
    public void run() {

        boolean selected = false;

        do{
            while(global.matchInProgress()){
                selected = benchMon.followCoachAdvice(teamID, contestantID);

                if(selected){
                    playgroundMon.getReady(contestantID);
                    pullRope();
                /* decrease strength */

                    playgroundMon.done(contestantID);

                }
                else{
                /* increase strength */
                }
                benchMon.sitDown(contestantID);
            }
        }while(!global.endContestantOps());


    }

    private void pullRope ()
    {
        try
        { sleep ((long) (1 + 10 * Math.random ()));
        }
        catch (InterruptedException e) {}
    }

    public int getContestantID() {
        return contestantID;
    }

    public int getTeamID() {
        return teamID;
    }

    public int getStrength() {
        return strength;
    }

    public void setStrength(int strength) {
        this.strength = strength;
    }

}
