package DistributedSolution.ServerSide.RefereeSite;

import DistributedSolution.ServerSide.States.RefereeState;
import DistributedSolution.Communication.ClientCom;
import DistributedSolution.Communication.CommConst;
import DistributedSolution.Communication.Message.Message;
import genclass.GenericIO;

import static java.lang.Thread.sleep;


public class RefereeSiteGlobalStub {

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


    public RefereeSiteGlobalStub(String serverUrl, int portNumb) {
        this.serverPortNumb = portNumb;
        this.serverHostName = serverUrl;
    }


    public void incrementTrialNum(){
        ClientCom con = new ClientCom(serverHostName, serverPortNumb);
        Message inMessage, outMessage;
        while (!con.open()) // aguarda ligação
        {
            try {
                GenericIO.writelnString("connection not open");
                sleep((long) (10));
            } catch (InterruptedException e) {
            }
        }
        outMessage = new Message(Message.ITRIALNUM);
        con.writeObject(outMessage);

        inMessage = (Message) con.readObject();
        if (inMessage.getType () != Message.ACK) {
            GenericIO.writelnString ("Thread: Tipo inválido! teve: " + inMessage.getType () + " esperava " + Message.ACK);
            GenericIO.writelnString(inMessage.toString());
            System.exit(1);
        }

        con.close ();
    }

    public void gameWinnerLinePoints(int teamID){
        ClientCom con = new ClientCom(serverHostName, serverPortNumb);
        Message inMessage, outMessage;
        while (!con.open()) // aguarda ligação
        {
            try {
                GenericIO.writelnString("connection not open");
                sleep((long) (10));
            } catch (InterruptedException e) {
            }
        }
        outMessage = new Message(Message.GWINNERLINEPOINTS,teamID);
        con.writeObject(outMessage);

        inMessage = (Message) con.readObject();
        if (inMessage.getType () != Message.ACK) {
            GenericIO.writelnString ("Thread: Tipo inválido! teve: " + inMessage.getType () + " esperava " + Message.ACK);
            GenericIO.writelnString(inMessage.toString());
            System.exit(1);
        }

        con.close ();
    }

    public void gameTieLine(){
        ClientCom con = new ClientCom(serverHostName, serverPortNumb);
        Message inMessage, outMessage;
        while (!con.open()) // aguarda ligação
        {
            try {
                GenericIO.writelnString("connection not open");
                sleep((long) (10));
            } catch (InterruptedException e) {
            }
        }
        outMessage = new Message(Message.GTIELINE);
        con.writeObject(outMessage);

        inMessage = (Message) con.readObject();
        if (inMessage.getType () != Message.ACK) {
            GenericIO.writelnString ("Thread: Tipo inválido! teve: " + inMessage.getType () + " esperava " + Message.ACK);
            GenericIO.writelnString(inMessage.toString());
            System.exit(1);
        }

        con.close ();
    }

    public void gameWinnerLineKO(int teamID){
        ClientCom con = new ClientCom(serverHostName, serverPortNumb);
        Message inMessage, outMessage;
        while (!con.open()) // aguarda ligação
        {
            try {
                GenericIO.writelnString("connection not open");
                sleep((long) (10));
            } catch (InterruptedException e) {
            }
        }
        outMessage = new Message(Message.GWINNERLINEKO,teamID);
        con.writeObject(outMessage);

        inMessage = (Message) con.readObject();
        if (inMessage.getType () != Message.ACK) {
            GenericIO.writelnString ("Thread: Tipo inválido! teve: " + inMessage.getType () + " esperava " + Message.ACK);
            GenericIO.writelnString(inMessage.toString());
            System.exit(1);
        }

        con.close ();
    }

    public void matchWinnerLine(int teamScore1 ,int teamScore2 ,int winnerID){
        ClientCom con = new ClientCom(serverHostName, serverPortNumb);
        Message inMessage, outMessage;
        while (!con.open()) // aguarda ligação
        {
            try {
                GenericIO.writelnString("connection not open");
                sleep((long) (10));
            } catch (InterruptedException e) {
            }
        }
        outMessage = new Message(Message.MWINNERLINE,teamScore1,teamScore2,winnerID);
        con.writeObject(outMessage);

        inMessage = (Message) con.readObject();
        if (inMessage.getType () != Message.ACK) {
            GenericIO.writelnString ("Thread: Tipo inválido! teve: " + inMessage.getType () + " esperava " + Message.ACK);
            GenericIO.writelnString(inMessage.toString());
            System.exit(1);
        }

        con.close ();
    }

