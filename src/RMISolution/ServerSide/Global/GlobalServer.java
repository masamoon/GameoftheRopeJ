package RMISolution.ServerSide.Global;


import RMISolution.Common.Constants;
import RMISolution.Interfaces.GlobalInterface;
import RMISolution.Interfaces.Register;
import genclass.GenericIO;

import java.rmi.AlreadyBoundException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;

public class GlobalServer {

    private static int shutdownSignals = 0;

    public static void main(String[] args) {

        if (System.getSecurityManager() == null)
        {
            System.setSecurityManager(new SecurityManager());
        }

        Registry registry = null;

        GlobalRemoteCom globalRemoteCom = null;
        GlobalInterface globalInterface = null;

        try
        {
            registry = LocateRegistry.getRegistry(Constants.registryAddr, Constants.registryServerPort);
        }
        catch (RemoteException e)
        {
            GenericIO.writelnString ("Exception in the location of the server: " + e.getMessage () + "!");
            e.printStackTrace ();
            System.exit (1);
        }

        Global global = new Global();
        globalRemoteCom = new GlobalRemoteCom(global);
        try {
            globalInterface = (GlobalInterface) UnicastRemoteObject.exportObject(globalRemoteCom, Constants.globalServerPort);
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
            regHandler.bind(Constants.global, globalInterface);
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

        global.closeFile();

        try
        {
            regHandler.unbind(Constants.global);
            regHandler.shutdown();
            UnicastRemoteObject.unexportObject(globalRemoteCom, true);
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
            synchronized(Class.forName("ServerSide.Global.GlobalServer"))
            {
                while(shutdownSignals<3)
                {
                    Class.forName("ServerSide.Global.GlobalServer").wait();
                    shutdownSignals++;
                }
            }
        }
        catch (ClassNotFoundException e)
        {   GenericIO.writelnString ("The Data Type ServerSide.Global.GlobalServer was not found!");
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
            Class.forName("ServerSide.Global.GlobalServer").notifyAll();
        }
        catch (ClassNotFoundException e)
        { GenericIO.writelnString ("The Data Type ServerSide.Global.GlobalServer was not found!");
            e.printStackTrace ();
            System.exit (1);
        }
    }
}
