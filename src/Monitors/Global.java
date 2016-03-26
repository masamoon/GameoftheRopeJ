package Monitors;

import Entities.Coach;
import States.CoachState;
import States.ContestantState;
import States.RefereeState;

import java.util.Arrays;
import java.util.Random;

/**
 * Created by jonnybel on 3/8/16.
 */
public class Global {


    private int gamescore_t1;

    private int gamescore_t2;

    private int trialscore_t1;

    private int trialscore_t2;

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

    boolean benchCalled;

    boolean benchReady;


    public Global(){

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

        this.benchReady = false;
        this.benchCalled = false;

        this.gamescore_t1 =0;
        this.gamescore_t2= 0;
        this.trialscore_t1= 0;
        this.trialscore_t2 =0;

        this.benchTeam1 = 0;
        this.benchTeam2 = 0;

        this.selectedTeam1 = new int [] {-1,-1,-1};
        this.selectedTeam2 = new int [] {-1,-1,-1};

        this.benchReady = false;

        this.strength_t1 = new int[5];
        this.strength_t2 = new int[5];

        Random r = new Random();

        for (int i = 0; i < this.strength_t1.length ; i++) {
            strength_t1[i] = r.nextInt(10);
            strength_t2[i] = r.nextInt(10);
        }

    }

    /**
     *gets benchCalled
     * @return
     */
    public boolean benchCalled() {
        return benchCalled;
    }

    /**
     *sets benchCalled
     * @param benchCalled
     */
    public void setBenchCalled(boolean benchCalled) {
        this.benchCalled = benchCalled;
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



    /**
     *  Check is match is in progresss
     * @return true if match is underway
     */
    public boolean matchInProgress() {
        if((gamescore_t1 + gamescore_t2) < 3)
            return true;
        else
            return false;

        //return ((gamescore_t1 + gamescore_t2) < 3) ? true : false;

    }
    /**
     * Check if match is finished
     * @return true if match is finished
     */
    public boolean matchFinished() {

        if((gamescore_t1 + gamescore_t2) >= 3)
            return true;
        else
            return false;
    }

    /**
     *Check if game is finished
     * @return true if game is finished
     */
    public boolean gameFinished(){
        if((trialscore_t1+trialscore_t2)<6)
            return false;
        else
            return true;
        //return ((trialscore_t1 + trialscore_t2)<6) ? true : false;

    }

    /* State controls */

    /** contestants
    * @param contestantID contestant's ID
    * @param state contestant's state to be updated to
     *@param teamID team's ID
    * */
    public synchronized void setContestantState (int contestantID, int teamID, ContestantState state){
       if(teamID == 0)
           this.contestantStates_t1[contestantID] = state;
        else if (teamID == 1)
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

    /**
     *
     * @return
     */
    public int getTrialscore_t1() {
        return trialscore_t1;
    }

    /**
     *
     * @param trialscore_t1
     */
    public void setTrialscore_t1(int trialscore_t1) {
        this.trialscore_t1 = trialscore_t1;
    }

    /**
     *
     * @return
     */
    public int getTrialscore_t2(){
        return trialscore_t2;
    }

    /**
     *
     * @param trialscore_t2
     */
    public void setTrialscore_t2(int trialscore_t2){
        this.trialscore_t2 = trialscore_t2;
    }

    /**
     *
     */
    public void incTrialscore_t1(){
        this.trialscore_t1+=1;
    }

    /**
     *
     */
    public void incTrialscore_t2(){
        this.trialscore_t2+=1;
    }


    /**
     * increments number o contestants sitting on the bench
     * @param teamID contestant's team's id
     */
    public void incrementSittingAtBench(int teamID){
        if(teamID==1){
            benchTeam1++;
            System.out.println("Total contestants sitting on bench of team " + teamID + ":"+ benchTeam1);
        }
        else{
            benchTeam2++;
            System.out.println("Total contestants sitting on bench of team " + teamID + ":"+ benchTeam2);
        }


    }

    /**
     * decrements number o contestants sitting on the bench
     * @param teamID contestant's team's id
     */
    public void decrementSittingAtBench(int teamID){
        if(teamID==1){
            benchTeam1--;
            System.out.println("Total contestants sitting on bench of team " + teamID + ":"+ benchTeam1);
        }
        else {
            benchTeam2--;
            System.out.println("Total contestants sitting on bench of team " + teamID + ":" + benchTeam2);
        }
    }

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
            System.out.println("Total contestants standing up for team" + teamID + ": "+ standingTeam1);
        }
        else{
            standingTeam2++;
            System.out.println("Total contestants standing up for team" + teamID + ": "+ standingTeam2);
        }


    }

    /**
     * decrements number of contestants in position for trial beginning
     * @param teamID contestants team's id
     */
    public void decrementStandingInPosition(int teamID){
        if(teamID==1){
            standingTeam1--;
            System.out.println("Total contestants sitting on bench of team " + teamID + ": "+ standingTeam1);
        }
        else {
            standingTeam2--;
            System.out.println("Total contestants standing up for team  " + teamID + ": " + standingTeam2);
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
     * @param teamID
     * @param id
     * @return
     */
    public int getStrength(int teamID, int id){
        if(teamID==1)
            return strength_t1[id];
        else
            return strength_t2[id];

    }

    /**
     *
     * @param teamID
     * @param id
     * @param str
     */
    public void setStrength(int teamID, int id, int str){
        if(teamID==1)
            strength_t1[id] = str;
        else
            strength_t2[id] = str;

    }
}
