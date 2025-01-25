package tic.tac.toe.game.iti.client;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import java.io.File;
import java.nio.file.Files;
import java.util.Timer;
import java.util.TimerTask;
import javafx.scene.Scene;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.JSONValue;

public class RecordController {

    @FXML
    private Button cell_1_btn;
    @FXML
    private Button cell_2_btn;
    @FXML
    private Button cell_3_btn;
    @FXML
    private Button cell_4_btn;
    @FXML
    private Button cell_5_btn;
    @FXML
    private Button cell_6_btn;
    @FXML
    private Button cell_7_btn;
    @FXML
    private Button cell_8_btn;
    @FXML
    private Button cell_9_btn;
    
    @FXML
    private Label player_1;
    @FXML
    private Label player_2;

    private Stage stage;
    private Timer replayTimer;
    private JSONObject gameRecord;
    private int currentMoveIndex = 0;
    private Scene prevScene;

    private Button[][] board;

    public void initialize() {
        board = new Button[][]{
                {cell_1_btn, cell_2_btn, cell_3_btn},
                {cell_4_btn, cell_5_btn, cell_6_btn},
                {cell_7_btn, cell_8_btn, cell_9_btn}
        };

        clearBoard();
    }

    public void setStage(Stage stage) {
        this.stage = stage;
        prevScene=stage.getScene();
    }

    public void loadGameRecord(File jsonFile) {
        try {
            gameRecord = (JSONObject) JSONValue.parse(new String(Files.readAllBytes(jsonFile.toPath())));

            JSONObject players = (JSONObject) gameRecord.get("players");
            JSONObject player1 = (JSONObject) players.get("player1");
            JSONObject player2 = (JSONObject) players.get("player2");

            String player1Name = (String) player1.get("name");
            String player1Symbol = (String) player1.get("symbol");
            String player2Name = (String) player2.get("name");
            String player2Symbol = (String) player2.get("symbol");
            //namesLabel.setText(player1Name + " (" + player1Symbol + ") " +  "vs " + player2Name + " (" + player2Symbol + ")");
            player_1.setText(player1Name);
            player_2.setText(player2Name);
            replayGame();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void clearBoard() {
        for (Button[] row : board) {
            for (Button cell : row) {
                cell.setText("");
            }
        }
    }

    private void replayGame() {
        if (replayTimer != null) {
            replayTimer.cancel();
        }

        clearBoard();

        JSONArray moves = (JSONArray) gameRecord.get("moves");
        currentMoveIndex = 0;

        replayTimer = new Timer();
        replayTimer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                if (currentMoveIndex >= moves.size()) {
                    replayTimer.cancel();
                    return;
                }

                JSONObject move = (JSONObject) moves.get(currentMoveIndex);
                String player = (String) move.get("player");
                JSONArray position = (JSONArray) move.get("position");
                int row = ((Long) position.get(0)).intValue();
                int col = ((Long) position.get(1)).intValue();

                Platform.runLater(() -> {
                    board[row][col].setText(player.equals(((JSONObject) ((JSONObject) gameRecord.get("players")).get("player1")).get("name")) ? "X" : "O");
                });

                currentMoveIndex++;
            }
        }, 0, 2000);
    }

    @FXML
    private void backIconhandler(MouseEvent event) {
        stage.setScene(prevScene);
    }
}
