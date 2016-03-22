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

    private int trialscore_t1;

    private int trialscore_t2;

    private ContestantState contestantStates_t1 [];

    private ContestantState contestantStates_t2 [];

    private RefereeState refereeState;

    private CoachState coachStates [];


    /**
     *  Check is match is in progresss
     * @return true if match is underway
     */
    public boolean matchInProgress() {
        return ((gamescore_t1 + gamescore_t2) < 3) ? true : false;

    }

    /**
     * Check if match is finished
     * @return true if match is finished
     */
    public boolean matchFinished() {

        return ((gamescore_t1 + gamescore_t2) >= 3) ? true : false;
    }

    /**
     * Check if game is in progress
     * @return true if game is underway
     */
    public boolean gameInProgress(){

        return true;

    }

    /**
     *
     * @return
     */
    public boolean endContestantOps(){

        return true;
    }


    /**
     *Check if game is finished
     * @return true if game is finished
     */
    public boolean gameFinished(){

        return ((trialscore_t1 + trialscore_t2)<6) ? true : false;

    }



    /* State controls */

    /** contestants
    * @param contestantID contestant's ID
    * @param state contestant's state to be updated to
     *@param teamID team's ID
    * */
    public synchronized void setContestantState (int teamID, int contestantID, ContestantState state){
       if(teamID == 1)
           this.contestantStates_t1[contestantID] = state;
        else if (teamID == 2)
           this.contestantStates_t2[contestantID] = state;

        // todo: report status (log)
    }

    /**
    * get contestant State
    * @param contestantID contestant's ID
    * @param teamID team's ID
    * @return current contestant's state
     */
    public synchronized ContestantState getContestantState (int contestantID, int teamID){
        if(teamID == 1)
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
     *Gets the score for team 1
     * @return score for team1
     */
    public int getGamescore_t1(){ return this.gamescore_t1; }

    /**
     *Gets the score for team 2
     * @return score for team2
     */
    public int getGamescore_t2(){ return this.gamescore_t2; }

    /**
     * sets gamescore for team1 as new value
     * @param score
     */
    public void setGamescore_t1(int score){this.gamescore_t1= score; }

    /**
     * sets gamescore for team2 as new value
     * @param score
     */
    public void setGamescore_t2(int score){this.gamescore_t2= score; }

    /**
     * increment by 1 the score of team1
     */
    public void incGamescore_t1(){this.gamescore_t1+=1; }

    /**
     * increment by 1 the score of team2
     */
    public void incGamescore_t2(){this.gamescore_t2+=1; }


    public int getTrialscore_t1() {
        return trialscore_t1;
    }

    public void setTrialscore_t1(int trialscore_t1) {
        this.trialscore_t1 = trialscore_t1;
    }

    public int getTrialscore_t2(){
        return trialscore_t2;
    }

    public void setTrialscore_t2(int trialscore_t2){
        this.trialscore_t2 = trialscore_t2;
    }

    public void incTrialscore_t1(){
        this.trialscore_t1+=1;
    }

    public void incTrialscore_t2(){
        this.trialscore_t2+=1;
    }
}
