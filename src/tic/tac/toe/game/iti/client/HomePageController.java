package tic.tac.toe.game.iti.client;

import java.io.IOException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.JSONValue;
import tic.tac.toe.game.iti.client.Registeration.LoginPageController;
import tic.tac.toe.game.iti.client.ServerSide.MassageType;
import tic.tac.toe.game.iti.client.ServerSide.ServerHandler;
import tic.tac.toe.game.iti.client.player.Player;

public class HomePageController extends Controller {

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
    private static Label sUsername;
    private static Label sScore;

    @FXML
    private Button recordsBtn;

    public static String currentPlayers;
    @FXML
    private Label myUsername;
    @FXML
    private Label myScore;

    public void setStage(Stage stage) {
        this.stage = stage;
        sUserNames = usernames;
        sScores = scores;
        sChallenges = challenges;
        sUsername = myUsername;
        sScore = myScore;
    }

    public void setCurrentStage(Stage stage) {
        this.stage = stage;
        sUserNames = usernames;
        sScores = scores;
        sChallenges = challenges;
        sUsername = myUsername;
        sScore = myScore;
        updateAvailablePlayers(currentPlayers);
    }

    @FXML
    public void handleLogout(ActionEvent event) {
        JSONObject output = new JSONObject();
        output.put("type", MassageType.LOGOUT_MSG);
        try {
            ServerHandler.massageOut.writeUTF(output.toJSONString());
        } catch (IOException ex) {
            Logger.getLogger(HomePageController.class.getName()).log(Level.SEVERE, null, ex);
        }
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("Registeration/LoginPage.fxml"));
            Parent root = loader.load();

            LoginPageController controller = loader.getController();
            controller.setStage(stage);
            ServerHandler.isLoggedIn = false;
            stage.setScene(new Scene(root));
            stage.setTitle("Login Page");
        } catch (IOException ex) {
            Logger.getLogger(WelcomeController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public static void updateAvailablePlayers(String msg) {
        JSONObject obj = (JSONObject) JSONValue.parse(msg);
        JSONObject data = (JSONObject) obj.get("data");
        sUsername.setText((String) data.get("username"));
        sScore.setText("" + data.get("score"));
        ArrayList<Player> players = new ArrayList();
        JSONArray array = (JSONArray) data.get("players");
        for (int i = 0; i < array.size(); i++) {
            JSONObject playersData = (JSONObject) JSONValue.parse((String) array.get(i));
            players.add(new Player((String) playersData.get("username"), "", "", ((Long) playersData.get("score")).intValue()));
        }
        sUserNames.getChildren().clear();
        sScores.getChildren().clear();
        sChallenges.getChildren().clear();

        for (Player player : players) {
            Label user = new Label(player.getUserName());
            user.setStyle("-fx-text-fill:#1a7bcc; -fx-font-weight: bold; -fx-font-family: 'Pacifico'; -fx-font-size: 28px;");
            sUserNames.getChildren().add(user);

            Label score = new Label(String.valueOf(player.getScore()));
            score.setStyle("-fx-text-fill:#f45162; -fx-font-weight: bold; -fx-font-family: 'Pacifico'; -fx-font-size: 28px;");
            sScores.getChildren().add(score);

            Button challengeBtn = new Button("Send Request");
            challengeBtn.getStyleClass().add("blue-request-Btn");
            
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

    @FXML
    private void handleRecords(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("OnlineRecords.fxml"));
            Parent root = loader.load();

            OnlineRecordsController controller = loader.getController();
            controller.setStage(stage);

            stage.setScene(new Scene(root));
            stage.setTitle("Online Records");
        } catch (IOException ex) {
            Logger.getLogger(HomePageController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public void askReplay() {
    }

    private void handleLogout(MouseEvent event) {
        JSONObject output = new JSONObject();
        output.put("type", MassageType.LOGOUT_MSG);
        try {
            ServerHandler.massageOut.writeUTF(output.toJSONString());
        } catch (IOException ex) {
            Logger.getLogger(HomePageController.class.getName()).log(Level.SEVERE, null, ex);
        }
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("Registeration/LoginPage.fxml"));
            Parent root = loader.load();

            LoginPageController controller = loader.getController();
            controller.setStage(stage);

            stage.setScene(new Scene(root));
            stage.setTitle("Login Page");
        } catch (IOException ex) {
            Logger.getLogger(WelcomeController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}
