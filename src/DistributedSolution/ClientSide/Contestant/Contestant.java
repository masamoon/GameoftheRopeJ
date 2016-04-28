package DistributedSolution.ClientSide.Contestant;

import Nondistributedsolution.Monitors.Bench;
import Nondistributedsolution.Monitors.Global;
import Nondistributedsolution.Monitors.Playground;

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
    }

    /** Life Cycle of the Contestant Thread
     */
    @Override
    public void run() {

        while(contestantGlobalStub.matchInProgress()){

            contestantBenchStub.sitDown(contestantID, teamID);

            if(contestantGlobalStub.matchInProgress()){
                contestantPlaygroundStub.followCoachAdvice(contestantID, teamID); //// TODO: 02/04/2016 verify the location of operations

                contestantPlaygroundStub.getReady(contestantID, teamID);
                pullRope();
                contestantPlaygroundStub.done(teamID);

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


}
