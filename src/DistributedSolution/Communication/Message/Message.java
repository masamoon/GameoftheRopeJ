package DistributedSolution.Communication.Message;

import DistributedSolution.ClientSide.Coach.CoachState;
import DistributedSolution.ClientSide.Contestant.ContestantState;
import DistributedSolution.ClientSide.Referee.RefereeState;


import java.io.Serializable;

/**
 * Created by Andre on 05/04/2016.
 */
public class Message  implements Serializable{

    /**
     *  Chave de serialização
     *    @serialField serialVersionUID
     */
    private static final long serialVersionUID = 1L;


    /**
     *
     */


    /**
     *
     */


    private int id;

    private int team1, team2, team3;

    private int int1,int2,int3,int4;



    private int contestantID;


    private int score;

    private int gamesNum;

    private boolean b;



    private int type = -1;

    String fname = null;

    private int portNumBench;

    private String serverUrlBench;

    private int portNumPlayground;

    private String serverUrlPlayground;

    private int portNumRSite;

    private String serverUrlRSite;

    private int portNumGlobal;

    private int strength;

    private int trialNum;



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
     * wait for calling
     * @serialField WFCALLING
     */
    public static final int WFCALLING = 19;

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
     *
     */
    public static final int STEAM = 29;


    public static final int SCOACHSTATE = 30;

    public static final int SCONTESTANTSTATE = 31;

    public static final int SREFEREESTATE = 32;

    public static final int SSTRENGTH = 33;

    public static final int SREADYFTRIAL = 34;

    public static final int BWAKEREF = 35;

    public static final int STRIALCALLED = 36;

    public static final int WBENCH = 37;

    public static final int SBENCHCALLED = 38;

    public static final int GGAMESNUM = 39;

    public static final int GGAMESNUMR = 40;

    public static final int SMINPROGRESS = 41;

    public static final int CFLAGPOS = 42;

    public static final int ITRIALNUM = 43;

    public static final int ITEAMSCORE = 44;

    public static final int GWINNERLINEPOINTS = 45;

    public static final int GTIELINE = 46;

    public static final int MWINNERLINEPOINTS = 47;

    public static final int GTRIALNUM = 48;

    public static final int GTRIALNUMR = 49;

    public static final int MTIELINE = 50;

    public static final int GWINNERLINEKO = 51;

    public static final int MWINNERLINE = 52;

    public static final int LROPE = 53;








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
        this.type = type;

    }


    /**
     *Message form 2
     * @param type
     * @param teamID
     */
    public Message(int type, int int1){
        this.type = type;
        this.int1 = int1;
    }

    /**
     *Message form 3
     * @param type
     * @param teamID
     * @param contestantID
     *
     */
    public Message(int type,int int1, int int2){
        this.type = type;
        this.int1 = int1;
        this.int2 = int2;

    }

    /**
     *Message form 4
     * @param type
     * @param contestantState
     */
    public Message(int type, int int1, int int2,ContestantState contestantState){
        this.type = type;
        this.contestantState = contestantState;
        this.int1 = int1;
        this.int2 = int2;
    }

    /**
     *Message form 5
     * @param type
     * @param coachState
     */
    public Message(int type, int int1, CoachState coachState){
        this.type = type;
        this.coachState = coachState;
        this.int1 = int1;
    }

    public Message(int type,RefereeState refereeState){
        this.type = type;
        this.refereeState = refereeState;

    }

    public Message(int type, int int1, int int2, int int3, int int4){
        this.type = type;
        this.int1 = int1;
        this.int2 = int2;
        this.int3 = int3;
        this.int4 = int4;
    }

    public Message(int type, boolean b){
        this.type = type;
        this.b = b;
    }

    public Message(int type, int int1, int int2, int int3) {
        this.type = type;
        this.int1 = int1;
        this.int2 = int2;
        this.int3 = int3;
    }





    public Message(int setnfic, String fName, int nGames) {

    }




    public int getPortNumBench() {
        return portNumBench;
    }

    public void setPortNumBench(int portNumBench) {
        this.portNumBench = portNumBench;
    }

    public String getServerUrlBench() {
        return serverUrlBench;
    }

    public void setServerUrlBench(String serverUrlBench) {
        this.serverUrlBench = serverUrlBench;
    }

    public int getPortNumPlayground() {
        return portNumPlayground;
    }

    public void setPortNumPlayground(int portNumPlayground) {
        this.portNumPlayground = portNumPlayground;
    }

    public String getServerUrlPlayground() {
        return serverUrlPlayground;
    }

    public void setServerUrlPlayground(String serverUrlPlayground) {
        this.serverUrlPlayground = serverUrlPlayground;
    }

    public int getTeam1() {
        return team1;
    }

    public int getTeam2() {
        return team2;
    }

    public int getTeam3() {
        return team3;
    }

    public int getPortNumRSite() {
        return portNumRSite;
    }

    public void setPortNumRSite(int portNumRSite) {
        this.portNumRSite = portNumRSite;
    }

    public String getServerUrlRSite() {
        return serverUrlRSite;
    }

    public void setServerUrlRSite(String serverUrlRSite) {
        this.serverUrlRSite = serverUrlRSite;
    }

    public int getPortNumGlobal() {
        return portNumGlobal;
    }

    public void setPortNumGlobal(int portNumGlobal) {
        this.portNumGlobal = portNumGlobal;
    }

    public String getServerUrlGlobal() {
        return serverUrlGlobal;
    }

    public void setServerUrlGlobal(String serverUrlGlobal) {
        this.serverUrlGlobal = serverUrlGlobal;
    }

    public int getType(){
        return type;
    }

    public String getFName ()
    {
        return (fname);
    }




    public ContestantState getContestantState(){
        return contestantState;
    }
    public RefereeState getRefereeState() {
        return refereeState;
    }

    public CoachState getCoachState() {
        return coachState;
    }

    public int getGamesNum() {
        return gamesNum;
    }
    public int getTrialNum() {
        return trialNum;
    }

    public int getScore() {
        return score;
    }

    public boolean getB() {
        return b;
    }

    public int getInt1() {
        return int1;
    }

    public int getInt2() {
        return int2;
    }

    public int getInt3() {
        return int3;
    }

    public int getInt4() {
        return int4;
    }


    public int getStrength() {
        return strength;
    }

    @Override
    public String toString ()
    {
        return ("Tipo = " + type
                );
    }
}
