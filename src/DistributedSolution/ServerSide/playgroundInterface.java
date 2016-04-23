package DistributedSolution.ServerSide;

import DistributedSolution.Message.Message;
import DistributedSolution.Message.MessageException;

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

        switch (inMessage.getType()) {

        }
        return outMessage;
    }
}

