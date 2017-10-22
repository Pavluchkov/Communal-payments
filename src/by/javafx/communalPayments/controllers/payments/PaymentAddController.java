package by.javafx.communalPayments.controllers.payments;

import by.javafx.communalPayments.controllers.MainController;
import by.javafx.communalPayments.controllers.counters.MeasureController;
import by.javafx.communalPayments.objects.*;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

import java.sql.Date;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;

public class PaymentAddController extends MainController {
    private MainController mainController;
    private Payments payment = new Payments();

    private ObservableList<ObjectAccounting> tableObject = FXCollections.observableArrayList();
    private ObservableList<Services> tableService = FXCollections.observableArrayList();
    private ObservableList<String> listObjects = FXCollections.observableArrayList();
    private ObservableList<String> listServices = FXCollections.observableArrayList();

    private ObservableList<Counters> tableCounters = FXCollections.observableArrayList();
    private ArrayList<Counters> listCounters = new ArrayList<>();

    private ArrayList<Measurement> listMeasure = new ArrayList<>();
    private ArrayList<Counters> newListCounters = new ArrayList<>();
    private double sum;
    private double rate;
    private double area;
    private int residents;
    private int objectId;
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

    public PaymentAddController(MainController mainController) {
        this.mainController = mainController;
    }

    public PaymentAddController() {
    }

    @FXML
    public void initialize() {

        try {
            tableObject = database.getListObjects(new ObjectAccounting());
            tableService = database.getListObjects(new Services());

        } catch (SQLException e) {
            printDialogError("Работа с базой данных", "Ошибка чтения данных БД !", e.getMessage());
            return;
        }

        for (ObjectAccounting obj : tableObject) {
            listObjects.add(obj.getObjectName());
        }

        for (Services obj : tableService) {
            listServices.add(obj.getServiceName());
        }

        objectCombo.valueProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                checkCombo(serviceComboValue);
            }
        });

        serviceCombo.valueProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                serviceComboValue = newValue;
                checkCombo(serviceComboValue);
            }
        });

        objectCombo.setItems(listObjects);
        serviceCombo.setItems(listServices);
        objectCombo.setValue(listObjects.get(0));
        serviceCombo.setValue(listServices.get(0));

    }

    @FXML
    public void btnOkClicked() {
        for (Counters obj : newListCounters) {
            try {
                database.change(obj);
            } catch (SQLException e) {
                printDialogError("Работа с базой данных", "Ошибка записи данных в БД !", e.getMessage());
                return;
            }
        }

        for (Measurement obj : listMeasure) {
            try {
                database.add(obj);
            } catch (SQLException e) {
                printDialogError("Работа с базой данных", "Ошибка записи данных в БД !", e.getMessage());
                return;
            }
        }

        if (listMeasure.size() != 0) {
            payment.setAccrued(sum);

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

        try {
            database.add(payment);
        } catch (SQLException e) {
            printDialogError("Работа с базой данных", "Ошибка записи данных в БД !", e.getMessage());
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
            printDialogError("Внесение показаний счетчика", "Ошибка внесения показаний!",
                    "Отсутствуют счетчики для данного объекта учета.\nВ разделе <Счетчики> необходимо добавить счетчик.");
        }

        listCounters.clear();

    }

    @FXML
    public void btnCancelClicked() {
        Stage stage = (Stage) btnCancel.getScene().getWindow();
        stage.close();
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
        sumField.setText(String.valueOf(sum));
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

    private void checkCombo(String newValue) {
        for (Services obj : tableService) {

            if (obj.getServiceName().equals(newValue)) {
                rate = obj.getRate();
                payment.setRate(rate);
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
                            objectId = objectAccounting.getId();
                            area = objectAccounting.getArea();
                            residents = objectAccounting.getResidents();

                            payment.setObject(objectId);

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
                        sumField.setText(String.valueOf(sum));
                        payment.setVolume(residents);
                        payment.setAccrued(sum);
                    }
                }
            }

        }
    }

    private void fillListCounters() {
        ObjectAccounting objectAccounting = new ObjectAccounting();
        Services services = new Services();

        for (ObjectAccounting obj : tableObject) {
            if (objectCombo.getValue().equals(obj.getObjectName())) {
                objectAccounting = obj;
            }
        }

        for (Services obj : tableService) {
            if (serviceCombo.getValue().equals(obj.getServiceName())) {
                services = obj;
            }
        }

        try {
            tableCounters = database.getListObjects(new Counters());
        } catch (SQLException e) {
            printDialogError("Работа с базой данных", "Ошибка чтения данных из БД !", e.getMessage());
            return;
        }

        for (Counters obj : tableCounters) {
            if ((obj.getObject() == objectAccounting.getId()) && (obj.getService() == services.getId())) {
                listCounters.add(obj);
            }
        }
    }
}