package DistributedSolution.ClientSide;

import DistributedSolution.ClientSide.Coach.Coach;
import DistributedSolution.ClientSide.Contestant.Contestant;
import DistributedSolution.ClientSide.Referee.Referee;
import DistributedSolution.Message.Message;
import genclass.GenericIO;

/**
 * Created by Andre on 27/04/2016.
 */
public class GameOfTheRopeClient {
    /**
     *   Programa principal.
     */

    public static void main (String [] args)
    {
        int nContestants = 5;                                   // número de clientes
        int nTeams = 2;                                     // número máximo de barbeiros
        Contestant[] contestant = new Contestant [nContestants];     // array de threads cliente
        Coach [] coach = new Coach[nTeams];             // array de threads barbeiro
        Referee referee = new Referee();
        int nGames;                                           // número de iterações do ciclo de vida dos clientes
        String fName;                                        // nome do ficheiro de logging
        String serverHostName = null;                        // nome do sistema computacional onde está o servidor
        int serverPortNumb;                                  // número do port de escuta do servidor

     /* Obtenção dos parâmetros do problema */

        GenericIO.writelnString ("\n" + "      Problema dos Barbeiros Sonolentos\n");
        GenericIO.writeString ("Numero de iterações? ");
        nGames = GenericIO.readlnInt ();
        GenericIO.writeString ("Nome do ficheiro de logging? ");
        fName = GenericIO.readlnString ();
        GenericIO.writeString ("Nome do sistema computacional onde está o servidor? ");
        serverHostName = GenericIO.readlnString ();
        GenericIO.writeString ("Número do port de escuta do servidor? ");
        serverPortNumb = GenericIO.readlnInt ();

     /* Criação dos threads barbeiro e cliente */

        for (int i = 0; i < nContestants; i++)
            contestant[i] = new Contestant (i, serverHostName, serverPortNumb);
        for (int i = 0; i < nTeams; i++)
            coach[i] = new Coach (i,serverHostName, serverPortNumb);

     /* Comunicação ao servidor dos parâmetros do problema */

        ClientCom con;                                       // canal de comunicação
        Message inMessage, outMessage;                       // mensagens trocadas

        con = new ClientCom (serverHostName, serverPortNumb);
        while (!con.open ())
        { try
        { Thread.sleep ((long) (1000));
        }
        catch (InterruptedException e) {}
        }
        outMessage = new Message (Message.SETNFIC, fName, nGames);
        con.writeObject (outMessage);
        inMessage = (Message) con.readObject ();
        if (inMessage.getType() != Message.NFICDONE)
        { GenericIO.writelnString ("Arranque da simulação: Tipo inválido!");
            GenericIO.writelnString (inMessage.toString ());
            System.exit (1);
        }
        con.close ();

     /* Arranque da simulação */

        for (int i = 0; i < nContestants; i++)
            contestant[i].start ();
        for (int i = 0; i < nTeams; i++)
            coach[i].start ();

     /* Aguardar o fim da simulação */

        GenericIO.writelnString ();
        for (int i = 0; i < nContestants; i++)
        { try
        { contestant[i].join ();
        }
        catch (InterruptedException e) {}
            GenericIO.writelnString ("Contestant " + i + " terminated.");
        }
        GenericIO.writelnString ();
        for (int i = 0; i < nTeams; i++)
        { try
        { coach[i].join ();
        }
        catch (InterruptedException e) {}
            GenericIO.writelnString ("Coach " + i + " terminated.");
        }
        /*{ while (barber[i].isAlive ())
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

        try {
            referee.join();
        }
        catch (InterruptedException e){}
        GenericIO.writelnString ("Referee terminated");
        GenericIO.writelnString ();

    }
}
