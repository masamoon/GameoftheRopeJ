package DistributedSolution.ClientSide.Contestant;

import java.util.Random;

/**
 * Created by jonnybel on 3/8/16.
 */

public class Contestant extends Thread {


    /**
     * ID of this Contestant
     */
    private int contestantID;

    /**
     * Team ID of this contestant: either 0 (team1) or 1 (team2);
     */
    private int teamID;

    /**
     * BenchRemote Monitor Object
     */
    private ContestantBenchStub contestantBenchStub;

    /**
     *  PlaygroundRemote Monitor Object
     */
    private ContestantPlaygroundStub contestantPlaygroundStub;

    /**
     * General Information Repository Object
     */
    private ContestantGlobalStub contestantGlobalStub;

    /**
     * Strength level of this Contestant
     */
    private int strength;

    /**
     * Current State of this Contestant
     */
    private ContestantState contestantState;


    /**
     * Contestant Object Constructor
     * @param contestantID
     * @param teamID
     * @param
     * @param
     * @param
     */
    public Contestant(int contestantID, int teamID,  ContestantBenchStub contestantBenchStub, ContestantPlaygroundStub contestantPlaygroundStub, ContestantGlobalStub contestantGlobalStub) {
        this.contestantID = contestantID;
        this.teamID = teamID;
        this.contestantBenchStub = contestantBenchStub;
        this.contestantPlaygroundStub = contestantPlaygroundStub;
        this.contestantGlobalStub = contestantGlobalStub;

        Random r = new Random();
        this.strength = r.nextInt(6) + 5;
    }

    /** Life Cycle of the Contestant Thread
     */
    @Override
    public void run() {

        // TODO: method in stub for this (and respective message)
        contestantBenchStub.setStrength(contestantID, teamID, strength);

        while(contestantGlobalStub.matchInProgress()){

            contestantBenchStub.sitDown(contestantID, teamID);

            if(contestantGlobalStub.matchInProgress()){
                contestantPlaygroundStub.followCoachAdvice(contestantID, teamID);

                contestantPlaygroundStub.getReady(contestantID, teamID, strength);
                pullRope();
                contestantPlaygroundStub.done(contestantID, teamID);

            }
        }
    }

    /**
    *  Internal function for the Contestant thread where he pulls the rope, sleeping for a randomly generated time interval.
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
