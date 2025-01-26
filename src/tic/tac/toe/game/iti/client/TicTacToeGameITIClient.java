package tic.tac.toe.game.iti.client;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.animation.PauseTransition;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import javafx.util.Duration;
import tic.tac.toe.game.iti.client.ServerSide.ServerHandler;

public class TicTacToeGameITIClient extends Application {

    public static boolean isClosed = false;

    @Override
    public void start(Stage primaryStage) {
        
        try {
            
            primaryStage.getIcons().add(new Image(getClass().getResourceAsStream("/Assets/icon.png")));

            primaryStage.setWidth(800);
            primaryStage.setHeight(600);

            ImageView splashBG = new ImageView();
            splashBG.setImage(new Image(getClass().getResourceAsStream("/Assets/splashBG.png")));
            splashBG.setFitWidth(primaryStage.getWidth());
            splashBG.setFitHeight(primaryStage.getHeight());
            splashBG.setPreserveRatio(true);

            StackPane splashRoot = new StackPane(splashBG);

            Scene splashScene = new Scene(splashRoot);
            primaryStage.setScene(splashScene);
            primaryStage.setTitle("Splash Screen");
            primaryStage.show();

            primaryStage.setFullScreen(false);
            primaryStage.fullScreenProperty().addListener((observable, oldValue, newValue) -> {
                if (newValue) {
                    primaryStage.setFullScreen(false);
                }
            });

            primaryStage.setResizable(false);

            PauseTransition pause = new PauseTransition(Duration.seconds(2));
            pause.setOnFinished(event -> {

                try {
                    FXMLLoader loader = new FXMLLoader(getClass().getResource("Welcome.fxml"));
                    Parent root = loader.load();

                    WelcomeController controller = loader.getController();
                    controller.setStage(primaryStage);
                    ServerHandler.stage = primaryStage;

                    primaryStage.setOnCloseRequest((closeEvent) -> {
                        isClosed = true;
                        if (ServerHandler.socket != null) {
                            try {
                                ServerHandler.closeSocket();
                            } catch (IOException ex) {
                                Logger.getLogger(TicTacToeGameITIClient.class.getName()).log(Level.SEVERE, null, ex);
                            }
                        }
                    });

                    primaryStage.setScene(new Scene(root));
                    primaryStage.setTitle("Welcome Page");
                } catch (IOException ex) {
                    Logger.getLogger(TicTacToeGameITIClient.class.getName()).log(Level.SEVERE, null, ex);
                }
            });
            pause.play();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}
