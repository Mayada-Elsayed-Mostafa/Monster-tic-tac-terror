package tic.tac.toe.game.iti.client;

import java.awt.Color;
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
import javafx.scene.control.Label;
import javafx.scene.control.TextInputDialog;
import javafx.stage.Stage;
import javax.swing.plaf.ColorUIResource;

public class GameController {

    Stage stage;
    private int score1, score2 = 0;
    private int moveCount = 0;
    private String[][] board = new String[3][3];
    private boolean isPlayer1Turn = true;
    private String player1Name = "Player 1";
    private String player2Name = "Player 2";
    File file;

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

    public void setStage(Stage stage) {
        this.stage = stage;
        startGame();
    }

    FileWriter fw = null;

    String textOfFile = "";

    @FXML
    public void handleCellClick(ActionEvent event) {
        Button clickedButton = (Button) event.getSource();

        if (clickedButton.getText().isEmpty()) {
            if (isPlayer1Turn) {
                clickedButton.setStyle("-fx-text-fill: #D4A5A5;");
                clickedButton.setText("X");
                textOfFile = clickedButton.getId() + "," + player1Name + ",X";
                if (fw != null) {
                    try {
                        fw.append(textOfFile).append("\n");
                    } catch (IOException ex) {
                        ex.printStackTrace();
                    }
                } else {
                    System.out.println("FileWriter not initialized.");
                }
            } else {
                clickedButton.setStyle("-fx-text-fill: #497F5B;");
                clickedButton.setText("O");
                textOfFile = clickedButton.getId() + "," + player2Name + ",O";
                if (fw != null) {
                    try {
                        fw.append(textOfFile).append("\n");
                    } catch (IOException ex) {
                        ex.printStackTrace();
                    }
                } else {
                    System.out.println("FileWriter not initialized.");
                }
            }
            clickedButton.setDisable(true);

            moveCount++;
            if (checkWinner()) {
                String winner = isPlayer1Turn ? player1Name + " (X)" : player2Name + " (O)";
                System.out.println(winner + " wins!");
                if (player2Name.equals(winner)) {
                    score2 += 10;
                    score2Number.setText(String.valueOf(score2));
                } else if (player1Name.equals(winner)) {
                    score1 += 10;
                    score1Number.setText(String.valueOf(score1));
                }
                resetGame();
            } else if (moveCount == 9) {
                System.out.println("It's a tie!");
                resetGame();
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
    private void recordHandeler(ActionEvent event) {
        LocalDateTime now = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd_HH-mm-ss");
        String formattedDateTime = now.format(formatter);

        file = new File(player1Name + " VS " + player2Name + ".txt");

        try {
            fw = new FileWriter(file, true);
            fw.flush();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    @FXML
    private void endHandeler(ActionEvent event) {
        try {
            if (fw != null) {
                fw.close();
            }
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

    public void startGame() {
        showAlertForPlayerNames();
        namesLabel.setText(player1Name + " VS " + player2Name);
        resetGame();
    }
}
