package RMISolution.ServerSide.Playground;


import RMISolution.Common.Constants;
import RMISolution.Common.VectorClock;
import RMISolution.Interfaces.PlaygroundInterface;

import java.rmi.RemoteException;

public class PlaygroundRemoteCom implements PlaygroundInterface{

    private final VectorClock vc;

    private final Playground playground;

    public PlaygroundRemoteCom(Playground playground) {
        this.vc = new VectorClock(Constants.ENTITIES_NUM, 0);
        this.playground = playground;
    }

    /**
     * Call trial invocation by the client
     * @param vc Vector Clock
     * @return updated vector clock
     * @throws RemoteException
     */
    public VectorClock callTrial(VectorClock vc) throws RemoteException {
        this.vc.update(vc);

        playground.callTrial(vc);

        return this.vc.getCopy();
    }

    /**
     * Wait for contestants invocation by the client
     * @param vc Vector Clock
     * @param teamID
     * @return updated vector clock
     * @throws RemoteException
     */
    public VectorClock waitForContestants(VectorClock vc, int teamID) throws RemoteException {
        this.vc.update(vc);

        playground.waitForContestants(vc,teamID);

        return this.vc.getCopy();
    }

    /**
     * followCoachAdvice invocation by the client
     * @param vc Vector Clock
     * @param teamID
     * @return updated vector clock
     * @throws RemoteException
     */
    public VectorClock followCoachAdvice(VectorClock vc, int contestantID, int teamID) throws RemoteException {
        this.vc.update(vc);

        playground.followCoachAdvice(this.vc, contestantID, teamID);

        return this.vc.getCopy();
    }

    /**
     * informReferee invocation by the client
     * @param vc Vector Clock
     * @param teamID
     * @return updated vector clock
     * @throws RemoteException
     */
    public VectorClock informReferee(VectorClock vc, int teamID) throws RemoteException {
        this.vc.update(vc);

        playground.informReferee(this.vc, teamID);

        return this.vc.getCopy();
    }

    /**
     * startTrial invocation by the client
     * @param vc vector clock
     * @return updated vector clock
     * @throws RemoteException
     */
    public VectorClock startTrial(VectorClock vc) throws RemoteException {
        this.vc.update(vc);

        playground.startTrial(this.vc);

        return this.vc.getCopy();

    }

    /**
     * getReady invocation by the client
     * @param vc vector clock
     * @param contestantID
     * @param teamID
     * @param strength
     * @return updated vectorClock
     * @throws RemoteException
     */
    public VectorClock getReady(VectorClock vc, int contestantID, int teamID, int strength) throws RemoteException {
        this.vc.update(vc);

        playground.getReady(this.vc, contestantID, teamID, strength);

        return this.vc.getCopy();
    }

    /**
     * Done invocation by the client
     * @param vc vector clock
     * @param contestantID
     * @param teamID
     * @return updated vector clock
     * @throws RemoteException
     */
    public VectorClock done(VectorClock vc, int contestantID, int teamID) throws RemoteException {
        this.vc.update(vc);

        VectorClock ret = playground.done(this.vc, contestantID, teamID);

        this.vc.update(ret);

        return this.vc.getCopy();
    }

    /**
     * assertTrialDecision by the Client
     * @param vc vector clock
     * @return updated vector clock
     * @throws RemoteException
     */
    public VectorClock assertTrialDecision(VectorClock vc) throws RemoteException {
        this.vc.update(vc);

        playground.assertTrialDecision(this.vc);

        return this.vc.getCopy();
    }

    public void shutdown () throws RemoteException{
        PlaygroundServer.finish();
    }
}
