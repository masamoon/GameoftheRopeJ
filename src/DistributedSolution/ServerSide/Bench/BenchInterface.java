package DistributedSolution.ServerSide.Bench;

import DistributedSolution.Communication.Message.Message;
import DistributedSolution.Communication.Message.MessageException;
import DistributedSolution.Communication.ServerCom;
import DistributedSolution.Communication.ServerInterface;
import genclass.GenericIO;

import java.net.SocketException;

/**
 * Created by Andre on 11/04/2016.
 */
public class BenchInterface implements ServerInterface {

    private BenchRemote benchRemote;

    private boolean serviceEnded;


    public BenchInterface(BenchRemote benchRemote) {

        this.benchRemote = benchRemote;

    }

    @Override
    public Message processAndReply(Message inMessage, ServerCom scon) throws MessageException,SocketException {
        Message outMessage = null;
        int teamID = inMessage.getTeamID();
        int contestantID = inMessage.getContestantID();

        switch (inMessage.getType()) {
            case Message.CCONTESTANTS:

                benchRemote.callContestants(teamID);
                outMessage = new Message(Message.ACK);
                break;
           /* case Message.WCONTESTANTS:
                benchRemote.wakeContestants();
                outMessage = new Message(Message.ACK);
                break;*/
            case Message.SDOWN:
                benchRemote.sitDown(contestantID,teamID);
                outMessage = new Message(Message.ACK);
                break;
            case Message.REVNOTES:
                benchRemote.reviewNotes(teamID);
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