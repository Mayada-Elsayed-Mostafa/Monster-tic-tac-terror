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
import tic.tac.toe.game.iti.client.player.Player;

public class LoginPageController {

    private Stage stage;

    @FXML
    private TextField username;
    @FXML
    private PasswordField password;
    @FXML
    private Hyperlink creatAccount;

    public void setStage(Stage stage) {
        this.stage = stage;
    }

    private void showAlert(Alert.AlertType alertType, String title, String message) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setContentText(message);
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
            loginData.put("type", MassageType.LOGIN_MSG);
            loginData.put("data", new Player(_username, _password));
            try {
                ServerHandler.massageOut.writeUTF(loginData.toJSONString());
            } catch (IOException ex) {
                Logger.getLogger(LoginPageController.class.getName()).log(Level.SEVERE, null, ex);
            }
            while (ServerHandler.msg == null) {
            }
            JSONObject data = (JSONObject) JSONValue.parse(ServerHandler.msg);
            if (data.get("type").equals(MassageType.LOGINSUCCESS_MSG)) {
                showAlert(Alert.AlertType.CONFIRMATION, "Successful", "you are logged in successfully");
                navigateToHome(data.get("data"));
            } else if (data.get("type").equals(MassageType.LOGINFAIL_MSG)) {
                showAlert(Alert.AlertType.WARNING, "unsuccessful", "Log in failed, try again");
            }
            ServerHandler.msg = null;
        }
    }

    @FXML
    private void navigateToHome(Object data) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("HomePage.fxml"));
            Parent root = loader.load();

            HomePageController controller = loader.getController();
            controller.setStage(stage);

            stage.setScene(new Scene(root));
            stage.setTitle("Home Page");
        } catch (IOException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR, "An error occured, please try again", ButtonType.OK);
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
            alert.showAndWait();
        }
    }

}
