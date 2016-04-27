package DistributedSolution.ServerSide.Bench;

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
        int teamID = inMessage.getTeamID();
        int contestantID = inMessage.getContestantID();

        switch (inMessage.getType()) {
            case Message.CCONTESTANTS:

                bench.callContestants(teamID);
                outMessage = new Message(Message.ACK);
                break;
            case Message.WCONTESTANTS:
                bench.wakeContestants();
                outMessage = new Message(Message.ACK);
                break;
            case Message.SDOWN:
                bench.sitDown(contestantID,teamID);
                outMessage = new Message(Message.ACK);
                break;
            default:
                GenericIO.writelnString("Invalid message type");
                break;

        }
        return outMessage;

    }
}