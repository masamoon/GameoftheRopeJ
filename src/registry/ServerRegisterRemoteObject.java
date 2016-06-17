package registry;

import java.rmi.NotBoundException;
import java.rmi.registry.Registry;
import java.rmi.registry.LocateRegistry;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

import common.Constants;
import interfaces.Register;
import genclass.GenericIO;


/**
 *  This data type instantiates and registers a remote object that enables the registration of other remote objects
 *  located in the same or other processing nodes in the local registry service.
 *  common is based in Java RMI.
 */

public class ServerRegisterRemoteObject
{

    private static int serversFinished = 0;

    /**
     *  Main task.
     */

    public static void main(String[] args)
    {
    /* get location of the registry service */

        String rmiRegHostName = Constants.registryAddr;
        int rmiRegPortNumb = Constants.registryServerPort;

    /* create and install the security manager */

        if (System.getSecurityManager () == null)
            System.setSecurityManager (new SecurityManager ());
        GenericIO.writelnString ("Security manager was installed!");

    /* instantiate a registration remote object and generate a stub for it */

        RegisterRemoteObject regEngine = new RegisterRemoteObject (rmiRegHostName, rmiRegPortNumb);
        Register regEngineStub = null;
        int listeningPort = Constants.registryObjectPort;                            /* it should be set accordingly in each case */

        try
        { regEngineStub = (Register) UnicastRemoteObject.exportObject (regEngine, listeningPort);
        }
        catch (RemoteException e)
        { GenericIO.writelnString ("RegisterRemoteObject stub generation exception: " + e.getMessage ());
            System.exit (1);
        }
        GenericIO.writelnString ("Stub was generated!");

    /* register it with the local registry service */

        String nameEntry = Constants.registry;
        Registry registry = null;

        try
        { registry = LocateRegistry.getRegistry (rmiRegHostName, rmiRegPortNumb);
        }
        catch (RemoteException e)
        { GenericIO.writelnString ("RMI registry creation exception: " + e.getMessage ());
            System.exit (1);
        }
        GenericIO.writelnString ("RMI registry was created!");

        try
        { registry.rebind (nameEntry, regEngineStub);
        }
        catch (RemoteException e)
        { GenericIO.writelnString ("RegisterRemoteObject remote exception on registration: " + e.getMessage ());
            System.exit (1);
        }
        GenericIO.writelnString ("RegisterRemoteObject object was registered!");


        // Wait for all the servers to send stop signals
        waitForFinish();

        // End Server
        try
        {
            registry.unbind(nameEntry);
            UnicastRemoteObject.unexportObject(regEngine, true);
        }
        catch(NotBoundException e){
            GenericIO.writelnString("registry not bound!" + e.getMessage());
            e.printStackTrace();
            System.exit(1);
        }
        catch(RemoteException e){
            GenericIO.writelnString("registry registry not found!" + e.getMessage());
            e.printStackTrace();
            System.exit(1);
        }
    }


    /**
     * Wait for the servers registered to Stop (bench, playground, refereeSite, global).
     */
    public static void waitForFinish()
    {
        try
        {
            synchronized(Class.forName("registry.ServerRegisterRemoteObject"))
            {
                while (serversFinished < 4)
                {
                    Class.forName("registry.ServerRegisterRemoteObject").wait();
                    serversFinished++;
                }
            }
        }
        catch (ClassNotFoundException e)
        {   GenericIO.writelnString ("The Data Type registry.ServerRegisterRemoteObject was not found!");
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
            Class.forName("registry.ServerRegisterRemoteObject").notifyAll();
        }
        catch (ClassNotFoundException e)
        { GenericIO.writelnString ("The Data Type was not found!");
            e.printStackTrace ();
            System.exit (1);
        }
    }
}