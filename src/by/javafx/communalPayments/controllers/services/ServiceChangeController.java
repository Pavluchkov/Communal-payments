package by.javafx.communalPayments.controllers.services;

import by.javafx.communalPayments.controllers.MainController;
import by.javafx.communalPayments.objects.FormPayment;
import by.javafx.communalPayments.objects.Services;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.sql.SQLException;

public class ServiceChangeController extends MainController {
    private Services object;
    private MainController mainController;
    private ObservableList<FormPayment> tableForm = FXCollections.observableArrayList();

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

    public ServiceChangeController(MainController mainController) {
        this.mainController = mainController;
    }

    public ServiceChangeController() {
    }

    @FXML
    public void initialize() {
        object = (Services) mainController.getSelectedObject();

        try {
            tableForm = database.getListObjects(new FormPayment());
        } catch (SQLException e) {
            e.printStackTrace();
        }

        ObservableList<String> formPayments = FXCollections.observableArrayList();

        for (FormPayment obj : tableForm) {
            formPayments.add(obj.getForm());
            if (object.getFormPayments() == obj.getId()) {
                formPaymentCmb.setValue(obj.getForm());
            }
        }

        nameField.setText(object.getServiceName());
        unitField.setText(object.getUnit());
        rateField.setText(String.valueOf(object.getRate()));
        formPaymentCmb.setItems(formPayments);
    }

    @FXML
    public void btnOkClicked() {

        String serviceName = nameField.getText();
        String unit = unitField.getText();
        double rate = Double.parseDouble(rateField.getText());
        int formId = 0;
        int serviceId = object.getId();

        for (FormPayment obj : tableForm) {
            if (obj.getForm().equals(formPaymentCmb.getSelectionModel().getSelectedItem())) {
                formId = obj.getId();
            }
        }

        try {
            database.change(new Services(serviceId, serviceName, unit, rate, formId));
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
