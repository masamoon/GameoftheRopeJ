package DistributedSolution.ServerSide.Playground;

import DistributedSolution.Communication.Message.Message;
import DistributedSolution.Communication.Message.MessageException;
import DistributedSolution.Communication.ServerCom;
import DistributedSolution.Communication.ServerInterface;
import genclass.GenericIO;

/**
 * Created by Andre on 12/04/2016.
 */
public class PlaygroundInterface implements ServerInterface {

    private PlaygroundRemote playgroundRemote;

    private boolean serviceEnded;


    public PlaygroundInterface(PlaygroundRemote playgroundRemote){

        this.playgroundRemote = playgroundRemote;

    }

    @Override
    public Message processAndReply(Message inMessage, ServerCom scon) throws MessageException {
        Message outMessage = null;                           // mensagem de resposta

        int teamID= inMessage.getTeamID();
        int contestantID= inMessage.getContestantID();

        switch (inMessage.getType()) {
          /*  case Message.WFCALLING:

                playgroundRemote.waitForCalling(teamID);
                outMessage = new Message(Message.ACK);
                break;*/
            case Message.CTRIAL:
                playgroundRemote.callTrial();
                outMessage = new Message(Message.ACK);
                break;
            case Message.WCONTESTANTS:

                playgroundRemote.waitForContestants(teamID);
                outMessage = new Message(Message.ACK);
                break;
            case Message.FCOACHADV:

                playgroundRemote.followCoachAdvice(contestantID,teamID);
                outMessage = new Message(Message.ACK);
                break;
            case Message.INFREF:

                playgroundRemote.informReferee(teamID);
                outMessage = new Message(Message.ACK);
                break;
            case Message.STRIAL:
                playgroundRemote.startTrial();
                outMessage = new Message(Message.ACK);
                break;
            case Message.GREADY:
                playgroundRemote.getReady(teamID);
                outMessage = new Message(Message.ACK);
                break;
            case Message.DONE:
                playgroundRemote.done(teamID);
                outMessage = new Message(Message.ACK);
                break;
            case Message.ASSTRIALDEC:
                playgroundRemote.assertTrialDecision();
                outMessage = new Message(Message.ACK);
                break;

            default:
                GenericIO.writelnString("Invalid message type");
                break;

        }
        return outMessage;
    }

    /**
     * Tell the service if it is allowed to end or not.
     * @return True if the system can terminate, false otherwise.
     */
    @Override
    public boolean serviceEnded() {
        return serviceEnded;
    }
}

