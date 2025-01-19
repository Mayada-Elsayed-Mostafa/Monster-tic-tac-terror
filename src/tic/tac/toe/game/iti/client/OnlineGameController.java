package tic.tac.toe.game.iti.client;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.TextInputDialog;
import javafx.stage.Stage;
import org.json.simple.JSONObject;
import org.json.simple.JSONValue;
import tic.tac.toe.game.iti.client.ServerSide.MassageType;
import tic.tac.toe.game.iti.client.ServerSide.ServerHandler;
import static tic.tac.toe.game.iti.client.ServerSide.ServerHandler.stage;

public class OnlineGameController{

    Stage stage;
    private int score1, score2 = 0;
    private int moveCount = 0;
    private String[][] board = new String[3][3];
    private boolean isMyTurn = true;
    private String player1Name = "Player 1";
    private String player2Name = "Player 2";
    public boolean doIStart = false;
    private boolean isGameFinished = false;
    private Button[] cells;
    String textOfFile = "";
    
    @FXML
    private Button cell_1_btn, cell_2_btn, cell_3_btn, cell_4_btn, cell_5_btn, cell_6_btn, cell_7_btn, cell_8_btn, cell_9_btn;
    @FXML
    private Button recordBtn;
    @FXML
    private Button endGameBtn;
    @FXML
    private Label score1Number;
    @FXML
    private Label score2Number;
    @FXML
    private Label namesLabel;
    
