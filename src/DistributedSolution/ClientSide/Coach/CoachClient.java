package DistributedSolution.ClientSide.Coach;

import DistributedSolution.ClientSide.ClientCom;
import DistributedSolution.Message.Message;
import genclass.GenericIO;

import static java.lang.Thread.sleep;

/**
 * Created by Andre on 26/04/2016.
 */
public class CoachClient {
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {

        String mainServerUrl = "localhost";
        int mainServerPort = 22279;
        if(args.length == 1){
            mainServerUrl = args[0];
            System.out.println("Main Hub configured: " + mainServerUrl + ":" + mainServerPort);
        }
        else{
            System.out.println("Main Hub default configuration used: " + mainServerUrl + ":" + mainServerPort);
        }

        int teamID;


        int portNumbBench;
        String serverUrlBench;

        int portNumbPlayground;
        String serverUrlPlayground;

        while(true){
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

                portNumbBench = inMessage.getPortNumBench();
                serverUrlBench = inMessage.getServerUrlBench();

                portNumbPlayground = inMessage.getPortNumPlayground();
                serverUrlPlayground = inMessage.getServerUrlPlayground();

                con.close();
                break;
            }
            con.close();
        }


        CoachBenchStub coachBenchStub;
        CoachPlaygroundStub coachPlaygroundStub;
        CoachGlobalStub coachGlobalStub;


        coachBenchStub = new CoachBenchStub(serverUrlBench, portNumbBench);
        coachPlaygroundStub = new CoachPlaygroundStub(serverUrlPlayground, portNumbPlayground);
        coachGlobalStub = new CoachGlobalStub(serverUrlPlayground, portNumbPlayground);

        Coach coach = new Coach(teamID,coachBenchStub,coachPlaygroundStub);

        System.out.println("Coach is running . . .");

        coach.start();

        try {
            coach.join();
        } catch (InterruptedException ex) {
        }
    }
}
