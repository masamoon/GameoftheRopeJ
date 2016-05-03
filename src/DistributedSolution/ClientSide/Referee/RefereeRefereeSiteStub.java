package DistributedSolution.ClientSide.Referee;

import DistributedSolution.Communication.ClientCom;
import DistributedSolution.Communication.CommConst;
import DistributedSolution.Communication.Message.Message;
import genclass.GenericIO;

import static java.lang.Thread.sleep;

public class RefereeRefereeSiteStub {

    /**
     *  Nome do sistema computacional onde está localizado o servidor
     *    @serialField serverHostName
     */

    private String serverHostName;

    /**
     *  Número do port de escuta do servidor
     *    @serialField serverPortNumb
     */

    private int serverPortNumb;

    public RefereeRefereeSiteStub(String serverUrl, int portNumbStorage) {
        this.serverPortNumb = portNumbStorage;
        this.serverHostName = serverUrl;
    }

    public void announceGame(){
        ClientCom con = new ClientCom(serverHostName, serverPortNumb);
        Message inMessage, outMessage;

        while (!con.open()) // aguarda ligação
        {
            try {
                sleep((long) (10));
            } catch (InterruptedException e) {
            }
        }

        outMessage = new Message(Message.ANNGAME);
        con.writeObject(outMessage);
        inMessage = (Message) con.readObject();
        if (inMessage.getType () != Message.ACK) {
            GenericIO.writelnString ("Thread: Tipo inválido! teve: " + inMessage.getType () + " esperava " + Message.ACK);
            GenericIO.writelnString(inMessage.toString());
            System.exit(1);
        }

        con.close ();
    }

    public void announceMatch(){
        ClientCom con = new ClientCom(serverHostName, serverPortNumb);
        Message inMessage, outMessage;

        while (!con.open()) // aguarda ligação
        {
            try {
                sleep((long) (10));
            } catch (InterruptedException e) {
            }
        }

        outMessage = new Message(Message.ANNMATCH);
        GenericIO.writelnString("OUTCOMING MESSAGE: "+outMessage.getType());
        con.writeObject(outMessage);
        inMessage = (Message) con.readObject();
        if (inMessage.getType () != Message.ACK) {
            GenericIO.writelnString ("Thread: Tipo inválido! teve: " + inMessage.getType () + " esperava " + Message.ACK);
            GenericIO.writelnString(inMessage.toString());
            System.exit(1);
        }

        con.close ();
    }

    public void waitForBench(){
        ClientCom con = new ClientCom(serverHostName, serverPortNumb);
        Message inMessage, outMessage;

        while (!con.open()) // aguarda ligação
        {
            try {
                sleep((long) (10));
            } catch (InterruptedException e) {
            }
        }
        outMessage = new Message(Message.WBENCH);
        GenericIO.writelnString("OUTCOMING MESSAGE: "+outMessage.getType());
        con.writeObject(outMessage);
        inMessage = (Message) con.readObject();
        if (inMessage.getType () != Message.ACK) {
            GenericIO.writelnString ("Thread: Tipo inválido! teve: " + inMessage.getType () + " esperava " + Message.ACK);
            GenericIO.writelnString(inMessage.toString());
            System.exit(1);
        }

        con.close ();
    }

    public void declareGameWinner(){
        ClientCom con = new ClientCom(serverHostName, serverPortNumb);
        Message inMessage, outMessage;

        while (!con.open()) // aguarda ligação
        {
            try {
                sleep((long) (10));
            } catch (InterruptedException e) {
            }
        }

        outMessage = new Message(Message.DGAMEWINNER);
        con.writeObject(outMessage);
        inMessage = (Message) con.readObject();
        if (inMessage.getType () != Message.ACK) {
            GenericIO.writelnString ("Thread: Tipo inválido! teve: " + inMessage.getType () + " esperava " + Message.ACK);
            GenericIO.writelnString(inMessage.toString());
            System.exit(1);
        }

        con.close ();
    }

    public void declareMatchWinner(){
        ClientCom con = new ClientCom(serverHostName, serverPortNumb);
        Message inMessage, outMessage;

        while (!con.open()) // aguarda ligação
        {
            try {
                sleep((long) (10));
            } catch (InterruptedException e) {
            }
        }

        outMessage = new Message(Message.DMATCHWINNER);
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

        ClientCom con = new ClientCom(CommConst.refereeSiteServerName, CommConst.refereeSiteServerPort);
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
