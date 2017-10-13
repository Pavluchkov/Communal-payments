package by.javafx.communalPayments.controllers.objectAccounting;

import by.javafx.communalPayments.controllers.MainController;
import by.javafx.communalPayments.objects.MyObjects;
import by.javafx.communalPayments.objects.ObjectAccounting;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.sql.SQLException;

public class ObjChangeController extends MainController {
    private ObjectAccounting objectAccounting;
    private MainController mainController;

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

    public ObjChangeController(MainController mainController) {
        this.mainController = mainController;
    }

    public ObjChangeController() {
    }

    @FXML
    public void initialize() {
        objectAccounting = (ObjectAccounting) mainController.getSelectedObject();
        personalAccount.setText(String.valueOf(objectAccounting.getId()));
        nameObject.setText(objectAccounting.getObjectName());
        owner.setText(objectAccounting.getOwner());
        address.setText(objectAccounting.getAddress());
        residents.setText(String.valueOf(objectAccounting.getResidents()));
        area.setText(String.valueOf(objectAccounting.getArea()));
    }

    @FXML
    public void btnOkClicked() {
        int id = objectAccounting.getId();

        objectAccounting.setId(Integer.parseInt(personalAccount.getText()));
        objectAccounting.setObjectName(nameObject.getText());
        objectAccounting.setOwner(owner.getText());
        objectAccounting.setAddress(address.getText());
        objectAccounting.setResidents(Integer.parseInt(residents.getText()));
        objectAccounting.setArea(Double.parseDouble(area.getText()));

        try {
            database.change(objectAccounting, id);
        } catch (SQLException e) {
            e.getMessage();
        }

        mainController.fillTable(objectAccounting);

        btnCancelClicked();

    }

    @FXML
    public void btnCancelClicked() {

        Stage stage = new Stage();
        stage = (Stage) btnCancel.getScene().getWindow();
        stage.close();
    }
}
