package DistributedSolution.Communication.Message;

import DistributedSolution.ServerSide.States.CoachState;
import DistributedSolution.ServerSide.States.ContestantState;
import DistributedSolution.ServerSide.States.RefereeState;


import java.io.Serializable;


public class Message  implements Serializable{

    /**
     *  Chave de serialização
     *    @serialField serialVersionUID
     */
    private static final long serialVersionUID = 1L;



    private int int1,int2,int3,int4;


    private boolean b;



    private int type = -1;

    String fname = null;


    private String serverUrlGlobal;
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

    /**
     * @serialField POSITIVE
     */
    public static final int POSITIVE = 100;

    /**
     * @serialField NEGATIVE
     */
    public static final int NEGATIVE = 200;


    public static final int CONFIGCOACH = 101;

    public static final int CONFIGCOACHR = 102;

    public static final int CONFIGCONTESTANT = 201;

    public static final int CONFIGCONTESTANTR = 202;

    public static final int CONFIGREFEREE = 301;

    public static final int CONFIGREFEREER = 302;

    /**
     * @serialField TERMINATE
     */
    public static final int TERMINATE = 400;

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
     * Wait for Contestants
     * @serialField WCONTESTANTS
     */
    public static final int WCONTESTANTS = 10;

    /**
     * inform referee
     * @serialField INFREF
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
     *  Set strength of contestant at the bench
     */
    public static final int SSTRENGTHB = 19;

    /**
     * get the contestant strength from the bench
     */
    public static final int GSTRENGTHB = 47;
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
     * @serialField ANNMATCH
     */
    public static final int ANNMATCH = 23;

    /**
     * announce game
     * @serialField ANNGAME
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
     * @serialField MINPROGRESS
     */
    public static final int MINPROGRESS = 27;

    /**
     * CONTESTANT - GLOBAL
     */

    /**
     * REFEREE - GLOBAL
     *
     */
    /**
     * game finished
     */
    public static final int GFINISHED = 28;

    /**
     *set team
     */
    public static final int STEAM = 29;

    /**
     * set coach state
     */
    public static final int SCOACHSTATE = 30;

    /**
     * set contestant state
     */
    public static final int SCONTESTANTSTATE = 31;

    /**
     * set referee state
     */
    public static final int SREFEREESTATE = 32;

    /**
     * set strength
     */
    public static final int SSTRENGTH = 33;

    /**
     * set ready for trial
     */
    public static final int SREADYFTRIAL = 34;

    /**
     * bench wake referee
     */
    public static final int BWAKEREF = 35;

    /**
     * set trial called
     */
    public static final int STRIALCALLED = 36;

    /**
     * wait for bench
     */
    public static final int WBENCH = 37;

    /**
     * set bench called
     */
    public static final int SBENCHCALLED = 38;

    /**
     * get gamesNum
     */
    public static final int GGAMESNUM = 39;

    /**
     * response to get gamesNum operation
     */
    public static final int GGAMESNUMR = 40;

    /**
     * set match in progress
     */
    public static final int SMINPROGRESS = 41;

    /**
     * change flag position
     */
    public static final int CFLAGPOS = 42;

    /**
     * increment trial num
     */
    public static final int ITRIALNUM = 43;

    /**
     * increment team score
     */
    public static final int ITEAMSCORE = 44;

    /**
     * Game Winner Line Points
     */
    public static final int GWINNERLINEPOINTS = 45;

    /**
     * Game Tie Line
     */
    public static final int GTIELINE = 46;


    /**
     * get trial num
     */
    public static final int GTRIALNUM = 48;

    /**
     * response to get trial number operation
     */
    public static final int GTRIALNUMR = 49;

    /**
     * match tie line
     */
    public static final int MTIELINE = 50;

    /**
     * game winner line Knock-Out
     */
    public static final int GWINNERLINEKO = 51;

    /**
     * match winner line
     */
    public static final int MWINNERLINE = 52;

    /**
     * leave rope
     */
    public static final int LROPE = 53;

    /**
     * get flag pos
     */
    public static final int GFLAGPOS = 54;

    /**
     * response to get flag pos operation
     */
    public static final int GFLAGPOSR = 55;

    /**
     * increment games num
     */
    public static final int IGAMESNUM = 56;

    /**
     * reset flag pos
     */
    public static final int RESETFLAGPOS = 57;

