package DistributedSolution.ClientSide.Contestant;

/**
 * Created by jonnybel on 3/8/16.
 */
public enum ContestantState {


    INIT("   "),

    SIT_AT_THE_BENCH("SIT"),

    STAND_IN_POSITION("POS"),

    DO_YOUR_BEST("DYB");


    private final String acronym;

    private ContestantState(String acronym)
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
