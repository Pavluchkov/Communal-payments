package by.javafx.communalPayments.controllers.services;

import by.javafx.communalPayments.controllers.MainController;
import by.javafx.communalPayments.representationObjects.FormPayments;
import by.javafx.communalPayments.representationObjects.Services;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class ServiceChangeController extends MainController {
    private Services object;
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

    public ServiceChangeController(MainController mainController) {
        this.mainController = mainController;
    }

    public ServiceChangeController() {
    }

    @FXML
    public void initialize() {
        object = (Services) mainController.getSelectedObject();

        tableForm = database.getTableFormPayments();
        ObservableList<String> formPayments = FXCollections.observableArrayList();

        for (FormPayments obj : tableForm) {
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
        double rate;

        if (serviceName.isEmpty()) {
            printDialogError("Ввод данных", "Ошибка ввода данных !", "Введите имя услуги.");
            return;
        }

        try {
            rate = Double.parseDouble(rateField.getText());
        } catch (NumberFormatException e) {
            printDialogError("Ввод данных", "Ошибка ввода данных !", e.getMessage());
            return;
        }

        int formId = 0;
        int serviceId = object.getId();

        for (FormPayments obj : tableForm) {
            if (obj.getForm().equals(formPaymentCmb.getSelectionModel().getSelectedItem())) {
                formId = obj.getId();
            }
        }

        if (!database.changeService(new Services(serviceId, serviceName, unit, rate, formId))) {
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
