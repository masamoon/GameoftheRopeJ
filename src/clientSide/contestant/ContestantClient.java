package clientSide.contestant;


import common.Constants;
import interfaces.BenchInterface;
import interfaces.GlobalInterface;
import interfaces.PlaygroundInterface;

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
            registry = LocateRegistry.getRegistry(Constants.registryAddr, Constants.registryServerPort);
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

        ContestantThread contestant [] [] = new ContestantThread[Constants.TEAMS_NUM][5];
        for(int i = 0; i < Constants.TEAMS_NUM; i++)
        {
            for (int j = 0; j < 5; j++)
                contestant[i][j] = new ContestantThread(i, j, bench, playground, global);
        }

        System.out.println("threads created");

        for(int i = 0; i < Constants.TEAMS_NUM; i++)
        {
            for (int j = 0; j < 5; j++) {
                contestant[i][j].start();

                System.out.println("starting contestant" + i + j);
            }
        }


        for(int i = 0; i < Constants.TEAMS_NUM; i++)
        {
            for (int j = 0; j < 5; j++)
                try {
                    contestant[i][j].join();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
        }

        try
        {
            bench.shutdown();
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
