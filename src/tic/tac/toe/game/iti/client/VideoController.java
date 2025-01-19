package tic.tac.toe.game.iti.client;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;
import javafx.stage.Stage;

public class VideoController implements Initializable {

    private Stage stage;
    private Scene previousScene;
    private GameController gameController;
    private String title;
    private String videoUrl;

    public void setStage(Stage stage) {
        this.stage = stage;
        video.setPreserveRatio(false);
    }

    public void setPreviousScene(Scene scene) {
        this.previousScene = scene;
    }

    public void setGameController(GameController controller) {
        this.gameController = controller;
    }
    
    public void setTitle(String title) {
        this.title = title;
    }

    public void setVideoUrl(String videoUrl) {
        this.videoUrl = videoUrl;
        initializeMediaPlayer();
    }

    @FXML
    private MediaView video;
    private MediaPlayer mediaPlayer;

    @Override
    public void initialize(URL url, ResourceBundle rb) {

    }

    private void initializeMediaPlayer() {
        if (videoUrl != null) {
            mediaPlayer = new MediaPlayer(new Media(this.getClass().getResource(videoUrl).toExternalForm()));
            mediaPlayer.setAutoPlay(true);
            video.setMediaPlayer(mediaPlayer);

            mediaPlayer.setOnEndOfMedia(() -> {
                if (stage != null && previousScene != null) {
                    stage.setScene(previousScene);
                    if (gameController != null) {
                        gameController.askReplay();
                    }
                }
            });
        }

    }
}
