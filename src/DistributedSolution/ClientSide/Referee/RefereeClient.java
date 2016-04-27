package DistributedSolution.ClientSide.Referee;

import DistributedSolution.Communication.ClientCom;
import DistributedSolution.Communication.CommConst;
import DistributedSolution.Communication.Message.Message;
import genclass.GenericIO;

import java.util.Arrays;

import static java.lang.Thread.sleep;

/**
 * Created by Andre on 26/04/2016.
 */
public class RefereeClient {
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {

        RefereeRefereeSiteStub refereeRefereeSiteStub = new RefereeRefereeSiteStub(CommConst.refereeSiteServerName, CommConst.refereeSiteServerPort);
        RefereePlaygroundStub refereePlaygroundStub = new RefereePlaygroundStub(CommConst.playgroundServerName, CommConst.playgroundServerPort);
        RefereeGlobalStub refereeGlobalStub = new RefereeGlobalStub(CommConst.globalServerName, CommConst.globalServerPort);

        Referee referee = new Referee(refereePlaygroundStub,refereeRefereeSiteStub,refereeGlobalStub);

        referee.start();

        GenericIO.writelnString("Referee starting");

        try {
            referee.join ();
        } catch (InterruptedException e) {}


        System.out.println("Sending TERMINATE message to the logging");

        Message inMessage, outMessage;
        ClientCom con = new ClientCom(CommConst.loggServerName, CommConst.loggServerPort);
        while (!con.open()) {
            try {
                sleep((long) (10));
            } catch (InterruptedException e) {
            }
        }
        outMessage = new Message(Message.TERMINATE);
        con.writeObject(outMessage);
        inMessage = (Message) con.readObject();
        if (inMessage.getType() != Message.ACK) {
            System.out.println("Tipo Inválido. Message:" + inMessage.toString());
            System.out.println(Arrays.toString(Thread.currentThread().getStackTrace()));
            System.exit(1);
        }
        con.close();
    }
}
