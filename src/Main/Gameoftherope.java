package Main;

import Entities.Coach;
import Entities.Contestant;
import Entities.Referee;
import Logging.Logger;
import Monitors.Bench;
import Monitors.Global;
import Monitors.Playground;
import Monitors.RefereeSite;
import genclass.GenericIO;

import java.util.ArrayList;

/**
 * Created by Andre on 19/03/2016.
 *
 *  Main program.
 */
public class Gameoftherope {

    public static void main(String[] args){

        Global global = new Global();
        Logger logger = new Logger("/",global);


        Bench bench = new Bench(global,logger);

        Playground playground = new Playground(global,logger);

        RefereeSite refereeSite = new RefereeSite(global,logger);

        Referee referee = new Referee(playground, refereeSite, global, logger);

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
