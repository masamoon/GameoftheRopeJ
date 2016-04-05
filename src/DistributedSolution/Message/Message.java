package DistributedSolution.Message;

import DistributedSolution.Coach.CoachState;
import DistributedSolution.Contestant.ContestantState;
import DistributedSolution.Referee.RefereeState;


import java.io.Serializable;

/**
 * Created by Andre on 05/04/2016.
 */
public class Message  implements Serializable{

    /**
     *
     */

    private static final long serialVersionUID = 1002L;
    /**
     *
     */
    private MessageType type;

    /**
     *
     */
    private int id;

    /**
     *
     */
    private ContestantState contestantState;

    /**
     *
     */
    private RefereeState refereeState;

    /**
     *
     */
    private CoachState coachState;


    /**
     *
     * @param type
     */
    public Message(MessageType type){

        this.type = type;
    }


    /**
     *
     * @param type
     * @param teamID
     */
    public Message(MessageType type, int teamID){

    }

    /**
     *
     * @param type
     * @param teamID
     * @param contestantID
     *
     */
    public Message(MessageType type, int teamID, int contestantID){

    }

    /**
     *
     * @param type
     * @param contestantState
     */
    public Message(MessageType type, ContestantState contestantState){

    }

    /**
     *
     * @param type
     * @param coachState
     */
    public Message(MessageType type, CoachState coachState){

    }



}
