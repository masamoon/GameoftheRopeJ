package RMISolution.ClientSide.Coach;


import RMISolution.Common.EntityStates.CoachState;
import RMISolution.Common.RetObject;
import RMISolution.Common.VectorClock;
import RMISolution.Interfaces.BenchInterface;
import RMISolution.Interfaces.GlobalInterface;
import RMISolution.Interfaces.PlaygroundInterface;

import java.rmi.RemoteException;

public class CoachRemoteCom {

    private final int NUM_ENTITIES = 13;

    private final VectorClock vc;

    private final BenchInterface bench;

    private final PlaygroundInterface playground;

    private final GlobalInterface global;

    public CoachRemoteCom (BenchInterface bench, PlaygroundInterface playground, GlobalInterface global, int teamID){
        this.bench = bench;
        this.playground = playground;
        this.global = global;

        if(teamID == 0) vc = new VectorClock(NUM_ENTITIES, 1);
        else vc = new VectorClock(NUM_ENTITIES, 7);
    }

    /**
     * Invocation of the method from the Remote global Repository to get the state of the match
     * @return boolean representing is the match is in progress
     * @throws RemoteException
     */

    public boolean matchInProgress() throws RemoteException{
        return global.matchInProgress();
    }

    /**
     * Invocation of the method to Review Notes from the Remote Bench
     * Coach State is changed and VC incremented and synced
     * @param teamID
     * @throws RemoteException
     */
    public void reviewNotes(int teamID) throws RemoteException {
        ((CoachThread)Thread.currentThread()).setCoachState(CoachState.WAIT_FOR_REFEREE_COMMAND);

        vc.increment();

        VectorClock ret = bench.reviewNotes(vc, teamID);

        vc.update(ret);
    }

    /**
     * Invocation of the methods to call contestants and wait contestants from the Remote Bench
     * VC incremented and incremented and synced
     * @param teamID
     * @throws RemoteException
     */
    public void callContestants(int teamID) throws RemoteException {
        vc.increment();

        VectorClock ret = bench.callContestants(vc, teamID);

        vc.update(ret);

        ((CoachThread)Thread.currentThread()).setCoachState(CoachState.ASSEMBLE_TEAM);

        ret = playground.waitForContestants(vc, teamID);

        vc.update(ret);
    }


    /**
     * Invocation of the method to inform the referee from the Remote Playground
     * Coach state is updated and VC incremented and synced
     * @param teamID
     * @throws RemoteException
     */
    public void informReferee (int teamID) throws RemoteException {
        ((CoachThread)Thread.currentThread()).setCoachState(CoachState.WATCH_TRIAL);

        vc.increment();

        VectorClock ret = playground.informReferee(vc, teamID);

        vc.update(ret);
    }


}
