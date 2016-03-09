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

    private Global globalMon;

    public Contestant(int contestantID, int teamID, int strength, Bench benchMon, Playground playgroundMon, Global globalMon) {
        this.contestantID = contestantID;
        this.teamID = teamID;
        this.strength = strength;
        this.benchMon = benchMon;
        this.playgroundMon = playgroundMon;
        this.globalMon = globalMon;
    }

    @Override
    public void run() {

        /* todo */

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
