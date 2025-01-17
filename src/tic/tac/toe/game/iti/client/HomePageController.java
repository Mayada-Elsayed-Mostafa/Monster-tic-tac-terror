/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tic.tac.toe.game.iti.client;

import java.util.List;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import tic.tac.toe.game.iti.client.player.Player;

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
    
    private static VBox sUserNames;
    private static VBox sScores;
    private static VBox sChallenges;
    
    public void setStage(Stage stage) {
        this.stage = stage;
        sUserNames = usernames;
        sScores = scores;
        sChallenges = challenges;
    }
    
    public void handleLogout(ActionEvent event){
        
    }
    
    public static void updateAvailablePlayers(List<Player> players){
        sUserNames.getChildren().clear();
        sScores.getChildren().clear();
        sChallenges.getChildren().clear();
        
        for(Player player : players){
            Label user = new Label(player.getUserName());
            sUserNames.getChildren().add(user);
            
            Label score = new Label(String.valueOf(player.getScore()));
            sScores.getChildren().add(score);
            
            Button challengeBtn = new Button("Challenge");
            challengeBtn.setOnAction(event -> {
                handleChallenge(player);
            });
            sChallenges.getChildren().add(challengeBtn);
        }
    }
    
    private static void handleChallenge(Player player) {
        
    }
}
