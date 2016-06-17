package tmp.DistributedSolution.ClientSide.Coach;

import tmp.DistributedSolution.Communication.ClientCom;
import tmp.DistributedSolution.Communication.CommConst;
import tmp.DistributedSolution.Communication.GameParameters;
import tmp.DistributedSolution.Communication.Message.Message;
import genclass.GenericIO;

import java.util.ArrayList;
import java.util.Arrays;

import static java.lang.Thread.sleep;


public class CoachClient {
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {

        CoachBenchStub coachBenchStub = new CoachBenchStub(CommConst.benchServerName, CommConst.benchServerPort);
        CoachPlaygroundStub coachPlaygroundStub = new CoachPlaygroundStub(CommConst.playgroundServerName, CommConst.playgroundServerPort);
        CoachGlobalStub coachGlobalStub = new CoachGlobalStub(CommConst.globalServerName,CommConst.globalServerPort);

        ArrayList<Coach> coaches = new ArrayList<>(GameParameters.nTeams);
        for (int i = 0; i < GameParameters.nTeams; i++)
            coaches.add(new Coach(i,coachBenchStub,coachPlaygroundStub,coachGlobalStub));

        GenericIO.writelnString("Number of coaches: " + coaches.size());
        for (Coach c : coaches)
            c.start();

        for (Coach c : coaches) {
            try {
                c.join ();
            } catch (InterruptedException e) {}
        }


        System.out.println("Sending TERMINATE message to the global");
        coachGlobalStub.terminate();
        System.out.println("CoachThread Client Finished");

    }
}
