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



public class SignupPageController {

    Stage stage;
    @FXML
    private TextField userNameTF;
    private TextField emailTF;
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
        String email = emailTF.getText();
        String password = passwordTF1.getText();
        String confirmPassword = confirmTF1.getText();

        if (username.isEmpty() || email.isEmpty() || password.isEmpty() || confirmPassword.isEmpty()) {
            showAlert("Error", "All fields are required.");
            return;
        }

        if (!password.equals(confirmPassword)) {
            showAlert("Error", "Passwords do not match.");
            return;
        }
 

         try (Socket socket = new Socket("localhost", 5000);
             DataOutputStream dos = new DataOutputStream(socket.getOutputStream())) {

            String data = username + ","  + password;

            dos.writeUTF(data);
            dos.flush();
            showAlert("Success", "Data sent to server. Await confirmation.");
        } catch (Exception e) {
            e.printStackTrace();
            showAlert("Error", "Unable to connect to server: " + e.getMessage());
        }
    }
    

    @FXML
    private void haveanAcountHTHandler(ActionEvent event) {
//        try {
//            FXMLLoader loader = new FXMLLoader(getClass().getResource("LoginPage.fxml"));
//            Parent root = loader.load();
//
//            LoginPageController controller = loader.getController();
//            controller.setStage(stage);
//
//            stage.setScene(new Scene(root));
//            stage.setTitle("LoginPage");
//        } catch (IOException e) {
//            Alert alert = new Alert(Alert.AlertType.ERROR, "An error occured, please try again", ButtonType.OK);
//            alert.showAndWait();
//        }
    }

    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setContentText(message);
        alert.showAndWait();
    }

 
}
