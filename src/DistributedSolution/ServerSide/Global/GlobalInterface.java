package DistributedSolution.ServerSide.Global;

import DistributedSolution.Communication.Message.Message;
import DistributedSolution.Communication.Message.MessageException;
import DistributedSolution.Communication.ServerCom;
import DistributedSolution.Communication.ServerInterface;
import genclass.GenericIO;

/**
 * Created by Andre on 12/04/2016.
 */
public class GlobalInterface implements ServerInterface {
    private GlobalRemote globalRemote;

    private boolean serviceEnded;


    public GlobalInterface(GlobalRemote globalRemote){

        this.globalRemote = globalRemote;

    }

    @Override
    public Message processAndReply(Message inMessage, ServerCom scon) throws MessageException {
        Message outMessage = null;                           // mensagem de resposta

        switch (inMessage.getType()) {
            case Message.SETNFIC: if ((inMessage.getFName () == null) || (inMessage.getFName ().equals ("")))
                throw new MessageException ("File does not exist", inMessage);
                break;
            case Message.MINPROGRESS:
                boolean matchInProgress = globalRemote.matchInProgress();
                if(matchInProgress)
                    outMessage = new Message(Message.POSITIVE);
                else
                    outMessage = new Message(Message.NEGATIVE);
                break;

            case Message.GFINISHED:
                boolean gameFinished = globalRemote.gameFinished();
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

    /**
     * Tell the service if it is allowed to end or not.
     * @return True if the system can terminate, false otherwise.
     */
    @Override
    public boolean serviceEnded() {
        return serviceEnded;
    }
}
