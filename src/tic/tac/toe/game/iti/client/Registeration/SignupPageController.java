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
import tic.tac.toe.game.iti.client.ServerSide.MassageType;
import tic.tac.toe.game.iti.client.ServerSide.ServerHandler;
import tic.tac.toe.game.iti.client.WelcomeController;
import tic.tac.toe.game.iti.client.player.Player;

public class SignupPageController {

    Stage stage;
    @FXML
    private TextField userNameTF;
    @FXML
    private PasswordField passwordTF1;
    @FXML
    private PasswordField confirmTF1;
    @FXML
    private Button signupBtn;
    @FXML
    private Hyperlink haveanAcountHT;
    @FXML
    private Button backBtn;

    public void setStage(Stage stage) {
        this.stage = stage;
    }

    @FXML
    private void signupHandler(ActionEvent event) {
        String username = userNameTF.getText();
        String password = passwordTF1.getText();
        String confirmPassword = confirmTF1.getText();

        if (username.isEmpty() || password.isEmpty() || confirmPassword.isEmpty()) {
            showAlert("Error", "All fields are required.");
            return;
        } else if (username.length() < 3) {
            showAlert("Invalid", "Your username must be at least 3 characters");
            return;
        } else if (password.length() < 6) {
            showAlert("Invalid", "Your password must be at least 6 characters");
            return;
        } else if (!password.equals(confirmPassword)) {
            showAlert("Error", "Passwords do not match.");
            return;
        }

        try {
            Player p = new Player(username, password);
            JSONObject jsonObject = new JSONObject();
            JSONObject player = new JSONObject();
            player.put("username", p.getUserName());
            player.put("password", p.getPassword());
            player.put("status", p.getStatus());
            player.put("score", p.getScore());
            jsonObject.put("type", MassageType.REGISTER_MSG);
            jsonObject.put("data", player.toJSONString());

            String jsonString = jsonObject.toString();

            ServerHandler.massageOut.writeUTF(jsonString);
            while (ServerHandler.msg == null) {
                System.out.print("");
            }
            JSONObject data = (JSONObject) JSONValue.parse(ServerHandler.msg);
            if (data.get("type").equals(MassageType.REGISTER_SUCCESS_MSG)) {
                showAlert("Successful", "you are signed up successfully");
                goLogin();

            } else if (data.get("type").equals(MassageType.REGISTER_FAIL_MSG)) {
                showAlert("unsuccessful", "please, try again");
            }
            ServerHandler.msg=null;
        } catch (Exception e) {
            e.printStackTrace();
            showAlert("Error", "connect to server" + e.getMessage());
        }
    }

    @FXML
    private void haveanAcountHTHandler(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("LoginPage.fxml"));
            Parent root = loader.load();

            LoginPageController controller = loader.getController();
            controller.setStage(stage);

            stage.setScene(new Scene(root));
            stage.setTitle("LoginPage");
        } catch (IOException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR, "please try again", ButtonType.OK);
            alert.initOwner(stage.getScene().getWindow());
            alert.showAndWait();
        }
    }

    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setContentText(message);
        alert.initOwner(stage.getScene().getWindow());
        alert.showAndWait();
    }

    private void goLogin() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("LoginPage.fxml"));
        Parent root = loader.load();

        LoginPageController controller = loader.getController();
        controller.setStage(stage);

        stage.setScene(new Scene(root));
        stage.setTitle("LoginPage");
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
            Logger.getLogger(SignupPageController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}