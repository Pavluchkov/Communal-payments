package by.javafx.communalPayments.controllers.objectAccounting;

import by.javafx.communalPayments.controllers.Controller;
import by.javafx.communalPayments.objects.ObjectAccounting;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.sql.SQLException;

public class ObjAccountController extends Controller {

    @FXML
    private TextField personalAccount;
    @FXML
    private TextField nameObject;
    @FXML
    private TextField owner;
    @FXML
    private TextField address;
    @FXML
    private TextField residents;
    @FXML
    private TextField area;

    @FXML
    private Button btnCancel;

    @FXML
    public void btnOkClicked() {

        ObjectAccounting object = new ObjectAccounting(Integer.parseInt(personalAccount.getText()), nameObject.getText(),
                owner.getText(), address.getText(), Integer.parseInt(residents.getText()), Double.parseDouble(area.getText()));

        try {
            database.addObject(object);
        } catch (SQLException e) {
            e.getMessage();
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
