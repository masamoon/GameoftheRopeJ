package Interfaces;

import States.RefereeState;

/**
 * Created by jonnybel on 3/9/16.
 */
public interface RefereeGlobal {

    /* todo: parametros das funcoes (se necessario) */
    void declareGameWinner ();
    void declareMatchWinner ();

    void setRefereeState(RefereeState refereeState);
    RefereeState getRefereeState();

    int getGameScore ();
    void setGameScore (int score);

    boolean getGameState();
    void setGameState(boolean gameState);

    int getMatchScore ();
    void setMatchScore (int score);

    boolean getMatchState();
    void setMatchState(boolean matchState);

}
