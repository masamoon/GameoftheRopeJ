package RMISolution.ServerSide.RefereeSite;

import RMISolution.Common.Constants;
import RMISolution.Common.VectorClock;
import RMISolution.Interfaces.RefereeSiteInterface;

import java.rmi.RemoteException;

public class RefereeSiteRemoteCom implements RefereeSiteInterface{

    private final VectorClock vc;

    private final RefereeSite refereeSite;

    public RefereeSiteRemoteCom(RefereeSite refereeSite) {
        this.vc = new VectorClock(Constants.ENTITIES_NUM, 0);
        this.refereeSite = refereeSite;
    }

    /**
     * announceGame invocation by the client
     * @param vc vector clock
     * @return updated vector clock
     * @throws RemoteException
     */
    public VectorClock announceGame(VectorClock vc) throws RemoteException {
        this.vc.update(vc);

        refereeSite.announceGame(vc);

        return this.vc.getCopy();
    }

    /**
     * announceMatch invocation by the client
     * @param vc vector clock
     * @return updated vector clock
     * @throws RemoteException
     */
    public VectorClock announceMatch(VectorClock vc) throws RemoteException {
        this.vc.update(vc);

        refereeSite.announceMatch(vc);

        return this.vc.getCopy();
    }


    public void benchWakeRef() throws RemoteException {
        refereeSite.benchWakeRef();
    }

    public void waitForBench() throws RemoteException {
        refereeSite.waitForBench();
    }

    /**
     * declareGameWinner invocation by the client
     * @param vc vector clock
     * @return updated vector clock
     * @throws RemoteException
     */
    public VectorClock declareGameWinner(VectorClock vc) throws RemoteException {
        this.vc.update(vc);

        refereeSite.declareGameWinner(vc);

        return this.vc.getCopy();
    }

    /**
     * declareMatchWinner invocation by the client
     * @param vc vector clock
     * @return updated vector clock
     * @throws RemoteException
     */
    public VectorClock declareMatchWinner(VectorClock vc) throws RemoteException {
        this.vc.update(vc);

        refereeSite.declareMatchWinner(vc);

        return this.vc.getCopy();
    }


    public void setReadyForTrial(boolean readyForTrial) throws RemoteException {
        refereeSite.setReadyForTrial(readyForTrial);
    }

    public int getTrialNum() throws RemoteException {
        return refereeSite.getTrialNum();
    }

    public int getGamesNum() throws RemoteException {
        return refereeSite.getGamesNum();
    }
    public void shutdown() throws RemoteException{
        RefereeSiteServer.finish();
    }
}