    /**
     * reset trial num
     */
    public static final int RESETTRIALNUM = 58;


    /**
     * contestant's state
     *@serialField contestantState
     */
    private ContestantState contestantState;

    /**
     *referee's state
     * @serialField refereeState
     */
    private RefereeState refereeState;

    /**
     *coach's state
     * @serialField coachState
     */
    private CoachState coachState;


    /**
     * Message form 1
     * @param type Message's type specifying which operation it's invoking
     */
    public Message(int type){
        this.type = type;

    }


    /**
     *Message form 2
     * @param type Message's type specifying which operation it's invoking
     * @param int1 first integer parameter
     */
    public Message(int type, int int1){
        this.type = type;
        this.int1 = int1;
    }

    /**
     *Message form 3
     * @param type Message's type specifying which operation it's invoking
     * @param int1 first integer parameter
     * @param int2 second integer parameter
     *
     */
    public Message(int type,int int1, int int2){
        this.type = type;
        this.int1 = int1;
        this.int2 = int2;

    }

    /**
     *Message form 4
     * @param type Message's type specifying which operation it's invoking
     * @param int1 first integer parameter
     * @param int2 second integer parameter
     * @param contestantState contestant's state
     */
    public Message(int type, int int1, int int2,ContestantState contestantState){
        this.type = type;
        this.contestantState = contestantState;
        this.int1 = int1;
        this.int2 = int2;
    }

    /**
     *Message form 5
     * @param type Message's type specifying which operation it's invoking
     * @param int1 first integer parameter
     * @param coachState coache's state
     */
    public Message(int type, int int1, CoachState coachState){
        this.type = type;
        this.coachState = coachState;
        this.int1 = int1;
    }

    /**
     *Message form 6
     * @param type Message's type specifying which operation it's invoking
     * @param refereeState
     */
    public Message(int type, RefereeState refereeState){
        this.type = type;
        this.refereeState = refereeState;

    }

    /**
     *Message form 7
     * @param type Message's type specifying which operation it's invoking
     * @param int1 first integer parameter
     * @param int2 second integer parameter
     * @param int3 third integer parameter
     * @param int4 fourth integer parameter
     */
    public Message(int type, int int1, int int2, int int3, int int4){
        this.type = type;
        this.int1 = int1;
        this.int2 = int2;
        this.int3 = int3;
        this.int4 = int4;
    }

    /**
     *Message form 8
     * @param type Message's type specifying which operation it's invoking
     * @param b first boolean parameter
     */
    public Message(int type, boolean b){
        this.type = type;
        this.b = b;
    }

    /**
     *Message form 9
     * @param type Message's type specifying which operation it's invoking
     * @param int1 first integer parameter
     * @param b first boolean parameter
     */
    public Message(int type, int int1, boolean b){
        this.type = type;
        this.int1 = int1;
        this.b = b;
    }

    /**
     *
     * @param type Message's type specifying which operation it's invoking
     * @param int1 first integer parameter
     * @param int2 second integer parameter
     * @param int3 third integer parameter
     */
    public Message(int type, int int1, int int2, int int3) {
        this.type = type;
        this.int1 = int1;
        this.int2 = int2;
        this.int3 = int3;
    }

    /**
     *
     * @return
     */
    public int getType(){
        return type;
    }

    /**
     *
     * @return
     */
    public String getFName ()
    {
        return (fname);
    }


    /**
     *
     * @return
     */
    public ContestantState getContestantState(){
        return contestantState;
    }

    /**
     *
     * @return
     */
    public RefereeState getRefereeState() {
        return refereeState;
    }

    /**
     *
     * @return
     */
    public CoachState getCoachState() {
        return coachState;
    }



    /**
     *
     * @return boolean parameter
     */
    public boolean getB() {
        return b;
    }

    /**
     *
     * @return first parameter integer
     */
    public int getInt1() {
        return int1;
    }

    /**
     *
     * @return second parameter integer
     */
    public int getInt2() {
        return int2;
    }

    /**
     *
     * @return third parameter integer
     */
    public int getInt3() {
        return int3;
    }

    /**
     *
     * @return fourth parameter integer
     */
    public int getInt4() {
        return int4;
    }

    /**
     *
     * @return String representation of Message
     */
    @Override
    public String toString ()
    {
        return ("Tipo = " + type
                );
    }
}
