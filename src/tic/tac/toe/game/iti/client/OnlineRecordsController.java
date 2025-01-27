package tic.tac.toe.game.iti.client;

import java.io.File;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class OnlineRecordsController  {
    
    Stage stage;
    @FXML
    private VBox filesList;
    @FXML
    private VBox buttonsList;
    public void setStage(Stage stage) {
        this.stage = stage;
        startController();
    }
    private void startController(){
        File files = new File("onlineRecords");
        File[] jsonFiles = files.listFiles((dir, name) -> name.toLowerCase().endsWith(".json"));
        for(File json : jsonFiles){
            Button button = new Button();
            button.setMnemonicParsing(false);
            button.getStyleClass().add("text-fileBtn-name");
            button.setText("Display Record");
            buttonsList.setMargin(button, new Insets(5.0, 0.0, 0.0, 0.0));
            button.setOnAction((event) -> {
                handleFileRecored(json);
            });
            buttonsList.getChildren().add(button);
            Text text = new Text();
            text.setStrokeType(javafx.scene.shape.StrokeType.OUTSIDE);
            text.setStrokeWidth(0.0);
            text.getStyleClass().add("text-file-name");
            text.setText(json.getName().substring(0, json.getName().length() - 5));
            text.setWrappingWidth(348.6708984375);
            filesList.setMargin(text, new Insets(10.0, 0.0, 4.0, 7.0));
            filesList.getChildren().add(text);
       }
    }

    private void handleFileRecored(File json) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("Record.fxml"));
            Parent root = loader.load();

            RecordController controller = loader.getController();
            controller.setStage(stage);
            controller.loadGameRecord(json);

            stage.setScene(new Scene(root));
            stage.setTitle("Record");
        } catch (IOException ex) {
            Logger.getLogger(HomePageController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void handleBackBtn(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/tic/tac/toe/game/iti/client/HomePage.fxml"));
            Parent root = loader.load();

            HomePageController controller = loader.getController();
            controller.setCurrentStage(stage);

            stage.setScene(new Scene(root));
            stage.setTitle("Home Page");
        } catch (IOException ex) {
            Logger.getLogger(OnlineRecordsController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @FXML
    private void handleBackIconBtn(MouseEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/tic/tac/toe/game/iti/client/HomePage.fxml"));
            Parent root = loader.load();

            HomePageController controller = loader.getController();
            controller.setCurrentStage(stage);

            stage.setScene(new Scene(root));
            stage.setTitle("Home Page");
        } catch (IOException ex) {
            Logger.getLogger(OnlineRecordsController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
}
