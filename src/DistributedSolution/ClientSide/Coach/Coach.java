package DistributedSolution.ClientSide.Coach;

import Nondistributedsolution.Monitors.Bench;
import Nondistributedsolution.Monitors.Global;
import Nondistributedsolution.Monitors.Playground;
import genclass.GenericIO;

/**
 * Created by jonnybel on 3/8/16.
 */
public class Coach extends Thread{

    /**
     * Team ID of this Coach: either 0 (team1) or 1 (team2);
     */
    private int teamID;

    /**
     * BenchRemote Monitor Object
     */
    private CoachBenchStub coachBenchStub;

    /**
     *  PlaygroundRemote Monitor Object
     */
    private CoachPlaygroundStub coachPlaygroundStub;

    /**
     * General Information Repository Object
     */
    private CoachGlobalStub coachGlobalStub;

    private CoachState coachState;

    /**
     * Coach object Constructor
     * @param teamID teamID
     * @param coachBenchStub
     * @param coachPlaygroundStub
     * @param coachGlobalStub
     */
    public Coach(int teamID, CoachBenchStub coachBenchStub, CoachPlaygroundStub coachPlaygroundStub, CoachGlobalStub coachGlobalStub) {
        this.teamID = teamID;
        this.coachBenchStub = coachBenchStub;
        this.coachPlaygroundStub = coachPlaygroundStub;
        this.coachGlobalStub = coachGlobalStub;
    }

    /**
     *  Coach Thread life cycle.
     */
    @Override
    public void run()
    {
        GenericIO.writelnString("Coach "+teamID+" running!");
        while(coachGlobalStub.matchInProgress()){

            GenericIO.writelnString("Coach "+teamID+" reviewNotes");
            coachPlaygroundStub.reviewNotes(teamID);

            GenericIO.writelnString("Coach "+teamID+" callContestants");
            coachBenchStub.callContestants(teamID);

            GenericIO.writelnString("Coach "+teamID+" waitforcontestants");
            coachPlaygroundStub.waitForContestants(teamID);

            GenericIO.writelnString("Coach "+teamID+" informreferee");
            coachPlaygroundStub.informReferee(teamID);


        }
    }
    public void setCoachState(CoachState coachState) {
        this.coachState = coachState;
    }

}
