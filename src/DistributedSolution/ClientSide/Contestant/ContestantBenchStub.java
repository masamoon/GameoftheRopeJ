package DistributedSolution.ClientSide.Contestant;

import DistributedSolution.Communication.ClientCom;
import DistributedSolution.Communication.CommConst;
import DistributedSolution.Communication.Message.Message;
import genclass.GenericIO;

import static java.lang.Thread.sleep;


public class ContestantBenchStub {

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

    public ContestantBenchStub(String serverUrl, int portNumb) {
        this.serverPortNumb = portNumb;
        this.serverHostName = serverUrl;
    }

    public void sitDown(int contestantID, int teamID) {

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

    public void setStrength(int contestantID, int teamID, int strength){
        ClientCom con = new ClientCom(serverHostName, serverPortNumb);
        Message inMessage, outMessage;

        while (!con.open()) // aguarda ligação
        {
            try {
                sleep((long) (10));
            } catch (InterruptedException e) {
            }
        }

        outMessage = new Message(Message.SSTRENGTHB,contestantID,teamID,strength);
        con.writeObject(outMessage);
        inMessage = (Message) con.readObject();
        if (inMessage.getType () != Message.ACK) {
            GenericIO.writelnString ("Thread: Tipo inválido! teve: " + inMessage.getType () + " esperava " + Message.ACK);
            GenericIO.writelnString(inMessage.toString());
            System.exit(1);
        }
        con.close ();
    }
    public int getStrength(int contestantID, int teamID){
        ClientCom con = new ClientCom(serverHostName, serverPortNumb);
        Message inMessage, outMessage;

        while (!con.open()) // aguarda ligação
        {
            try {
                sleep((long) (10));
            } catch (InterruptedException e) {
            }
        }

        outMessage = new Message(Message.GSTRENGTHB, contestantID, teamID);
        con.writeObject(outMessage);
        inMessage = (Message) con.readObject();
        if (inMessage.getType () != Message.ACK) {
            GenericIO.writelnString ("Thread: Tipo inválido! teve: " + inMessage.getType () + " esperava " + Message.ACK);
            GenericIO.writelnString(inMessage.toString());
            System.exit(1);
        }
        con.close ();

        return inMessage.getInt1();
    }

        public void terminate (){
            Message  outMessage;
            ClientCom con = new ClientCom(CommConst.benchServerName, CommConst.benchServerPort);
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
