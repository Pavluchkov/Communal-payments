package by.javafx.communalPayments.controllers;

import by.javafx.communalPayments.controllers.counters.CountersController;
import by.javafx.communalPayments.controllers.objectAccounting.ObjAccountController;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TabPane;
import javafx.scene.image.Image;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class MainController {
    @FXML
    private TabPane tabPane;

    @FXML
    public void objAccountAdd(){
        dialogWindow(new ObjAccountController(), "/by/javafx/communalPayments/fxml/objAccountDialog/addObjAccount.fxml",
                "Добавление объекта учета", 570, 310);
    }

    @FXML
    public void objAccountChange(){

    }
    @FXML
    public void objAccountDelete(){

    }

    @FXML
    public void countersAdd(){
        dialogWindow(new CountersController(), "/by/javafx/communalPayments/fxml/countersDialog/addCounters.fxml",
                  "Добавление счетчика", 520, 230);
    }

    @FXML
    public void countersChange(){
        dialogWindow(new CountersController(), "/by/javafx/communalPayments/fxml/countersDialog/changeCounters.fxml",
                "Изменение счетчика", 520, 230);
    }

    @FXML
    public void countersDelete(){
        dialogWindow(new CountersController(), "/by/javafx/communalPayments/fxml/countersDialog/deleteCounters.fxml",
                "Удаление счетчика", 450, 190);
    }

    @FXML
    public void servListAdd(){

    }

    @FXML
    public void servListChange() {

    }

    @FXML
    public void servListDelete(){

    }

    public void dialogWindow(Controller controller, String resource, String title, int width, int height){
        Stage stage = new Stage();
        FXMLLoader fxmlLoader = new FXMLLoader();
        fxmlLoader.setLocation(getClass().getResource(resource));
        fxmlLoader.setController(controller);
        Parent root = null;
        try {
            root = fxmlLoader.load();
        } catch (Throwable e) {
            System.out.println(e.getMessage());
        }
        stage.setTitle(title);
        stage.setScene(new Scene(root, width, height));
        stage.initModality(Modality.WINDOW_MODAL);
        stage.initOwner(tabPane.getScene().getWindow());
        javafx.scene.image.Image ico = new Image("/by/javafx/communalPayments/ico/icon.png");
        stage.getIcons().add(ico);
        stage.setResizable(false);
        stage.show();
    }
}
