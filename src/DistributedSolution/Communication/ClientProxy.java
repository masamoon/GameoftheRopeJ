package DistributedSolution.Communication;

/**
 * Created by Andre on 11/04/2016.
 */
import DistributedSolution.Communication.Message.Message;
import DistributedSolution.Communication.Message.MessageException;
import DistributedSolution.ServerSide.Bench.BenchInterface;
import genclass.GenericIO;

import java.net.SocketException;
import java.util.logging.Level;
import java.util.logging.Logger;


/**
 *   Este tipo de dados define o thread agente prestador de serviço para uma solução do Problema dos Barbeiros
 *   Sonolentos que implementa o modelo cliente-servidor de tipo 2 (replicação do servidor) com lançamento estático dos
 *   threads barbeiro.
 *   A comunicação baseia-se em passagem de mensagens sobre sockets usando o protocolo TCP.
 */

public class ClientProxy extends Thread
{
    /**
     *  Contador de threads lançados
     *
     *    @serialField nProxy
     */

    private static int nProxy;

    /**
     *  Canal de comunicação
     *
     *    @serialField sconi
     */

    private ServerCom sconi;

    /**
     * Canal de comunicação
     *
     * @serialField scon
     */
    private ServerCom scon;

    /**
     *  Interface serverInterface
     *
     *    @serialField serverInterface
     */

    private ServerInterface serverInterface;

    /**
     *
     * @param sconi
     * @param serverInterface
     */

    public ClientProxy (ServerCom scon ,ServerCom sconi, ServerInterface serverInterface)
    {
        super ("Proxy_" + getProxyId ());

        this.sconi = sconi;
        this.scon = scon;
        this.serverInterface = serverInterface;
    }

    /**
     *  Ciclo de vida do thread agente prestador de serviço.
     */

    @Override
    public void run ()
    {
        Message inMessage = null, // mensagem de entrada
                outMessage = null;                      // mensagem de saída


        inMessage = (Message) sconi.readObject();                     // ler pedido do cliente
        GenericIO.writelnString("incoming message to Server: "+inMessage.toString());

        try
        {
            outMessage = serverInterface.processAndReply(inMessage, scon);         // processá-lo
        } catch (MessageException e)
        {
            System.out.println("Thread " + getName() + ": " + e.getMessage() + "!");
            System.out.println(e.getMessageVal().toString());
            System.exit(1);
        } catch (SocketException ex) {
            Logger.getLogger(ClientProxy.class.getName()).log(Level.SEVERE, null, ex);
        }
        sconi.writeObject(outMessage);                                // enviar resposta ao cliente
        sconi.close();                                                // fechar canal de comunicação

        if(serverInterface.serviceEnded())
        {
            System.out.println("Closing service ... Done!");
            System.exit(0);
        }
    }

    /**
     *  Geração do identificador da instanciação.
     *
     *    @return identificador da instanciação
     */

    private static int getProxyId ()
    {
        Class<ClientProxy> cl = null;             // representação do tipo de dados ClientProxy na máquina
        //   virtual de Java
        int proxyId;                                         // identificador da instanciação

        try
        { cl = (Class<ClientProxy>) Class.forName ("DistributedSolution.Communication.ClientProxy");
        }
        catch (ClassNotFoundException e)
        { GenericIO.writelnString ("O tipo de dados ClientProxy não foi encontrado!");
            e.printStackTrace ();
            System.exit (1);
        }

        synchronized (cl)
        { proxyId = nProxy;
            nProxy += 1;
        }

        return proxyId;
    }
}

