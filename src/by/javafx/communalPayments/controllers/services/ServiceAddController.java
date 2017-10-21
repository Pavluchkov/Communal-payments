package by.javafx.communalPayments.controllers.services;

import by.javafx.communalPayments.controllers.MainController;
import by.javafx.communalPayments.objects.FormPayments;
import by.javafx.communalPayments.objects.Services;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.sql.SQLException;

public class ServiceAddController extends MainController {
    private MainController mainController;
    private ObservableList<FormPayments> tableForm = FXCollections.observableArrayList();

    @FXML
    private TextField nameField;
    @FXML
    private TextField unitField;
    @FXML
    private TextField rateField;
    @FXML
    private ComboBox<String> formPaymentCmb;

    @FXML
    private Button btnCancel;

    public ServiceAddController(MainController mainController) {
        this.mainController = mainController;
    }

    public ServiceAddController() {
    }

    @FXML
    public void initialize() {

        try {
            tableForm = database.getListObjects(new FormPayments());
        } catch (SQLException e) {
            e.printStackTrace();
        }

        ObservableList<String> formPayments = FXCollections.observableArrayList();

        for (FormPayments obj : tableForm) {
            formPayments.add(obj.getForm());
        }

        formPaymentCmb.setItems(formPayments);
        formPaymentCmb.setValue(formPayments.get(0));

    }

    @FXML
    public void btnOkClicked() {

        String serviceName = nameField.getText();
        String unit = unitField.getText();
        double rate;

        if(rateField.getText().isEmpty()){
            rate = 0;
        } else rate = Double.parseDouble(rateField.getText());

        int formId = 0;

        for (FormPayments obj : tableForm) {
            if (obj.getForm().equals(formPaymentCmb.getSelectionModel().getSelectedItem())) {
                formId = obj.getId();
            }
        }

        try {
            database.add(new Services(0, serviceName, unit, rate, formId));
        } catch (SQLException e) {
            e.printStackTrace();
        }

        btnCancelClicked();
    }

    @FXML
    private void btnCancelClicked() {
        Stage stage = (Stage) btnCancel.getScene().getWindow();
        stage.close();
    }
}
