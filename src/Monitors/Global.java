package Monitors;

import Entities.Coach;
import States.CoachState;
import States.ContestantState;
import States.RefereeState;

/**
 * Created by jonnybel on 3/8/16.
 */
public class Global {


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

    /* contestants */
    public synchronized void setContestantState (int contestantID, ContestantState state){
        this.contestantStates[contestantID] = state;

        // todo: report status (log)
    }
    public synchronized ContestantState getContestantState (int contestantID){
        return this.contestantStates[contestantID];

    }

    /* referee */
    public synchronized void setRefereeState (RefereeState state){
        this.refereeState = state;

        // todo: report status (log)
    }
    public synchronized RefereeState getRefereeState (){
        return this.refereeState;

    }

    /* coaches */
    public synchronized void setCoachState (int teamID, CoachState state){
        this.coachStates[teamID] = state;

        // todo: report status (log)
    }
    public synchronized CoachState getCoachState (int teamID){
        return this.coachStates[teamID];

    }

}
