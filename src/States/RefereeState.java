package States;

/**
 * Created by jonnybel on 3/8/16.
 */
public enum RefereeState {

    /* todo: add comments */

    START_OF_THE_MATCH("SOTM"),

    START_OF_A_GAME("SOAG"),

    TEAMS_READY("TR"),

    WAIT_FOR_TRIAL_CONCLUSION("WFTC"),

    END_OF_A_GAME("EOAG"),

    END_OF_THE_MATCH("EOTM");

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
