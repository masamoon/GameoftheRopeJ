package DistributedSolution.ServerSide.RefereeSite;

import DistributedSolution.Communication.ClientProxy;
import DistributedSolution.Communication.CommConst;
import DistributedSolution.Communication.ServerCom;

import java.net.SocketException;
import java.net.SocketTimeoutException;

public class RefereeSiteExec {
    public static void main(String[] args) throws SocketException {

        ServerCom scon, sconi;                             // canais de comunicação
        ClientProxy cliProxy;                               // thread agente prestador do serviço

        RefereeSiteGlobalStub refereeSiteGlobalStub = new RefereeSiteGlobalStub(CommConst.globalServerName, CommConst.globalServerPort);

        // estabelecimento do servico
        scon = new ServerCom(CommConst.refereeSiteServerPort);    // criação do canal de escuta e sua associação
        scon.start();                                       // com o endereço público
        RefereeSiteRemote refereeSiteRemote= new RefereeSiteRemote(refereeSiteGlobalStub);
        RefereeSiteInterface refereeSiteInterface = new RefereeSiteInterface(refereeSiteRemote);
        System.out.println("RefereeSire service has started!");
        System.out.println("Server is listening.");

        // processamento de pedidos
        while (true) {
            //scon.setTimeout(500);
            try {
                sconi = scon.accept();                         // entrada em processo de escuta
                cliProxy = new ClientProxy(sconi, refereeSiteInterface);    // lançamento do agente prestador do serviço
                cliProxy.start();
            } catch (Exception e) {
                System.exit(0);
            }
        }

    }
}
