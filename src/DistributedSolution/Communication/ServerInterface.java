package DistributedSolution.Communication;

import DistributedSolution.Communication.Message.Message;
import DistributedSolution.Communication.Message.MessageException;

import java.net.SocketException;

public interface ServerInterface {
    /**
     * Processes the received messages and replies to the entity that sent it.
     *
     * @param inMessage The received message.
     * @param scon Server communication.
     * @return Returns the reply to the received message.
     * @throws MessageException
     * @throws SocketException
     */
    Message processAndReply (Message inMessage, ServerCom scon) throws MessageException, SocketException;

    /**
     * Tell the service if it is allowed to end or not.
     * @return True if the system can terminate, false otherwise.
     */
    boolean serviceEnded();
}
