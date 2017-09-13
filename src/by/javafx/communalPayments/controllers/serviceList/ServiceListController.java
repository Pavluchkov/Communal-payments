package by.javafx.communalPayments.controllers.serviceList;

import by.javafx.communalPayments.controllers.Controller;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.stage.Stage;

public class ServiceListController extends Controller {
    @FXML
    private Button btnCancel;

    @FXML
    public void btnCancelClicked(){
        Stage stage = new Stage();
        stage = (Stage)btnCancel.getScene().getWindow();
        stage.close();
    }
}
