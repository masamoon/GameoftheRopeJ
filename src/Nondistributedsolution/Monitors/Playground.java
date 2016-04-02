package Nondistributedsolution.Monitors;

import Nondistributedsolution.Logging.Logger;
import Nondistributedsolution.States.CoachState;
import Nondistributedsolution.States.ContestantState;
import Nondistributedsolution.States.RefereeState;

import java.util.stream.IntStream;


/**
 * Created by jonnybel on 3/8/16.
 */
public class Playground {


    private Global global;

    private int teamsReady;

    private int contestantsDone;


    private boolean trialCalled;

    private boolean trialStarted;

    private Logger logger;

    public Playground( Global global, Logger logger){
        this.global = global;

        this.teamsReady = 0;
        this.contestantsDone = 0;

        this.trialStarted = false;
        this.trialCalled = false;

        this.logger = logger;


    }

    /**
     * A team's Coach entera a blocking state and waits for a Referee command
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
     *  Referee calls the trial:
     *
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
     *Coach waits for the team to assemble
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
     * Contestant operation
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


    /** Coach informs Referee of the readiness of his team
     *@param teamID team's ID
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
     *   Referee begins the trial
     *   waits for the all the contestants to be done
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
     * Contestant operation
     * @param contestantID contestant's ID
     * @param teamID team's id
     */
    public synchronized  void getReady( int contestantID, int teamID) {
        global.setContestantState(contestantID,teamID,ContestantState.DO_YOUR_BEST,logger);

    }


    /**
     * Contestant operation
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
    *   Referee decides who's the winner
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
    * Coach operation
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
