package RMISolution.ClientSide.Contestant;


import RMISolution.ClientSide.Coach.CoachThread;
import RMISolution.Common.Constants;
import RMISolution.Interfaces.BenchInterface;
import RMISolution.Interfaces.GlobalInterface;
import RMISolution.Interfaces.PlaygroundInterface;

import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

public class ContestantClient {
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

        ContestantThread contestant [] = new ContestantThread[(Constants.TEAMS_NUM)*5];
        for(int i = 0; i < Constants.TEAMS_NUM; i++)
        {
            for (int j = 0; j < 5; j++)
                contestant[i] = new ContestantThread(i, j, bench, playground, global);
        }

        for(ContestantThread c : contestant){
            c.start();
        }

        for (ContestantThread c : contestant) {
            try {
                c.join();
            } catch (InterruptedException e) {
            }
        }

        // todo: shutdown

    }
}
