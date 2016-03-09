package Interfaces;

import States.ContestantState;

/**
 * Created by jonnybel on 3/9/16.
 */
public interface ContestantGlobal {

    void setContestantState (ContestantState contestantState);
    ContestantState getContestantState ();

}
