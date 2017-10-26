package by.javafx.communalPayments.controllers.counters;

import by.javafx.communalPayments.controllers.MainController;
import by.javafx.communalPayments.objects.Counters;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;

public class CounterDeleteController extends MainController {
    private MainController mainController;
    private Counters object;

    @FXML
    private Label nameDeleteObject;
    @FXML
    private Button btnCancel;

    public CounterDeleteController(MainController mainController) {
        this.mainController = mainController;
    }

    public CounterDeleteController() {
    }

    @FXML
    public void initialize() {
        object = (Counters) mainController.getSelectedObject();
        nameDeleteObject.setText("\"" + object.getCounterName() + "\"");
    }

    @FXML
    public void btnOkClicked() {

        if (!objectDelete(object)) {
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
