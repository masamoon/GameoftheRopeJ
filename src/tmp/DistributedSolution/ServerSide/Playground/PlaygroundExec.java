package DistributedSolution.ServerSide.Playground;

import DistributedSolution.Communication.ClientProxy;
import DistributedSolution.Communication.CommConst;
import DistributedSolution.Communication.ServerCom;
import DistributedSolution.ServerSide.RefereeSite.RefereeSiteInterface;
import DistributedSolution.ServerSide.RefereeSite.RefereeSiteRemote;

import java.net.SocketException;


public class PlaygroundExec {
    public static void main(String[] args) throws SocketException {

        ServerCom scon, sconi;                             // canais de comunicação
        ClientProxy cliProxy;                               // thread agente prestador do serviço
        PlaygroundGlobalStub playgroundGlobalStub = new PlaygroundGlobalStub(CommConst.globalServerName, CommConst.globalServerPort);
        PlaygroundRefereeSiteStub playgroundRefereeSiteStub = new PlaygroundRefereeSiteStub(CommConst.refereeSiteServerName, CommConst.refereeSiteServerPort);
        PlaygroundBenchStub playgroundBenchStub = new PlaygroundBenchStub(CommConst.benchServerName, CommConst.benchServerPort);

        // estabelecimento do servico
        scon = new ServerCom(CommConst.playgroundServerPort);    // criação do canal de escuta e sua associação
        scon.start();                                       // com o endereço público
        PlaygroundRemote playgroundRemote= new PlaygroundRemote(playgroundGlobalStub,playgroundBenchStub, playgroundRefereeSiteStub);
        PlaygroundInterface playgroundInterface = new PlaygroundInterface(playgroundRemote);
        System.out.println("playground service has started!");
        System.out.println("Server is listening.");

        // processamento de pedidos
        while (true) {
            //scon.setTimeout(500);
            try {
                sconi = scon.accept();                         // entrada em processo de escuta
                cliProxy = new ClientProxy(sconi, playgroundInterface);    // lançamento do agente prestador do serviço
                cliProxy.start();
            } catch (Exception e) {
                System.exit(0);
            }
        }

    }
}
