package RMISolution.ClientSide.Referee;


import RMISolution.ClientSide.Coach.CoachThread;
import RMISolution.Common.Constants;
import RMISolution.Interfaces.BenchInterface;
import RMISolution.Interfaces.GlobalInterface;
import RMISolution.Interfaces.PlaygroundInterface;
import RMISolution.Interfaces.RefereeSiteInterface;

import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

public class RefereeClient {

    public static void main (String [] args)
    {

        BenchInterface bench = null;
        GlobalInterface global = null;
        PlaygroundInterface playground = null;
        RefereeSiteInterface refereeSite = null;

        Registry registry;

        try {
            registry = LocateRegistry.getRegistry(Constants.registryAddr, Constants.registryPort);
            bench = (BenchInterface) registry.lookup(Constants.bench);
            global = (GlobalInterface) registry.lookup(Constants.global);
            playground = (PlaygroundInterface) registry.lookup(Constants.playground);
            refereeSite = (RefereeSiteInterface) registry.lookup(Constants.refereeSite);
        }
        catch (RemoteException e) {
            e.printStackTrace();
        }
        catch (NotBoundException e) {
            e.printStackTrace();
        }

        RefereeThread referee = new RefereeThread(bench, playground, refereeSite, global);

        referee.start();

        try {
            referee.join();
        } catch (InterruptedException e) {
        }

        // todo: shutdown

    }
}
