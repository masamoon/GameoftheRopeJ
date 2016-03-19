package Main;

import Entities.Coach;
import Entities.Contestant;
import Entities.Referee;
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

        Bench bench = new Bench();



        Playground playground = new Playground(global);

        RefereeSite refereeSite = new RefereeSite();

        Referee referee = new Referee(playground, refereeSite, global);

        Coach coach_t1 = new Coach(0,bench,playground,global);
        Coach coach_t2 = new Coach(1,bench,playground,global);

        ArrayList<Contestant> contestants_t1 = new ArrayList<>();
        ArrayList<Contestant> contestants_t2 = new ArrayList<>();

        for (int i = 0; i < 4 ; i++) {
            Contestant c = new Contestant(i,0,5,bench,playground,global); //todo: remove static strength
            contestants_t1.add(c);
        }

        for (int i = 0; i < 4 ; i++) {
            Contestant c = new Contestant(i,1,5,bench,playground,global); //todo: remove static strength
            contestants_t2.add(c);
        }

        coach_t1.start();
        coach_t2.start();
        referee.start();

        for(Contestant c : contestants_t1){
            c.start();
        }

        for(Contestant c : contestants_t2){
            c.start();
        }

    }
}
