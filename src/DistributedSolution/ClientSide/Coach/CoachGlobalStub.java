package DistributedSolution.ClientSide.Coach;

import DistributedSolution.Communication.ClientCom;
import DistributedSolution.Communication.Message.Message;
import genclass.GenericIO;
import sun.net.www.content.text.Generic;

import static java.lang.Thread.sleep;

/**
 * Created by Andre on 26/04/2016.
 */
public class CoachGlobalStub {
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

    public CoachGlobalStub(String serverUrl, int portNumb) {
        this.serverPortNumb = portNumb;
        this.serverHostName = serverUrl;
    }

    public boolean matchInProgress(){
        GenericIO.writelnString("entering match in progress...");
        ClientCom con = new ClientCom(serverHostName, serverPortNumb);
        GenericIO.writelnString("Client com instantiated");
        Message inMessage, outMessage;
        GenericIO.writelnString("Checking connection");
        while (!con.open()) // aguarda ligação
        {
            try {
                GenericIO.writelnString("connection not open");
                sleep((long) (10));
            } catch (InterruptedException e) {
            }
        }
        GenericIO.writelnString("sending message match in progress...");
        outMessage = new Message(Message.MINPROGRESS);
        GenericIO.writelnString("outcoming message to server (coach): "+outMessage.getType());
        con.writeObject(outMessage);
        GenericIO.writelnString("wrote message to server (coach): "+outMessage.getType());

//        GenericIO.writelnString("incoming message from server (coach): "+con.readObject().toString());
        inMessage = (Message) con.readObject();
        GenericIO.writelnString("received message from server (coach): "+inMessage.getType());
        if ((inMessage.getType() != Message.POSITIVE) && (inMessage.getType() != Message.NEGATIVE)) {
            GenericIO.writelnString ("Thread: Tipo inválido! teve: " + inMessage.getType () + " esperava " + Message.NEGATIVE +
                    " ou " + Message.POSITIVE);
            GenericIO.writelnString(inMessage.toString());
            System.exit(1);
        }
        con.close ();

        if (inMessage.getType() == Message.POSITIVE) {
            GenericIO.writelnString("exiting match in progress POSITIVE");
            return true;
        } else {
            GenericIO.writelnString("exiting match in progress NEGATIVE");
            return false;
        }


    }


}
