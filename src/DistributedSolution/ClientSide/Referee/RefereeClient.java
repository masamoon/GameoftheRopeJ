package DistributedSolution.ClientSide.Referee;

import DistributedSolution.ClientSide.ClientCom;
import DistributedSolution.ClientSide.Contestant.Contestant;
import DistributedSolution.ClientSide.Contestant.ContestantBenchStub;
import DistributedSolution.ClientSide.Contestant.ContestantGlobalStub;
import DistributedSolution.ClientSide.Contestant.ContestantPlaygroundStub;
import DistributedSolution.Message.Message;
import genclass.GenericIO;

import static java.lang.Thread.sleep;

/**
 * Created by Andre on 26/04/2016.
 */
public class RefereeClient {
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

            outMessage = new Message(Message.CONFIGREFEREE);
            con.writeObject(outMessage);
            inMessage = (Message) con.readObject();
            if (inMessage.getType() != Message.CONFIGREFEREER /*&& inMessage.getType() != Message.CONFIG_NOTREADY*/) {
                GenericIO.writelnString("Thread: Tipo inválido! teve: " + inMessage.getType() + " esperava " + Message.CONFIGREFEREER);
                GenericIO.writelnString(inMessage.toString());
                System.exit(1);
            }
            if (inMessage.getType() == Message.CONFIGREFEREER) {
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


        RefereePlaygroundStub refereePlaygroundStub;
        RefereeRefereeSiteStub refereeRefereeSiteStub;
        RefereeGlobalStub refereeGlobalStub;


        refereePlaygroundStub = new RefereePlaygroundStub(serverUrlBench, portNumbBench);
        refereeRefereeSiteStub = new RefereeRefereeSiteStub(serverUrlPlayground, portNumbPlayground);
        refereeGlobalStub = new RefereeGlobalStub(serverUrlPlayground, portNumbPlayground);

        Referee referee = new Referee(contestantBenchStub, contestantPlaygroundStub,contestantPlaygroundStub);

        System.out.println("Coach is running . . .");

        referee.start();

        try {
            referee.join();
        } catch (InterruptedException ex) {
        }
    }
}
