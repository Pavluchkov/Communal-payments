package by.javafx.communalPayments.main;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("/by/javafx/communalPayments/fxml/mainWindow.fxml"));
        primaryStage.setTitle("Hello World");
        primaryStage.setScene(new Scene(root, 635, 300));
        primaryStage.setMinWidth(635);
        primaryStage.setMinHeight(300);
        Image ico = new Image("/by/javafx/communalPayments/ico/icon.png");
        primaryStage.getIcons().add(ico);
        //primaryStage.setResizable(false);
        primaryStage.show();
    }


    public static void main(String[] args) {
        launch(args);
    }
}
