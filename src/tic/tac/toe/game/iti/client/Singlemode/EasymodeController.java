package tic.tac.toe.game.iti.client.Singlemode;

import java.io.FileWriter;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import tic.tac.toe.game.iti.client.WelcomeController;

public class EasymodeController implements Initializable {

    Stage stage;
    FileWriter fw = null;

    @FXML
    private Text gameStatus;
    @FXML
    private Button buttonOne, buttonTwo, buttonThree, buttonFour, buttonFive, buttonSix, buttonSeven, buttonEight, buttonNine;

    private char[][] board = new char[3][3];
    private Button[][] buttons;
    private char currentPlayer = 'X'; 
    private boolean gameOver = false;

    @FXML
    private void buttonOneHandler(ActionEvent event) {
        handleMove(0, 0);
    }

    @FXML
    private void buttonTwoHandler(ActionEvent event) {
        handleMove(0, 1);
    }

    @FXML
    private void buttonThreeHandler(ActionEvent event) {
        handleMove(0, 2);
    }

    @FXML
    private void buttonFourHandler(ActionEvent event) {
        handleMove(1, 0);
    }

    @FXML
    private void buttonFiveHandler(ActionEvent event) {
        handleMove(1, 1);
    }

    @FXML
    private void buttonSixHandler(ActionEvent event) {
        handleMove(1, 2);
    }

    @FXML
    private void buttonSevenHandler(ActionEvent event) {
        handleMove(2, 0);
    }

    @FXML
    private void buttonEightHandler(ActionEvent event) {
        handleMove(2, 1);
    }

    @FXML
    private void buttonNineHandler(ActionEvent event) {
        handleMove(2, 2);
    }

    @FXML
    private void restartButtonHandler(ActionEvent event) {
        initializeGame();
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        buttons = new Button[][]{
            {buttonOne, buttonTwo, buttonThree},
            {buttonFour, buttonFive, buttonSix},
            {buttonSeven, buttonEight, buttonNine}
        };
        initializeGame();
    }

    private void initializeGame() {
        board = new char[3][3];
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                board[i][j] = ' ';
                buttons[i][j].setText("");
            }
        }
        currentPlayer = 'X';
        gameOver = false;
        gameStatus.setText("Game In Progress");
    }

    private void handleMove(int row, int col) {
        if (gameOver) {
            gameStatus.setText("Game Over! Restart to play again.");
            return;
        }

        if (board[row][col] == ' ') {
            makeMove(row, col, currentPlayer);
            if (checkWinner()) {
                gameStatus.setText(currentPlayer + " Wins!");
                gameOver = true;
            } else if (isBoardFull()) {
                gameStatus.setText("It's a Draw!");
                gameOver = true;
            } else {
                currentPlayer = (currentPlayer == 'X') ? 'O' : 'X';
                if (currentPlayer == 'O') {
                    computerMove();
                }
            }
        }
    }

    private void makeMove(int row, int col, char player) {
        board[row][col] = player;
        buttons[row][col].setText(String.valueOf(player));
    }

    private boolean checkWinner() {
        for (int i = 0; i < 3; i++) {
            if (board[i][0] == currentPlayer && board[i][1] == currentPlayer && board[i][2] == currentPlayer) {
                return true;
            }
            if (board[0][i] == currentPlayer && board[1][i] == currentPlayer && board[2][i] == currentPlayer) {
                return true;
            }
        }
        if (board[0][0] == currentPlayer && board[1][1] == currentPlayer && board[2][2] == currentPlayer) {
            return true;
        }
        if (board[0][2] == currentPlayer && board[1][1] == currentPlayer && board[2][0] == currentPlayer) {
            return true;
        }
        return false;
    }

    private boolean isBoardFull() {
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (board[i][j] == ' ') {
                    return false;
                }
            }
        }
        return true;
    }

    private void computerMove() {
        if (gameOver) {
            return;
        }
        int[] move = getRandomMove();
        makeMove(move[0], move[1], 'O');
        if (checkWinner()) {
            gameStatus.setText("Computer Wins!");
            gameOver = true;
        } else if (isBoardFull()) {
            gameStatus.setText("It's a Draw!");
            gameOver = true;
        } else {
            currentPlayer = 'X';
        }
    }

    private int[] getRandomMove() {
        int[] move = new int[2];
        do {
            move[0] = (int) (Math.random() * 3);
            move[1] = (int) (Math.random() * 3);
        } while (board[move[0]][move[1]] != ' ');
        return move;
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
}
