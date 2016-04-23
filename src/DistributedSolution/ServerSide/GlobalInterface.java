package DistributedSolution.ServerSide;

import DistributedSolution.Message.Message;
import DistributedSolution.Message.MessageException;

/**
 * Created by Andre on 12/04/2016.
 */
public class GlobalInterface {
    private Playground playground;


    public GlobalInterface(Playground playground){

        this.playground = playground;

    }

    public Message processAndReply(Message inMessage) throws MessageException {
        Message outMessage = null;                           // mensagem de resposta

        switch (inMessage.getType()) {
            case Message.SETNFIC: if ((inMessage.getFName () == null) || (inMessage.getFName ().equals ("")))
                throw new MessageException ("File does not exist", inMessage);
                break;


        }


        return outMessage;
    }
}
