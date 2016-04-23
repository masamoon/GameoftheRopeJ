package DistributedSolution.ServerSide;

import DistributedSolution.Message.Message;
import DistributedSolution.Message.MessageException;

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
                break;
            case Message.WCONTESTANTS:
                break;
            case Message.SDOWN:
                break;
            

        }


        return outMessage;

    }
}