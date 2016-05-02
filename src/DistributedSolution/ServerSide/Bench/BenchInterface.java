package DistributedSolution.ServerSide.Bench;

import DistributedSolution.Communication.Message.Message;
import DistributedSolution.Communication.Message.MessageException;
import DistributedSolution.Communication.ServerCom;
import DistributedSolution.Communication.ServerInterface;
import genclass.GenericIO;

import java.net.SocketException;

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
        int strength;
        boolean b;

        switch (inMessage.getType()) {
            case Message.CCONTESTANTS:
                teamID = inMessage.getInt1();
                benchRemote.callContestants(teamID);
                outMessage = new Message(Message.ACK);
                break;
            case Message.SSTRENGTHB:
                contestantID = inMessage.getInt1();
                teamID = inMessage.getInt2();
                strength = inMessage.getInt3();
                benchRemote.setStrength(contestantID, teamID, strength);
                outMessage = new Message(Message.ACK);
                break;
            case Message.GSTRENGTHB:
                contestantID = inMessage.getInt1();
                teamID = inMessage.getInt2();
                int ret = benchRemote.getStrength(contestantID, teamID);
                outMessage = new Message(Message.ACK, ret);
                break;
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