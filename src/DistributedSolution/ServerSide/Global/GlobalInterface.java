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
            case Message.STEAM:
                globalRemote.selectTeam(inMessage.getInt1(),inMessage.getInt2(),inMessage.getInt3(),inMessage.getInt4());
                outMessage = new Message(Message.ACK);
                break;
            case Message.SCOACHSTATE:
                globalRemote.setCoachState(inMessage.getInt1(),inMessage.getCoachState());
                outMessage = new Message(Message.ACK);
                break;
            case Message.SSTRENGTH:
                globalRemote.setStrength(inMessage.getInt2(),inMessage.getInt1(),inMessage.getInt3());
                outMessage = new Message(Message.ACK);
                break;
            case Message.CFLAGPOS:
                globalRemote.changeFlagPos(inMessage.getInt1());
                outMessage = new Message(Message.ACK);
                break;

            case Message.SMINPROGRESS:
                globalRemote.setMatchInProgress(inMessage.getB());
                outMessage = new Message(Message.ACK);
                break;

            case Message.ITRIALNUM:
                globalRemote.incrementTrialNum();
                outMessage = new Message(Message.ACK);
                break;

            case Message.ITEAMSCORE:
                globalRemote.incTeamScore(inMessage.getInt1());
                outMessage = new Message(Message.ACK);
                break;

            case Message.GWINNERLINEPOINTS:
                globalRemote.gameWinnerLinePoints(inMessage.getInt1());
                outMessage = new Message(Message.ACK);
                break;

            case Message.GTIELINE:
                globalRemote.gameTieLine();
                outMessage = new Message(Message.ACK);
                break;

            case Message.GWINNERLINEKO:
                globalRemote.gameWinnerLineKO(inMessage.getInt1());
                outMessage = new Message(Message.ACK);
                break;

            case Message.MWINNERLINE:
                globalRemote.matchWinnerLine(inMessage.getInt2(),inMessage.getInt1(),inMessage.getInt3());
                outMessage = new Message(Message.ACK);
                break;

            case Message.MTIELINE:
                globalRemote.matchTieLine();
                outMessage = new Message(Message.ACK);
                break;

            case Message.SREFEREESTATE:
                globalRemote.setRefereeState(inMessage.getRefereeState());
                break;

            case Message.GFLAGPOS:
                int flagpos = globalRemote.getFlagPos();
                outMessage = new Message(Message.GFLAGPOSR, flagpos);
                break;

            case Message.IGAMESNUM:
                globalRemote.incrementGamesNum();
                outMessage = new Message(Message.IGAMESNUM);
                break;

            case Message.RESETFLAGPOS:
                globalRemote.resetFlagPos();
                outMessage = new Message(Message.RESETFLAGPOS);
                break;

            case Message.RESETTRIALNUM:
                globalRemote.resetTrialNum();
                outMessage = new Message(Message.RESETTRIALNUM);
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
