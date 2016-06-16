package RMISolution.ServerSide.Bench;

import RMISolution.Common.Constants;
import RMISolution.Common.VectorClock;
import RMISolution.Interfaces.BenchInterface;

import java.rmi.RemoteException;

public class BenchRemoteCom implements BenchInterface {

    private final VectorClock vc;

    private final Bench bench;

    public BenchRemoteCom(Bench bench) {
        this.bench = bench;
        this.vc = new VectorClock(Constants.ENTITIES_NUM, 0);
    }

    /**
     * Invocation of the method to Review Notes from the Remote Bench
     * Coach State is changed and VC incremented and synced
     * @param teamID
     * @throws RemoteException
     */
    public VectorClock reviewNotes(VectorClock vc, int teamID) throws RemoteException {
        this.vc.update(vc);

        bench.reviewNotes(this.vc, teamID);

        return this.vc.getCopy();
    }

    /**
     * Invocation of the method from the Remote Bench to sit down
     * Contestant state is changed and VC updated and synced
     * @param contestantID
     * @param teamID
     * @throws RemoteException
     */
    public VectorClock sitDown(VectorClock vc, int contestantID, int teamID) throws RemoteException {
        this.vc.update(vc);

        bench.sitDown(this.vc, contestantID, teamID);

        return this.vc.getCopy();
    }

    /**
     * Invocation of the method to call contestants from the Remote Bench
     * VC synced
     * @param teamID
     * @throws RemoteException
     */
    public VectorClock callContestants(VectorClock vc, int teamID) throws RemoteException {
        this.vc.update(vc);

        bench.callContestants(teamID);

        return this.vc.getCopy();
    }

    public void wakeBench() throws RemoteException {
        bench.wakeBench();
    }

    public void setBenchCalled(int teamID, boolean called) throws RemoteException {
        bench.setBenchCalled(teamID, called);
    }

    public void setTrialCalled(boolean trialCalled) throws RemoteException {
        bench.setTrialCalled(trialCalled);
    }

    /**
     * Invocation of the method from the Remote Bench to set the contestant's strength
     * @param contestantID
     * @param teamID
     * @param strength
     * @throws RemoteException
     */
    public void setStrength(int contestantID, int teamID, int strength) throws RemoteException {
        bench.setStrength(contestantID, teamID, strength);
    }

    /**
     * Invocation of the method from the Remote Bench to get the contestant strength
     * @param contestantID
     * @param teamID
     * @return
     * @throws RemoteException
     */
    public int getStrength(int contestantID, int teamID) throws RemoteException {
        return bench.getStrength(contestantID, teamID);
    }

    public void shutdown() throws RemoteException{
        BenchServer.finish();
    }
}
