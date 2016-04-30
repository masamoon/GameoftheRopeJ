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
        int teamID;
        int contestantID;
        boolean b;

        switch (inMessage.getType()) {
            case Message.CCONTESTANTS:
                teamID = inMessage.getInt1();
                benchRemote.callContestants(teamID);
                outMessage = new Message(Message.ACK);
                break;
           /* case Message.WCONTESTANTS:
                benchRemote.wakeContestants();
                outMessage = new Message(Message.ACK);
                break;*/
            case Message.SDOWN:
                teamID = inMessage.getInt1();
                contestantID = inMessage.getInt2();
                benchRemote.sitDown(contestantID,teamID);
                outMessage = new Message(Message.ACK);
                break;
            case Message.REVNOTES:
                teamID = inMessage.getInt1();
                benchRemote.reviewNotes(teamID);
                outMessage = new Message(Message.ACK);
                break;
            case Message.STRIALCALLED:
                b = inMessage.getB();
                benchRemote.setTrialCalled(b);
                outMessage = new Message(Message.ACK);
                break;
            case Message.WBENCH:
                benchRemote.wakeBench();
                outMessage = new Message(Message.ACK);
                break;
            case Message.SBENCHCALLED:
                b = inMessage.getB();
                teamID = inMessage.getInt1();
                benchRemote.setBenchCalled(teamID,b);
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