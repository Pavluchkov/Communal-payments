package by.javafx.communalPayments.controllers.payments;

import by.javafx.communalPayments.controllers.MainController;
import by.javafx.communalPayments.controllers.counters.MeasureController;
import by.javafx.communalPayments.objectsPerfomance.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

import java.sql.Date;
import java.time.LocalDate;
import java.util.ArrayList;

public class PaymentAddController extends MainController {

    private Payments payment = new Payments();

    private ObservableList<ObjectAccounting> tableObject = FXCollections.observableArrayList();
    private ObservableList<Services> tableService = FXCollections.observableArrayList();
    private ObservableList<String> listObjects = FXCollections.observableArrayList();
    private ObservableList<String> listServices = FXCollections.observableArrayList();
    private ArrayList<Counters> listCounters = new ArrayList<>();
    private ArrayList<Measurement> listMeasure = new ArrayList<>();
    private ArrayList<Counters> newListCounters = new ArrayList<>();
    private double sum;
    private double area;
    private int residents;
    private String serviceComboValue;

    @FXML
    private ComboBox<String> objectCombo;
    @FXML
    private ComboBox<String> serviceCombo;
    @FXML
    private TextField sumField;
    @FXML
    private Button btnMeasure;
    @FXML
    private Button btnOk;
    @FXML
    private Button btnCancel;
    @FXML
    private Button btnCancelMeasure;
    @FXML
    private HBox layout_1;
    @FXML
    private HBox layout_2;

    @FXML
    public void initialize() {

        tableObject = database.getTableObject();
        tableService = database.getTableServices();

        for (ObjectAccounting obj : tableObject) {
            listObjects.add(obj.getObjectName());
        }

        for (Services obj : tableService) {
            listServices.add(obj.getServiceName());
        }

        objectCombo.valueProperty().addListener((observable, oldValue, newValue) -> checkCombo(serviceComboValue));

        serviceCombo.valueProperty().addListener((observable, oldValue, newValue) -> {
            serviceComboValue = newValue;
            checkCombo(serviceComboValue);
        });

        objectCombo.setItems(listObjects);
        serviceCombo.setItems(listServices);
        objectCombo.setValue(listObjects.get(0));
        serviceCombo.setValue(listServices.get(0));

    }

    @FXML
    public void btnOkClicked() {

        for (Counters obj : newListCounters) {

            if (!database.changeCounter(obj)) {
                return;
            }
        }

        for (Measurement obj : listMeasure) {

            if (!database.addMeasurement(obj)) {
                return;
            }
        }

        if (listMeasure.size() != 0) {
            payment.setAccrued(Math.rint(sum * 100) / 100);

            try {
                payment.setPaid(Double.parseDouble(sumField.getText()));
            } catch (NumberFormatException e) {
                printDialogError("Ввод данных", "Ошибка ввода данных !", e.getMessage());
                return;
            }

            for (Services obj : tableService) {
                if (payment.getService() == obj.getId()) {
                    payment.setUnit(obj.getUnit());
                }

            }

        } else {

            try {
                payment.setPaid(Double.parseDouble(sumField.getText()));
                payment.setDate(Date.valueOf(LocalDate.now()));
            } catch (NumberFormatException e) {
                printDialogError("Ввод данных", "Ошибка ввода данных !", e.getMessage());
                return;
            }

        }

        if (!database.addPayment(payment)) {
            return;
        }

        sum = 0;
        btnCancelClicked();
    }

    @FXML
    public void inputMeasureClicked() {

        fillListCounters();

        if (listCounters.size() != 0) {

            for (Counters obj : listCounters) {
                dialogWindow(serviceCombo.getScene().getWindow(), new MeasureController(this, obj), "/by/javafx/communalPayments/fxml/countersDialog/inputMeasure.fxml",
                        "Внесение показаний счетчика", 430, 255);
            }

        } else {
            printDialogError("Счетчики", "Ошибка внесения показаний!",
                    "Отсутствуют счетчики для данного объекта учета.\nВ разделе <Счетчики> необходимо добавить счетчик.");
        }

        listCounters.clear();

    }

    @FXML
    public void btnCancelClicked() {

        Stage stage = (Stage) btnCancel.getScene().getWindow();
        stage.close();
    }

    private void checkCombo(String newValue) {

        for (Services obj : tableService) {

            if (obj.getServiceName().equals(newValue)) {
                double rate = obj.getRate();
                payment.setRate(obj.getRate());
                payment.setService(obj.getId());
                payment.setUnit(obj.getUnit());

                if (obj.getFormPayments() == 1) {
                    sum = 0;
                    sumField.setText("");
                    setLayout(false);
                } else {
                    setLayout(true);

                    for (ObjectAccounting objectAccounting : tableObject) {

                        if (objectAccounting.getObjectName().equals(objectCombo.getValue())) {
                            area = objectAccounting.getArea();
                            residents = objectAccounting.getResidents();
                            payment.setObject(objectAccounting.getId());

                        }

                    }

                    if (obj.getFormPayments() == 2) {
                        sum = Math.rint(rate * area * 100) / 100;
                        payment.setVolume(area);
                        payment.setAccrued(sum);
                        sumField.setText(String.valueOf(sum));
                    }

                    if (obj.getFormPayments() == 3) {
                        sum = Math.rint(rate * residents * 100) / 100;
                        payment.setVolume(residents);
                        payment.setAccrued(sum);
                        sumField.setText(String.valueOf(sum));
                    }
                }
            }

        }
    }

    private void fillListCounters() {

        ObjectAccounting objectAccounting = new ObjectAccounting();

        for (ObjectAccounting obj : tableObject) {

            if (objectCombo.getValue().equals(obj.getObjectName())) {
                objectAccounting = obj;
            }
        }

        Services services = new Services();

        for (Services obj : tableService) {
            if (serviceCombo.getValue().equals(obj.getServiceName())) {
                services = obj;
            }
        }

        ObservableList<Counters> tableCounters;

        tableCounters = database.getTableCounters();

        for (Counters obj : tableCounters) {

            if ((obj.getObject() == objectAccounting.getId()) && (obj.getService() == services.getId())) {
                listCounters.add(obj);
            }
        }
    }

    public void setCounter(Counters counter) {
        newListCounters.add(counter);
    }

    public void setMeasure(Measurement measure) {
        listMeasure.add(measure);
    }

    public void setLayout(boolean b) {

        if (b) {
            layout_1.setVisible(true);
            layout_2.setVisible(false);
            sumField.setDisable(false);

        } else {
            layout_1.setVisible(false);
            layout_2.setVisible(true);
            sumField.setDisable(true);
        }
    }

    public void setSum(double summa) {

        this.sum += summa;
        sumField.setText(String.valueOf(Math.rint(sum * 100) / 100));
    }

    public double getRate(int serviceId) {

        double rate = 0;

        for (Services obj : tableService) {
            if (serviceId == obj.getId()) {
                rate = obj.getRate();
            }
        }

        return rate;
    }

    public void setPayment(Payments payment) {

        this.payment = payment;
    }

}
