package DistributedSolution.ServerSide;

import DistributedSolution.Communication.ClientProxy;
import DistributedSolution.Communication.ServerCom;
import DistributedSolution.ServerSide.Bench.Bench;
import DistributedSolution.ServerSide.Bench.BenchInterface;
import DistributedSolution.ServerSide.Playground.Playground;
import DistributedSolution.ServerSide.Playground.PlaygroundInterface;
import DistributedSolution.ServerSide.RefereeSite.RefereeSite;
import DistributedSolution.ServerSide.RefereeSite.RefereeSiteInterface;
import genclass.GenericIO;

/**
 * Created by Andre on 12/04/2016.
 */
public class GameOfTheRopeServer {

    /**
     *  Número do port de escuta do serviço a ser prestado (4000, por defeito)
     *
     *    @serialField portNumb
     */

    private static final int portNumb = 4000;

    /**
     *  Programa principal.
     */

    public static void main (String [] args)
    {
        Bench bench;                                    // bench (representa o serviço a ser prestado)
        Playground playground;
        RefereeSite refereeSite;
        BenchInterface benchInterface;                      // interface ao bench
        PlaygroundInterface playgroundInterface;
        RefereeSiteInterface refereeSiteInterface;
        ServerCom scon, sconi;                               // canais de comunicação
        ClientProxy cliProxy;                                // thread agente prestador do serviço

     /* estabelecimento do servico */

        scon = new ServerCom (portNumb);                     // criação do canal de escuta e sua associação
        scon.start ();                                       // com o endereço público
        bench = new Bench();                         // activação do serviço
        playground = new Playground();
        refereeSite = new RefereeSite();
        benchInterface = new BenchInterface(bench);        // activação do interface com o serviço
        playgroundInterface = new PlaygroundInterface(playground);
        refereeSiteInterface = new RefereeSiteInterface(refereeSite);
        GenericIO.writelnString ("O serviço foi estabelecido!");
        GenericIO.writelnString ("O servidor esta em escuta.");

     /* processamento de pedidos */

        while (true)
        { sconi = scon.accept ();                            // entrada em processo de escuta
            cliProxy = new ClientProxy (sconi, benchInterface);    // lançamento do agente prestador do serviço
            cliProxy.start ();
        }
    }
}
