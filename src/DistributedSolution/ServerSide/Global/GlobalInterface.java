package DistributedSolution.ServerSide.Global;

import DistributedSolution.Message.Message;
import DistributedSolution.Message.MessageException;
import DistributedSolution.ServerSide.Playground.Playground;
import genclass.GenericIO;

/**
 * Created by Andre on 12/04/2016.
 */
public class GlobalInterface {
    private Global global;


    public GlobalInterface(Global global){

        this.global = global;

    }

    public Message processAndReply(Message inMessage) throws MessageException {
        Message outMessage = null;                           // mensagem de resposta

        switch (inMessage.getType()) {
            case Message.SETNFIC: if ((inMessage.getFName () == null) || (inMessage.getFName ().equals ("")))
                throw new MessageException ("File does not exist", inMessage);
                break;
            case Message.MINPROGRESS:
                boolean matchInProgress = global.matchInProgress();
                if(matchInProgress)
                    outMessage = new Message(Message.POSITIVE);
                else
                    outMessage = new Message(Message.NEGATIVE);
                break;

            case Message.GFINISHED:
                boolean gameFinished = global.gameFinished();
                if(gameFinished)
                    outMessage = new Message(Message.POSITIVE);
                else
                    outMessage = new Message(Message.NEGATIVE);
                break;
            default:
                GenericIO.writelnString("Invalid message type");
                break;

        }


        return outMessage;
    }
}
