package DistributedSolution.ServerSide.Global;

import DistributedSolution.ServerSide.States.CoachState;
import DistributedSolution.ServerSide.States.ContestantState;
import DistributedSolution.ServerSide.States.RefereeState;
import genclass.TextFile;

import java.util.ArrayList;

public class GlobalRemote {


    private final int NUMBER_OF_TEAMS = 2;
    private final int NUMBER_OF_PLAYERS_PER_TEAM = 5;

    private int gamesNum;

    private int [] [] contestantStrengths;

    private ContestantState [] [] contestantStates ;

    private RefereeState refereeState;

    private CoachState coachStates [];


    private ArrayList<Integer> team1AtRope;
    private ArrayList <Integer> team2AtRope;

    private int flagPos;

    private int trialNum;

    private boolean matchInProgress;

    private TextFile f;
    private String path;

    public GlobalRemote(String path){

        f = new TextFile();
        f.openForWriting(null,"log.txt");
        String opline = "                       Game of the Rope - Description of the internal state";
        String str =  "Ref Coa1 Cont 1 Cont 2 Cont 3 Cont 4 Cont 5 Coa2 Cont 1 Cont 2 Cont 3 Cont 4 Cont 5      Trial";
        String str2 = "Sta Stat Sta SG Sta SG Sta SG Sta SG Sta SG Stat Sta SG Sta SG Sta SG Sta SG Sta SG 3 2 1 . 1 2 3 NB PS";
        f.writelnString(opline);
        f.writelnString("");
        f.writelnString(str);
        f.writelnString(str2);

        this.path = path;

        contestantStates = new ContestantState [NUMBER_OF_TEAMS] [NUMBER_OF_PLAYERS_PER_TEAM];

        for (int i=0; i < NUMBER_OF_TEAMS; i++) {
            for(int j=0; j < NUMBER_OF_PLAYERS_PER_TEAM; j++) {
                contestantStates [i] [j] = ContestantState.INIT;
            }
        }

        coachStates = new CoachState[2];

        for (int i = 0; i < coachStates.length ; i++) {
            coachStates[i] = CoachState.INIT;
        }

        this.gamesNum = 0;


        contestantStrengths = new int [NUMBER_OF_TEAMS] [NUMBER_OF_PLAYERS_PER_TEAM];

        this.flagPos = 0;
        this.matchInProgress = true;

    }

    /**
     *
     * Inserts new line into logger
     *
     */
    public synchronized void insertLine(){

        String ref_state = getRefereeState().getAcronym();
        String coach_state_1 = getCoachState(0).getAcronym();
        String coach_state_2 = getCoachState(1).getAcronym();

        StringBuilder team1 = new StringBuilder();
        StringBuilder team2 = new StringBuilder();

        for (int i = 0; i <= 4 ; i++) {
            team1.append(getContestantState(i,0).getAcronym());
            team1.append(" ");
            if(getStrength(i,0)<10)
                team1.append(" ");
            team1.append(getStrength(i,0));
            team1.append(" ");
        }

        for (int i = 0; i <= 4 ; i++) {
            team2.append(getContestantState(i,1).getAcronym());
            team2.append(" ");
            if(getStrength(i,1)<10)
                team2.append(" ");
            team2.append(getStrength(i,1));
            team2.append(" ");
        }

        int trial_no = getTrialNum();

        StringBuilder selection1 = new StringBuilder();
        StringBuilder selection2 = new StringBuilder();

        if(!team1AtRope.isEmpty()){
            for(int sel = 0; sel< team1AtRope.size(); sel++)
            {
                int id = team1AtRope.get(sel) +1;
                selection1.append( id + " ");
            }
        }
        int length = team1AtRope.size();
        if (length <3){
            for(int i=0; i<(3-length); i++){
                selection1.append("- ");
            }
        }

        if(!team2AtRope.isEmpty()){
            for(int sel = 0; sel< team2AtRope.size(); sel++)
            {
                int id = team2AtRope.get(sel) +1;
                selection2.append( id + " ");
            }
        }
        length = team2AtRope.size();
        if (length <3){
            for(int i=0; i<(3-length); i++){
                selection2.append("- ");
            }
        }

        String line = ref_state +" " + coach_state_1+" " +  team1.toString() + coach_state_2 + " "+ team2.toString()
                +selection1.toString() +". "+selection2.toString() + " " + trial_no+"  " + flagPos;

        f.writelnString(line);

    }

