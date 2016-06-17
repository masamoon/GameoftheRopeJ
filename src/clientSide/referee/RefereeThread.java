package clientSide.referee;

import interfaces.GlobalInterface;
import interfaces.PlaygroundInterface;
import interfaces.RefereeSiteInterface;
import common.entityStates.RefereeState;

import java.rmi.RemoteException;


public class RefereeThread extends Thread {

    /**
     * Referee Remote Comms object
     */
    private RefereeRemoteCom remote;


    /**
     * RefereeThread Current State
     */
    private RefereeState refereeState;

    /**
     * Referee Thread Object Constructor
     * @param playground
     * @param refereeSite
     * @param global
     */
    public RefereeThread(PlaygroundInterface playground, RefereeSiteInterface refereeSite, GlobalInterface global){
        this.remote = new RefereeRemoteCom(playground, refereeSite, global);
    }

    /** Life Cycle of the RefereeThread Thread
     */
    @Override
    public void run(){

        try {
            remote.announceMatch();
            do{
                remote.announceGame();
                while(!remote.gameFinished()){
                    remote.callTrial();
                    remote.startTrial();
                    remote.assertTrialDecision();
                }
                remote.declareGameWinner ();
            }while(remote.matchInProgress());
            remote.declareMatchWinner();

            // global.closeFile();

        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

    public void setRefereeState(RefereeState refereeState) {
        this.refereeState = refereeState;
    }

}
