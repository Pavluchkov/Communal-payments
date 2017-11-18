package by.javafx.communalPayments.controllers.objectAccounting;

import by.javafx.communalPayments.controllers.MainController;
import by.javafx.communalPayments.objectsPerfomance.ObjectAccounting;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class ObjAddController extends MainController {

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
    public void initialize() {

    }

    @FXML
    public void btnOkClicked() {

        ObjectAccounting object = new ObjectAccounting();

        try {

            object.setId(Integer.parseInt(personalAccount.getText()));
            object.setObjectName(nameObject.getText());
            object.setOwner(owner.getText());
            object.setAddress(address.getText());
            object.setResidents(Integer.parseInt(residents.getText()));
            object.setArea(Double.parseDouble(area.getText()));

        } catch (NumberFormatException e) {
            printDialogError("Ввод данных", "Ошибка ввода данных !", e.getMessage());
            return;
        }

        if (!database.addObject(object)) {
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
