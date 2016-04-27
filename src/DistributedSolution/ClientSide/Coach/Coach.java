package DistributedSolution.ClientSide.Coach;

import Nondistributedsolution.Monitors.Bench;
import Nondistributedsolution.Monitors.Global;
import Nondistributedsolution.Monitors.Playground;

/**
 * Created by jonnybel on 3/8/16.
 */
public class Coach extends Thread{

    /**
     * Team ID of this Coach: either 0 (team1) or 1 (team2);
     */
    private int teamID;

    /**
     * Bench Monitor Object
     */
    private CoachBenchStub coachBenchStub;

    /**
     *  Playground Monitor Object
     */
    private CoachPlaygroundStub coachPlaygroundStub;

    /**
     * General Information Repository Object
     */
    private CoachGlobalStub coachGlobalStub;


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

        while(coachGlobalStub.matchInProgress()){

            coachPlaygroundStub.waitForCalling(teamID);

            coachBenchStub.callContestants(teamID);
            coachPlaygroundStub.waitForContestants(teamID);

            coachPlaygroundStub.informReferee(teamID);

            coachPlaygroundStub.reviewNotes(teamID);

        }
    }

}
