package DistributedSolution.ServerSide.Bench;

import DistributedSolution.Communication.ClientCom;
import DistributedSolution.Communication.CommConst;
import DistributedSolution.Communication.Message.Message;
import DistributedSolution.ClientSide.Coach.CoachState;
import genclass.GenericIO;

import static java.lang.Thread.sleep;

/**
 * Created by Andre on 30/04/2016.
 */
public class BenchGlobalStub {

    public BenchGlobalStub (){

    }

    public boolean matchInProgress(){
        GenericIO.writelnString("entering match in progress...");
        ClientCom con = new ClientCom(CommConst.globalServerName, CommConst.globalServerPort);
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

    public void selectTeam(int teamID, int team1, int team2, int team3){
        ClientCom con = new ClientCom(CommConst.globalServerName, CommConst.globalServerPort);
        Message inMessage, outMessage;
        while (!con.open()) // aguarda ligação
        {
            try {
                GenericIO.writelnString("connection not open");
                sleep((long) (10));
            } catch (InterruptedException e) {
            }
        }
        outMessage = new Message(Message.STEAM,teamID,team1,team2,team3);

        con.writeObject(outMessage);
        inMessage = (Message) con.readObject();
        if (inMessage.getType () != Message.ACK) {
            GenericIO.writelnString ("Thread: Tipo inválido! teve: " + inMessage.getType () + " esperava " + Message.ACK);
            GenericIO.writelnString(inMessage.toString());
            System.exit(1);
        }

        con.close ();
    }

    public void setCoachState (int teamID, CoachState state){
        ClientCom con = new ClientCom(CommConst.globalServerName, CommConst.globalServerPort);
        Message inMessage, outMessage;
        while (!con.open()) // aguarda ligação
        {
            try {
                GenericIO.writelnString("connection not open");
                sleep((long) (10));
            } catch (InterruptedException e) {
            }
        }
        outMessage = new Message(Message.SCOACHSTATE,teamID,state);
        con.writeObject(outMessage);
        inMessage = (Message) con.readObject();
        if (inMessage.getType () != Message.ACK) {
            GenericIO.writelnString ("Thread: Tipo inválido! teve: " + inMessage.getType () + " esperava " + Message.ACK);
            GenericIO.writelnString(inMessage.toString());
            System.exit(1);
        }

        con.close ();

    }

    public void setStrength(int contestantID, int teamID, int str ){
        ClientCom con = new ClientCom(CommConst.globalServerName, CommConst.globalServerPort);
        Message inMessage, outMessage;
        while (!con.open()) // aguarda ligação
        {
            try {
                GenericIO.writelnString("connection not open");
                sleep((long) (10));
            } catch (InterruptedException e) {
            }
        }
        outMessage = new Message(Message.SSTRENGTH,teamID,str);
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
