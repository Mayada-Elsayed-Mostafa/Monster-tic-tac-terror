package tic.tac.toe.game.iti.client;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

public class WelcomeController {

    private Stage stage;

    @FXML
    private Button multiModeBtn;
    @FXML
    private Button onlineBtn;
    @FXML
    private Button singleModeBtn;

    public void setStage(Stage stage) {
        this.stage = stage;

    }

    @FXML
    public void goToOnlineGame() {
//        try {
//            FXMLLoader loader = new FXMLLoader(getClass().getResource("LoginPage.fxml"));
//            Parent root = loader.load();
//
//            LoginPageController controller = loader.getController();
//            controller.setStage(stage);
//
//            stage.setScene(new Scene(root));
//            stage.setTitle("Login Page");
//        } catch (IOException ex) {
//            Logger.getLogger(WelcomePageController.class.getName()).log(Level.SEVERE, null, ex);
//        }
    }

    @FXML
    private void multiModeBtnHandel(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("Game.fxml"));
            if (loader == null) {
                throw new IOException("Game.fxml could not be loaded.");
            }
            Parent root = loader.load();

            GameController controller = loader.getController();
            controller.setStage(stage);

            stage.setScene(new Scene(root));
            stage.setTitle("Game Page");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void singleModeHandeler(ActionEvent event) {
//        try {
//            FXMLLoader loader = new FXMLLoader(getClass().getResource("Singlemode.fxml"));
//            Parent root = loader.load();
//
//            SinglemodeController controller = loader.getController();
//            controller.setStage(stage);
//
//            stage.setScene(new Scene(root));
//            stage.setTitle("Single Mode Page");
//        } catch (IOException ex) {
//            Logger.getLogger(SinglemodeController.class.getName()).log(Level.SEVERE, "Error loading Singlemode.fxml", ex);
//        }

    }
}
