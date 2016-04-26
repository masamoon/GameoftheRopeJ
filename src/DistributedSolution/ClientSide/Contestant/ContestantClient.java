package DistributedSolution.ClientSide.Contestant;

import DistributedSolution.ClientSide.ClientCom;
import DistributedSolution.ClientSide.Coach.Coach;
import DistributedSolution.ClientSide.Coach.CoachBenchStub;
import DistributedSolution.ClientSide.Coach.CoachGlobalStub;
import DistributedSolution.ClientSide.Coach.CoachPlaygroundStub;
import DistributedSolution.Message.Message;
import genclass.GenericIO;

import static java.lang.Thread.sleep;

/**
 * Created by Andre on 26/04/2016.
 */
public class ContestantClient {
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {

        String mainServerUrl = "localhost";
        int mainServerPort = 22279;
        if (args.length == 1) {
            mainServerUrl = args[0];
            System.out.println("Main Hub configured: " + mainServerUrl + ":" + mainServerPort);
        } else {
            System.out.println("Main Hub default configuration used: " + mainServerUrl + ":" + mainServerPort);
        }

        int teamID;
        int contestantID;


        int portNumbBench;
        String serverUrlBench;

        int portNumbPlayground;
        String serverUrlPlayground;

        while (true) {
            ClientCom con = new ClientCom(mainServerUrl, mainServerPort);
            Message inMessage, outMessage;

            while (!con.open()) // aguarda ligação
            {
                try {
                    sleep((long) (10));
                } catch (InterruptedException e) {
                }
            }

            outMessage = new Message(Message.CONFIGCOACH);
            con.writeObject(outMessage);
            inMessage = (Message) con.readObject();
            if (inMessage.getType() != Message.CONFIGCOACHR /*&& inMessage.getType() != Message.CONFIG_NOTREADY*/) {
                GenericIO.writelnString("Thread: Tipo inválido! teve: " + inMessage.getType() + " esperava " + Message.CONFIGCOACHR);
                GenericIO.writelnString(inMessage.toString());
                System.exit(1);
            }
            if (inMessage.getType() == Message.CONFIGCOACHR) {
                teamID = inMessage.getTeamID();
                contestantID = inMessage.getContestantID();

                portNumbBench = inMessage.getPortNumBench();
                serverUrlBench = inMessage.getServerUrlBench();

                portNumbPlayground = inMessage.getPortNumPlayground();
                serverUrlPlayground = inMessage.getServerUrlPlayground();

                con.close();
                break;
            }
            con.close();
        }


        ContestantBenchStub contestantBenchStub;
        ContestantPlaygroundStub contestantPlaygroundStub;
        ContestantGlobalStub contestantGlobalStub;


        contestantBenchStub = new ContestantBenchStub(serverUrlBench, portNumbBench);
        contestantPlaygroundStub = new ContestantPlaygroundStub(serverUrlPlayground, portNumbPlayground);
        contestantGlobalStub = new ContestantGlobalStub(serverUrlPlayground, portNumbPlayground);

        Contestant contestant = new Contestant(contestantID, teamID, contestantBenchStub, contestantPlaygroundStub,contestantPlaygroundStub);

        System.out.println("Coach is running . . .");

        contestant.start();

        try {
            contestant.join();
        } catch (InterruptedException ex) {
        }
    }
}

