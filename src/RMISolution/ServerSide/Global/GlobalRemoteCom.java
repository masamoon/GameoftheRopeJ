package RMISolution.ServerSide.Global;


import RMISolution.Common.EntityStates.CoachState;
import RMISolution.Common.EntityStates.ContestantState;
import RMISolution.Common.EntityStates.RefereeState;
import RMISolution.Common.RetObject;
import RMISolution.Common.VectorClock;
import RMISolution.Interfaces.GlobalInterface;

import java.rmi.RemoteException;

public class GlobalRemoteCom implements GlobalInterface{

    private VectorClock vc;

    private final Global global;

    public GlobalRemoteCom(VectorClock vc, Global global) {
        this.vc = vc;
        this.global = global;
    }

    public VectorClock matchWinnerLine(VectorClock vc, int score1, int score2, int winner) throws RemoteException {
        this.vc.update(vc);

        global.matchWinnerLine(score1, score2, winner);

        return this.vc.getCopy();
    }

    public VectorClock matchTieLine(VectorClock vc) throws RemoteException {
        this.vc.update(vc);

        global.matchTieLine();

        return this.vc.getCopy();
    }

    public VectorClock gameWinnerLinePoints(VectorClock vc, int nteam) throws RemoteException {
        this.vc.update(vc);

        global.gameWinnerLinePoints(this.vc, nteam);

        return this.vc.getCopy();
    }

    public VectorClock gameWinnerLineKO(VectorClock vc, int nteam) throws RemoteException {
        this.vc.update(vc);

        global.gameWinnerLineKO(this.vc, nteam);

        return this.vc.getCopy();
    }

    public VectorClock gameTieLine(VectorClock vc) throws RemoteException {
        this.vc.update(vc);

        global.gameTieLine(this.vc);

        return this.vc.getCopy();
    }

    public VectorClock leaveRope(VectorClock vc, int contestantID, int teamID) throws RemoteException {

        this.vc.update(vc);

        global.leaveRope(this.vc, contestantID, teamID);

        return this.vc.getCopy();

    }

    public void setMatchInProgress(boolean matchInProgress) throws RemoteException {
        global.setMatchInProgress(matchInProgress);
    }

    public boolean matchInProgress() throws RemoteException {
        return global.matchInProgress();
    }

    public boolean gameFinished() throws RemoteException {
        return global.gameFinished();
    }

    public VectorClock setContestantState(VectorClock vc, int contestantID, int teamID, ContestantState state) throws RemoteException {
        this.vc.update(vc);

        global.setContestantState(this.vc, contestantID, teamID, state);

        return this.vc;
    }

    public ContestantState getContestantState(int contestantID, int teamID) throws RemoteException {
        return global.getContestantState(contestantID, teamID);
    }

    public VectorClock setRefereeState(VectorClock vc, RefereeState state) throws RemoteException {
        this.vc.update(vc);

        global.setRefereeState(this.vc, state);

        return this.vc;
    }

    public RefereeState getRefereeState() throws RemoteException {
        return global.getRefereeState();
    }

    public VectorClock setCoachState(VectorClock vc, int teamID, CoachState state) throws RemoteException {
        this.vc.update(vc);

        global.setCoachState(this.vc, teamID, state);

        return this.vc;
    }

    public CoachState getCoachState(int teamID) throws RemoteException {
        return global.getCoachState(teamID);
    }

    public int getStrength(int id, int teamID) throws RemoteException {
        return global.getStrength(id, teamID);
    }

    public void setStrength(int id, int teamID, int str) throws RemoteException {
        global.setStrength(id, teamID, str);
    }

    public int getTrialNum() throws RemoteException {
        return global.getTrialNum();
    }

    public void incrementTrialNum() throws RemoteException {
        global.incrementTrialNum();
    }

    public void resetTrialNum() throws RemoteException {
        global.resetTrialNum();
    }

    public VectorClock changeFlagPos(VectorClock vc, int flagPos) throws RemoteException {
        this.vc.update(vc);

        global.changeFlagPos(flagPos);

        return this.vc;
    }

    public void resetFlagPos() throws RemoteException {
        global.resetFlagPos();
    }

    public int getFlagPos() throws RemoteException {
        return global.getFlagPos();
    }

    public void incrementGamesNum() throws RemoteException {
        global.incrementGamesNum();
    }

    public VectorClock newGame (VectorClock vc) throws RemoteException {

        global.setRefereeState(vc, RefereeState.START_OF_A_GAME);
        global.incrementGamesNum();
        global.resetTrialNum();

        return null;
    }
}
