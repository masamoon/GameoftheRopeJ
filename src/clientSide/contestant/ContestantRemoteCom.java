package clientSide.contestant;

import common.entityStates.ContestantState;
import common.VectorClock;
import interfaces.BenchInterface;
import interfaces.GlobalInterface;
import interfaces.PlaygroundInterface;

import java.rmi.RemoteException;

public class ContestantRemoteCom {

    private final int NUM_ENTITIES = 13;

    private final VectorClock vc;

    private final BenchInterface bench;

    private final PlaygroundInterface playground;

    private final GlobalInterface global;

    public ContestantRemoteCom (int teamID, int contestantID, BenchInterface bench, PlaygroundInterface playground, GlobalInterface global){
        this.bench = bench;
        this.playground = playground;
        this.global = global;

        if(teamID == 0) vc = new VectorClock(NUM_ENTITIES, (2 + contestantID));
        else vc = new VectorClock(NUM_ENTITIES, (7 + contestantID));
    }

    /**
     * Invocation of the methods from both the Remote bench and global Repository to initialize this contestant's strength
     * @param contestantID
     * @param teamID
     * @param strength
     * @throws RemoteException
     */
    public void setStrengthBench(int contestantID, int teamID, int strength) throws RemoteException{
        bench.setStrength(contestantID, teamID, strength);
    }

    public void setStrengthGlobal(int contestantID, int teamID, int strength) throws RemoteException{
        global.setStrength(contestantID, teamID, strength);
    }

    public int getStrength(int contestantID, int teamID) throws RemoteException {
        return bench.getStrength(contestantID, teamID);
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
     * Invocation of the method from the Remote bench to sit down
     * Contestant state is changed and VC incremented and synced
     * Contestant internal Strength is updated
     * @param contestantID
     * @param teamID
     * @throws RemoteException
     */
    public void sitDown(int contestantID, int teamID) throws RemoteException{
        ((ContestantThread)Thread.currentThread()).setContestantState(ContestantState.SIT_AT_THE_BENCH);

        vc.increment();

        VectorClock ret = bench.sitDown(vc, contestantID,teamID);

        vc.update(ret);

        ((ContestantThread)Thread.currentThread()).setStrength(bench.getStrength(contestantID, teamID));
    }

    /**
     * Invocation of the method from the Remote playground to follow coach advice
     * Contestant State is changed and VC is incremented and synced
     * @param contestantID
     * @param teamID
     * @throws RemoteException
     */
    public void followCoachAdvice (int contestantID, int teamID) throws RemoteException {
        ((ContestantThread)Thread.currentThread()).setContestantState(ContestantState.STAND_IN_POSITION);

        vc.increment();

        VectorClock ret = playground.followCoachAdvice(vc, contestantID, teamID);

        vc.update(ret);
    }

    /**
     * Invocation of the method from the Remote playground to get ready
     * Contestant state is changed and VC is incremented and synced
     * @param contestantID
     * @param teamID
     * @param strength
     * @throws RemoteException
     */
    public void getReady (int contestantID, int teamID, int strength) throws RemoteException {
        ((ContestantThread)Thread.currentThread()).setContestantState(ContestantState.DO_YOUR_BEST);

        vc.increment();

        VectorClock ret = playground.getReady(vc, contestantID, teamID, strength);

        vc.update(ret);
    }

    /**
     * Invocation of the method from the Remote playground to get ready
     * VC is incremented and synced
     * @param contestantID
     * @param teamID
     * @throws RemoteException
     */
    public void done (int contestantID, int teamID) throws RemoteException {

        vc.increment();

        VectorClock ret = playground.done(vc, contestantID, teamID);

        vc.update(ret);
    }



}