    public void matchTieLine(){
        ClientCom con = new ClientCom(serverHostName, serverPortNumb);
        Message inMessage, outMessage;
        while (!con.open()) // aguarda ligação
        {
            try {
                GenericIO.writelnString("connection not open");
                sleep((long) (10));
            } catch (InterruptedException e) {
            }
        }
        outMessage = new Message(Message.MTIELINE);
        con.writeObject(outMessage);

        inMessage = (Message) con.readObject();
        if (inMessage.getType () != Message.ACK) {
            GenericIO.writelnString ("Thread: Tipo inválido! teve: " + inMessage.getType () + " esperava " + Message.ACK);
            GenericIO.writelnString(inMessage.toString());
            System.exit(1);
        }

        con.close ();
    }

    public void setRefereeState (RefereeState state){
        ClientCom con = new ClientCom(serverHostName, serverPortNumb);
        Message inMessage, outMessage;
        while (!con.open()) // aguarda ligação
        {
            try {
                GenericIO.writelnString("connection not open");
                sleep((long) (10));
            } catch (InterruptedException e) {
            }
        }
        outMessage = new Message(Message.SREFEREESTATE,state);
        con.writeObject(outMessage);
        inMessage = (Message) con.readObject();
        if (inMessage.getType () != Message.ACK) {
            GenericIO.writelnString ("Thread: Tipo inválido! teve: " + inMessage.getType () + " esperava " + Message.ACK);
            GenericIO.writelnString(inMessage.toString());
            System.exit(1);
        }

        con.close ();

    }

    public int getFlagPos(){
        ClientCom con = new ClientCom(serverHostName, serverPortNumb);
        Message inMessage, outMessage;
        while (!con.open()) // aguarda ligação
        {
            try {
                GenericIO.writelnString("connection not open");
                sleep((long) (10));
            } catch (InterruptedException e) {
            }
        }
        outMessage = new Message(Message.GFLAGPOS);
        con.writeObject(outMessage);
        inMessage = (Message) con.readObject();
        if (inMessage.getType () != Message.GFLAGPOSR) {
            GenericIO.writelnString ("Thread: Tipo inválido! teve: " + inMessage.getType () + " esperava " + Message.ACK);
            GenericIO.writelnString(inMessage.toString());
            System.exit(1);
        }

        return inMessage.getInt1();
    }

    public void incrementGamesNum(){
        ClientCom con = new ClientCom(serverHostName, serverPortNumb);
        Message inMessage, outMessage;
        while (!con.open()) // aguarda ligação
        {
            try {
                GenericIO.writelnString("connection not open");
                sleep((long) (10));
            } catch (InterruptedException e) {
            }
        }
        outMessage = new Message(Message.IGAMESNUM);
        con.writeObject(outMessage);
        inMessage = (Message) con.readObject();
        if (inMessage.getType () != Message.ACK) {
            GenericIO.writelnString ("Thread: Tipo inválido! teve: " + inMessage.getType () + " esperava " + Message.ACK);
            GenericIO.writelnString(inMessage.toString());
            System.exit(1);
        }


    }

    public void resetFlagPos(){
        ClientCom con = new ClientCom(serverHostName, serverPortNumb);
        Message inMessage, outMessage;
        while (!con.open()) // aguarda ligação
        {
            try {
                GenericIO.writelnString("connection not open");
                sleep((long) (10));
            } catch (InterruptedException e) {
            }
        }
        outMessage = new Message(Message.RESETFLAGPOS);
        con.writeObject(outMessage);
        inMessage = (Message) con.readObject();
        if (inMessage.getType () != Message.ACK) {
            GenericIO.writelnString ("Thread: Tipo inválido! teve: " + inMessage.getType () + " esperava " + Message.ACK);
            GenericIO.writelnString(inMessage.toString());
            System.exit(1);
        }

    }

    public void resetTrialNum(){
        ClientCom con = new ClientCom(serverHostName, serverPortNumb);
        Message inMessage, outMessage;
        while (!con.open()) // aguarda ligação
        {
            try {
                GenericIO.writelnString("connection not open");
                sleep((long) (10));
            } catch (InterruptedException e) {
            }
        }
        outMessage = new Message(Message.RESETTRIALNUM);
        con.writeObject(outMessage);
        inMessage = (Message) con.readObject();
        if (inMessage.getType () != Message.ACK) {
            GenericIO.writelnString ("Thread: Tipo inválido! teve: " + inMessage.getType () + " esperava " + Message.ACK);
            GenericIO.writelnString(inMessage.toString());
            System.exit(1);
        }

    }
}
