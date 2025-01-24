package tic.tac.toe.game.iti.client.ServerSide;

import org.json.simple.JSONObject;
import org.json.simple.JSONValue;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import org.json.simple.JSONArray;
import tic.tac.toe.game.iti.client.HomePageController;
import tic.tac.toe.game.iti.client.OnlineGameController;
import tic.tac.toe.game.iti.client.TicTacToeGameITIClient;
import tic.tac.toe.game.iti.client.WelcomeController;
import tic.tac.toe.game.iti.client.player.Player;

public class ServerHandler {

    public static Stage stage;
    public static DataInputStream massageIn;
    public static DataOutputStream massageOut;
    public static Socket socket;
    public static String msg = null;
    public static boolean isFinished = false;
    public static boolean isLoggedIn = false;
    public static boolean isClosedNormally = false;

    public static void setSocket(String ip) throws IOException {
        ServerHandler.socket = new Socket(ip, 5005);
        ServerHandler.massageIn = new DataInputStream(ServerHandler.socket.getInputStream());
        ServerHandler.massageOut = new DataOutputStream(ServerHandler.socket.getOutputStream());
        Thread listner;
        isFinished = false;
        listner = new Thread(new Runnable() {
            @Override
            public void run() {
                while (!ServerHandler.isFinished) {
                    try {
                        String responseMsg = massageIn.readUTF();
                        JSONObject respone = (JSONObject) JSONValue.parse(responseMsg);
                        if (respone.get("type").equals(MassageType.SERVER_CLOSE_MSG)) {
                            Platform.runLater(() -> {
                                serverDisconnection();
                            });

                        } else if (respone.get("type").equals(MassageType.UPDATE_LIST_MSG) && isLoggedIn) {
                            JSONArray array = (JSONArray) respone.get("data");
                            ArrayList<Player> dtoPlayers = new ArrayList<Player>();
                            for (int i = 0; i < array.size(); i++) {
                                JSONObject obj = (JSONObject) JSONValue.parse((String) array.get(i));
                                dtoPlayers.add(new Player((String) obj.get("username"), "", "", ((Long) obj.get("score")).intValue()));
                            }
                            HomePageController.currentPlayers = dtoPlayers;
                            Platform.runLater(new Runnable() {
                                @Override
                                public void run() {
                                    HomePageController.updateAvailablePlayers(dtoPlayers);
                                }
                            });
                        } else if (respone.get("type").equals(MassageType.CHALLENGE_REQUEST_MSG)) {
                            String challengerUsername = (String) respone.get("data");

                            Platform.runLater(() -> {
                                Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                                alert.setTitle("Challenge Request");
                                alert.setHeaderText(challengerUsername + " has challenged you to a game!");
                                alert.setContentText("Do you want to accept the challenge?");

                                ButtonType acceptButton = new ButtonType("Accept");
                                ButtonType rejectButton = new ButtonType("Reject");

                                alert.getButtonTypes().setAll(acceptButton, rejectButton);

                                alert.showAndWait().ifPresent(response -> {
                                    JSONObject reply = new JSONObject();
                                    if (response == acceptButton) {
                                        reply.put("type", MassageType.CHALLENGE_ACCEPT_MSG);
                                        reply.put("data", challengerUsername);
                                    }
                                    try {
                                        ServerHandler.massageOut.writeUTF(reply.toJSONString());
                                    } catch (IOException e) {
                                        e.printStackTrace();
                                    }
                                });
                            });
                        } else if (respone.get("type").equals(MassageType.CHALLENGE_START_MSG)) {
                            JSONObject gameData = (JSONObject) JSONValue.parse((String) respone.get("data"));
                            String opponentUsername;
                            if (!(boolean) gameData.get("isStarted")) {
                                opponentUsername = (String) gameData.get("player1");
                                Platform.runLater(() -> {
                                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                                    alert.setTitle("Challenge Accepted");
                                    alert.setHeaderText("Your challenge has been accepted!");
                                    alert.setContentText(opponentUsername + " is ready to play.");

                                    alert.showAndWait();
                                });
                            }
                            Platform.runLater(() -> {
                                OnlineGameController.navigateToGame(responseMsg);
                            });

                        } else if (respone.get("type").equals(MassageType.UPDATE_LIST_MSG)) {
                            JSONArray array = (JSONArray) respone.get("data");
                            ArrayList<Player> dtoPlayers = new ArrayList<Player>();
                            for (int i = 0; i < array.size(); i++) {
                                JSONObject obj = (JSONObject) JSONValue.parse((String) array.get(i));
                                dtoPlayers.add(new Player((String) obj.get("username"), "", "", ((Long) obj.get("score")).intValue()));
                            }
                            HomePageController.currentPlayers = dtoPlayers;
                        } else if (respone.get("type").equals(MassageType.CHALLENGE_ACCEPT_MSG)) {

                        } else {
                            ServerHandler.msg = responseMsg;
                        }
                    } catch (IOException ex) {
                        ServerHandler.isFinished = true;
                        ServerHandler.massageIn = null;
                        ServerHandler.massageOut = null;
                        ServerHandler.socket = null;
                        ServerHandler.msg = null;
                        if (!TicTacToeGameITIClient.isClosed) {
                            Platform.runLater(() -> {
                                if (!isClosedNormally) {
                                    serverDisconnection();
                                    isClosedNormally = !isClosedNormally;
                                }
                            });
                        }
                    }
                }
            }
        }
        );
        listner.start();
    }

    public static void closeSocket() throws IOException {
        JSONObject object = new JSONObject();
        object.put("type", MassageType.CLIENT_CLOSE_MSG);
        ServerHandler.massageOut.writeUTF(object.toJSONString());
        ServerHandler.isFinished = true;
        ServerHandler.massageIn.close();
        ServerHandler.massageOut.close();
        ServerHandler.socket.close();
        ServerHandler.massageIn = null;
        ServerHandler.massageOut = null;
        ServerHandler.socket = null;

    }

    private static void serverDisconnection() {

        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle("Connection Lost");
        alert.setContentText("You will be redirected to the home page");
        alert.showAndWait();

        try {
            FXMLLoader loader = new FXMLLoader(ServerHandler.class.getResource("/tic/tac/toe/game/iti/client/Welcome.fxml"));
            Parent root = loader.load();

            WelcomeController controller = loader.getController();
            controller.setStage(stage);

            stage.setScene(new Scene(root));
            stage.setTitle("Welcome Page");
        } catch (IOException ex) {
            Logger.getLogger(ServerHandler.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
