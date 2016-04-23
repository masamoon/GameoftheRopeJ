package DistributedSolution.ServerSide;

import DistributedSolution.Message.Message;
import DistributedSolution.Message.MessageException;
import genclass.GenericIO;

/**
 * Created by Andre on 11/04/2016.
 */
public class BenchInterface {

    private Bench bench;


    public BenchInterface(Bench bench) {

        this.bench = bench;

    }

    public Message processAndReply(Message inMessage) throws MessageException {
        Message outMessage = null;

        switch (inMessage.getType()) {
            case Message.CCONTESTANTS:
                outMessage = new Message(Message.ACK);
                break;
            case Message.WCONTESTANTS:
                outMessage = new Message(Message.ACK);
                break;
            case Message.SDOWN:
                outMessage = new Message(Message.ACK);
                break;
            default:
                GenericIO.writelnString("Invalid message type");
                break;

        }
        return outMessage;

    }
}