package clientSide.contestant;

import common.entityStates.ContestantState;
import interfaces.BenchInterface;
import interfaces.GlobalInterface;
import interfaces.PlaygroundInterface;

import java.rmi.RemoteException;
import java.util.Random;


public class ContestantThread extends Thread {


    /**
     * ID of this ContestantThread
     */
    private int contestantID;

    /**
     * Team ID of this contestant: either 0 (team1) or 1 (team2);
     */
    private int teamID;

    /**
     * Remote Comms Object
     */
    private ContestantRemoteCom remote;

    /**
     * Strength level of this ContestantThread
     */
    private int strength;

    /**
     * Current State of this ContestantThread
     */
    private ContestantState contestantState;


    /**
     *
     * @param contestantID
     * @param teamID
     * @param bench
     * @param playground
     * @param global
     */
    public ContestantThread(int teamID, int contestantID, BenchInterface bench, PlaygroundInterface playground, GlobalInterface global) {
        this.contestantID = contestantID;
        this.teamID = teamID;
        this.remote = new ContestantRemoteCom(contestantID, teamID, bench, playground, global);
        this.contestantState = ContestantState.INIT;

        Random r = new Random();
        this.strength = r.nextInt(6) + 5;
    }

    /** Life Cycle of the ContestantThread Thread
     */
    @Override
    public void run() {

        try {
            // initialize contestant strength at the shared zones
            //benchMon.initializeStrength(contestantID, teamID, strength);
            //global.initializeStrength(contestantID,teamID,strength
            remote.setStrengthBench(contestantID,teamID,strength);
            remote.setStrengthGlobal(contestantID,teamID,strength);


            //remote.initializeStrength(contestantID,teamID,strength);

            while(remote.matchInProgress()){

                remote.sitDown(contestantID, teamID);
                this.strength = remote.getStrength(contestantID, teamID);

                if(remote.matchInProgress()){

                    remote.followCoachAdvice(contestantID, teamID);

                    remote.getReady(contestantID, teamID, strength);
                    pullRope();
                    remote.done(contestantID, teamID);
                }
            }
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

    /**
     *  Internal function for the ContestantThread thread where he pulls the rope, sleeping for a randomly generated time interval.
     */
    private void pullRope ()
    {
        try
        { sleep ((long) (1 + 10 * Math.random ()));
        }
        catch (InterruptedException e) {}
    }

    public int getStrength() {
        return strength;
    }

    public void setStrength(int strength){
        this.strength = strength;
    }

    public void setContestantState(ContestantState contestantState) {
        this.contestantState = contestantState;
    }
}
