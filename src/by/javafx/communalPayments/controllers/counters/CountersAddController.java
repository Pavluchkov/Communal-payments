package by.javafx.communalPayments.controllers.counters;

import by.javafx.communalPayments.controllers.Controller;
import by.javafx.communalPayments.objects.Counters;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.sql.SQLException;

public class CountersAddController extends Controller {
    private ObservableList<String> listObjects = FXCollections.observableArrayList();
    private ObservableList<String> listServices = FXCollections.observableArrayList();

    @FXML
    private ComboBox<String> objectCombo;
    @FXML
    private ComboBox<String> serviceCombo;
    @FXML
    private TextField nameField;
    @FXML
    private Button btnCancel;

    @FXML
    public void initialize(){
        try {
            this.listObjects = database.getColumn("accountingobject", "objectName");
            this.listServices = database.getColumn("services", "serviceName");
        } catch (SQLException e) {
            e.printStackTrace();
        }

       if((listObjects != null) && (listServices != null)){
            objectCombo.setItems(listObjects);
            serviceCombo.setItems(listServices);
        }
    }

    @FXML
    void btnOkClicked(){

        int selectedIndexObj = objectCombo.getSelectionModel().getSelectedIndex();
        int selectedIndexService = serviceCombo.getSelectionModel().getSelectedIndex();
        int personalAccount = 0;
        int serviceId = 0;

        try {
            personalAccount = Integer.parseInt(database.getValueColumn("accountingobject", "personalAccount", selectedIndexObj));
            serviceId = Integer.parseInt(database.getValueColumn("services", "id", selectedIndexService));
        } catch (SQLException e) {
            e.printStackTrace();
        }

        Counters counter = new Counters(0, nameField.getText(), serviceId, personalAccount);

        try {
            database.addCounter(counter);
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
