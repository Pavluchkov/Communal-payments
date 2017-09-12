package by.javafx.communalPayments.controllers.counters;

import by.javafx.communalPayments.controllers.Controller;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.stage.Stage;

 public class CountersController extends Controller {

    @FXML
    private Button btnCancel;

    @FXML
    void btnCancelClicked(){
        Stage stage = (Stage) btnCancel.getScene().getWindow();
        stage.close();
    }
}
