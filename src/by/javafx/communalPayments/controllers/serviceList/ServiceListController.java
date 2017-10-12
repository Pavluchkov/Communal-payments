package by.javafx.communalPayments.controllers.serviceList;

import by.javafx.communalPayments.controllers.MainController;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.stage.Stage;

public class ServiceListController extends MainController {
    @FXML
    private Button btnCancel;

    @FXML
    public void btnCancelClicked(){
        Stage stage = new Stage();
        stage = (Stage)btnCancel.getScene().getWindow();
        stage.close();
    }
}
