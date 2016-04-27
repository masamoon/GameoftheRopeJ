package DistributedSolution.ServerSide.Playground;

import DistributedSolution.Message.Message;
import DistributedSolution.Message.MessageException;
import genclass.GenericIO;

/**
 * Created by Andre on 12/04/2016.
 */
public class PlaygroundInterface {

    private Playground playground;


    public PlaygroundInterface(Playground playground){

        this.playground = playground;

    }

    public Message processAndReply(Message inMessage) throws MessageException {
        Message outMessage = null;                           // mensagem de resposta

        int teamID= inMessage.getTeamID();
        int contestantID= inMessage.getContestantID();

        switch (inMessage.getType()) {
            case Message.WFCALLING:

                playground.waitForCalling(teamID);
                outMessage = new Message(Message.ACK);
                break;
            case Message.CTRIAL:
                playground.callTrial();
                outMessage = new Message(Message.ACK);
                break;
            case Message.WCONTESTANTS:

                playground.waitForContestants(teamID);
                outMessage = new Message(Message.ACK);
                break;
            case Message.FCOACHADV:

                playground.followCoachAdvice(contestantID,teamID);
                outMessage = new Message(Message.ACK);
                break;
            case Message.INFREF:

                playground.informReferee(teamID);
                outMessage = new Message(Message.ACK);
                break;
            case Message.STRIAL:
                playground.startTrial();
                outMessage = new Message(Message.ACK);
                break;
            case Message.GREADY:
                playground.getReady(contestantID,teamID);
                outMessage = new Message(Message.ACK);
                break;
            case Message.DONE:
                playground.done(teamID);
                outMessage = new Message(Message.ACK);
                break;
            case Message.ASSTRIALDEC:
                playground.assertTrialDecision();
                outMessage = new Message(Message.ACK);
                break;
            case Message.REVNOTES:
                playground.reviewNotes(teamID);
                outMessage = new Message(Message.ACK);
                break;
            default:
                GenericIO.writelnString("Invalid message type");
                break;

        }
        return outMessage;
    }
}

