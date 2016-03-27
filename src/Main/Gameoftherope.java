package Main;

import Entities.Coach;
import Entities.Contestant;
import Entities.Referee;
import Logging.Logger;
import Monitors.Bench;
import Monitors.Global;
import Monitors.Playground;
import Monitors.RefereeSite;

import java.util.ArrayList;

/**
 * Created by Andre on 19/03/2016.
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

        ArrayList<Contestant> contestants_t1 = new ArrayList<>();
        ArrayList<Contestant> contestants_t2 = new ArrayList<>();


        referee.start();

        coach_t1.start();
        coach_t2.start();

        for (int i = 0; i < 5 ; i++) {
            Contestant c = new Contestant(i,0,bench,playground,global); //todo: remove static strength
            contestants_t1.add(c);
            c.start();
        }

        for (int i = 0; i < 5 ; i++) {
            Contestant c = new Contestant(i,1,bench,playground,global); //todo: remove static strength
            contestants_t2.add(c);
            c.start();
        }



        // todo: cenas para as threads ficarem "arrumadas" no fim

    }
}
