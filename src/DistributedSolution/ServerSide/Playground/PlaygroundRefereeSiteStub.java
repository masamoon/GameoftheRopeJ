package DistributedSolution.ServerSide.Playground;

import DistributedSolution.Communication.ClientCom;
import DistributedSolution.Communication.CommConst;
import DistributedSolution.Communication.Message.Message;
import genclass.GenericIO;

import static java.lang.Thread.sleep;

/**
 * Created by Andre on 30/04/2016.
 */
public class PlaygroundRefereeSiteStub {

    public PlaygroundRefereeSiteStub(){}

    public int getGamesNum(){
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
        outMessage = new Message(Message.GGAMESNUM);
        con.writeObject(outMessage);


        inMessage = (Message) con.readObject();
        if (inMessage.getType () != Message.GGAMESNUMR) {
            GenericIO.writelnString ("Thread: Tipo inválido! teve: " + inMessage.getType () + " esperava " + Message.ACK);
            GenericIO.writelnString(inMessage.toString());
            System.exit(1);
        }

        con.close ();


        return inMessage.getGamesNum();
    }

}
