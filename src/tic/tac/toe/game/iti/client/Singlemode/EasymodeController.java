package tic.tac.toe.game.iti.client.Singlemode;

import java.io.IOException;
import java.net.URL;
import java.util.Random;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
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
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import tic.tac.toe.game.iti.client.Controller;
import tic.tac.toe.game.iti.client.VideoController;

public class EasymodeController extends Controller implements Initializable {

    Stage stage;
    @FXML
    private Text gameStatus;
    @FXML
    private Button buttonOne, buttonTwo, buttonThree, buttonFour, buttonFive, buttonSix, buttonSeven, buttonEight, buttonNine;

    private char[][] board = new char[3][3];
    private Button[][] buttons;
    private char currentPlayer = 'X';
    private boolean gameOver = false;
    private String difficulty = "Easy";
    private final Random random = new Random();
    @FXML
    private Label player1;
    @FXML
    private Label player2;
    @FXML
    private ImageView endGameIconBtn;

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

    public void setStage(Stage stage) {
        this.stage = stage;
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

    public void setDifficulty(String difficulty) {
        this.difficulty = difficulty;
        stage.setTitle(difficulty + " Mode - Tic Tac Toe");
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
        if (!gameOver) {
            gameStatus.setText((currentPlayer == 'X' ? "Player's Turn" : "Computer's Turn") + " (" + currentPlayer + ")");
        }

        if (board[row][col] == ' ') {
            makeMove(row, col, currentPlayer);
            if (checkWinner(currentPlayer)) {
                gameStatus.setText(currentPlayer + " Wins!");
                if (currentPlayer == 'X') {
                    displayVideo("/Assets/winner.mp4");
                }

                gameOver = true;
            } else if (isBoardFull()) {
                gameStatus.setText("It's a Draw!");
                displayVideo("/Assets/tie.mp4");
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

    private boolean checkWinner(char player) {
        for (int i = 0; i < 3; i++) {
            if ((board[i][0] == player && board[i][1] == player && board[i][2] == player)
                    || (board[0][i] == player && board[1][i] == player && board[2][i] == player)) {
                return true;
            }
        }
        if ((board[0][0] == player && board[1][1] == player && board[2][2] == player)
                || (board[0][2] == player && board[1][1] == player && board[2][0] == player)) {
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
        int[] move;
        if (difficulty.equals("Easy")) {
            move = getRandomMove();
        } else if (difficulty.equals("Intermediate")) {
            move = random.nextInt(2) < 1 ? getOptimalMove() : getRandomMove();
        } else {
            move = getOptimalMove();
        }
        makeMove(move[0], move[1], 'O');
        if (checkWinner('O')) {
            displayVideo("/Assets/loser.mp4");
            gameStatus.setText("Computer Wins!");
            gameOver = true;
        } else {
            currentPlayer = 'X';
        }
    }

    private int[] getRandomMove() {
        int[] move = new int[2];
        do {
            move[0] = random.nextInt(3);
            move[1] = random.nextInt(3);
        } while (board[move[0]][move[1]] != ' ');
        return move;
    }

    private int[] getOptimalMove() {
        int bestScore = Integer.MIN_VALUE;
        int[] bestMove = {-1, -1};

        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (board[i][j] == ' ') {
                    board[i][j] = 'O';
                    int score = minimax(false);
                    board[i][j] = ' ';
                    if (score > bestScore) {
                        bestScore = score;
                        bestMove = new int[]{i, j};
                    }
                }
            }
        }
        return bestMove;
    }

    private int minimax(boolean isMaximizing) {
        if (checkWinner('O')) {
            return 1;
        }
        if (checkWinner('X')) {
            return -1;
        }
        if (isBoardFull()) {
            return 0;
        }
        int bestScore = isMaximizing ? Integer.MIN_VALUE : Integer.MAX_VALUE;
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (board[i][j] == ' ') {
                    board[i][j] = isMaximizing ? 'O' : 'X';
                    int score = minimax(!isMaximizing);
                    board[i][j] = ' ';
                    bestScore = isMaximizing ? Math.max(score, bestScore) : Math.min(score, bestScore);
                }
            }
        }
        return bestScore;
    }

    private void displayVideo(String videoUrl) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/tic/tac/toe/game/iti/client/video.fxml"));
            Parent root = loader.load();

            VideoController controller = loader.getController();
            controller.setStage(stage);
            controller.setPreviousScene(stage.getScene());
            controller.setController(this);
            controller.setVideoUrl(videoUrl);

            stage.setScene(new Scene(root));
        } catch (IOException e) {
            Alert alert = new Alert(Alert.AlertType.NONE, "An error occurred, please try again", ButtonType.OK);
            alert.showAndWait();
        }
    }

    @Override
    public void askReplay() {
        Alert alert = new Alert(Alert.AlertType.NONE, "Do you want to play again?", ButtonType.YES, ButtonType.NO);
        alert.setTitle("Play Again?");
        alert.showAndWait().ifPresent(response -> {
            if (response == ButtonType.YES) {
                initializeGame();
            } else {
                endHandeler();
            }
        });
    }

    private void endHandeler() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("Singlemode.fxml"));
            Parent root = loader.load();

            SinglemodeController controller = loader.getController();
            controller.setStage(stage);

            stage.setScene(new Scene(root));
            stage.setTitle("Single mode Page");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void endGameHandeler(MouseEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("Singlemode.fxml"));
            Parent root = loader.load();
            SinglemodeController controller = loader.getController();
            controller.setStage(stage);
            stage.setScene(new Scene(root));
            stage.setTitle("Single Mode Page");
        } catch (IOException ex) {
            Logger.getLogger(EasymodeController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @FXML
    private void restartGameHandeler(MouseEvent event) {
        initializeGame();
    }
}
