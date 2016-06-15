package RMISolution.ClientSide.Coach;

import RMISolution.Common.Constants;
import RMISolution.Interfaces.BenchInterface;
import RMISolution.Interfaces.GlobalInterface;
import RMISolution.Interfaces.PlaygroundInterface;

import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

public class CoachClient {

    public static void main (String [] args)
    {

        BenchInterface bench = null;
        GlobalInterface global = null;
        PlaygroundInterface playground = null;

        Registry registry;


        try {
            registry = LocateRegistry.getRegistry(Constants.registryAddr, Constants.registryPort);
            bench = (BenchInterface) registry.lookup(Constants.bench);
            global = (GlobalInterface) registry.lookup(Constants.global);
            playground = (PlaygroundInterface) registry.lookup(Constants.playground);
        }
        catch (RemoteException e) {
            e.printStackTrace();
        }
        catch (NotBoundException e) {
            e.printStackTrace();
        }

        CoachThread coach [] = new CoachThread[Constants.TEAMS_NUM];
        for(int i = 0; i < Constants.TEAMS_NUM; i++)
        {
            coach[i] = new CoachThread(i, bench, playground, global);
        }

        for(CoachThread c : coach){
            c.start();
        }

        for (CoachThread c : coach) {
            try {
                c.join();
            } catch (InterruptedException e) {
            }
        }

        // todo: shutdown

    }
}
