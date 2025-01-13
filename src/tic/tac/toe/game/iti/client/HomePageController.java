/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tic.tac.toe.game.iti.client;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

/**
 *
 * @author HAZEM-LAB
 */
public class HomePageController {
    
    
    
    private Stage stage;
    
    @FXML
    private VBox usernames;
    @FXML
    private VBox scores;
    @FXML
    private VBox challenges;
    
    public void setStage(Stage stage) {
        this.stage = stage;

    }
    
    public void handleLogout(ActionEvent event){
        
    }
}
