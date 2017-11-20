package by.javafx.communalPayments.main;

import by.javafx.communalPayments.controllers.MainController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

public class Main extends Application {
    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        FXMLLoader fxmlLoader = new FXMLLoader();
        fxmlLoader.setLocation(getClass().getResource("/by/javafx/communalPayments/fxml/mainWindow.fxml"));
        fxmlLoader.setController(new MainController());
        Parent root = fxmlLoader.load();
        primaryStage.setTitle("Communal payments");
        primaryStage.setScene(new Scene(root, 1000, 600));
        primaryStage.setMinWidth(1000);
        primaryStage.setMinHeight(600);
        Image ico = new Image("/by/javafx/communalPayments/ico/icon.png");
        primaryStage.getIcons().add(ico);
        primaryStage.show();

    }
}
