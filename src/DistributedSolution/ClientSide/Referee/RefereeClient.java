package DistributedSolution.ClientSide.Referee;

import DistributedSolution.Communication.ClientCom;
import DistributedSolution.Communication.CommConst;
import DistributedSolution.Communication.Message.Message;
import genclass.GenericIO;

import java.util.Arrays;

import static java.lang.Thread.sleep;

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


        // SEND THE TERMINATE MESSAGES
        refereeGlobalStub.terminate();
    }
}
