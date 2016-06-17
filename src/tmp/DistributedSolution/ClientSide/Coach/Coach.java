package tmp.DistributedSolution.ClientSide.Coach;

import tmp.Nondistributedsolution.Monitors.Bench;
import tmp.Nondistributedsolution.Monitors.Global;
import tmp.Nondistributedsolution.Monitors.Playground;
import genclass.GenericIO;

public class Coach extends Thread{

    /**
     * Team ID of this CoachThread: either 0 (team1) or 1 (team2);
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
     * CoachThread object Constructor
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
     *  CoachThread Thread life cycle.
     */
    @Override
    public void run()
    {
        GenericIO.writelnString("CoachThread "+teamID+" running!");
        while(coachGlobalStub.matchInProgress()){

            setCoachState(CoachState.WAIT_FOR_REFEREE_COMMAND);
            GenericIO.writelnString("CoachThread "+teamID+" reviewNotes");
            coachBenchStub.reviewNotes(teamID);

            GenericIO.writelnString("CoachThread "+teamID+" callContestants");
            coachBenchStub.callContestants(teamID);

            setCoachState(CoachState.ASSEMBLE_TEAM);
            GenericIO.writelnString("CoachThread "+teamID+" waitforcontestants");
            coachPlaygroundStub.waitForContestants(teamID);

            setCoachState(CoachState.WATCH_TRIAL);
            GenericIO.writelnString("CoachThread "+teamID+" informreferee");
            coachPlaygroundStub.informReferee(teamID);


        }
    }
    public void setCoachState(CoachState coachState) {
        this.coachState = coachState;
    }

}
