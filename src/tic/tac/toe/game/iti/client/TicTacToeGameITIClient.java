package tic.tac.toe.game.iti.client;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Application;
import static javafx.application.Application.launch;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import tic.tac.toe.game.iti.client.ServerSide.ServerHandler;

public class TicTacToeGameITIClient extends Application {

    public static boolean isClosed = false;

    @Override
    public void start(Stage primaryStage) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("Welcome.fxml"));
            Parent root = loader.load();

            WelcomeController controller = loader.getController();
            controller.setStage(primaryStage);

            ServerHandler.stage = primaryStage;

            primaryStage.setOnCloseRequest(event -> {
                isClosed = true;
                if (ServerHandler.socket != null) {
                    try {
                        ServerHandler.closeSocket();
                    } catch (IOException ex) {
                        Logger.getLogger(TicTacToeGameITIClient.class.getName())
                              .log(Level.SEVERE, "Error closing socket", ex);
                    }
                }
            });

            primaryStage.setScene(new Scene(root));
            primaryStage.setTitle("Welcome Page");

            primaryStage.setFullScreen(false);
            primaryStage.fullScreenProperty().addListener((observable, oldValue, newValue) -> {
                if (newValue) {
                    primaryStage.setFullScreen(false);
                }
            });

            primaryStage.setResizable(false);

            primaryStage.show();

        } catch (IOException e) {
            Logger.getLogger(TicTacToeGameITIClient.class.getName())
                  .log(Level.SEVERE, "Failed to load Welcome.fxml", e);
            System.err.println("Error: Unable to load the Welcome page. Please check the FXML file.");
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}
