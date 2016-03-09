package Interfaces;

import States.CoachState;

/**
 * Created by jonnybel on 3/9/16.
 */
public interface CoachGlobal {

    CoachState getCoachState();
    void setCoachState(CoachState coachState);
}
