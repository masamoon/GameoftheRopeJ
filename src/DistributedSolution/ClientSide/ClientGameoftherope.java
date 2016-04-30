package DistributedSolution.ClientSide;

import DistributedSolution.ClientSide.Coach.Coach;
import DistributedSolution.ClientSide.Coach.CoachBenchStub;
import DistributedSolution.ClientSide.Coach.CoachGlobalStub;
import DistributedSolution.ClientSide.Coach.CoachPlaygroundStub;
import DistributedSolution.ClientSide.Contestant.Contestant;
import DistributedSolution.ClientSide.Contestant.ContestantBenchStub;
import DistributedSolution.ClientSide.Contestant.ContestantGlobalStub;
import DistributedSolution.ClientSide.Contestant.ContestantPlaygroundStub;
import DistributedSolution.ClientSide.Referee.Referee;
import DistributedSolution.ClientSide.Referee.RefereeGlobalStub;
import DistributedSolution.ClientSide.Referee.RefereePlaygroundStub;
import DistributedSolution.ClientSide.Referee.RefereeRefereeSiteStub;
import DistributedSolution.Communication.ClientCom;
import DistributedSolution.Communication.CommConst;
import DistributedSolution.Communication.GameParameters;
import DistributedSolution.Communication.Message.Message;
import genclass.GenericIO;

/**
 * Created by Andre on 30/04/2016.
 */
