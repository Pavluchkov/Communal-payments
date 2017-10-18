package by.javafx.communalPayments.controllers.counters;

import by.javafx.communalPayments.controllers.MainController;
import by.javafx.communalPayments.objects.Counters;
import by.javafx.communalPayments.objects.Measurement;
import by.javafx.communalPayments.objects.ObjectAccounting;
import by.javafx.communalPayments.objects.Services;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.sql.Date;
import java.sql.SQLException;
import java.time.LocalDate;

public class MeasureController extends MainController {
    private MainController mainController;
    private Counters object;

    @FXML
    private Label textLabel;
    @FXML
    private TextField objectField;
    @FXML
    private TextField serviceField;
    @FXML
    private TextField measureField;
    @FXML
    private DatePicker datePicker;
    @FXML
    private Button btnCancel;

    public MeasureController(MainController mainController) {
        this.mainController = mainController;
    }

    public MeasureController() {
    }

    @FXML
    public void initialize() {
        object = (Counters) mainController.getSelectedObject();
        textLabel.setText(object.getCounterName());

        ObservableList<ObjectAccounting> tableObject = FXCollections.observableArrayList();
        ObservableList<Services> tableService = FXCollections.observableArrayList();

        try {
            tableObject = database.getListObjects(new ObjectAccounting());
            tableService = database.getListObjects(new Services());

        } catch (SQLException e) {
            e.printStackTrace();
        }

        for (ObjectAccounting obj : tableObject) {

            if (obj.getId() == object.getObject()) {
                objectField.setText(obj.getObjectName());
            }
        }

        for (Services obj : tableService) {

            if (obj.getId() == object.getService()) {
                serviceField.setText(obj.getServiceName());
            }
        }

        objectField.setFocusTraversable(false);
        serviceField.setFocusTraversable(false);
        datePicker.setValue(LocalDate.now());
        measureField.setText(String.valueOf(object.getRecentMeasure()));
    }

    @FXML
    public void btnOkClicked() {
        int counter = object.getId();
        double measure = Double.parseDouble(measureField.getText());
        Date date = Date.valueOf(datePicker.getValue());

        Measurement measurement = new Measurement(0, counter, measure, date);
        object.setRecentMeasure(measure);

        try {
            database.add(measurement);
            database.change(object);
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
