package Monitors;

import Entities.Coach;
import States.CoachState;
import States.ContestantState;
import States.RefereeState;

/**
 * Created by jonnybel on 3/8/16.
 */
public class Global {


    private int gamescore_t1;

    private int gamescore_t2;

    private ContestantState contestantStates [];

    private RefereeState refereeState;

    private CoachState coachStates [];

    public boolean matchInProgress() {

        return true;
    }

    public boolean matchFinished() {
        return true;
    }


    public boolean gameInProgress(){

        return true;
    }

    public boolean endContestantOps(){

        return true;
    }

    public boolean gameFinished(){
        return true;
    }



    /* State controls */

    /** contestants
    * @param contestantID contestant's ID
    * @param contestant's state to be updated to
    * */
    public synchronized void setContestantState (int contestantID, ContestantState state){
        this.contestantStates[contestantID] = state;

        // todo: report status (log)
    }

    /**
    * get contestant State
    * @param contestantID contestant's ID
    * @return current contestant's state
     */
    public synchronized ContestantState getContestantState (int contestantID){
        return this.contestantStates[contestantID];

    }

    /** referee
     @param referee's state to be updated to
    * */
    public synchronized void setRefereeState (RefereeState state){
        this.refereeState = state;

        // todo: report status (log)
    }

    /**
    * get referee State
    * @return current Referee's State
     */
    public synchronized RefereeState getRefereeState (){
        return this.refereeState;

    }

    /* coaches */

    /**
    * set Coach State to new state
    * @param teamID team's id
    * @param state coach's state to be updated to
     */
    public synchronized void setCoachState (int teamID, CoachState state){
        this.coachStates[teamID] = state;

        // todo: report status (log)
    }

  /**
   * get coach State
   * @return current coach's State
    */
    public synchronized CoachState getCoachState (int teamID){
        return this.coachStates[teamID];

    }

}
