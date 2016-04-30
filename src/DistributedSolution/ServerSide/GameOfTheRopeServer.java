package DistributedSolution.ServerSide;

import DistributedSolution.Communication.ClientProxy;
import DistributedSolution.Communication.CommConst;
import DistributedSolution.Communication.ServerCom;
import DistributedSolution.Communication.ServerInterface;
import DistributedSolution.ServerSide.Bench.BenchRemote;
import DistributedSolution.ServerSide.Bench.BenchInterface;
import DistributedSolution.ServerSide.Global.GlobalInterface;
import DistributedSolution.ServerSide.Global.GlobalRemote;
import DistributedSolution.ServerSide.Playground.PlaygroundRemote;
import DistributedSolution.ServerSide.Playground.PlaygroundInterface;
import DistributedSolution.ServerSide.RefereeSite.RefereeSiteRemote;
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

  //  private static final int portNumb = 9091;

    /**
     *  Programa principal.
     */

    public static void main (String [] args)
    {
        GlobalRemote globalRemote;
        BenchRemote benchRemote;                                    // benchRemote (representa o serviço a ser prestado)
        PlaygroundRemote playgroundRemote;
        RefereeSiteRemote refereeSiteRemote;
        BenchInterface benchInterface;                      // interface ao benchRemote
        PlaygroundInterface playgroundInterface;
        RefereeSiteInterface refereeSiteInterface;
        GlobalInterface globalInterface;
        ServerCom sconb,sconrs,scong,sconp, sconib,sconirs,sconig,sconip;                               // canais de comunicação

        ClientProxy cliProxybench;                                // thread agente prestador do serviço
        ClientProxy clientProxyglobal;
        ClientProxy clientProxyplayground;
        ClientProxy clientProxyrefereesite;

     /* estabelecimento do servico */

        sconb = new ServerCom (CommConst.benchServerPort);                     // criação do canal de escuta e sua associação
        sconb.start ();                                       // com o endereço público
        sconrs = new ServerCom (CommConst.refereeSiteServerPort);                     // criação do canal de escuta e sua associação
        sconrs.start ();                                       // com o endereço público
        sconp = new ServerCom (CommConst.playgroundServerPort);                     // criação do canal de escuta e sua associação
        sconp.start ();                                       // com o endereço público
        scong = new ServerCom (CommConst.globalServerPort);                     // criação do canal de escuta e sua associação
        scong.start ();                                       // com o endereço público

        globalRemote = new GlobalRemote("/");

        refereeSiteRemote = new RefereeSiteRemote(globalRemote);

        benchRemote = new BenchRemote(globalRemote, refereeSiteRemote);

        playgroundRemote = new PlaygroundRemote(globalRemote, benchRemote);



        benchInterface = new BenchInterface(benchRemote);        // activação do interface com o serviço
        playgroundInterface = new PlaygroundInterface(playgroundRemote);
        refereeSiteInterface = new RefereeSiteInterface(refereeSiteRemote);
        globalInterface = new GlobalInterface(globalRemote);

        GenericIO.writelnString ("O serviço foi estabelecido!");


     /* processamento de pedidos */

        while (true)
        {
            sconig = scong.accept();
            clientProxyglobal = new ClientProxy(scong,sconig,globalInterface);
            clientProxyglobal.start();

            sconip = sconp.accept();
            clientProxyplayground = new ClientProxy(sconp,sconip,playgroundInterface);
            clientProxyplayground.start();

            GenericIO.writelnString ("O servidor esta em escuta.");
            sconib = sconb.accept ();                            // entrada em processo de escuta
            cliProxybench = new ClientProxy (sconb,sconib, benchInterface);    // lançamento do agente prestador do serviço
            cliProxybench.start ();


            sconirs = sconrs.accept();
            clientProxyrefereesite = new ClientProxy(sconrs,sconirs,refereeSiteInterface);
            clientProxyrefereesite.start();

        }
    }
}
