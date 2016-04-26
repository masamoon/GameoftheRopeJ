package DistributedSolution.ServerSide;

import DistributedSolution.Message.Message;
import DistributedSolution.Message.MessageException;
import genclass.GenericIO;

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
            case Message.WFCALLING:
                outMessage = new Message(Message.ACK);
                break;
            case Message.CTRIAL:
                outMessage = new Message(Message.ACK);
                break;
            case Message.WCONTESTANTS:
                outMessage = new Message(Message.ACK);
                break;
            case Message.FCOACHADV:
                outMessage = new Message(Message.ACK);
                break;
            case Message.INFREF:
                outMessage = new Message(Message.ACK);
                break;
            case Message.STRIAL:
                outMessage = new Message(Message.ACK);
                break;
            case Message.GREADY:
                outMessage = new Message(Message.ACK);
                break;
            case Message.DONE:
                outMessage = new Message(Message.ACK);
                break;
            case Message.ASSTRIALDEC:
                outMessage = new Message(Message.ACK);
                break;
            case Message.REVNOTES:
                outMessage = new Message(Message.ACK);
                break;
            default:
                GenericIO.writelnString("Invalid message type");
                break;

        }
        return outMessage;
    }
}

