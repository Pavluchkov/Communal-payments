package by.javafx.communalPayments.controllers.objectAccounting;

import by.javafx.communalPayments.controllers.MainController;
import by.javafx.communalPayments.objects.ObjectAccounting;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;

public class ObjDeleteController extends MainController {
    private ObjectAccounting objectAccounting;
    private MainController mainController;

    @FXML
    private Label nameDeleteObject;
    @FXML
    private Button btnCancel;

    public ObjDeleteController(MainController mainController) {
        this.mainController = mainController;
    }

    public ObjDeleteController() {
    }

    @FXML
    public void initialize() {
        objectAccounting = (ObjectAccounting) mainController.getSelectedObject();
        nameDeleteObject.setText("\"" + objectAccounting.getObjectName() + "\"");
    }

    @FXML
    public void btnOkClicked() {

        if (!objectDelete(objectAccounting)) {
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
