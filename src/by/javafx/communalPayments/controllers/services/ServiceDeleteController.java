package by.javafx.communalPayments.controllers.services;

import by.javafx.communalPayments.controllers.MainController;
import by.javafx.communalPayments.representationObjects.Services;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;

public class ServiceDeleteController extends MainController {
    private Services object;
    private MainController mainController;

    @FXML
    private Label nameDeleteObject;
    @FXML
    private Button btnCancel;

    public ServiceDeleteController(MainController mainController) {
        this.mainController = mainController;
    }

    public ServiceDeleteController() {
    }

    @FXML
    public void initialize() {
        object = (Services) mainController.getSelectedObject();
        nameDeleteObject.setText("\"" + object.getServiceName() + "\"");
    }

    @FXML
    public void btnOkClicked() {

        if (!database.deleteService(object)) {
            return;
        }

        btnCancelClicked();

    }

    @FXML
    private void btnCancelClicked() {
        Stage stage = (Stage) btnCancel.getScene().getWindow();
        stage.close();
    }

}
