package interfaces;

import common.VectorClock;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface RefereeSiteInterface extends Remote {

    /**
     * Invocation of the method from the refereeSite to announce a Match
     * Referee State is changed and VCs synced
     * @throws RemoteException
     */
    public VectorClock announceMatch(VectorClock vc) throws RemoteException;

    /**
     * Invocation of the method from the refereeSite to announce a new Game
     * Referee State is changed and VCs ynced
     * @throws RemoteException
     */
    public VectorClock announceGame(VectorClock vc) throws RemoteException;

    public void benchWakeRef() throws RemoteException;

    /**
     * Invocation of the method from the refereeSite to wait for the contestants
     * Referee State is changed and VCs synced
     * @throws RemoteException
     */
    public void waitForBench() throws RemoteException;

    /**
     * Invocation of the method from the refereeSite to declare the game winner
     * VCs synced
     * @throws RemoteException
     */
    public VectorClock declareGameWinner (VectorClock vc) throws RemoteException;

    /**
     * Invocation of the method from the refereeSite to declare the match winner
     * Referee State is changed and VC incremented and synced
     * @throws RemoteException
     */
    public VectorClock declareMatchWinner(VectorClock vc) throws RemoteException;

    public void setReadyForTrial(boolean readyForTrial) throws RemoteException;

    public int getTrialNum () throws RemoteException;

    public int getGamesNum() throws RemoteException;

    public void shutdown() throws RemoteException;


}
