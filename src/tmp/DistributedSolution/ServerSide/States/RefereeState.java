package DistributedSolution.ServerSide.States;

public enum RefereeState {


    START_OF_THE_MATCH("SOM"),

    START_OF_A_GAME("SOG"),

    TEAMS_READY("TMR"),

    WAIT_FOR_TRIAL_CONCLUSION("WTC"),

    END_OF_A_GAME("EOG"),

    END_OF_THE_MATCH("EOM");

    private final String acronym;

    private RefereeState(String acronym)
    {
        this.acronym = acronym;
    }

    /**
     * Returns a smaller representation of the state (an acronym)
     *
     * @return state as an acronym
     */
    public String getAcronym() {
        return acronym;
    }
}
