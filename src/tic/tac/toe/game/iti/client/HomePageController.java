package tic.tac.toe.game.iti.client;

import java.io.IOException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import org.json.simple.JSONObject;
import tic.tac.toe.game.iti.client.ServerSide.MassageType;
import tic.tac.toe.game.iti.client.ServerSide.ServerHandler;
import tic.tac.toe.game.iti.client.player.Player;

public class HomePageController {

    private Stage stage;

    @FXML
    private VBox usernames;
    @FXML
    private VBox scores;
    @FXML
    private VBox challenges;

    private static VBox sUserNames;
    private static VBox sScores;
    private static VBox sChallenges;
    @FXML
    private Button sendRequest_BTN;

    public void setStage(Stage stage) {
        this.stage = stage;
        sUserNames = usernames;
        sScores = scores;
        sChallenges = challenges;
    }

    public void handleLogout(ActionEvent event) {

    }

    public static void updateAvailablePlayers(List<Player> players) {
        sUserNames.getChildren().clear();
        sScores.getChildren().clear();
        sChallenges.getChildren().clear();

        for (Player player : players) {
            Label user = new Label(player.getUserName());
            sUserNames.getChildren().add(user);

            Label score = new Label(String.valueOf(player.getScore()));
            sScores.getChildren().add(score);

            Button challengeBtn = new Button("Send Request");
            challengeBtn.setOnAction(event -> {
                sendRequestHandler(player);
            });
            sChallenges.getChildren().add(challengeBtn);
        }
    }

    private static void sendRequestHandler(Player player) {
        JSONObject challengeRequest = new JSONObject();
        challengeRequest.put("type", MassageType.CHALLENGE_REQUEST_MSG);
        challengeRequest.put("data", player.getUserName());

        try {
            ServerHandler.massageOut.writeUTF(challengeRequest.toJSONString());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
