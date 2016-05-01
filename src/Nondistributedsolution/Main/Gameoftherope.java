package Nondistributedsolution.Main;

import Nondistributedsolution.Coach.Coach;
import Nondistributedsolution.Contestant.Contestant;
import Nondistributedsolution.Referee.Referee;
import Nondistributedsolution.Monitors.Bench;
import Nondistributedsolution.Monitors.Global;
import Nondistributedsolution.Monitors.Playground;
import Nondistributedsolution.Monitors.RefereeSite;
import genclass.GenericIO;

/**
 * Created by Andre on 19/03/2016.
 *
 *  Main program.
 */
public class Gameoftherope {

    public static void main(String[] args){

        Global global = new Global("/");

        RefereeSite refereeSite = new RefereeSite(global);

        Bench bench = new Bench(global, refereeSite);

        Playground playground = new Playground(global, bench, refereeSite);



        Referee referee = new Referee(playground, refereeSite, global);

        Coach coach_t1 = new Coach(0,bench,playground,global);
        Coach coach_t2 = new Coach(1,bench,playground,global);

        Contestant [] contestants_t1 = new Contestant[5];
        Contestant [] contestants_t2 = new Contestant[5];


        referee.start();

        coach_t1.start();
        coach_t2.start();

        for (int i = 0; i < 5 ; i++) {
            Contestant c = new Contestant(i,0,bench,playground,global);
            contestants_t1[i] = c;
            c.start();
        }

        for (int i = 0; i < 5 ; i++) {
            Contestant c = new Contestant(i,1,bench,playground,global);
            contestants_t2[i] = c;
            c.start();
        }

        try
        {
            referee.join();
        }
        catch (InterruptedException e) {}
        GenericIO.writelnString ("Referee Finished.");

        try
        {
            coach_t1.join();
        }
        catch (InterruptedException e) {}
        GenericIO.writelnString ("Coach from Team 1 Finished.");

        try
        {
            coach_t2.join();
        }
        catch (InterruptedException e) {}
        GenericIO.writelnString ("Coach from Team 1 Finished.");

        GenericIO.writelnString ();
        for (int i = 0; i < 5; i++)
        {
            try {
                contestants_t1[i].join();
            }
            catch (InterruptedException e) {}
            GenericIO.writelnString ("Contestant " + i + " from Team 1 Finished.");
        }
        for (int i = 0; i < 5; i++)
        {
            try {
                contestants_t2[i].join();
            }
            catch (InterruptedException e) {}
            GenericIO.writelnString ("Contestant " + i + " from Team 2 has Finished.");
        }

        GenericIO.writelnString ();

    }
}
