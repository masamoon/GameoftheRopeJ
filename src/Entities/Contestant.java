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

        while(global.matchInProgress()){

            benchMon.sitDown(contestantID, teamID, global, playgroundMon); // entra no estado de espera SIT_AT_THE_BENCH

            System.out.println("contestant " + contestantID + " from team " + teamID +" about to followCoachAdvice...");
            playgroundMon.followCoachAdvice(contestantID, teamID);

            playgroundMon.getReady(contestantID, teamID); // passa o seu estado interno para DO_YOUR_BEST
            pullRope();
            playgroundMon.done(contestantID, teamID); // acorda o Referee e fica em espera (sem alterar o seu estado)

            System.out.println("MATCH IN PROGRESS?: "+ global.matchInProgress());
        }
        System.out.println("CONTESTANT FINISHED");
    }

    /*
    * pulls the rope
     */
    private void pullRope ()
    {
        System.out.println("* LE ROPE PULL* from"+teamID+" "+contestantID);
        try
        { sleep ((long) (1 + 10 * Math.random ()));
        }
        catch (InterruptedException e) {}
    }

    public int getContestantID() {
        return this.contestantID;
    }

    public int getTeamID() {
        return this.teamID;
    }

    public int getStrength() {
        return this.strength;
    }

    public void increaseStrength() {
        this.strength++;
    }

    public void decreaseStrength() {
        this.strength--;
    }

}
