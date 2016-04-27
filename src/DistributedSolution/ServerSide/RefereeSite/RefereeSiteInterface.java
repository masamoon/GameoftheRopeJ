package DistributedSolution.ServerSide.RefereeSite;

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
                refereeSite.announceGame();
                outMessage = new Message(Message.ACK);
                break;
            case Message.ANNMATCH:
                refereeSite.announceMatch();
                outMessage = new Message(Message.ACK);
                break;
            case Message.DGAMEWINNER:
                refereeSite.declareGameWinner();
                outMessage = new Message(Message.ACK);
                break;
            case Message.DMATCHWINNER:
                refereeSite.declareMatchWinner();
                outMessage = new Message(Message.ACK);
                break;
            default:
                GenericIO.writelnString("Invalid message type");
                break;
        }

        return outMessage;
    }
}