    /**
     * writes line on log file indicating the winner team and score
     * @param score1 score of team1
     * @param score2 score of team2
     * @param winner winner team
     */
    public void matchWinnerLine(int score1, int score2, int winner) {
        f.writelnString("Match was won by team " + winner + " (" + score1 + "-" + score2 + ").\n");
    }


    /**
     * writes line on log file indicating a draw
     */
    public void matchTieLine(){
        f.writelnString("Match was a draw.\n");
    }

    /**
     * writes line on log file indicating the game's winner
     * @param nteam winner team
     */
    public synchronized void gameWinnerLinePoints(int nteam){
        f.writelnString("Game "+gamesNum+" was won by team "+nteam+" by points in "+trialNum+" trials.\n");
    }

    /**
     *writes line on log file indicating a knock-out win by a team
     * @param nteam winner team
     */
    public synchronized void gameWinnerLineKO(int nteam){
        f.writelnString("Game "+gamesNum+" was won by team "+nteam+" by knock-out in "+trialNum+" trials.\n");
    }

    /**
     *writes line on log file indicating a draw game
     */
    public void gameTieLine(){
        f.writelnString("Game was a draw.\n");
    }

    /**
     * closes log file
     */
    public void closeFile(){
        f.endOfFile();
        f.close();
    }

    public synchronized void leaveRope (int contestantID, int teamID)
    {
        if(teamID==0) team1AtRope.remove(team1AtRope.indexOf(contestantID));
        if(teamID==1) team2AtRope.remove(team2AtRope.indexOf(contestantID));

        if(!gameFinished()) insertLine();
    }




    /**
     * Set the state of the current match.
     * @param matchInProgress boolean that indicates if match is in progress
     */
    public void setMatchInProgress(boolean matchInProgress) {
        this.matchInProgress = matchInProgress;
    }

    /**
     *  Check if match is currently in progress.
     * @return true if match is underway
     */
    public boolean matchInProgress() {
        return this.matchInProgress;
    }

    /**
     * Check if the current game is finished
     * @return true if game is finished
     */
    public boolean gameFinished(){
        if((Math.abs(flagPos)>=4) || trialNum>=6)
            return true;
        else
            return false;
    }

    /** Set a new state for a given contestant
     * @param contestantID contestant's ID
     * @param state contestant's state to be updated to
     *@param teamID team's ID of this contestant
     * */
    public synchronized void setContestantState (int contestantID, int teamID, ContestantState state){
        this.contestantStates[teamID][contestantID] = state;
        if(state == ContestantState.STAND_IN_POSITION){
            if(teamID==0) team1AtRope.add(contestantID);
            if(teamID==1) team2AtRope.add(contestantID);
        }
        insertLine();
    }

    /**
    * get contestant State
    * @param contestantID contestant's ID
    * @param teamID team's ID
    * @return current contestant's state
     */
    public synchronized ContestantState getContestantState (int contestantID, int teamID){

        return this.contestantStates[teamID][contestantID];
    }

    /** set a new state for the Referee
     @param state referee's state to be updated to
    * */
    public synchronized void setRefereeState (RefereeState state){
        this.refereeState = state;

        insertLine();
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
    public synchronized void setCoachState (int teamID, CoachState state){
        this.coachStates[teamID] = state;
        insertLine();
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
     * Get a contestant's current strength level
     * @param id Contestant's id
     * @param teamID team's id
     * @return the strength of this contestant
     */
    public int getStrength(int id, int teamID){
        return contestantStrengths [teamID] [id];

    }

    /**
     * Set a contestant's strength level
     * @param id Contestant's id
     * @param teamID team's id
     * @param str the strength of this contestant to bet set
     */
    public void setStrength(int id, int teamID, int str){
        contestantStrengths [teamID] [id] = str;
    }

    /**
     *  Gets the number of trials of the current game
     * @return number of trials
     */

    public int getTrialNum(){
        return this.trialNum;
    }

    /**
     * increments trial number by 1
     */
    public void incrementTrialNum(){
        this.trialNum +=1;
    }

    /**
     * resets trial number back to zero
     */
    public void resetTrialNum(){
        this.trialNum =0;
    }

    /**
     * Alters the position of the flag
     * @param flagPos
     */
    public void changeFlagPos(int flagPos) {
        this.flagPos += flagPos;
    }

    public void resetFlagPos() {
        this.flagPos = 0;
    }

    public int getFlagPos() {
        return this.flagPos;
    }

    /**
     * increments number of games by 1
     */
    public void incrementGamesNum() {
        this.gamesNum+=1;
    }

}
