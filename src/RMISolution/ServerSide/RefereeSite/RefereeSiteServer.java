package RMISolution.ServerSide.RefereeSite;


public class RefereeSiteServer {
    public static void main(String[] args) {

        String regAddress;
        int regPort;
        int portNumb;
        if (args.length == 3)
        {
            portNumb = Integer.parseInt(args[0]);
            regAddress = args[1];
            regPort = Integer.parseInt(args[2]);
        }
        else
        {
            portNumb = 1101;
            regAddress = "localhost";
            regPort = 2222;
        }

        /* obtenção da localização do serviço de registo RMI */
        if (System.getSecurityManager() == null)
        {
            System.setSecurityManager(new SecurityManager());
        }

        Registry registry = null;
        /* Remote Object */
        RemoteWorkshop remoteWorkshop = null;   // represents remote object
        WorkshopInterface workshopInter = null; // interface to the remote object

        // Locate by remote object name the registed services
        RepositoryInterface repoInter = null;       // Repository Interface (Remote Object)
        ShopInterface shopInter = null;     // Workshop Interface (Remote Object)

        ServerRegists repoRegist = new ServerRegists(regAddress, regPort, RemoteObjects.repo);
        try
        {
            registry = LocateRegistry.getRegistry(repoRegist.getRmiRegHostName(), repoRegist.getRmiRegPortNumb());
            repoInter = (RepositoryInterface) registry.lookup(repoRegist.getRegistEntry());
            shopInter = (ShopInterface) registry.lookup(RemoteObjects.shop);
        }
        catch (RemoteException e)
        {
            GenericIO.writelnString ("Exception in the location of the server: " + e.getMessage () + "!");
            e.printStackTrace ();
            System.exit (1);
        }
        catch (NotBoundException e)
        { GenericIO.writelnString ("The server is not registed: " + e.getMessage () + "!");
            e.printStackTrace ();
            System.exit (1);
        }

        Workshop workshop = new Workshop(repoInter, shopInter);
        remoteWorkshop = new RemoteWorkshop(workshop);
        try {
            workshopInter = (WorkshopInterface) UnicastRemoteObject.exportObject(remoteWorkshop, portNumb);
        } catch (RemoteException e) {
            GenericIO.writelnString("Exception in the RMI regist creation: " + e.getMessage());
            e.printStackTrace();
            System.exit(1);
        }
        GenericIO.writelnString("RMI Regist created!");

        /* Registry Workshop in RMI Registry */
        Register regHandler = null;
        try
        {
            regHandler = (Register) registry.lookup(RemoteObjects.registry);
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
            regHandler.bind(RemoteObjects.workshop, workshopInter);
        } catch (RemoteException e) {
            GenericIO.writelnString("Exception in the Shop Regist: " + e.getMessage());
            e.printStackTrace();
            System.exit(1);
        } catch (AlreadyBoundException e) {
            GenericIO.writelnString("Shop is already registed: " + e.getMessage());
            e.printStackTrace();
            System.exit(1);
        }
        GenericIO.writelnString("WorkShop registed and ready!");

        // Wait for the active entities to Stop (Entrepreneur and Crafstmen)
        waitForTheEnd();

        // End Server
        try
        {
            regHandler.unbind(RemoteObjects.workshop);
            regHandler.shutDown();
            UnicastRemoteObject.unexportObject(remoteWorkshop, true);
        }
        catch(NotBoundException e){
            GenericIO.writelnString("Workshop Regist not bound!" + e.getMessage());
            e.printStackTrace();
            System.exit(1);
        }
        catch(RemoteException e){
            GenericIO.writelnString("Workshop Regist not found!" + e.getMessage());
            e.printStackTrace();
            System.exit(1);
        }
    }

    /**
     * Wait for the active entities to Stop (Entrepreneur and Crafstmen).
     */
    public static void waitForTheEnd()
    {
        try
        {
            synchronized(Class.forName("serverSide.Workshop.WorkshopServer"))
            {
                while (run < 2)
                {
                    Class.forName("serverSide.Workshop.WorkshopServer").wait();
                    run++;
                }
            }
        }
        catch (ClassNotFoundException e)
        {   GenericIO.writelnString ("The Data Type serverSide.Workshop.WorkshopServer was not found!");
            e.printStackTrace ();
            System.exit (1);
        }
        catch (InterruptedException e)
        {   GenericIO.writelnString ("The static method was interruptded!");
            e.printStackTrace ();
            System.exit (1);
        }
    }

    /**
     * Ask for the Server to shut down.
     */
    public synchronized static void endRun()
    {
        try
        {
            Class.forName("serverSide.Workshop.WorkshopServer").notifyAll();
        }
        catch (ClassNotFoundException e)
        { GenericIO.writelnString ("The Data Type serverSide.Workshop.WorkshopServer was not found!");
            e.printStackTrace ();
            System.exit (1);
        }
    }

}
