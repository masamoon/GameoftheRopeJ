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

    private ContestantState contestantStates_t1 [];

    private ContestantState contestantStates_t2 [];

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
    * @param state contestant's state to be updated to
     *@param teamID team's ID
    * */
    public synchronized void setContestantState (int teamID, int contestantID, ContestantState state){
       if(teamID == 0)
           this.contestantStates_t1[contestantID] = state;
        else
           this.contestantStates_t2[contestantID] = state;

        // todo: report status (log)
    }

    /**
    * get contestant State
    * @param contestantID contestant's ID
    * @param teamID team's ID
    * @return current contestant's state
     */
    public synchronized ContestantState getContestantState (int teamID,int contestantID){
        if(teamID == 0)
            return this.contestantStates_t1[contestantID];
        else
            return this.contestantStates_t2[contestantID];
    }

    /** referee
     @param state referee's state to be updated to
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
   * @param teamID team's Id
   * @return current coach's State
    */
    public synchronized CoachState getCoachState (int teamID){
        return this.coachStates[teamID];

    }

    /**
     *
     * @return
     */
    public int getGamescore_t1(){ return this.gamescore_t1; }

    /**
     *
     * @return
     */
    public int getGamescore_t2(){ return this.gamescore_t2; }

}
