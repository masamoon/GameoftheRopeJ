package serverSide.bench;

import common.Constants;
import genclass.GenericIO;
import interfaces.BenchInterface;
import interfaces.GlobalInterface;
import interfaces.RefereeSiteInterface;
import interfaces.Register;

import java.rmi.AlreadyBoundException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;

public class BenchServer {

    private static int shutdownSignals = 0;


    public static void main(String[] args) {

        if (System.getSecurityManager() == null)
        {
            System.setSecurityManager(new SecurityManager());
        }

        Registry registry = null;

        benchRemoteCom benchRemote = null;
        BenchInterface BenchInterface = null;

        GlobalInterface global = null;
        RefereeSiteInterface refereeSite = null;

        try
        {
            registry = LocateRegistry.getRegistry(Constants.registryAddr, Constants.registryServerPort);
            global = (GlobalInterface) registry.lookup(Constants.global);
            refereeSite = (RefereeSiteInterface) registry.lookup(Constants.refereeSite);
        }
        catch (RemoteException e)
        {
            GenericIO.writelnString ("Exception in the location of the server: " + e.getMessage () + "!");
            e.printStackTrace ();
            System.exit (1);
        }
        catch (NotBoundException e)
        {
            GenericIO.writelnString ("The server is not registered: " + e.getMessage () + "!");
            e.printStackTrace ();
            System.exit (1);
        }

        Bench bench = new Bench(global, refereeSite);
        benchRemote = new benchRemoteCom(bench);
        try {
            BenchInterface = (BenchInterface) UnicastRemoteObject.exportObject(benchRemote, Constants.benchServerPort);
        } catch (RemoteException e) {
            GenericIO.writelnString("Exception exporting RMI object: " + e.getMessage());
            e.printStackTrace();
            System.exit(1);
        }

        Register regHandler = null;
        try
        {
            regHandler = (Register) registry.lookup(Constants.registry);
        }
        catch (RemoteException e)
        { GenericIO.writelnString ("RegisterRemoteObject lookup exception: " + e.getMessage ());
            e.printStackTrace ();
            System.exit (1);
        }
        catch (NotBoundException e)
        { GenericIO.writelnString ("RegisterRemoteObject not bound exception: " + e.getMessage ());
            e.printStackTrace ();
            System.exit (1);
        }

        try {
            regHandler.bind(Constants.bench, BenchInterface);
        } catch (RemoteException e) {
            GenericIO.writelnString("Exception in the bench registry: " + e.getMessage());
            e.printStackTrace();
            System.exit(1);
        } catch (AlreadyBoundException e) {
            GenericIO.writelnString("Class is already registered: " + e.getMessage());
            e.printStackTrace();
            System.exit(1);
        }

        waitForTheEnd();

        try
        {
            regHandler.unbind(Constants.bench);
            regHandler.shutdown();
            UnicastRemoteObject.unexportObject(benchRemote, true);
        }
        catch(NotBoundException e){
            GenericIO.writelnString("Class registry not bound!" + e.getMessage());
            e.printStackTrace();
            System.exit(1);
        }
        catch(RemoteException e){
            GenericIO.writelnString("Class registry not found!" + e.getMessage());
            e.printStackTrace();
            System.exit(1);
        }
    }

    public static void waitForTheEnd()
    {
        try
        {
            synchronized(Class.forName("serverSide.bench.BenchServer"))
            {
                while (shutdownSignals < 2)
                {
                    Class.forName("serverSide.bench.BenchServer").wait();
                    shutdownSignals++;
                }
            }
        }
        catch (ClassNotFoundException e)
        {   GenericIO.writelnString ("The Data Type serverSide.bench.BenchServer was not found!");
            e.printStackTrace ();
            System.exit (1);
        }
        catch (InterruptedException e)
        {   GenericIO.writelnString ("The static method was interrupted!");
            e.printStackTrace ();
            System.exit (1);
        }
    }

    /**
     * Ask for the Server to shut down.
     */
    public synchronized static void finish()
    {
        try
        {
            Class.forName("serverSide.bench.BenchServer").notifyAll();
        }
        catch (ClassNotFoundException e)
        { GenericIO.writelnString ("The Data Type serverSide.bench.BenchServer was not found!");
            e.printStackTrace ();
            System.exit (1);
        }
    }
}
