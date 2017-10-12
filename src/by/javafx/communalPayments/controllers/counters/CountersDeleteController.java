package by.javafx.communalPayments.controllers.counters;

import by.javafx.communalPayments.controllers.MainController;
import by.javafx.communalPayments.objects.Counters;
import by.javafx.communalPayments.objects.ObjectAccounting;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;

import java.sql.SQLException;

public class CountersDeleteController extends MainController {
    private Counters object;

    @FXML
    private Label nameDeleteObject;
    @FXML
    private Button btnCancel;

    public CountersDeleteController(Counters object) {
        this.object = object;
    }

    public CountersDeleteController() {
    }

    @FXML
    public void initialize() {
        nameDeleteObject.setText("\"" + object.getCounterName() + "\"");
    }

    @FXML
    public void btnOkClicked() {

        if (object != null) {
            try {
                database.delete(object);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        btnCancelClicked();

    }

    @FXML
    public void btnCancelClicked() {

        Stage stage = new Stage();
        stage = (Stage) btnCancel.getScene().getWindow();
        stage.close();
    }
}
