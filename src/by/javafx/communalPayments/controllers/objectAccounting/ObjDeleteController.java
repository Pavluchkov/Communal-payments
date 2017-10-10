package by.javafx.communalPayments.controllers.objectAccounting;

import by.javafx.communalPayments.controllers.Controller;
import by.javafx.communalPayments.objects.ObjectAccounting;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;

import java.sql.SQLException;

public class ObjDeleteController extends Controller {
    private ObjectAccounting objectAccounting;

    @FXML
    private Label nameDeleteObject;
    @FXML
    private Button btnCancel;

    public ObjDeleteController(ObjectAccounting objectAccounting) {
        this.objectAccounting = objectAccounting;
    }

    public ObjDeleteController() {
    }

    @FXML
    public void initialize() {
        nameDeleteObject.setText("\"" + objectAccounting.getObjectName() + "\"");
    }

    @FXML
    public void btnOkClicked() {

        if (objectAccounting != null) {
            try {
                database.deleteObject(objectAccounting);
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
