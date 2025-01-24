package tic.tac.toe.game.iti.client;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Optional;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.TextInputDialog;
import javafx.stage.Stage;
import jdk.nashorn.api.scripting.JSObject;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

public class GameController extends Controller {

    Stage stage;
    private int score1, score2 = 0;
    private int moveCount = 0;
    private String[][] board = new String[3][3];
    private boolean isPlayer1Turn = true;
    private String player1Name = "Player 1";
    private String player2Name = "Player 2";
    private boolean isRecording = false;
    @FXML
    private Button cell_1_btn, cell_2_btn, cell_3_btn, cell_4_btn, cell_5_btn, cell_6_btn, cell_7_btn, cell_8_btn, cell_9_btn;
    private Button[] buttons;

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

    public void setStage(Stage stage) {
        this.stage = stage;
        startGame();
        buttons = new Button[]{cell_1_btn, cell_2_btn, cell_3_btn, cell_4_btn, cell_5_btn, cell_6_btn, cell_7_btn, cell_8_btn, cell_9_btn};
    }

    JSONObject fileObject;

    JSONArray moves;

    @FXML
    public void handleCellClick(ActionEvent event) {
        Button clickedButton = (Button) event.getSource();

        if (clickedButton.getText().isEmpty()) {
            JSONArray cell = new JSONArray();
            for (int i = 0; i < buttons.length; i++) {
                if (clickedButton == buttons[i]) {
                    cell.add(i / 3);
                    cell.add(i % 3);
                    break;
                }
            }
            JSONObject move = new JSONObject();

            move.put("position", cell);
            if (isPlayer1Turn) {
                clickedButton.setStyle("-fx-text-fill: #D4A5A5;");
                clickedButton.setText("X");
                move.put("player", player1Name);

            } else {
                clickedButton.setStyle("-fx-text-fill: #497F5B;");
                clickedButton.setText("O");
                move.put("player", player2Name);
            }
            clickedButton.setDisable(true);

            moves.add(move);
            moveCount++;
            String winner = "";
            if (checkWinner()) {
                if (isRecording) {
                    fileObject.put("moves", moves);
                    DateTimeFormatter dtf = DateTimeFormatter.ofPattern("MM-dd_HH-mm");
                    LocalDateTime now = LocalDateTime.now();
                    String time = dtf.format(now);

                    File record = new File("offlineRecords/" + player1Name + "vs" + player2Name + " " + time + ".json");
                    try {
                        if (record.createNewFile()) {
                            FileWriter myWriter = new FileWriter(record);
                            myWriter.write(fileObject.toJSONString());
                            myWriter.close();
                        }

                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
                winner = isPlayer1Turn ? player1Name : player2Name;

                handleWinner(winner);

                if (player2Name.equals(winner)) {
                    score2 += 10;
                    score2Number.setText(String.valueOf(score2));
                } else if (player1Name.equals(winner)) {
                    score1 += 10;
                    score1Number.setText(String.valueOf(score1));
                }
            } else if (moveCount == 9) {
                if (isRecording) {
                    fileObject.put("moves", moves);
                    DateTimeFormatter dtf = DateTimeFormatter.ofPattern("MM-dd_HH-mm");
                    LocalDateTime now = LocalDateTime.now();
                    String time = dtf.format(now);

                    File record = new File("onlineRecords/" + player1Name + " Vs " + player2Name + " " + time + ".json");
                    try {
                        if (record.createNewFile()) {
                            FileWriter myWriter = new FileWriter(record);
                            myWriter.write(fileObject.toJSONString());
                            myWriter.close();
                        }

                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
                System.out.println("It's a tie!");
                handleWinner(winner);
            }

            isPlayer1Turn = !isPlayer1Turn;
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
    private void recordHandeler() {
        if (!isRecording) {
            isRecording = true;
            recordBtn.setDisable(true);

        }
    }

    @FXML
    private void endHandeler() {
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
        isPlayer1Turn = true;
        board = new String[3][3];
        isRecording = false;
        fileObject = new JSONObject();
        moves = new JSONArray();
        JSONObject player1 = new JSONObject();
        player1.put("name", player1Name);
        player1.put("symbol", "X");
        JSONObject player2 = new JSONObject();
        player2.put("name", player2Name);
        player2.put("symbol", "O");
        JSONObject players = new JSONObject();
        players.put("player1", player1);
        players.put("player2", player2);
        fileObject.put("players", players);
    }

    private void showAlertForPlayerNames() {

        TextInputDialog playerNameDialog = new TextInputDialog();
        playerNameDialog.setTitle("Enter Player Names");
        playerNameDialog.setHeaderText("Please enter names for both players.");

        playerNameDialog.getEditor().setPromptText("Player 1 Name");

        Optional<String> player1NameInput = playerNameDialog.showAndWait();
        if (player1NameInput.isPresent() && !player1NameInput.get().isEmpty()) {
            this.player1Name = player1NameInput.get();
        }

        playerNameDialog.getEditor().setPromptText("Player 2 Name");
        playerNameDialog.getEditor().clear();
        Optional<String> player2NameInput = playerNameDialog.showAndWait();
        if (player2NameInput.isPresent() && !player2NameInput.get().isEmpty()) {
            this.player2Name = player2NameInput.get();
        }
    }

    private void displayVideo(String videoUrl) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("video.fxml"));
            Parent root = loader.load();

            VideoController controller = loader.getController();
            controller.setStage(stage);
            controller.setPreviousScene(stage.getScene());
            controller.setController(this);
            controller.setVideoUrl(videoUrl);

            stage.setScene(new Scene(root));
        } catch (IOException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR, "An error occurred, please try again", ButtonType.OK);
            alert.showAndWait();
        }
    }

    private void handleWinner(String winner) {
        String videoUrl;
        if (winner != null && !winner.isEmpty()) {
            stage.setTitle(winner + " is the winner");
            videoUrl = "/Assets/winner.mp4";
        } else {
            stage.setTitle("It's a Tie!");
            videoUrl = "/Assets/tie.mp4";
        }
        displayVideo(videoUrl);
    }

    public void askReplay() {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Do you want to play again?", ButtonType.YES, ButtonType.NO);
        alert.setTitle("Play Again?");
        alert.showAndWait().ifPresent(response -> {
            if (response == ButtonType.YES) {
                resetGame();
            } else {
                endHandeler();
            }
        });
    }

    public void startGame() {
        showAlertForPlayerNames();
        fileObject = new JSONObject();
        moves = new JSONArray();
        namesLabel.setText(player1Name + " VS " + player2Name);
        resetGame();
    }
}
