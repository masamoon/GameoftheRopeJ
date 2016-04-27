package DistributedSolution.ClientSide.Contestant;

import DistributedSolution.ClientSide.ClientCom;
import DistributedSolution.Message.Message;
import DistributedSolution.ServerSide.Playground.Playground;
import genclass.GenericIO;

import static java.lang.Thread.sleep;

/**
 * Created by Andre on 19/04/2016.
 */
public class ContestantBenchStub {

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

    ContestantBenchStub(String serverUrl, int portNumbStorage) {
        this.serverPortNumb = portNumbStorage;
        this.serverHostName = serverUrl;
    }

    public void sitDown(int contestantID, int teamID, Playground playground) {

        ClientCom con = new ClientCom(serverHostName, serverPortNumb);
        Message inMessage, outMessage;

        while (!con.open()) // aguarda ligação
        {
            try {
                sleep((long) (10));
            } catch (InterruptedException e) {
            }
        }

        outMessage = new Message(Message.SDOWN, teamID,contestantID);
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
