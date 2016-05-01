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

        int strength;
        int teamID;
        int contestantID;

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
                teamID =  inMessage.getInt1();
                playgroundRemote.waitForContestants(teamID);
                outMessage = new Message(Message.ACK);
                break;
            case Message.FCOACHADV:
                teamID =  inMessage.getInt1();
                contestantID = inMessage.getInt2();
                playgroundRemote.followCoachAdvice(contestantID,teamID);
                outMessage = new Message(Message.ACK);
                break;
            case Message.INFREF:
                teamID =  inMessage.getInt1();
                playgroundRemote.informReferee(teamID);
                outMessage = new Message(Message.ACK);
                break;
            case Message.STRIAL:
                playgroundRemote.startTrial();
                outMessage = new Message(Message.ACK);
                break;
            case Message.GREADY:
                teamID = inMessage.getInt1();
                contestantID =  inMessage.getInt2();
                strength = inMessage.getInt3();
                playgroundRemote.getReady(contestantID, teamID, strength);
                outMessage = new Message(Message.ACK);
                break;
            case Message.DONE:
                teamID =  inMessage.getInt1();
                contestantID = inMessage.getInt2();
                playgroundRemote.done(contestantID, teamID);
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

