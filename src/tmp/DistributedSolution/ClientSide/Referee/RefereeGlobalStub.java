package DistributedSolution.ClientSide.Referee;

import DistributedSolution.Communication.ClientCom;
import DistributedSolution.Communication.CommConst;
import DistributedSolution.Communication.Message.Message;
import com.sun.deploy.util.SessionState;
import genclass.GenericIO;

import java.util.Arrays;

import static java.lang.Thread.sleep;

public class RefereeGlobalStub {
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

    public RefereeGlobalStub(String serverUrl, int portNumbStorage) {
        this.serverPortNumb = portNumbStorage;
        this.serverHostName = serverUrl;
    }

    public boolean matchInProgress(){
        ClientCom con = new ClientCom(serverHostName, serverPortNumb);
        Message inMessage, outMessage;

        while (!con.open()) // aguarda ligação
        {
            try {
                sleep((long) (10));
            } catch (InterruptedException e) {
            }
        }

        outMessage = new Message(Message.MINPROGRESS);
        con.writeObject(outMessage);
        inMessage = (Message) con.readObject();
        if ((inMessage.getType() != Message.POSITIVE) && (inMessage.getType() != Message.NEGATIVE)) {
            GenericIO.writelnString ("Thread: Tipo inválido! teve: " + inMessage.getType () + " esperava " + Message.NEGATIVE +
                    " ou " + Message.POSITIVE);
            GenericIO.writelnString(inMessage.toString());
            System.exit(1);
        }
        con.close ();

        if (inMessage.getType() == Message.POSITIVE) {
            return true;
        } else {
            return false;
        }
    }

    public boolean gameFinished(){
        ClientCom con = new ClientCom(serverHostName, serverPortNumb);
        Message inMessage, outMessage;

        while (!con.open()) // aguarda ligação
        {
            try {
                sleep((long) (10));
            } catch (InterruptedException e) {
            }
        }

        outMessage = new Message(Message.GFINISHED);
        con.writeObject(outMessage);
        inMessage = (Message) con.readObject();
        if ((inMessage.getType() != Message.POSITIVE) && (inMessage.getType() != Message.NEGATIVE)) {
            GenericIO.writelnString ("Thread: Tipo inválido! teve: " + inMessage.getType () + " esperava " + Message.NEGATIVE +
                    " ou " + Message.POSITIVE);
            GenericIO.writelnString(inMessage.toString());
            System.exit(1);
        }
        con.close ();

        if (inMessage.getType() == Message.POSITIVE) {
            return true;
        } else {
            return false;
        }


    }
    public void terminate (){
        Message  outMessage;
        ClientCom con = new ClientCom(CommConst.globalServerName, CommConst.globalServerPort);
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
