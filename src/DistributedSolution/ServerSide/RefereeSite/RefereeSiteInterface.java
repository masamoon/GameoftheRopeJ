package DistributedSolution.ServerSide.RefereeSite;

import DistributedSolution.Communication.Message.Message;
import DistributedSolution.Communication.Message.MessageException;
import DistributedSolution.Communication.ServerCom;
import DistributedSolution.Communication.ServerInterface;
import genclass.GenericIO;

/**
 * Created by Andre on 12/04/2016.
 */
public class RefereeSiteInterface implements ServerInterface {

    private RefereeSiteRemote refereeSiteRemote;

    private boolean serviceEnded;

    public RefereeSiteInterface(RefereeSiteRemote refereeSiteRemote){

        this.refereeSiteRemote = refereeSiteRemote;

    }

    @Override
    public Message processAndReply(Message inMessage, ServerCom scon) throws MessageException {
        Message outMessage = null;                           // mensagem de resposta

        switch (inMessage.getType()) {
            case Message.ANNGAME:
                refereeSiteRemote.announceGame();
                outMessage = new Message(Message.ACK);
                break;
            case Message.ANNMATCH:
                refereeSiteRemote.announceMatch();
                outMessage = new Message(Message.ACK);
                break;
            case Message.DGAMEWINNER:
                refereeSiteRemote.declareGameWinner();
                outMessage = new Message(Message.ACK);
                break;
            case Message.DMATCHWINNER:
                refereeSiteRemote.declareMatchWinner();
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
