package DistributedSolution.ClientSide.Coach;

import DistributedSolution.Communication.ClientCom;
import DistributedSolution.Communication.CommConst;
import DistributedSolution.Communication.GameParameters;
import DistributedSolution.Communication.Message.Message;
import genclass.GenericIO;

import java.util.ArrayList;
import java.util.Arrays;

import static java.lang.Thread.sleep;

/**
 * Created by Andre on 26/04/2016.
 */
public class CoachClient {
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {

        CoachBenchStub coachBenchStub = new CoachBenchStub(CommConst.benchServerName, CommConst.benchServerPort);
        CoachPlaygroundStub coachPlaygroundStub = new CoachPlaygroundStub(CommConst.playgroundServerName, CommConst.playgroundServerPort);
        CoachGlobalStub coachGlobalStub = new CoachGlobalStub(CommConst.globalServerName,CommConst.globalServerPort);

        ArrayList<Coach> coaches = new ArrayList<>(GameParameters.nTeams); //remove static
        for (int i = 0; i < GameParameters.nTeams; i++) //remove static
            coaches.add(new Coach(i,coachBenchStub,coachPlaygroundStub,coachGlobalStub));

        GenericIO.writelnString("Number of coaches: " + coaches.size());
        for (Coach c : coaches)
            c.start();

        for (Coach c : coaches) {
            try {
                c.join ();
            } catch (InterruptedException e) {}
        }

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
