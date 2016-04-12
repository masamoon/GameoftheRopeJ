package DistributedSolution.ServerSide;

import DistributedSolution.Message.Message;
import DistributedSolution.Message.MessageException;

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

        return outMessage;
    }
}
