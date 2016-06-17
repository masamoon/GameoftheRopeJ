package interfaces;

import common.VectorClock;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface BenchInterface extends Remote {

    /**
     * Invocation of the method to Review Notes from the Remote bench
     * VCs synced
     * @param teamID
     * @throws RemoteException
     */
    public VectorClock reviewNotes(VectorClock vc, int teamID) throws RemoteException;

    /**
     * Invocation of the method from the Remote bench to sit down
     * VCs synced
     * @param contestantID
     * @param teamID
     * @throws RemoteException
     */
    public VectorClock sitDown (VectorClock vc, int contestantID, int teamID) throws RemoteException;

    /**
     * Invocation of the method to call contestants from the Remote bench
     * VCs synced
     * @param teamID
     * @throws RemoteException
     */
    public VectorClock callContestants (VectorClock vc, int teamID) throws RemoteException;

    public void wakeBench () throws RemoteException;

    public void setBenchCalled (int teamID, boolean called) throws RemoteException;

    public void setTrialCalled(boolean trialCalled) throws  RemoteException;


    /**
     * Invocation of the method from the Remote bench to set the contestant's strength
     * @param contestantID
     * @param teamID
     * @param strength
     * @throws RemoteException
     */
    public void setStrength (int contestantID, int teamID, int strength) throws RemoteException;

    /**
     * Invocation of the method from the Remote bench to get the contestant strength
     * @param contestantID
     * @param teamID
     * @return
     * @throws RemoteException
     */
    public int getStrength (int contestantID, int teamID) throws RemoteException;

    public void shutdown() throws RemoteException;

}
