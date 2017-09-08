package by.javafx.communalPayments.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.stage.Stage;

 class CountersController extends Controller{

    @FXML
    private Button btnCancel;

    @FXML
    void btnCancelClicked(){
        Stage stage = (Stage) btnCancel.getScene().getWindow();
        stage.close();
    }

}
