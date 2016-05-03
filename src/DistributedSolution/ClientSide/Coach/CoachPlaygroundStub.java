package DistributedSolution.ClientSide.Coach;

import DistributedSolution.Communication.ClientCom;
import DistributedSolution.Communication.CommConst;
import DistributedSolution.Communication.Message.Message;
import genclass.GenericIO;

import java.util.Arrays;

import static java.lang.Thread.sleep;


public class CoachPlaygroundStub {

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

    public CoachPlaygroundStub(String serverUrl, int portNumb) {
        this.serverPortNumb = portNumb;
        this.serverHostName = serverUrl;
    }

    public void waitForContestants(int teamID){
        ClientCom con = new ClientCom(serverHostName, serverPortNumb);
        Message inMessage, outMessage;

        while (!con.open()) // aguarda ligação
        {
            try {
                sleep((long) (10));
            } catch (InterruptedException e) {
            }
        }

        outMessage = new Message(Message.WCONTESTANTS, teamID);
        con.writeObject(outMessage);
        inMessage = (Message) con.readObject();
        if (inMessage.getType () != Message.ACK) {
            GenericIO.writelnString ("Thread: Tipo inválido! teve: " + inMessage.getType () + " esperava " + Message.ACK);
            GenericIO.writelnString(inMessage.toString());
            System.exit(1);
        }

        con.close ();
    }

    public void informReferee(int teamID){
        ClientCom con = new ClientCom(serverHostName, serverPortNumb);
        Message inMessage, outMessage;

        while (!con.open()) // aguarda ligação
        {
            try {
                sleep((long) (10));
            } catch (InterruptedException e) {
            }
        }

        outMessage = new Message(Message.INFREF, teamID);
        con.writeObject(outMessage);
        inMessage = (Message) con.readObject();
        if (inMessage.getType () != Message.ACK) {
            GenericIO.writelnString ("Thread: Tipo inválido! teve: " + inMessage.getType () + " esperava " + Message.ACK);
            GenericIO.writelnString(inMessage.toString());
            System.exit(1);
        }

        con.close ();
    }
    public void terminate(){

        Message  outMessage;
        ClientCom con = new ClientCom(CommConst.playgroundServerName, CommConst.playgroundServerPort);
        while (!con.open()) {
            try {
                sleep((long) (10));
            } catch (InterruptedException e) {
            }
        }
        outMessage = new Message(Message.TERMINATE);
        con.writeObject(outMessage);
        con.close();
    }


}
