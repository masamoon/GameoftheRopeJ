package RMISolution.ClientSide.Referee;


import RMISolution.Common.EntityStates.RefereeState;
import RMISolution.Common.VectorClock;
import RMISolution.Interfaces.BenchInterface;
import RMISolution.Interfaces.GlobalInterface;
import RMISolution.Interfaces.PlaygroundInterface;
import RMISolution.Interfaces.RefereeSiteInterface;

import java.rmi.RemoteException;


public class RefereeRemoteCom {

    private final int NUM_ENTITIES = 13;

    private final VectorClock vc;

    private final BenchInterface bench;

    private final PlaygroundInterface playground;

    private final GlobalInterface global;

    private final RefereeSiteInterface refereeSite;

    public RefereeRemoteCom (BenchInterface bench, PlaygroundInterface playground, RefereeSiteInterface refereeSite, GlobalInterface global){
        this.bench = bench;
        this.playground = playground;
        this.global = global;
        this.refereeSite = refereeSite;

        vc = new VectorClock(NUM_ENTITIES, 0);
    }

    /**
     * Invocation of the method from the RefereeSite to announce a Match
     * Referee State is changed and VC incremented and synced
     * @throws RemoteException
     */
    public void announceMatch () throws RemoteException{
        ((RefereeThread)Thread.currentThread()).setRefereeState(RefereeState.START_OF_THE_MATCH);

        vc.increment();

        VectorClock ret = refereeSite.announceMatch(vc);

        vc.update(ret);
    }

    /**
     * Invocation of the method from the RefereeSite to announce a new Game
     * Referee State is changed and VC incremented and synced
     * @throws RemoteException
     */
    public void announceGame () throws RemoteException{
        ((RefereeThread)Thread.currentThread()).setRefereeState(RefereeState.START_OF_A_GAME);

        vc.increment();

        VectorClock ret = refereeSite.announceGame(vc);

        vc.update(ret);
    }

    /**
     * Invocation of the method from the Remote global Repository to get the state of the game
     * @return boolean representing is the game is in progress
     * @throws RemoteException
     */
    public boolean gameFinished () throws RemoteException{
        return global.gameFinished();
    }

    /**
     * Invocation of the method from the Playground to wait for the contestants and call a trial
     * Referee State is changed and VC incremented and synced
     * @throws RemoteException
     */
    public void callTrial () throws RemoteException {
        refereeSite.waitForBench();

        ((RefereeThread)Thread.currentThread()).setRefereeState(RefereeState.TEAMS_READY);

        vc.increment();

        VectorClock ret = playground.callTrial(vc);

        vc.update(ret);
    }

    /**
     * Invocation of the method from the Playground to start a trial
     * Referee State is changed and VC incremented and synced
     * @throws RemoteException
     */
    public void startTrial() throws RemoteException {
        ((RefereeThread)Thread.currentThread()).setRefereeState(RefereeState.WAIT_FOR_TRIAL_CONCLUSION);

        vc.increment();

        VectorClock ret = playground.startTrial(vc);

        vc.update(ret);
    }

    /**
     * Invocation of the method from the Playground to assert a trial decision
     * VC incremented and synced
     * @throws RemoteException
     */
    public void assertTrialDecision () throws RemoteException {

        vc.increment();

        VectorClock ret = playground.assertTrialDecision(vc);

        vc.update(ret);
    }

    /**
     * Invocation of the method from the RefereeSite to declare the game winner
     * Referee State is changed and VC incremented and synced
     * @throws RemoteException
     */
    public void declareGameWinner () throws RemoteException {
        ((RefereeThread)Thread.currentThread()).setRefereeState(RefereeState.END_OF_A_GAME);

        vc.increment();

        VectorClock ret = refereeSite.declareGameWinner(vc);

        vc.update(ret);
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
     * Invocation of the method from the RefereeSite to declare the match winner
     * Referee State is changed and VC incremented and synced
     * @throws RemoteException
     */
    public void declareMatchWinner () throws RemoteException {
        ((RefereeThread)Thread.currentThread()).setRefereeState(RefereeState.END_OF_THE_MATCH);

        vc.increment();

        VectorClock ret = refereeSite.declareMatchWinner(vc);

        vc.update(ret);
    }
}
