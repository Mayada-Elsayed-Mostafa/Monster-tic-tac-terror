package tic.tac.toe.game.iti.client;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.json.simple.JSONObject;
import tic.tac.toe.game.iti.client.ServerSide.MassageType;
import tic.tac.toe.game.iti.client.ServerSide.ServerHandler;

public class TicTacToeGameITIClient extends Application {

    @Override
    public void start(Stage primaryStage) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("Welcome.fxml"));
            Parent root = loader.load();

            WelcomeController controller = loader.getController();
            controller.setStage(primaryStage);
            ServerHandler.stage = primaryStage;
            primaryStage.setOnCloseRequest((event) -> {
                if(ServerHandler.socket!=null){
                    try {
                        ServerHandler.closeSocket();
                    } catch (IOException ex) {
                        Logger.getLogger(TicTacToeGameITIClient.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            });
            primaryStage.setScene(new Scene(root));
            primaryStage.setTitle("Welcome Page");
            primaryStage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        launch(args);
    }

}