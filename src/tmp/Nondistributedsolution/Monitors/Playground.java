package Nondistributedsolution.Monitors;

import Nondistributedsolution.Coach.Coach;
import Nondistributedsolution.Coach.CoachState;
import Nondistributedsolution.Contestant.Contestant;
import Nondistributedsolution.Contestant.ContestantState;
import Nondistributedsolution.Referee.Referee;
import Nondistributedsolution.Referee.RefereeState;

import java.util.stream.IntStream;


/**
 * This Class implements the shared region for the PlaygroundRemote, with synchronization based on monitors
 */
public class Playground {


    /**
     *  General Information Repository object
     */
    private final Global global;

    /**
     */
    private final Bench bench;

    private final RefereeSite refereeSite;

    /**
     * Number of complete teams that are standing in position (from 0 to 2)
     */
    private int teamsReady;

    /**
     * Number of contestants that have finished pulling the rope during a trial.
     */
    private int contestantsDone;

    /**
     * Status of a trial:
     * 0 - called;
     * 1 - started;
     * 2 - finished;
     */
    private int trialStatus;

    /**
     * Number of contestants standing in position for each team
     */
    private int [] standingInPosition;

    /**
     * Total power made by the contestants of a team during a trial
     */
    private int [] teamPower;


    /**
     * Constructor for the PlaygroundRemote
     * @param global
     */
    public Playground( Global global, Bench bench, RefereeSite refereeSite){
        this.global = global;
        this.bench = bench;
        this.refereeSite = refereeSite;

        this.teamsReady = 0;
        this.contestantsDone = 0;

        this.standingInPosition = new int [] {0,0};

        this.trialStatus = -1;

        this.teamPower = new int [] {0,0};
    }

    /**
     *  RefereeThread calls a trial
     */
    public synchronized void callTrial(){

        ((Referee)Thread.currentThread()).setRefereeState(RefereeState.TEAMS_READY);
        global.setRefereeState(RefereeState.TEAMS_READY);

        teamsReady=0;
        contestantsDone=0;

        for(int i =0; i<teamPower.length; i++)
        {
            teamPower [i] = 0;
        }

        trialStatus = 0;
        bench.setTrialCalled(true);

        bench.wakeBench();

        while(teamsReady<2)
        {
            try{
                wait ();
            }
            catch (InterruptedException e) {}
        }
    }

    /**
     * CoachThread waits for his selected team to be standing in position
     * @param teamID coach's teamID
     */
    public synchronized void waitForContestants(int teamID){

        ((Coach)Thread.currentThread()).setCoachState(CoachState.ASSEMBLE_TEAM);
        global.setCoachState(teamID, CoachState.ASSEMBLE_TEAM);

        while(standingInPosition[teamID]<3){
            try
            {
                wait ();
            }
            catch (InterruptedException e) {}
        }
    }

    /**
     * ContestantThread stands in position and waits for the trial to start
     * If he is the 3rd contestant of his team to get ready, he wakes the Coaches.
     * @param contestantID contestant's ID
     * @param teamID contestant's team ID
     */
    public synchronized void followCoachAdvice (int contestantID, int teamID) {

        ((Contestant)Thread.currentThread()).setContestantState(ContestantState.STAND_IN_POSITION);
        global.setContestantState(contestantID, teamID, ContestantState.STAND_IN_POSITION);
        standingInPosition[teamID] +=1;

        System.out.println("contestant " + contestantID + " from team " + teamID + " standing, total standing: " + standingInPosition[teamID]);
        if(standingInPosition[teamID]==3)
        {
            notifyAll();
        }

        while(trialStatus!=1)
        {
            try {
                wait();
            } catch (InterruptedException e) {}
        }
    }

    /**
     * CoachThread informs RefereeThread of the readiness of his team and waits for the trial to end.
     * @param teamID team's ID
     */

    public synchronized void informReferee(int teamID){

        ((Coach)Thread.currentThread()).setCoachState(CoachState.WATCH_TRIAL);
        global.setCoachState(teamID, CoachState.WATCH_TRIAL);

        bench.setBenchCalled(teamID, false);

        teamsReady++;

        if(teamsReady==2){
            notifyAll();
        }

        while(trialStatus!=2) {
            try {
                wait();
            } catch (InterruptedException e) {}
        }

    }

    /**
     *   Waits for both the teams to be ready and begins the trial
     */
    public synchronized  void startTrial(){

        ((Referee)Thread.currentThread()).setRefereeState(RefereeState.WAIT_FOR_TRIAL_CONCLUSION);
        global.setRefereeState(RefereeState.WAIT_FOR_TRIAL_CONCLUSION);

        bench.setTrialCalled(false);
        trialStatus=1;

        notifyAll();
    }


    /**
     * ContestantThread changes his state to Do_Your_Best before pulling the rope
     */
    public synchronized  void getReady(int contestantID, int teamID, int strength) {

        ((Contestant)Thread.currentThread()).setContestantState(ContestantState.DO_YOUR_BEST);
        global.setContestantState(contestantID, teamID, ContestantState.DO_YOUR_BEST);

        teamPower[teamID] += strength;
    }


    /**
     * The last contestant to finish pulling the rope wakes the referee
     * @param teamID team's id
     */
    public synchronized void done(int contestantID, int teamID){

        contestantsDone++;
        if(contestantsDone==6) {
            notifyAll();
        }

        while(trialStatus==1) {
            try {
                wait();
            } catch (InterruptedException e) {}
        }

        standingInPosition[teamID] -=1;
        global.leaveRope(contestantID, teamID);
    }

    /**
     *   RefereeThread decides who's the winner based on the total strength of both teams.
     *   He also checks if the game has been finished and if so he checks if the Match is finished.
     *
     */
    public synchronized void assertTrialDecision(){

        while(contestantsDone<6){
            try {
                wait();
            } catch (InterruptedException e) {}
        }

        int decision;
        if(teamPower[0]>teamPower[1])
            decision = -1;
        else if (teamPower[0]<teamPower[1])
            decision = 1;
        else
            decision = 0;

        global.changeFlagPos(decision);

        trialStatus = 2;

        System.out.println("Trial finished");

        if(global.gameFinished()){
            if(refereeSite.getGamesNum()==3)
            {
                global.setMatchInProgress(false);
                bench.wakeBench();
            }
        }
        notifyAll();
    }

}
