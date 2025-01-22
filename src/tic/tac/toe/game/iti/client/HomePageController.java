package tic.tac.toe.game.iti.client;

import java.io.IOException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import org.json.simple.JSONObject;
import tic.tac.toe.game.iti.client.Registeration.LoginPageController;
import tic.tac.toe.game.iti.client.ServerSide.MassageType;
import tic.tac.toe.game.iti.client.ServerSide.ServerHandler;
import tic.tac.toe.game.iti.client.player.Player;

public class HomePageController extends Controller{

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
    private Button recordsBtn;
    
    public static List<Player> currentPlayers;

    public void setStage(Stage stage) {
        this.stage = stage;
        sUserNames = usernames;
        sScores = scores;
        sChallenges = challenges;
    }
    
    public void setCurrentStage(Stage stage) {
        this.stage = stage;
        sUserNames = usernames;
        sScores = scores;
        sChallenges = challenges;
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

            stage.setScene(new Scene(root));
            stage.setTitle("Login Page");
        } catch (IOException ex) {
            Logger.getLogger(WelcomeController.class.getName()).log(Level.SEVERE, null, ex);
        }
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

}
