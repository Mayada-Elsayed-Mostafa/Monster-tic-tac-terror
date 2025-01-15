package Registeration;

import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import org.json.simple.JSONObject;
import tic.tac.toe.game.iti.client.Registeration.LoginPageController;



public class SignupPageController {

    Stage stage;
    @FXML
    private TextField userNameTF;
    @FXML
    private TextField passwordTF1;
    @FXML
    private TextField confirmTF1;
    @FXML
    private Button signupBtn;
    @FXML
    private Hyperlink haveanAcountHT;

    public void setStage(Stage stage) {
        this.stage = stage;
    }

    @FXML
    private void signupHandler(ActionEvent event) {
        String username = userNameTF.getText();
        String password = passwordTF1.getText();
        String confirmPassword = confirmTF1.getText();

        if (username.isEmpty() ||  password.isEmpty() || confirmPassword.isEmpty()) {
            showAlert("Error", "All fields are required.");
            return;
        }else if (username.length() < 3) {
            showAlert( "Invalid", "Your username must be at least 3 characters");
        } else if (password.length() < 6) {
            showAlert( "Invalid", "Your password must be at least 6 characters");
        }else if (!password.equals(confirmPassword)) {
            showAlert("Error", "Passwords do not match.");
            return;
        }
 

         try (Socket socket = new Socket("localhost", 5000);
             DataOutputStream dos = new DataOutputStream(socket.getOutputStream())) {

            JSONObject jsonObject = new JSONObject();
            jsonObject.put("username", username);
            jsonObject.put("password", password);

            String jsonString = jsonObject.toString();

            dos.writeUTF(jsonString);
            dos.flush();

            showAlert("Success","Data Saved");
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
            alert.showAndWait();
        }
    }

    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setContentText(message);
        alert.showAndWait();
    }

 
}
