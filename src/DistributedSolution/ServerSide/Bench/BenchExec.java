package DistributedSolution.ServerSide.Bench;

import DistributedSolution.Communication.ClientProxy;
import DistributedSolution.Communication.CommConst;
import DistributedSolution.Communication.ServerCom;
import DistributedSolution.ServerSide.Playground.PlaygroundInterface;
import DistributedSolution.ServerSide.Playground.PlaygroundRemote;

import java.net.SocketException;

/**
 * Created by Andre on 30/04/2016.
 */
public class BenchExec {
    public static void main(String[] args) throws SocketException {
       /* ServerCom scon, sconi;                             // canais de comunicação
        ClientProxy cliProxy;                               // thread agente prestador do serviço

        // estabelecimento do servico
        scon = new ServerCom(CommConst.benchServerPort);    // criação do canal de escuta e sua associação
        scon.start();                                       // com o endereço público
        BenchRemote benchRemote = new BenchRemote();
        BenchInterface benchInterface = new BenchInterface(benchRemote);
        System.out.println("Shop service has started!");
        System.out.println("Server is listening.");

        // processamento de pedidos
        while (true) {
            //scon.setTimeout(500);
            try {
                sconi = scon.accept();                         // entrada em processo de escuta
                cliProxy = new ClientProxy(scon, sconi, benchInterface);    // lançamento do agente prestador do serviço
                cliProxy.start();
            } catch (Exception e) {
                System.exit(0);
            }
        }
        */
    }
}
