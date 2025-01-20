/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tic.tac.toe.game.iti.client.Singlemode;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

public class SinglemodeController implements Initializable {

    private Stage stage;

    @FXML
    private Button easyBtn;
    @FXML
    private Button hardBtn;
    @FXML
    private Button intermediateBtn;

    public void setStage(Stage stage) {
        this.stage = stage;
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }

    @FXML
    private void goToEasyGame(ActionEvent event) {
        loadGame("Easy");
    }
    
    @FXML
    private void goToIntermediateGame(ActionEvent event) {
        loadGame("Intermediate");
    }

    @FXML
    private void goToHardGame(ActionEvent event) {
        loadGame("Hard");
    }
    
    private void loadGame(String difficulty){
        try{
            FXMLLoader loader = new FXMLLoader(getClass().getResource("Easymode.fxml"));
            Parent root = loader.load();

            EasymodeController controller = loader.getController();
            controller.setStage(stage);
            controller.setDifficulty(difficulty);

            stage.setScene(new Scene(root));
            stage.setTitle(difficulty + " Mode");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}