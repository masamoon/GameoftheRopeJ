package DistributedSolution.ServerSide.Global;

import DistributedSolution.Communication.ClientProxy;
import DistributedSolution.Communication.CommConst;
import DistributedSolution.Communication.ServerCom;
import DistributedSolution.ServerSide.Playground.PlaygroundInterface;
import DistributedSolution.ServerSide.Playground.PlaygroundRemote;

import java.net.SocketException;

/**
 * Created by Andre on 30/04/2016.
 */
public class GlobalExec {
    public static void main(String[] args) throws SocketException {
        ServerCom scon, sconi;                             // canais de comunicação
        ClientProxy cliProxy;                               // thread agente prestador do serviço

        /* estabelecimento do servico */
        scon = new ServerCom(CommConst.globalServerPort);    // criação do canal de escuta e sua associação
        scon.start();                                       // com o endereço público
        GlobalRemote globalRemote = new GlobalRemote("/");
        GlobalInterface globalInterface = new GlobalInterface(globalRemote);
        System.out.println("Global service has started!");


        /* processamento de pedidos */
        while (true) {
            //scon.setTimeout(500);
            try {
                sconi = scon.accept();                         // entrada em processo de escuta
                cliProxy = new ClientProxy(scon, sconi, globalInterface);    // lançamento do agente prestador do serviço
                cliProxy.start();
                System.out.println("Server is listening.");
            } catch (Exception e) {
                System.exit(0);
            }
        }
    }
}
