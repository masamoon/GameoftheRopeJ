package RMISolution.ServerSide.Playground;


import RMISolution.Common.Constants;
import RMISolution.Interfaces.*;
import genclass.GenericIO;

import java.rmi.AlreadyBoundException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;

public class PlaygroundServer {

    private static int shutdownSignals = 0;

    public static void main(String[] args) {

        if (System.getSecurityManager() == null)
        {
            System.setSecurityManager(new SecurityManager());
        }

        Registry registry = null;

        PlaygroundRemoteCom playgroundRemote = null;
        PlaygroundInterface playgroundInterface = null;

        GlobalInterface global = null;
        BenchInterface bench = null;
        RefereeSiteInterface refereeSite = null;

        try
        {
            registry = LocateRegistry.getRegistry(Constants.registryAddr, Constants.registryServerPort);
            global = (GlobalInterface) registry.lookup(Constants.global);
            bench = (BenchInterface) registry.lookup(Constants.bench);
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

        Playground playground = new Playground(global, bench, refereeSite);
        playgroundRemote = new PlaygroundRemoteCom(playground);
        try {
            playgroundInterface = (PlaygroundInterface) UnicastRemoteObject.exportObject(playgroundRemote, Constants.playgroundServerPort);
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
            regHandler.bind(Constants.playground, playgroundInterface);
        } catch (RemoteException e) {
            GenericIO.writelnString("Exception in the Registry: " + e.getMessage());
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
            regHandler.unbind(Constants.playground);
            regHandler.shutdown();
            UnicastRemoteObject.unexportObject(playgroundRemote, true);
        }
        catch(NotBoundException e){
            GenericIO.writelnString("Class Registry not bound!" + e.getMessage());
            e.printStackTrace();
            System.exit(1);
        }
        catch(RemoteException e){
            GenericIO.writelnString("Class Registry not found!" + e.getMessage());
            e.printStackTrace();
            System.exit(1);
        }
    }

    public static void waitForTheEnd()
    {
        try
        {
            synchronized(Class.forName("ServerSide.Playground.PlaygroundServer"))
            {
                while (shutdownSignals < 2)
                {
                    Class.forName("ServerSide.Playground.PlaygroundServer").wait();
                    shutdownSignals++;
                }
            }
        }
        catch (ClassNotFoundException e)
        {   GenericIO.writelnString ("The Data Type ServerSide.Playground.PlaygroundServer was not found!");
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
            Class.forName("ServerSide.Playground.PlaygroundServer").notifyAll();
        }
        catch (ClassNotFoundException e)
        { GenericIO.writelnString ("The Data Type ServerSide.Playground.PlaygroundServer was not found!");
            e.printStackTrace ();
            System.exit (1);
        }
    }
}
