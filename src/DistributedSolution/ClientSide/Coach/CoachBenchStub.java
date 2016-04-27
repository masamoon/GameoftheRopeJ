package DistributedSolution.ClientSide.Coach;

import DistributedSolution.ClientSide.ClientCom;
import DistributedSolution.Message.Message;
import genclass.GenericIO;

import java.util.Random;

import static java.lang.Thread.sleep;

/**
 * Created by Andre on 19/04/2016.
 */
public class CoachBenchStub {


    /**
     *  Nome do sistema computacional onde está localizado o servidor
     *    @serialField serverHostName
     */

    private String serverHostName = null;

    /**
     *  Número do port de escuta do servidor
     *    @serialField serverPortNumb
     */

    private int serverPortNumb;

    public CoachBenchStub(String hostUrl, int portNum) {
        this.serverHostName = hostUrl;
        this.serverPortNumb = portNum;
    }

    public void callContestants (int teamID){
        ClientCom con = new ClientCom(serverHostName, serverPortNumb);
        Message inMessage, outMessage;

        while (!con.open()) // aguarda ligação
        {
            try {
                sleep((long) (10));
            } catch (InterruptedException e) {
            }
        }

        outMessage = new Message(Message.CCONTESTANTS, teamID);
        con.writeObject(outMessage);
        inMessage = (Message) con.readObject();
        if (inMessage.getType () != Message.ACK) {
            GenericIO.writelnString ("Thread: Tipo inválido! teve: " + inMessage.getType () + " esperava " + Message.ACK);
            GenericIO.writelnString(inMessage.toString());
            System.exit(1);
        }

        con.close ();
    }


}
