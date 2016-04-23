package DistributedSolution.Message;

import DistributedSolution.ClientSide.Coach.CoachState;
import DistributedSolution.ClientSide.Contestant.ContestantState;
import DistributedSolution.ClientSide.Referee.RefereeState;


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


    /**
     *
     */


    private int id;

    private int teamID;

    private int contestantID;

    private int type = -1;

    String fname = null;

    /**
     *  Inicialização do ficheiro de logging (operação pedida pelo cliente)
     *    @serialField SETNFIC
     */
    public static final int SETNFIC  =  1;

    /**
     *  Ficheiro de logging foi inicializado (resposta enviada pelo servidor)
     *    @serialField NFICDONE
     */
    public static final int NFICDONE =  2;

    /**
     *@serialField ALLONROPE
     */
    public static final int ALLONROPE = 4;

    /**
     *@serialField ACK
     */
    public static final int ACK = 5;

    public static final int POSITIVE = 100;

    public static final int NEGATIVE = 200;

    /**
     *
     */
    public static final int GOTOSLP = 6;

    /**
     *
     */
    public static final int END = 7;

    /**
     * COACH - PLAYGROUND
     */

    /**
     * wait for calling
     */
    public static final int WFCALLING = 19;

    /**
     * Wait for Contestants
     * @serialField WCONTESTANTS
     */
    public static final int WCONTESTANTS = 10;

    /**
     * inform referee
     */
    public static final int INFREF = 21;

    /**
     * review notes
     */
    public static final int REVNOTES = 22;



    /**
     * COACH- BENCH
     */
    /**
     *Call Contestants
     * @serialField CCONTESTANTS
     */
    public static final int CCONTESTANTS = 9;


    /**
     * CONTESTANT - BENCH
     */

    /**
     * Sit down
     *@serialField  SDOWN
     */
    public static final int SDOWN = 8;

    /**
     * CONTESTANT - PLAYGROUND
     */

    /**
     * follow coach advice
     */
    public static final int FCOACHADV = 13;

    /**
     * get ready
     */
    public static final int GREADY = 14;

    /**
     * done
     */
    public static final int DONE = 15;


    /**
     * REFEREE - PLAYGROUND
     */
    /**
     * call trial
     */
    public static final int CTRIAL = 16;

    /**
     * start trial
     */
    public static final int STRIAL = 17;

    /**
     * assert trial decision
     */
    public static final int ASSTRIALDEC = 18;

    /**
     * REFEREE - REFEREE SITE
     */
    /**
     * announce match
     */
    public static final int ANNMATCH = 23;

    /**
     * announce game
     */
    public static final int ANNGAME = 24;

    /**
     * declare game winner
     */
    public static final int DGAMEWINNER = 25;

    /**
     * declare match winner
     */
    public static final int DMATCHWINNER = 26;

    /**
     * COACH - GLOBAL
     */
    /**
     * match in progress
     */
    public static final int MINPROGRESS = 27;

    /**
     * CONTESTANT - GLOBAL
     */
    /**
     *
     */








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
     * Message form 1
     * @param type
     */
    public Message(int type){


    }


    /**
     *Message form 2
     * @param type
     * @param teamID
     */
    public Message(int type, int teamID){
        this.teamID = teamID;
    }

    /**
     *Message form 3
     * @param type
     * @param teamID
     * @param contestantID
     *
     */
    public Message(int type,int teamID, int contestantID){

        this.teamID = teamID;
        this.contestantID = contestantID;
    }

    /**
     *Message form 4
     * @param type
     * @param contestantState
     */
    public Message(int type, ContestantState contestantState){

    }

    /**
     *Message form 5
     * @param type
     * @param coachState
     */
    public Message(int type, CoachState coachState){

    }




    public int getType(){
        return type;
    }

    public String getFName ()
    {
        return (fname);
    }

    @Override
    public String toString ()
    {
        return ("Tipo = " + type
                );
    }
}
