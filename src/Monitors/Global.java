package Monitors;

import Logging.Logger;
import States.CoachState;
import States.ContestantState;
import States.RefereeState;

import java.util.Random;

/**
 * Created by jonnybel on 3/8/16.
 */
public class Global {


    private int gamesNum;

    private int gamescore_t1;
    private int gamescore_t2;

    private int [] strength_t1;
    private int [] strength_t2;

    private ContestantState contestantStates_t1 [];

    private ContestantState contestantStates_t2 [];

    private RefereeState refereeState;

    private CoachState coachStates [];

    private int benchTeam1;
    private int benchTeam2;

    private int [] selectedTeam1;
    private int [] selectedTeam2;

    private int standingTeam1;
    private int standingTeam2;

    boolean benchCalled1;
    boolean benchCalled2;

    boolean benchReady;

    private int flagPos;

    private int trialNum;

    private boolean matchInProgress;


    public Global(){

        refereeState = RefereeState.START_OF_THE_MATCH;

        contestantStates_t1 = new ContestantState[5];

        for (int i = 0; i < contestantStates_t1.length ; i++) {
            contestantStates_t1[i] = ContestantState.INIT;
        }

        contestantStates_t2 = new ContestantState[5];

        for (int i = 0; i < contestantStates_t2.length ; i++) {
            contestantStates_t2[i] = ContestantState.INIT;
        }

        coachStates = new CoachState[2];

        for (int i = 0; i < coachStates.length ; i++) {
            coachStates[i] = CoachState.INIT;
        }

        this.gamesNum = 0;

        this.benchReady = false;
        this.benchCalled1 = false;
        this.benchCalled2 = false;

        this.gamescore_t1 =0;
        this.gamescore_t2= 0;

        this.benchTeam1 = 0;
        this.benchTeam2 = 0;

        this.selectedTeam1 = new int [] {-1,-1,-1};
        this.selectedTeam2 = new int [] {-1,-1,-1};

        this.benchReady = false;

        this.strength_t1 = new int[5];
        this.strength_t2 = new int[5];

        this.flagPos = 0;

        this.standingTeam1 = 0;
        this.standingTeam2 = 0;

        this.matchInProgress = true;

        Random r = new Random();

        for (int i = 0; i < this.strength_t1.length ; i++) {
            strength_t1[i] = r.nextInt(10);
            strength_t2[i] = r.nextInt(10);
        }

    }

    /**
     *gets benchCalled
     * @return true if bench is called
     */
    public boolean benchCalled(int teamID) {
        if(teamID==0)
            return benchCalled1;
        else
            return benchCalled2;
    }

    /**
     *sets benchCalled
     * @param teamID team's id
     * @param benchCalled
     */
    public void setBenchCalled(int teamID, boolean benchCalled) {
        if(teamID==0)
            this.benchCalled1 = benchCalled;
        else
            this.benchCalled2 = benchCalled;
    }

    /**
     * sets the selected team for a trial on a team
     * @param teamID team's id
     * @param first first contestant
     * @param second second contestant
     * @param third third contestant
     */
    public void selectTeam(int teamID, int first, int second, int third) {
        if(teamID==0)
            this.selectedTeam1 = new int [] {first,second,third};
        else
            this.selectedTeam2 = new int [] {first,second,third};
    }

    /**
     *gets the selected team for a trial
     * @param teamID
     * @return selected team for trial
     */
    public int[] getSelection(int teamID) {
        if(teamID==0)
            return selectedTeam1;
        else
            return selectedTeam2;
    }

    /**
     * erases the selected teams
     */
    public void eraseTeamSelections(){
        this.selectedTeam1 = new int [] {-1,-1,-1};
        this.selectedTeam2 = new int [] {-1,-1,-1};
    }


    public void setMatchInProgress(boolean matchInProgress) {
        this.matchInProgress = matchInProgress;
    }

    /**
     *  Check is match is in progresss
     * @return true if match is underway
     */
    public boolean matchInProgress() {
        return this.matchInProgress;
    }

    /**
     *Check if game is finished
     * @return true if game is finished
     */
    public boolean gameFinished(){
        if((Math.abs(flagPos)>=4) || trialNum>=6)
            return true;
        else
            return false;
    }

