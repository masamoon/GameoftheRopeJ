package DistributedSolution.ServerSide.Playground;

import DistributedSolution.ServerSide.Bench.BenchRemote;
import DistributedSolution.ServerSide.Global.GlobalRemote;
import Nondistributedsolution.Contestant.Contestant;
import Nondistributedsolution.Monitors.Bench;
import Nondistributedsolution.Monitors.Global;

import java.util.stream.IntStream;


/**
 * This Class implements the shared region for the PlaygroundRemote, with synchronization based on monitors
 */
public class PlaygroundRemote {


    /**
     *  General Information Repository object
     */
    private final PlaygroundGlobalStub global;

    /**
     *  General Information Repository object
     */
    private final PlaygroundBenchStub bench;

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
    public PlaygroundRemote(PlaygroundGlobalStub global, PlaygroundBenchStub bench){
        this.global = global;
        this.bench = bench;

        this.teamsReady = 0;
        this.contestantsDone = 0;

        this.standingInPosition = new int [] {0,0};

        this.trialStatus = -1;

        this.teamPower = new int [] {0,0};
    }

    /**
     *  Referee calls a trial
     */
    public synchronized void callTrial(){

        teamsReady=0;
        contestantsDone=0;

        // todo: this will work differently
        global.eraseTeamSelections();

        // turns out to be unnecessary:
        //bench.eraseTeamSelections();

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
     * Coach waits for his selected team to be standing in position
     * @param teamID coach's teamID
     */
    public synchronized void waitForContestants(int teamID){

        while(standingInPosition[teamID]<3){
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
     * Coach informs Referee of the readiness of his team and waits for the trial to end.
     * @param teamID team's ID
     */

    public synchronized void informReferee(int teamID){

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

        bench.setTrialCalled(false);
        trialStatus=1;

        notifyAll();
    }


    /**
     * Contestant changes his state to Do_Your_Best before pulling the rope
     */
    public synchronized  void getReady(int teamID) {
        // todo: add my strength to the total power of my team (for the trial assertion)
        teamPower[teamID] += ((Contestant)Thread.currentThread()).getStrength();
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

        while(trialStatus==1) {
            try {
                wait();
            } catch (InterruptedException e) {}
        }

        standingInPosition[teamID] -=1;
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
        int decision;
        if(team1Power>team2Power)
            decision = -1;
        else if (team1Power<team2Power)
            decision = 1;
        else
            decision = 0;

        global.changeFlagPos(decision);

        trialStatus = 2;

        System.out.println("Trial finished");

        if(global.gameFinished()){
            if(global.getGamesNum()==3)
            {
                global.setMatchInProgress(false);
                bench.wakeBench();
            }
        }
        notifyAll();
    }

}
