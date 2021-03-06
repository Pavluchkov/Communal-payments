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

public class ServiceAddController extends MainController {
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

    @FXML
    public void initialize() {

        tableForm = database.getTableFormPayments();

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

        for (FormPayments obj : tableForm) {
            if (obj.getForm().equals(formPaymentCmb.getSelectionModel().getSelectedItem())) {
                formId = obj.getId();
            }
        }

        if (!database.addService(new Services(0, serviceName, unit, rate, formId))) {
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