    /* State controls */

    /** contestants
    * @param contestantID contestant's ID
    * @param state contestant's state to be updated to
     *@param teamID team's ID
    * */
    public synchronized void setContestantState (int contestantID, int teamID, ContestantState state, Logger logger){
       if(teamID == 0)
           this.contestantStates_t1[contestantID] = state;
        else if (teamID == 1)
           this.contestantStates_t2[contestantID] = state;

        logger.insertLine();
    }

    /**
    * get contestant State
    * @param contestantID contestant's ID
    * @param teamID team's ID
    * @return current contestant's state
     */
    public synchronized ContestantState getContestantState (int contestantID, int teamID){
        if(teamID == 0)
            return this.contestantStates_t1[contestantID];
        else
            return this.contestantStates_t2[contestantID];
    }

    /** referee
     @param state referee's state to be updated to
    * */
    public synchronized void setRefereeState (RefereeState state, Logger logger){
        this.refereeState = state;

        logger.insertLine();
    }

    /**
    * get referee State
    * @return current Referee's State
     */
    public synchronized RefereeState getRefereeState (){
        return this.refereeState;
    }

    /**
    * set Coach State to new state
    * @param teamID team's id
    * @param state coach's state to be updated to
     */
    public synchronized void setCoachState (int teamID, CoachState state, Logger logger){
        this.coachStates[teamID] = state;
        logger.insertLine();
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
    /**
     * increment by 1 the score of team1
     */
    public void incGamescore_t1(){this.gamescore_t1+=1; }

    /**
     * increment by 1 the score of team2
     */
    public void incGamescore_t2(){this.gamescore_t2+=1; }

    /**
     * gets number of contestants sitting on the bench
     * @param teamID contestants team's id
     * @return number of contestants sitting on the bench
     */
    public int getSittingAtBench(int teamID) {
        if(teamID==1)
            return benchTeam1;
        else
            return benchTeam2;
    }

    /**
     * gets benchReady
     * @return
     */
    public boolean getBenchReady() {
        return benchReady;
    }

    /**
     * sets benchReady
     * @param benchReady
     */
    public void setBenchReady(boolean benchReady){
        this.benchReady = benchReady;
    }

    /**
     *increments number of contestants in position for trial beginning
     * @param teamID contestants team's id
     */
    public void incrementStandingInPosition(int teamID){
        if(teamID==1){
            standingTeam1++;
        }
        else{
            standingTeam2++;
        }


    }

    /**
     * decrements number of contestants in position for trial beginning
     * @param teamID contestants team's id
     */
    public void decrementStandingInPosition(int teamID){
        if(teamID==1){
            standingTeam1--;
        }
        else {
            standingTeam2--;
        }
    }

    /**
     * gets number of contestants standing in position for trial beginning
     * @param teamID
     * @return
     */
    public int getStandingInPosition(int teamID) {
        if(teamID==1)
            return standingTeam1;
        else
            return standingTeam2;
    }

    /**
     *
     * @param id
     * @param teamID
     * @return the strength of this contestant
     */
    public int getStrength(int id, int teamID){
        if(teamID==1)
            return strength_t1[id];
        else
            return strength_t2[id];

    }

    /**
     *
     * @param id
     * @param teamID
     * @param str
     */
    public void setStrength(int id, int teamID, int str){
        if(teamID==1)
            strength_t1[id] = str;
        else
            strength_t2[id] = str;

    }



    public int getTrialNum(){
        return this.trialNum;
    }

    /**
     * increments trial num by 1
     */
    public void incrementTrialNum(){
        this.trialNum +=1;
    }

    /**
     * resets trial num back to zero
     */
    public void resetTrialNum(){
        this.trialNum =0;
    }

    public int getFlagPos() {
        return flagPos;
    }

    public void setFlagPos(int flagPos) {
        this.flagPos = flagPos;
    }

    public int getGamesNum() {
        return gamesNum;
    }

    /**
     * increments number of games by 1
     */
    public void incrementGamesNum() {
        this.gamesNum+=1;
    }

    public int[] getStrength_t1(){ return this.strength_t1;}

    public int[] getStrength_t2(){ return this.strength_t2;}
}
