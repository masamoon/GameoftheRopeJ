package Nondistributedsolution.Coach;

/**
 * Created by jonnybel on 3/8/16.
 */
public enum CoachState {

    INIT("    "),

    WAIT_FOR_REFEREE_COMMAND("WFRC"),

    ASSEMBLE_TEAM("ASMT"),

    WATCH_TRIAL("WTRL");

    private final String acronym;

    private CoachState(String acronym)
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