public class ClientGameoftherope {
    public static void main (String [] args)
    {
        int nCustomer = 5;                                   // número de clientes
        int nBarber = 2;                                     // número máximo de barbeiros
        Contestant [] contestants = new Contestant[GameParameters.nContestants*GameParameters.nTeams];     // array de threads cliente
        Coach[] coaches = new Coach[GameParameters.nTeams];            // array de threads barbeiro
        Referee referee;
        int nIter;                                           // número de iterações do ciclo de vida dos clientes
        String fName;                                        // nome do ficheiro de logging
        String serverHostName = null;                        // nome do sistema computacional onde está o servidor
        int serverPortNumb;                                  // número do port de escuta do servidor

     /* Obtenção dos parâmetros do problema */

        /*GenericIO.writelnString ("\n" + "      Problema dos Barbeiros Sonolentos\n");
        GenericIO.writeString ("Numero de iterações? ");
        nIter = GenericIO.readlnInt ();
        GenericIO.writeString ("Nome do ficheiro de logging? ");
        fName = GenericIO.readlnString ();
        GenericIO.writeString ("Nome do sistema computacional onde está o servidor? ");
        serverHostName = GenericIO.readlnString ();
        GenericIO.writeString ("Número do port de escuta do servidor? ");
        serverPortNumb = GenericIO.readlnInt ();*/

     /* Criação dos threads barbeiro e cliente */

        ContestantGlobalStub contestantGlobalStub = new ContestantGlobalStub(CommConst.globalServerName,CommConst.globalServerPort);
        ContestantBenchStub contestantBenchStub = new ContestantBenchStub(CommConst.benchServerName,CommConst.benchServerPort);
        ContestantPlaygroundStub contestantPlaygroundStub = new ContestantPlaygroundStub(CommConst.playgroundServerName, CommConst.playgroundServerPort);
        CoachBenchStub coachBenchStub = new CoachBenchStub(CommConst.benchServerName, CommConst.benchServerPort);
        CoachPlaygroundStub coachPlaygroundStub = new CoachPlaygroundStub(CommConst.playgroundServerName, CommConst.playgroundServerPort);
        CoachGlobalStub coachGlobalStub = new CoachGlobalStub(CommConst.globalServerName,CommConst.globalServerPort);
        RefereeRefereeSiteStub refereeRefereeSiteStub = new RefereeRefereeSiteStub(CommConst.refereeSiteServerName, CommConst.refereeSiteServerPort);
        RefereePlaygroundStub refereePlaygroundStub = new RefereePlaygroundStub(CommConst.playgroundServerName, CommConst.playgroundServerPort);
        RefereeGlobalStub refereeGlobalStub = new RefereeGlobalStub(CommConst.globalServerName, CommConst.globalServerPort);

        for (int i = 0; i < GameParameters.nContestants; i++)
            contestants[i] = new Contestant (i,0,contestantBenchStub,contestantPlaygroundStub,contestantGlobalStub);
        for (int i = 0; i < GameParameters.nContestants; i++)
            contestants[i] = new Contestant (i,1,contestantBenchStub,contestantPlaygroundStub,contestantGlobalStub);

        for (int i = 0; i < GameParameters.nTeams; i++)
            coaches[i] = new Coach (i,coachBenchStub,coachPlaygroundStub,coachGlobalStub);

        referee = new Referee(refereePlaygroundStub,refereeRefereeSiteStub,refereeGlobalStub);

     /* Comunicação ao servidor dos parâmetros do problema */

        ClientCom cong,conb,conrs,conp;                                       // canal de comunicação
        Message inMessage, outMessage;                       // mensagens trocadas

        cong = new ClientCom (CommConst.globalServerName, CommConst.globalServerPort);
        while (!cong.open ())
        { try
        { Thread.sleep ((long) (1000));
        }
        catch (InterruptedException e) {}
        }

        outMessage = new Message (Message.SETNFIC);
        cong.writeObject (outMessage);
        inMessage = (Message) cong.readObject ();
        if (inMessage.getType() != Message.NFICDONE)
        { GenericIO.writelnString ("Arranque da simulação: Tipo inválido!");
            GenericIO.writelnString (inMessage.toString ());
            System.exit (1);
        }
        cong.close ();

        conb = new ClientCom (CommConst.benchServerName, CommConst.benchServerPort);
        while (!conb.open ())
        { try
        { Thread.sleep ((long) (1000));
        }
        catch (InterruptedException e) {}
        }

        outMessage = new Message (Message.SETNFIC);
        conb.writeObject (outMessage);
        inMessage = (Message) conb.readObject ();
        if (inMessage.getType() != Message.NFICDONE)
        { GenericIO.writelnString ("Arranque da simulação: Tipo inválido!");
            GenericIO.writelnString (inMessage.toString ());
            System.exit (1);
        }
        conb.close ();

        conrs = new ClientCom (CommConst.refereeSiteServerName, CommConst.refereeSiteServerPort);
        while (!conrs.open ())
        { try
        { Thread.sleep ((long) (1000));
        }
        catch (InterruptedException e) {}
        }

        outMessage = new Message (Message.SETNFIC);
        conrs.writeObject (outMessage);
        inMessage = (Message) conrs.readObject ();
        if (inMessage.getType() != Message.NFICDONE)
        { GenericIO.writelnString ("Arranque da simulação: Tipo inválido!");
            GenericIO.writelnString (inMessage.toString ());
            System.exit (1);
        }
        conrs.close ();

        conp = new ClientCom (CommConst.playgroundServerName, CommConst.playgroundServerPort);
        while (!conp.open ())
        { try
        { Thread.sleep ((long) (1000));
        }
        catch (InterruptedException e) {}
        }

        outMessage = new Message (Message.SETNFIC);
        conp.writeObject (outMessage);
        inMessage = (Message) conp.readObject ();
        if (inMessage.getType() != Message.NFICDONE)
        { GenericIO.writelnString ("Arranque da simulação: Tipo inválido!");
            GenericIO.writelnString (inMessage.toString ());
            System.exit (1);
        }
        conp.close ();






     /* Arranque da simulação */

        for (int i = 0; i < GameParameters.nContestants; i++)
            contestants[i].start();
        for (int i = 0; i < GameParameters.nContestants; i++)
            contestants[i].start();

        for (int i = 0; i < GameParameters.nTeams; i++)
            coaches[i].start();

        referee.start();


     /* Aguardar o fim da simulação */

        GenericIO.writelnString ();
        for (int i = 0; i < GameParameters.nContestants; i++)
        { try
        { contestants[i].join ();
        }
        catch (InterruptedException e) {}
            GenericIO.writelnString ("O cliente " + i + " terminou.");
        }
        GenericIO.writelnString ();

        /*for (int i = 0; i < nBarber; i++)
        { while (barber[i].isAlive ())
        { barber[i].sendInterrupt ();
            Thread.yield ();
        }
            try
            { barber[i].join ();
            }
            catch (InterruptedException e) {}
            GenericIO.writelnString ("O barbeiro " + i + " terminou.");
        }*/
        GenericIO.writelnString ();
    }
}
