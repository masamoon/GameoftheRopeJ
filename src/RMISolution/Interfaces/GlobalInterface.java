package RMISolution.Interfaces;

import RMISolution.Common.EntityStates.CoachState;
import RMISolution.Common.EntityStates.ContestantState;
import RMISolution.Common.EntityStates.RefereeState;
import RMISolution.Common.VectorClock;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface GlobalInterface extends Remote {

    public VectorClock matchWinnerLine (VectorClock vc, int score1, int score2, int winner) throws RemoteException;

    public VectorClock matchTieLine (VectorClock vc) throws RemoteException;

    public VectorClock gameWinnerLinePoints (VectorClock vc, int nteam) throws RemoteException;

    public VectorClock gameWinnerLineKO (VectorClock vc, int nteam) throws RemoteException;

    public VectorClock gameTieLine (VectorClock vc) throws RemoteException;

    public VectorClock leaveRope (VectorClock vc, int contestantID, int teamID) throws RemoteException;

    public void setMatchInProgress (boolean matchInProgress) throws RemoteException;

    /**
     * Invocation of the method from the Remote global Repository to get the state of the match
     * @return boolean representing is the match is in progress
     * @throws RemoteException
     */
    public boolean matchInProgress() throws RemoteException;

    /**
     * Invocation of the method from the Remote global Repository to get the state of the game
     * @return boolean representing is the game is in progress
     * @throws RemoteException
     */
    public boolean gameFinished() throws RemoteException;

    public VectorClock setContestantState (VectorClock vc, int contestantID, int teamID, ContestantState state) throws RemoteException;

    public ContestantState getContestantState (int contestantID, int teamID) throws  RemoteException;

    public VectorClock setRefereeState (VectorClock vc, RefereeState state) throws RemoteException;

    public RefereeState getRefereeState () throws  RemoteException;

    public VectorClock setCoachState (VectorClock vc, int teamID, CoachState state) throws RemoteException;

    public CoachState getCoachState (int teamID) throws  RemoteException;

    public int getStrength(int id, int teamID) throws RemoteException;

    /**
     * Invocation of the method from the Remote Global Repository to set the contestant's strength
     * @param id
     * @param teamID
     * @param str
     * @throws RemoteException
     */
    public void setStrength(int id, int teamID, int str) throws RemoteException;

    public int getTrialNum() throws RemoteException;

    public void incrementTrialNum() throws RemoteException;

    public void resetTrialNum() throws RemoteException;

    public VectorClock changeFlagPos(VectorClock vc, int flagPos) throws RemoteException;

    public void resetFlagPos() throws RemoteException;

    public int getFlagPos() throws RemoteException;

    public void incrementGamesNum() throws RemoteException;

    public VectorClock newGame (VectorClock vc) throws RemoteException;

}
