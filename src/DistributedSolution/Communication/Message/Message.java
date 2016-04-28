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

    private int portNumBench;

    private String serverUrlBench;

    private int portNumPlayground;

    private String serverUrlPlayground;

    private int portNumRSite;

    private String serverUrlRSite;

    private int portNumGlobal;

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

    public static final int POSITIVE = 100;

    public static final int NEGATIVE = 200;

    public static final int CONFIGCOACH = 101;

    public static final int CONFIGCOACHR = 102;

    public static final int CONFIGCONTESTANT = 201;

    public static final int CONFIGCONTESTANTR = 202;

    public static final int CONFIGREFEREE = 301;

    public static final int CONFIGREFEREER = 302;

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
    public Message(int type, int teamID){
        this.type = type;
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
        this.type = type;
        this.teamID = teamID;
        this.contestantID = contestantID;
    }

    /**
     *Message form 4
     * @param type
     * @param contestantState
     */
    public Message(int type, ContestantState contestantState){
        this.type = type;
    }

    /**
     *Message form 5
     * @param type
     * @param coachState
     */
    public Message(int type, CoachState coachState){

    }

    public Message(int setnfic, String fName, int nGames) {

    }




    public int getType(){
        return type;
    }

    public String getFName ()
    {
        return (fname);
    }

    public int getTeamID() {
        return teamID;
    }

    public void setTeamID(int teamID) {
        this.teamID = teamID;
    }

    public int getContestantID() {
        return contestantID;
    }

    public void setContestantID(int contestantID) {
        this.contestantID = contestantID;
    }



    @Override
    public String toString ()
    {
        return ("Tipo = " + type
                );
    }
}
