package tic.tac.toe.game.iti.client;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import org.json.simple.JSONObject;
import tic.tac.toe.game.iti.client.ServerSide.MassageType;
import tic.tac.toe.game.iti.client.ServerSide.ServerHandler;

public class OptionsDisplayController extends Controller implements Initializable {

    Stage stage;

    @FXML
    private Label players;
    @FXML
    private Label score;
    @FXML
    private Label winner;
    @FXML
    private Label winnerScore;
    @FXML
    private Label loser;
    @FXML
    private Label loserScore;
    @FXML
    private ImageView endGameIconBtn;
    @FXML
    private ImageView restartGameIconBtn;
    @FXML
    private Label player1;
    @FXML
    private Label player2;

    public void setStage(Stage stage, String msg) {
        this.stage = stage;
    }

    @Override
    public void askReplay() {
    }

    @FXML
    private void restartIConHandler(MouseEvent event) {
        restartRequest();
    }


    public void restartRequest() {
        JSONObject restart = new JSONObject();
        restart.put("type", MassageType.RESTART_REQUEST_MSG);
        try {
            ServerHandler.massageOut.writeUTF(restart.toJSONString());
        } catch (IOException ex) {
            Logger.getLogger(OnlineGameController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void displayVideo(String videoUrl) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("video.fxml"));
            Parent root = loader.load();
            FXMLLoader loader1 = new FXMLLoader(getClass().getResource("optionsDisplay.fxml"));
            Parent rot = loader1.load();

            VideoController controller = loader.getController();
            controller.setStage(ServerHandler.stage);

            controller.setController(this);
            controller.setVideoUrl(videoUrl);

            ServerHandler.stage.setScene(new Scene(rot));
            controller.setPreviousScene(ServerHandler.stage.getScene());
            ServerHandler.stage.setScene(new Scene(root));

        } catch (IOException e) {
            Alert alert = new Alert(Alert.AlertType.NONE, "An error occurred, please try again", ButtonType.OK);
            alert.showAndWait();
        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        winner.setText(OnlineGameController.winnerName + "");
        loser.setText(OnlineGameController.loserName + "");
        winnerScore.setText(OnlineGameController.player1Score + "");
        loserScore.setText(OnlineGameController.player2Score + "");
        player1.setText(OnlineGameController.winnerName);
        player2.setText(OnlineGameController.loserName);
    }

    @FXML
    private void endGameFunction(MouseEvent event) {
        try {
            OnlineGameController.isGameFinished=true;
            FXMLLoader loader = new FXMLLoader(getClass().getResource("HomePage.fxml"));
            Parent root = loader.load();

            HomePageController controller = loader.getController();
            controller.setCurrentStage(ServerHandler.stage);

            ServerHandler.stage.setScene(new Scene(root));
            ServerHandler.stage.setTitle("Home Page");
        } catch (IOException ex) {
            Logger.getLogger(OptionsDisplayController.class.getName()).log(Level.SEVERE, null, ex);
        }
        endGameRequest();
    }

    public void endGameRequest() {
        JSONObject end = new JSONObject();
        end.put("type", MassageType.END_GAME_MSG);
        try {
            ServerHandler.massageOut.writeUTF(end.toJSONString());
        } catch (IOException ex) {
            Logger.getLogger(OnlineGameController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
}
