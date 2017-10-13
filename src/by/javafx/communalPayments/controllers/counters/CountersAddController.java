package by.javafx.communalPayments.controllers.counters;

import by.javafx.communalPayments.controllers.MainController;
import by.javafx.communalPayments.objects.Counters;
import by.javafx.communalPayments.objects.ObjectAccounting;
import by.javafx.communalPayments.objects.ServiceList;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.sql.SQLException;

public class CountersAddController extends MainController {
    private ObservableList<ObjectAccounting> tableObject = FXCollections.observableArrayList();
    private ObservableList<ServiceList> tableService = FXCollections.observableArrayList();

    @FXML
    private ComboBox<String> objectCombo;
    @FXML
    private ComboBox<String> serviceCombo;
    @FXML
    private TextField nameField;
    @FXML
    private Button btnCancel;

    @FXML
    public void initialize() {
        try {
            tableObject = database.getListObjects(new ObjectAccounting());
            tableService = database.getListObjects(new ServiceList());

        } catch (SQLException e) {
            e.printStackTrace();
        }

        ObservableList<String> listObjects = FXCollections.observableArrayList();
        ObservableList<String> listServices = FXCollections.observableArrayList();

        for (ObjectAccounting obj : tableObject) {
            listObjects.add(obj.getObjectName());
        }

        for (ServiceList obj : tableService) {

            if(obj.getFormPayments() == 1){
                listServices.add(obj.getServiceName());
            }

        }

        objectCombo.setItems(listObjects);
        serviceCombo.setItems(listServices);
        objectCombo.setValue(listObjects.get(0));
        serviceCombo.setValue(listServices.get(0));
    }

    @FXML
    void btnOkClicked() {
        int id = 0;
        int objectId = 0;
        int serviceId = 0;
        String counterName = nameField.getText();

        String selectedItemObj = objectCombo.getSelectionModel().getSelectedItem();
        String selectedItemService = serviceCombo.getSelectionModel().getSelectedItem();

        for (ObjectAccounting obj : tableObject) {
            if (selectedItemObj.equals(obj.getObjectName())) {
                objectId = obj.getId();
            }
        }

        for (ServiceList obj : tableService) {
            if (selectedItemService.equals(obj.getServiceName())) {
                serviceId = obj.getId();
            }
        }

        Counters counter = new Counters(id, counterName, serviceId, objectId);

        try {
            database.add(counter);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        btnCancelClicked();
    }

    @FXML
    void btnCancelClicked() {
        Stage stage = (Stage) btnCancel.getScene().getWindow();
        stage.close();
    }
}
