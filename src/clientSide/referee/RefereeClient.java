package clientSide.referee;


import common.Constants;
import interfaces.GlobalInterface;
import interfaces.PlaygroundInterface;
import interfaces.RefereeSiteInterface;

import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

public class RefereeClient {

    public static void main (String [] args)
    {

        GlobalInterface global = null;
        PlaygroundInterface playground = null;
        RefereeSiteInterface refereeSite = null;

        Registry registry;

        try {
            registry = LocateRegistry.getRegistry(Constants.registryAddr, Constants.registryServerPort);
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

        RefereeThread referee = new RefereeThread(playground, refereeSite, global);

        referee.start();

        try {
            referee.join();
        } catch (InterruptedException e) {
        }

        try
        {
            refereeSite.shutdown();
            playground.shutdown();
            global.shutdown();
        }
        catch (RemoteException e)
        {
            e.printStackTrace ();
            System.exit (1);
        }

    }
}
