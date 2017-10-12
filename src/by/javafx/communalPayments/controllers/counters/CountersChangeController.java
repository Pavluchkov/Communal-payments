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

public class CountersChangeController extends MainController {
    private Counters object;
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

    public CountersChangeController(Counters object) {
        this.object = object;
    }

    public CountersChangeController() {
    }

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
            if (obj.getId() == object.getObject()) {
                objectCombo.setValue(obj.getObjectName());
            }
        }

        for (ServiceList obj : tableService) {
            listServices.add(obj.getServiceName());
            if (obj.getId() == object.getService()) {
                serviceCombo.setValue(obj.getServiceName());
            }
        }

        nameField.setText(object.getCounterName());
        objectCombo.setItems(listObjects);
        serviceCombo.setItems(listServices);
    }

    @FXML
    void btnOkClicked() {
        int id = object.getId();
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
            database.change(counter);
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
