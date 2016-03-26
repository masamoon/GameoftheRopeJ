package States;

/**
 * Created by jonnybel on 3/8/16.
 */
public enum CoachState {

    /* todo: add comments */

    INIT("I"),

    WAIT_FOR_REFEREE_COMMAND("WFRC"),

    ASSEMBLE_TEAM("AT"),

    WATCH_TRIAL("WT");

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
