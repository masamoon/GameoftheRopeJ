package DistributedSolution.ClientSide.Contestant;

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
public class ContestantClient {
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {

        ContestantBenchStub contestantBenchStub = new ContestantBenchStub(CommConst.benchServerName, CommConst.benchServerPort);
        ContestantPlaygroundStub contestantPlaygroundStub = new ContestantPlaygroundStub(CommConst.playgroundServerName, CommConst.playgroundServerPort);
        ContestantGlobalStub contestantGlobalStub = new ContestantGlobalStub(CommConst.playgroundServerName, CommConst.playgroundServerPort);

        ArrayList<Contestant> contestants = new ArrayList<>(GameParameters.nContestants*GameParameters.nTeams);
        for(int i = 0; i< GameParameters.nTeams; i++) {
            for (int j = 0; j < GameParameters.nContestants; j++)
                contestants.add(new Contestant(j,i,contestantBenchStub,contestantPlaygroundStub,contestantGlobalStub));
        }

        GenericIO.writelnString("Number of contestants: " + contestants.size());
        for (Contestant c : contestants)
            c.start();

        for (Contestant c : contestants) {
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

