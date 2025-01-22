package tic.tac.toe.game.iti.client.ServerSide;

public class MassageType {

    public static final String LOGIN_MSG = "login";
    public static final String LOGIN_SUCCESS_MSG = "login successful";
    public static final String LOGIN_FAIL_MSG = "login failed";
    public static final String LOGOUT_MSG = "logout";
    public static final String REGISTER_MSG = "register";
    public static final String REGISTER_SUCCESS_MSG = "register succeccful";
    public static final String REGISTER_FAIL_MSG = "register failed";
    public static final String UPDATE_LIST_MSG = "update";
    public static final String SERVER_CLOSE_MSG = "server close";
    public static final String CHALLENGE_REQUEST_MSG = "challenge req";
    public static final String CHALLENGE_ACCEPT_MSG = "challenge acc";
    public static final String START_GAME_MSG = "start game";
    public static final String WITHDRAW_GAME_MSG = "withdraw game";
    public static final String PLAY_MSG = "play";
    public static final String END_GAME_MSG = "end game";
    public static final String RESTART_REQUEST_MSG = "restart req"; //replay game
    public static final String RESTART_ACCEPT_MSG = "restart acc";  //replay acc
    public static final String RESTART_REJECT_MSG = "restart rej";  //replay rej
    public static final String CONTINUE_GAME_MSG = "continue game";
    public static final String CLIENT_CLOSE_MSG = "client close";
    public static final String IN_BETWEEN_GAME_MSG = "in between game";

    public static final String CHALLENGE_REJECT_MSG = "challenge reject";
    public static final String CHALLENGE_START_MSG = "challenge start";
}
