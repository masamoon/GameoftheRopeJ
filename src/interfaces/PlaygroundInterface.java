package interfaces;

import common.VectorClock;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface PlaygroundInterface extends Remote{

    public VectorClock callTrial (VectorClock vc) throws RemoteException;

    /**
     * Invocation of the method to wait for the contestants from the Remote playground
     * VCs synced
     * @param teamID
     * @throws RemoteException
     */
    public VectorClock waitForContestants(VectorClock vc, int teamID) throws RemoteException;

    /**
     * Invocation of the method from the Remote playground to follow coach advice
     * VCs synced
     * @param contestantID
     * @param teamID
     * @throws RemoteException
     */
    public VectorClock followCoachAdvice (VectorClock vc, int contestantID, int teamID) throws RemoteException;

    /**
     * Invocation of the method to inform the referee from the Remote playground
     * VCs synced
     * @param teamID
     * @throws RemoteException
     */
    public VectorClock informReferee (VectorClock vc, int teamID) throws RemoteException;

    /**
     * Invocation of the method from the refereeSite to start a trial
     * Referee State is changed and VCs synced
     * @throws RemoteException
     */
    public VectorClock startTrial (VectorClock vc) throws RemoteException;


    /**
     * Invocation of the method from the Remote playground to get ready
     * Contestant state is changed and VCs synced
     * @param contestantID
     * @param teamID
     * @param strength
     * @throws RemoteException
     */
    public VectorClock getReady(VectorClock vc, int contestantID, int teamID, int strength) throws  RemoteException;

    /**
     * Invocation of the method from the Remote playground to get ready
     * VCs synced
     * @param contestantID
     * @param teamID
     * @throws RemoteException
     */
    public VectorClock done(VectorClock vc, int contestantID, int teamID) throws RemoteException;

    /**
     * Invocation of the method from the refereeSite to assert a trial decision
     * VCs synced
     * @throws RemoteException
     */
    public VectorClock assertTrialDecision (VectorClock vc) throws RemoteException;

    public void shutdown() throws RemoteException;

}
