package tic.tac.toe.game.iti.client.Registeration;

import java.io.IOException;
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
import javafx.scene.control.Hyperlink;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import org.json.simple.JSONObject;
import org.json.simple.JSONValue;
import tic.tac.toe.game.iti.client.HomePageController;
import tic.tac.toe.game.iti.client.ServerSide.MassageType;
import tic.tac.toe.game.iti.client.ServerSide.ServerHandler;
import tic.tac.toe.game.iti.client.WelcomeController;
import tic.tac.toe.game.iti.client.player.Player;

public class LoginPageController {

    private Stage stage;
    
    @FXML
    private Button loginBtn;
    @FXML
    private TextField username;
    @FXML
    private PasswordField password;
    @FXML
    private Hyperlink creatAccount;
    @FXML
    private Button backBtn;

    public void setStage(Stage stage) {
        this.stage = stage;
    }

    private void showAlert(Alert.AlertType alertType, String title, String message) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setContentText(message);
        alert.initOwner(stage.getScene().getWindow());
        alert.showAndWait();
    }

    @FXML
    public void loginButtonHandler(ActionEvent event) {
        String _username = username.getText();
        String _password = password.getText();
        JSONObject loginData = new JSONObject();
        if (_username.length() < 3) {
            showAlert(Alert.AlertType.WARNING, "Invalid", "Your username must be at least 3 characters");
        } else if (_password.length() < 6) {
            showAlert(Alert.AlertType.WARNING, "Invalid", "Your password must be at least 6 characters");
            
        } else {
            Player p = new Player(_username, _password);
            JSONObject player = new JSONObject();
            player.put("username", p.getUserName());
            player.put("password", p.getPassword());
            player.put("status", p.getStatus());
            player.put("score", p.getScore());
            loginData.put("type", MassageType.LOGIN_MSG);
            loginData.put("data", player.toJSONString());
            try {
                ServerHandler.massageOut.writeUTF(loginData.toJSONString());
            } catch (IOException ex) {
                Logger.getLogger(LoginPageController.class.getName()).log(Level.SEVERE, null, ex);
            }
            while (ServerHandler.msg == null) {
                System.out.print("");
            }
            JSONObject data = (JSONObject) JSONValue.parse(ServerHandler.msg);
            if (data.get("type").equals(MassageType.LOGIN_SUCCESS_MSG)) {
                ServerHandler.isLoggedIn = true;
                navigateToHome(ServerHandler.msg);
            } else if (data.get("type").equals(MassageType.LOGIN_FAIL_MSG)) {
                showAlert(Alert.AlertType.WARNING, "unsuccessful", "Log in failed, try again");
            }
            ServerHandler.msg = null;
        }
    }

    private void navigateToHome(Object data) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/tic/tac/toe/game/iti/client/HomePage.fxml"));
            Parent root = loader.load();

            HomePageController controller = loader.getController();
            controller.setStage(stage);
            
            HomePageController.updateAvailablePlayers((String) data);

            stage.setScene(new Scene(root));
            stage.setTitle("Home Page");
        } catch (IOException e) {
            e.printStackTrace();
            Alert alert = new Alert(Alert.AlertType.ERROR, "An error occured, please try again", ButtonType.OK);
            alert.initOwner(stage.getScene().getWindow());
            alert.showAndWait();
        }
    }

    @FXML
    private void createAccountHandler() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("SignupPage.fxml"));
            Parent root = loader.load();

            SignupPageController controller = loader.getController();
            controller.setStage(stage);

            stage.setScene(new Scene(root));
            stage.setTitle("Signup Page");
        } catch (IOException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR, "An error occured, please try again", ButtonType.OK);
            alert.initOwner(stage.getScene().getWindow());
            alert.showAndWait();
        }
    }

    @FXML
    private void handleBackBtn(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/tic/tac/toe/game/iti/client/Welcome.fxml"));
            Parent root = loader.load();
            WelcomeController controller = loader.getController();
            controller.setStage(stage);
            ServerHandler.isClosedNormally = true;
            ServerHandler.closeSocket();
            stage.setScene(new Scene(root));
            stage.setTitle("Welcome Page");
            new Thread(() -> {
                try {
                    Thread.sleep(5000);
                    ServerHandler.isClosedNormally = false;
                } catch (InterruptedException ex) {
                    Logger.getLogger(LoginPageController.class.getName()).log(Level.SEVERE, null, ex);
                }
            }).start();
        } catch (IOException ex) {
            Logger.getLogger(LoginPageController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}