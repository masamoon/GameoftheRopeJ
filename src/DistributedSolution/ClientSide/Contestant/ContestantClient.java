package DistributedSolution.ClientSide.Contestant;

import DistributedSolution.Communication.ClientCom;
import DistributedSolution.Communication.CommConst;
import DistributedSolution.Communication.GameParameters;
import DistributedSolution.Communication.Message.Message;
import genclass.GenericIO;

import java.util.ArrayList;
import java.util.Arrays;

import static java.lang.Thread.sleep;


public class ContestantClient {
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {

        ContestantBenchStub contestantBenchStub = new ContestantBenchStub(CommConst.benchServerName, CommConst.benchServerPort);
        ContestantPlaygroundStub contestantPlaygroundStub = new ContestantPlaygroundStub(CommConst.playgroundServerName, CommConst.playgroundServerPort);
        ContestantGlobalStub contestantGlobalStub = new ContestantGlobalStub(CommConst.globalServerName, CommConst.globalServerPort);

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


        System.out.println("Sending TERMINATE message to the Global");
        contestantGlobalStub.terminate();

        System.out.println("ContestantThread Client Finished");


    }
}