    public void setStage(Stage stage,String msg) {
        this.stage = stage;
        cells = new Button[]{cell_1_btn, cell_2_btn, cell_3_btn, cell_4_btn, cell_5_btn, cell_6_btn, cell_7_btn, cell_8_btn, cell_9_btn};
        startGame(msg);
    }
    public static void navigateToGame(String msg){
        try {
            FXMLLoader loader = new FXMLLoader(TicTacToeGameITIClient.class.getClass().getResource("/tic/tac/toe/game/iti/client/OnlineGame.fxml"));
            Parent root = loader.load();
            
            OnlineGameController controller = loader.getController();
            controller.setStage(ServerHandler.stage, msg);
            ServerHandler.stage.setScene(new Scene(root));
            ServerHandler.stage.setTitle("Online Game");
        } catch (IOException ex) {
            Logger.getLogger(OnlineGameController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @FXML
    public void handleCellClick(ActionEvent event){
        Button clickedButton = (Button) event.getSource();

        if (clickedButton.getText().isEmpty()){
            if (isMyTurn){
                int cellNumber = 0;
                for(int i = 0; i < cells.length; i++){
                    if(clickedButton == cells[i]){
                        cellNumber = i;
                        break;
                    }
                }
                clickedButton.setStyle("-fx-text-fill: #D4A5A5;");
                clickedButton.setText("X");
                clickedButton.setDisable(true);
                isMyTurn =! isMyTurn;
                moveCount++;
                JSONObject play = new JSONObject();
                play.put("type", MassageType.PLAY_MSG);
                play.put("data", cellNumber);
                try {
                    ServerHandler.massageOut.writeUTF(play.toJSONString());
                } catch (IOException ex){
                    Logger.getLogger(OnlineGameController.class.getName()).log(Level.SEVERE, null, ex);
                }
                if(checkWinner()){
                    //win video
                    score1 += 10;
                    handleEndGame();
                }
                else if(moveCount == 9){
                    //tie video
                    handleEndGame();
                }
            }
        }
    }

    public boolean checkWinner() {

        board[0][0] = cell_1_btn.getText();
        board[0][1] = cell_2_btn.getText();
        board[0][2] = cell_3_btn.getText();
        board[1][0] = cell_4_btn.getText();
        board[1][1] = cell_5_btn.getText();
        board[1][2] = cell_6_btn.getText();
        board[2][0] = cell_7_btn.getText();
        board[2][1] = cell_8_btn.getText();
        board[2][2] = cell_9_btn.getText();

        for (int i = 0; i < 3; i++) {
            if (board[i][0].equals(board[i][1]) && board[i][1].equals(board[i][2]) && !board[i][0].isEmpty()) {
                return true;
            }
            if (board[0][i].equals(board[1][i]) && board[1][i].equals(board[2][i]) && !board[0][i].isEmpty()) {
                return true;
            }
        }

        if (board[0][0].equals(board[1][1]) && board[1][1].equals(board[2][2]) && !board[0][0].isEmpty()) {
            return true;
        }
        if (board[0][2].equals(board[1][1]) && board[1][1].equals(board[2][0]) && !board[0][2].isEmpty()) {
            return true;
        }
        return false;
    }

    @FXML
    private void recordHandeler(ActionEvent event) {
        LocalDateTime now = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd_HH-mm-ss");
        String formattedDateTime = now.format(formatter);
    }

    @FXML
    private void endHandeler(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("Welcome.fxml"));
            Parent root = loader.load();

            WelcomeController controller = loader.getController();
            controller.setStage(stage);

            stage.setScene(new Scene(root));
            stage.setTitle("Welcome Page");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void resetGame() {
        cell_1_btn.setText("");
        cell_2_btn.setText("");
        cell_3_btn.setText("");
        cell_4_btn.setText("");
        cell_5_btn.setText("");
        cell_6_btn.setText("");
        cell_7_btn.setText("");
        cell_8_btn.setText("");
        cell_9_btn.setText("");
        cell_1_btn.setDisable(false);
        cell_2_btn.setDisable(false);
        cell_3_btn.setDisable(false);
        cell_4_btn.setDisable(false);
        cell_5_btn.setDisable(false);
        cell_6_btn.setDisable(false);
        cell_7_btn.setDisable(false);
        cell_8_btn.setDisable(false);
        cell_9_btn.setDisable(false);
        moveCount = 0;
    }

    public void startGame(String msg) {
        JSONObject mainObject = (JSONObject) JSONValue.parse(msg);
        JSONObject object = (JSONObject) JSONValue.parse((String)mainObject.get("data"));
        player1Name = (String) object.get("player1");
        player2Name = (String) object.get("player2");
        namesLabel.setText(player1Name+" vs "+player2Name);
        boolean start = (boolean) object.get("isStarted");
        doIStart = start;
        isMyTurn = start;
        Thread listener=new Thread(() -> {
            while(!isGameFinished && ServerHandler.socket != null){
                while (ServerHandler.msg == null ) {
                    try {
                        if(ServerHandler.socket==null){
                            return;
                        }
                        Thread.sleep(100);  // Prevents busy-waiting
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                JSONObject data = (JSONObject) JSONValue.parse(ServerHandler.msg);
                String msgType = (String) data.get("type");
                if(msgType.equals(MassageType.PLAY_MSG)){
                    int cellNumber = ((Long) data.get("data")).intValue();
                    Platform.runLater(() -> {
                        cells[cellNumber].setDisable(true);
                        cells[cellNumber].setStyle("-fx-text-fill: #D4A5A5;");
                        cells[cellNumber].setText("O");
                        isMyTurn = !isMyTurn;
                        moveCount++;

                        if(checkWinner()){
                            //lose video
                            score2 += 10;

                            handleEndGame();

                        }
                        else if(moveCount == 9){
                            //tie video

                            handleEndGame();

                        }
                    });
                    
                    ServerHandler.msg = null;
                }
                else if(msgType.equals(MassageType.WITHDRAW_GAME_MSG)){
                    //win video
                    score1 += 10;
                    Platform.runLater(() -> {
                        Alert check = new Alert(Alert.AlertType.INFORMATION,"Your opponent has withdrawn");
                        check.showAndWait();
                        endGame();  //no server interaction
                    });
                    ServerHandler.msg = null;
                }
            }
        });
        listener.start();
    }

    public void handleEndGame(){
            ButtonType restart = new ButtonType("Restart");
            ButtonType endGame = new ButtonType("End the game");
            Alert check = new Alert(Alert.AlertType.CONFIRMATION,"Restart game?",restart,endGame);
            Optional<ButtonType> returnType = check.showAndWait();

            if(returnType.isPresent()){
                if(returnType.get() == restart){
                    resetGame();
                }
                else{
                    endGame();
                }
            }
            else{
                endGame();
            }
    }

    private void endGame() {
        
    }
    
}
