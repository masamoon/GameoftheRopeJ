package RMISolution.ServerSide.RefereeSite;


import RMISolution.Common.Constants;
import RMISolution.Interfaces.*;
import genclass.GenericIO;

import java.rmi.AlreadyBoundException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;

public class RefereeSiteServer {

    private static int shutdownSignals = 0;


    public static void main(String[] args) {

        if (System.getSecurityManager() == null)
        {
            System.setSecurityManager(new SecurityManager());
        }

        Registry registry = null;

        RefereeSiteRemoteCom refereeSiteRemote = null;
        RefereeSiteInterface refereeSiteInterface = null;

        GlobalInterface global = null;

        try
        {
            registry = LocateRegistry.getRegistry(Constants.registryAddr, Constants.registryServerPort);
            global = (GlobalInterface) registry.lookup(Constants.global);
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

        RefereeSite refereeSite = new RefereeSite(global);
        refereeSiteRemote = new RefereeSiteRemoteCom(refereeSite);
        try {
            refereeSiteInterface = (RefereeSiteInterface) UnicastRemoteObject.exportObject(refereeSiteRemote, Constants.refereeSiteServerPort);
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
            regHandler.bind(Constants.refereeSite, refereeSiteInterface);
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
            regHandler.unbind(Constants.refereeSite);
            regHandler.shutdown();
            UnicastRemoteObject.unexportObject(refereeSiteRemote, true);
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
            synchronized(Class.forName("ServerSide.RefereeSite.RefereeSiteServer"))
            {
                while (shutdownSignals < 1)
                {
                    Class.forName("ServerSide.RefereeSite.RefereeSiteServer").wait();
                    shutdownSignals++;
                }
            }
        }
        catch (ClassNotFoundException e)
        {   GenericIO.writelnString ("The Data Type ServerSide.RefereeSite.RefereeSiteServer was not found!");
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
            Class.forName("ServerSide.RefereeSite.RefereeSiteServer").notifyAll();
        }
        catch (ClassNotFoundException e)
        { GenericIO.writelnString ("The Data Type ServerSide.RefereeSite.RefereeSiteServer was not found!");
            e.printStackTrace ();
            System.exit (1);
        }
    }

}
