package DistributedSolution.ServerSide;

import DistributedSolution.Message.Message;
import DistributedSolution.Message.MessageException;
import genclass.GenericIO;

/**
 * Created by Andre on 12/04/2016.
 */
public class RefereeSiteInterface {
    private RefereeSite refereeSite;


    public RefereeSiteInterface(RefereeSite refereeSite){

        this.refereeSite =refereeSite;

    }

    public Message processAndReply(Message inMessage) throws MessageException {
        Message outMessage = null;                           // mensagem de resposta

        switch (inMessage.getType()) {
            case Message.ANNGAME:
                outMessage = new Message(Message.ACK);
                break;
            case Message.ANNMATCH:
                outMessage = new Message(Message.ACK);
                break;
            case Message.DGAMEWINNER:
                outMessage = new Message(Message.ACK);
                break;
            case Message.DMATCHWINNER:
                outMessage = new Message(Message.ACK);
                break;
            default:
                GenericIO.writelnString("Invalid message type");
                break;
        }

        return outMessage;
    }
}
