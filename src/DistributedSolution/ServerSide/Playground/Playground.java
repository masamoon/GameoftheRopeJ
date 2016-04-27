package DistributedSolution.ServerSide.Playground;

import DistributedSolution.ServerSide.Bench.Bench;
import DistributedSolution.ServerSide.Global.Global;
import Nondistributedsolution.Coach.CoachState;
import Nondistributedsolution.Contestant.ContestantState;
import Nondistributedsolution.Logging.Logger;
import Nondistributedsolution.Referee.RefereeState;

import java.util.stream.IntStream;


/**
 * Created by jonnybel on 3/8/16.
 *
 * This Class implements the shared region for the Playground, with synchronization based on monitors
 */
public class Playground {


    /**
     *  General Information Repository object
     */
    private final Global global;

    /**
     * Logger object
     */
    private final Logger logger;

    /**
     * Number of complete teams that are standing in position (from 0 to 2)
     */
    private int teamsReady;

    /**
     * Number of contestants that have finished pulling the rope during a trial.
     */
    private int contestantsDone;

    /**
     * True if a trial has just been called by the Referee
     */
    private boolean trialCalled;

    /**
     * True if a trial has just been started by the Referee
     */
    private boolean trialStarted;

    /**
     * Constructor for the Playground
     * @param global
     * @param logger
     */
    public Playground(Global global, Logger logger){
        this.global = global;

        this.teamsReady = 0;
        this.contestantsDone = 0;

        this.trialStarted = false;
        this.trialCalled = false;

        this.logger = logger;


    }

    /**
     * Coach enters a blocking state and waits for the Referee to call a trial
     * @param teamID Coach's teamID
     */
    public synchronized void waitForCalling(int teamID){

        global.setCoachState(teamID, CoachState.WAIT_FOR_REFEREE_COMMAND,logger);

        while(!trialCalled)
        {
            try {
                wait();
            } catch (InterruptedException e) {}
        }
    }


    /**
    *   The last contestant to sit wakes the Ref
     */
    public synchronized void benchWakeRef(){
        notifyAll();
    }



    /**
     *  Referee waits for the contestants to be seated at the bench and calls a trial
     */
    public synchronized void callTrial(){

        global.incrementTrialNum();
        while(!global.getBenchReady()){
            try {
                wait();
            } catch (InterruptedException e) {}
        }
        teamsReady=0;
        contestantsDone=0;

        global.setRefereeState(RefereeState.TEAMS_READY,logger);
        global.eraseTeamSelections();
        trialCalled = true;

        notifyAll();
    }

    /**
     * Coach waits for his selected team to be standing in position
     * @param teamID coach's teamID
     */
    public synchronized void waitForContestants(int teamID){

        global.setCoachState(teamID, CoachState.ASSEMBLE_TEAM,logger);
        while(global.getStandingInPosition(teamID)<3){
            try
            {
                wait ();
            }
            catch (InterruptedException e) {}
        }
    }


    /**
     * Contestant stands in position and waits for the trial to start
     * If he is the 3rd contestant of his team to get ready, he wakes the Coaches.
     * @param contestantID contestant's ID
     * @param teamID contestant's team ID
     */
    public synchronized void followCoachAdvice (int contestantID, int teamID) {

        global.incrementStandingInPosition(teamID);
        global.setContestantState(contestantID, teamID, ContestantState.STAND_IN_POSITION,logger);

        if(global.getStandingInPosition(teamID)==3)
        {
            notifyAll();
        }

        while(!trialStarted)
        {
            try {
                wait();
            } catch (InterruptedException e) {}
        }

    }


    /** Coach informs Referee of the readiness of his team and waits for the trial to end.
     * @param teamID team's ID
     */
    public synchronized  void informReferee(int teamID){

        global.setBenchCalled(teamID, false);

        global.setCoachState(teamID, CoachState.WATCH_TRIAL,logger);

        teamsReady++;

        if(teamsReady==2){
            notifyAll();
        }

        while(trialCalled || trialStarted) {
            try {
                wait();
            } catch (InterruptedException e) {}
        }

    }

    /**
     *   Waits for both the teams to be ready and begins the trial
     */
    public synchronized  void startTrial(){

        while(teamsReady<2)
        {
            try{
                wait ();
            }
            catch (InterruptedException e) {}
        }

        global.setRefereeState(RefereeState.WAIT_FOR_TRIAL_CONCLUSION,logger);

        trialStarted = true;
        trialCalled = false;

        notifyAll();

    }


    /**
     * Contestant changes his state to Do_Your_Best before pulling the rope
     * @param contestantID contestant's ID
     * @param teamID team's id
     */
    public synchronized  void getReady( int contestantID, int teamID) {
        global.setContestantState(contestantID,teamID,ContestantState.DO_YOUR_BEST,logger);

    }


    /**
     * The last contestant to finish pulling the rope wakes the referee
     * @param teamID team's id
     */
    public synchronized void done(int teamID){

        contestantsDone++;
        if(contestantsDone==6) {
            notifyAll();
        }

        while(trialStarted) {
            try {
                wait();
            } catch (InterruptedException e) {}
        }

        global.decrementStandingInPosition(teamID);

    }

   /**
    *   Referee decides who's the winner based on the total strength of both teams.
    *   He also checks if the game has been finished and if so he checks if the Match is finished.
    *
    */
    public synchronized void assertTrialDecision(){

        while(contestantsDone<6){
            try {
                wait();
            } catch (InterruptedException e) {}
        }

        int team1Power = IntStream.of(global.getSelection(0)).sum();
        int team2Power = IntStream.of(global.getSelection(1)).sum();

        /*
        System.out.println("trial assertion:");
        System.out.println("trial number: " + global.getTrialNum());

        System.out.println("team 1 power " + team1Power);
        System.out.println("team 2 power " + team2Power);
        */
        global.setFlagPos((global.getFlagPos()+(team1Power-team2Power))); //TODO: change flag position to increments by 1 to the victor team

        //System.out.println("new flag pos: " + global.getFlagPos());

        trialStarted = false;


        if(global.gameFinished()){
            global.setRefereeState(RefereeState.END_OF_A_GAME, logger);
            if(global.getGamesNum()==3)
            {
                global.setMatchInProgress(false);
                global.setRefereeState(RefereeState.END_OF_THE_MATCH, logger);
            }
        }

        notifyAll();

    }

   /**
    * Coach checks if the match is finished and if so, he wakes the contestants that are still waiting on the bench.
    * If the match isn't finished, he selects his team for the next trial.
    * @param teamID team's ID
    * @param bench reference to the Bench
    */
   public synchronized void reviewNotes(int teamID, Bench bench) {


       if(!global.matchInProgress()) {

           bench.wakeContestants();
       }
       for(int i=0; i<5; i++){
           final int finalI = i;
           boolean contains = IntStream.of(global.getSelection(teamID)).anyMatch(x -> x == finalI);

           if(contains){
               int str = global.getStrength(i, teamID);
               if(str > 0)
                   global.setStrength(i, teamID,--str);
           }
           else{
               int str = global.getStrength(i, teamID);
               if(str < 10)
                   global.setStrength(i, teamID,++str);
           }
       }
   }

}
