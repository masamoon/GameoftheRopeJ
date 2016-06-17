package clientSide.coach;

import common.entityStates.CoachState;
import interfaces.BenchInterface;
import interfaces.GlobalInterface;
import interfaces.PlaygroundInterface;

import java.rmi.RemoteException;

public class CoachThread extends Thread {

    /**
     * Team ID of this CoachThread: either 0 (team1) or 1 (team2);
     */
    private int teamID;

    /**
     * Coach Remote Comms Object
     */
    private CoachRemoteCom remote;

    /**
     * Current state of this CoachThread
     */
    private CoachState coachState;

    /**
     * Coach thread object constructor
     * @param teamID
     * @param bench
     * @param playground
     * @param global
     */
    public CoachThread(int teamID, BenchInterface bench, PlaygroundInterface playground, GlobalInterface global) {
        this.teamID = teamID;
        this.remote = new CoachRemoteCom(bench, playground, global, teamID);
        this.coachState = CoachState.INIT;
    }

    /**
     *  CoachThread Thread life cycle.
     */
    @Override
    public void run()
    {
        try {
            while(remote.matchInProgress()){

                System.out.println("coach " + teamID + " status: reviewing");
                remote.reviewNotes(teamID);

                System.out.println("coach " + teamID + " status: calling and waiting");
                remote.callContestants(teamID);

                System.out.println("coach " + teamID + " status: informing");
                remote.informReferee(teamID);
            }
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }
    public void setCoachState(CoachState coachState) {
        this.coachState = coachState;
    }
}
