package by.javafx.communalPayments.controllers.counters;

import by.javafx.communalPayments.controllers.MainController;
import by.javafx.communalPayments.objects.Counters;
import by.javafx.communalPayments.objects.ObjectAccounting;
import by.javafx.communalPayments.objects.Services;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.sql.SQLException;

public class CounterChangeController extends MainController {
    private Counters object;
    private MainController mainController;
    private ObservableList<ObjectAccounting> tableObject = FXCollections.observableArrayList();
    private ObservableList<Services> tableService = FXCollections.observableArrayList();

    @FXML
    private ComboBox<String> objectCombo;
    @FXML
    private ComboBox<String> serviceCombo;
    @FXML
    private TextField nameField;
    @FXML
    private Button btnCancel;

    public CounterChangeController(MainController mainController) {
        this.mainController = mainController;
    }

    public CounterChangeController() {
    }

    @FXML
    public void initialize() {
        object = (Counters) mainController.getSelectedObject();

        try {
            tableObject = database.getListObjects(new ObjectAccounting());
            tableService = database.getListObjects(new Services());

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

        for (Services obj : tableService) {

            if (obj.getFormPayments() == 1) {
                listServices.add(obj.getServiceName());
            }

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

        for (Services obj : tableService) {
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

        mainController.fillTable(object);
        btnCancelClicked();
    }

    @FXML
    void btnCancelClicked() {
        Stage stage = (Stage) btnCancel.getScene().getWindow();
        stage.close();
    }
}
