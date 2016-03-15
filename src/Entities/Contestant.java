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


        do{
            while(global.matchInProgress()){
                playgroundMon.followCoachAdvice(teamID, contestantID); // acorda o coach e fica em STAND_IN_POSITION

                    playgroundMon.getReady(contestantID); // passa o seu estado interno para DO_YOUR_BEST
                    pullRope();
                    playgroundMon.done(contestantID); // acorda o Referee e fica em espera (sem alterar o seu estado)

                }
            // todo: incrementar e decrementar força é chamado por outra entidade pq esta está bloqueada

            benchMon.sitDown(contestantID); // entra no estado de espera SIT_AT_THE_BENCH

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
