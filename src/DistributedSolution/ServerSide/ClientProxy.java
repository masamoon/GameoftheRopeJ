package DistributedSolution.ServerSide;

/**
 * Created by Andre on 11/04/2016.
 */
import DistributedSolution.Message.Message;
import DistributedSolution.Message.MessageException;
import DistributedSolution.ServerSide.Bench.BenchInterface;
import genclass.GenericIO;


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
     *  Interface bench
     *
     *    @serialField benchInterface
     */

    private BenchInterface benchInterface;

    /**
     *
     * @param sconi
     * @param benchInterface
     */

    public ClientProxy (ServerCom sconi, BenchInterface benchInterface)
    {
        super ("Proxy_" + getProxyId ());

        this.sconi = sconi;
        this.benchInterface = benchInterface;
    }

    /**
     *  Ciclo de vida do thread agente prestador de serviço.
     */

    @Override
    public void run ()
    {
        Message inMessage = null,                                      // mensagem de entrada
                outMessage = null;                      // mensagem de saída

        inMessage = (Message) sconi.readObject ();                     // ler pedido do cliente
        try
        { outMessage = benchInterface.processAndReply (inMessage);         // processá-lo
        }
        catch (MessageException e)
        { GenericIO.writelnString ("Thread " + getName () + ": " + e.getMessage () + "!");
            GenericIO.writelnString (e.getMessageVal ().toString ());
            System.exit (1);
        }
        sconi.writeObject(outMessage);                                // enviar resposta ao cliente
        sconi.close();                                                // fechar canal de comunicação
    }

    /**
     *  Geração do identificador da instanciação.
     *
     *    @return identificador da instanciação
     */

    private static int getProxyId ()
    {
        Class<DistributedSolution.ServerSide.ClientProxy> cl = null;             // representação do tipo de dados ClientProxy na máquina
        //   virtual de Java
        int proxyId;                                         // identificador da instanciação

        try
        { cl = (Class<DistributedSolution.ServerSide.ClientProxy>) Class.forName ("serverSide.ClientProxy");
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

