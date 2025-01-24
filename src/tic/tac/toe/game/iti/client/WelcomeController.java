package tic.tac.toe.game.iti.client;

import java.io.IOException;
import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextInputDialog;
import javafx.stage.Stage;
import tic.tac.toe.game.iti.client.Registeration.LoginPageController;
import tic.tac.toe.game.iti.client.ServerSide.ServerHandler;
import tic.tac.toe.game.iti.client.Singlemode.SinglemodeController;

public class WelcomeController {

    private Stage stage;
    private String ip;
    private boolean isIpEntered = false;

    @FXML
    private Button multiModeBtn;
    @FXML
    private Button onlineBtn;
    @FXML
    private Button singleModeBtn;
    @FXML
    private Button offlineRcdsBtn;

    public void setStage(Stage stage) {
        this.stage = stage;
    }

    @FXML
    public void goToOnlineGame() {
        if (!isIpEntered) {
            showAlertForIPAdress();
        } if(isIpEntered) {
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
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("Singlemode/Singlemode.fxml"));
            Parent root = loader.load();

            SinglemodeController controller = loader.getController();
            controller.setStage(stage);

            stage.setScene(new Scene(root));
            stage.setTitle("Single Mode Page");
        } catch (IOException ex) {
            Logger.getLogger(SinglemodeController.class.getName()).log(Level.SEVERE, "Error loading Singlemode.fxml", ex);
        }
    }

    public void showAlertForIPAdress() {
        TextInputDialog ipTI = new TextInputDialog();
        ipTI.setTitle("IP Address");
        ipTI.setHeaderText("Enter the IP Address...");

        Optional<String> ipInput = ipTI.showAndWait();

        if (ipInput.isPresent() && !ipInput.get().trim().isEmpty()) {
            ip = ipInput.get().trim();
            try {
                ServerHandler.setSocket(ip);
                isIpEntered = true;
            } catch (IOException ex) {
                Alert alert = new Alert(Alert.AlertType.WARNING, "Unable to connect to the server. Please try again later.", ButtonType.OK);
                alert.setTitle("Connection Error");
                alert.showAndWait();
            }
        } else {
            Alert a = new Alert(Alert.AlertType.WARNING, "Please Enter IP to continue...", ButtonType.OK);
            a.showAndWait();
        }
    }
    
    public void offlineRcdsBtnHandle(ActionEvent event){
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("OfflineRecords.fxml"));
            Parent root = loader.load();

            OfflineRecordsController controller = loader.getController();
            controller.setStage(stage);

            stage.setScene(new Scene(root));
            stage.setTitle("Offline Records Page");
        } catch (IOException ex) {
            Logger.getLogger(OfflineRecordsController.class.getName()).log(Level.SEVERE, "Error loading Singlemode.fxml", ex);
        }
    }
}
