package DistributedSolution.ServerSide.Bench;

import DistributedSolution.Communication.ClientProxy;
import DistributedSolution.Communication.CommConst;
import DistributedSolution.Communication.ServerCom;
import DistributedSolution.ServerSide.Playground.PlaygroundInterface;
import DistributedSolution.ServerSide.Playground.PlaygroundRemote;

import java.net.SocketException;

public class BenchExec {
    public static void main(String[] args) throws SocketException {


       ServerCom scon, sconi;                             // canais de comunicação
        ClientProxy cliProxy;                               // thread agente prestador do serviço
        BenchGlobalStub benchGlobalStub = new BenchGlobalStub(CommConst.globalServerName, CommConst.globalServerPort);
        BenchRefereeSiteStub benchRefereeSiteStub = new BenchRefereeSiteStub(CommConst.refereeSiteServerName, CommConst.refereeSiteServerPort);

        // estabelecimento do servico
        scon = new ServerCom(CommConst.benchServerPort);    // criação do canal de escuta e sua associação
        scon.start();                                       // com o endereço público
        BenchRemote benchRemote = new BenchRemote(benchGlobalStub,benchRefereeSiteStub);
        BenchInterface benchInterface = new BenchInterface(benchRemote);
        System.out.println("Bench service has started!");
        System.out.println("Server is listening.");

        // processamento de pedidos
        while (true) {
            //scon.setTimeout(500);
            try {
                sconi = scon.accept();                         // entrada em processo de escuta
                cliProxy = new ClientProxy(sconi, benchInterface);    // lançamento do agente prestador do serviço
                cliProxy.start();
            } catch (Exception e) {
                System.exit(0);
            }
        }



    }
}
