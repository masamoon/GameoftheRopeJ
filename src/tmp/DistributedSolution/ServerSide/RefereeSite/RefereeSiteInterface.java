package DistributedSolution.ServerSide.RefereeSite;

import DistributedSolution.Communication.Message.Message;
import DistributedSolution.Communication.Message.MessageException;
import DistributedSolution.Communication.ServerCom;
import DistributedSolution.Communication.ServerInterface;
import genclass.GenericIO;

public class RefereeSiteInterface implements ServerInterface {

    private RefereeSiteRemote refereeSiteRemote;

    private boolean serviceEnded;

    public RefereeSiteInterface(RefereeSiteRemote refereeSiteRemote){

        this.refereeSiteRemote = refereeSiteRemote;

    }

    @Override
    public Message processAndReply(Message inMessage, ServerCom scon) throws MessageException {
        Message outMessage = null;                           // mensagem de resposta

        switch (inMessage.getType()) {
            case Message.ANNGAME:
                refereeSiteRemote.announceGame();
                outMessage = new Message(Message.ACK);
                break;
            case Message.ANNMATCH:
                refereeSiteRemote.announceMatch();
                outMessage = new Message(Message.ACK);
                break;
            case Message.DGAMEWINNER:
                refereeSiteRemote.declareGameWinner();
                outMessage = new Message(Message.ACK);
                break;
            case Message.DMATCHWINNER:
                refereeSiteRemote.declareMatchWinner();
                outMessage = new Message(Message.ACK);
                break;
            case Message.GGAMESNUM:
                int gamesNum = refereeSiteRemote.getGamesNum();
                outMessage = new Message(Message.GGAMESNUMR, gamesNum);
                break;
            case Message.SREADYFTRIAL:
                refereeSiteRemote.setReadyForTrial(inMessage.getB());
                outMessage = new Message(Message.ACK);
                break;
            case Message.BWAKEREF:
                refereeSiteRemote.benchWakeRef();
                outMessage = new Message(Message.ACK);
                break;
            case Message.GTRIALNUM:
                int trialNum = refereeSiteRemote.getTrialNum();
                outMessage = new Message(Message.GTRIALNUMR,trialNum);
                break;
            case Message.WBENCH:
                refereeSiteRemote.waitForBench();
                outMessage = new Message(Message.ACK);
                break;
            case Message.TERMINATE:
                refereeSiteRemote.terminate();
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
