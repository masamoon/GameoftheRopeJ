package DistributedSolution.ServerSide.Playground;

import DistributedSolution.Communication.ClientProxy;
import DistributedSolution.Communication.CommConst;
import DistributedSolution.Communication.ServerCom;
import DistributedSolution.ServerSide.RefereeSite.RefereeSiteInterface;
import DistributedSolution.ServerSide.RefereeSite.RefereeSiteRemote;

import java.net.SocketException;

/**
 * Created by Andre on 30/04/2016.
 */
public class PlaygroundExec {
    public static void main(String[] args) throws SocketException {
       /*
        ServerCom scon, sconi;                             // canais de comunicação
        ClientProxy cliProxy;                               // thread agente prestador do serviço

        // estabelecimento do servico
        scon = new ServerCom(CommConst.playgroundServerPort);    // criação do canal de escuta e sua associação
        scon.start();                                       // com o endereço público
        PlaygroundRemote playgroundRemote= new PlaygroundRemote();
        PlaygroundInterface playgroundInterface = new PlaygroundInterface(playgroundRemote);
        System.out.println("Shop service has started!");
        System.out.println("Server is listening.");

        // processamento de pedidos
        while (true) {
            //scon.setTimeout(500);
            try {
                sconi = scon.accept();                         // entrada em processo de escuta
                cliProxy = new ClientProxy(scon, sconi, playgroundInterface);    // lançamento do agente prestador do serviço
                cliProxy.start();
            } catch (Exception e) {
                System.exit(0);
            }
        }
        */
    }
}
