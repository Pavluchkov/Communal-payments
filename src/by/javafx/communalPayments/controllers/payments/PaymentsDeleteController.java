package by.javafx.communalPayments.controllers.payments;

import by.javafx.communalPayments.controllers.MainController;
import by.javafx.communalPayments.representationObjects.Payments;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;

public class PaymentsDeleteController extends MainController {

    private MainController mainController;
    private Payments object;

    @FXML
    private Label nameDeleteObject;
    @FXML
    private Button btnCancel;

    public PaymentsDeleteController(MainController mainController) {
        this.mainController = mainController;
    }

    @FXML
    public void initialize() {

        object = (Payments) mainController.getSelectedObject();
        nameDeleteObject.setText("\"Платеж № " + object.getId() + "\"");
    }

    @FXML
    public void btnOkClicked() {

        if (!database.deletePayment(object)) {
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
