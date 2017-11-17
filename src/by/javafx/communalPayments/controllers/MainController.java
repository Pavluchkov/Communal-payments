package by.javafx.communalPayments.controllers;

import by.javafx.communalPayments.controllers.counters.CounterAddController;
import by.javafx.communalPayments.controllers.counters.CounterChangeController;
import by.javafx.communalPayments.controllers.counters.CounterDeleteController;
import by.javafx.communalPayments.controllers.objectAccounting.ObjAddController;
import by.javafx.communalPayments.controllers.objectAccounting.ObjChangeController;
import by.javafx.communalPayments.controllers.objectAccounting.ObjDeleteController;
import by.javafx.communalPayments.controllers.payments.PaymentAddController;
import by.javafx.communalPayments.controllers.payments.PaymentsDeleteController;
import by.javafx.communalPayments.controllers.report.ReportController;
import by.javafx.communalPayments.controllers.services.ServiceAddController;
import by.javafx.communalPayments.controllers.services.ServiceChangeController;
import by.javafx.communalPayments.controllers.services.ServiceDeleteController;
import by.javafx.communalPayments.interfaces.observerInterfaces.Observer;
import by.javafx.communalPayments.objects.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.PieChart;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.Window;

import java.sql.Date;
import java.time.LocalDate;
import java.time.format.TextStyle;
import java.util.Collections;
import java.util.Locale;

public class MainController implements Observer {

    protected Database database = new Database(this);
    private MyObjects selectedObject;
    private ReportController report;

    @FXML
    private TabPane tabPane;
    @FXML
    private PieChart pieChart;
    @FXML
    private BarChart<String, Double> barChart;
    @FXML
    private ComboBox<String> reportObjCombo;
    @FXML
    private ComboBox<String> reportMonthCombo;
    @FXML
    private ComboBox<String> reportYearCombo;

    @FXML
    private TableView<ObjectAccounting> T1_objAccounting;
    @FXML
    private TableColumn<ObjectAccounting, Integer> T1_personalAccountColumn;
    @FXML
    private TableColumn<ObjectAccounting, String> T1_nameObjColumn;
    @FXML
    private TableColumn<ObjectAccounting, String> T1_ownerColumn;
    @FXML
    private TableColumn<ObjectAccounting, String> T1_addressColumn;
    @FXML
    private TableColumn<ObjectAccounting, Integer> T1_residentsColumn;
    @FXML
    private TableColumn<ObjectAccounting, Double> T1_areaColumn;

    @FXML
    private TableView<Counters> T2_counters;
    @FXML
    private TableColumn<Counters, Integer> T2_id_counterColumn;
    @FXML
    private TableColumn<Counters, Integer> T2_nameObjColumn;
    @FXML
    private TableColumn<Counters, Integer> T2_serviceColumn;
    @FXML
    private TableColumn<Counters, String> T2_counterNameColumn;
    @FXML
    private TableColumn<Counters, Double> T2_recentMeasureColumn;

    @FXML
    private TableView<Services> T3_service;
    @FXML
    private TableColumn<Services, Integer> T3_id_serviceColumn;
    @FXML
    private TableColumn<Services, String> T3_serviceNameColumn;
    @FXML
    private TableColumn<Services, String> T3_unitColumn;
    @FXML
    private TableColumn<Services, Double> T3_rateColumn;
    @FXML
    private TableColumn<Services, Integer> T3_formPaymentsColumn;

    @FXML
    private TableView<Payments> T4_payments;
    @FXML
    private TableColumn<Payments, Integer> T4_id_paymentsColumn;
    @FXML
    private TableColumn<Payments, Integer> T4_objectColumn;
    @FXML
    private TableColumn<Payments, Integer> T4_serviceColumn;
    @FXML
    private TableColumn<Payments, String> T4_unitColumn;
    @FXML
    private TableColumn<Payments, Double> T4_volumeColumn;
    @FXML
    private TableColumn<Payments, Double> T4_rateColumn;
    @FXML
    private TableColumn<Payments, Double> T4_accruedColumn;
    @FXML
    private TableColumn<Payments, Double> T4_paidColumn;
    @FXML
    private TableColumn<Payments, Date> T4_dateColumn;

    @FXML
    private void initialize() {

        database.registerObserver(this);
        this.report = new ReportController(this);

        T1_personalAccountColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        T1_nameObjColumn.setCellValueFactory(new PropertyValueFactory<>("objectName"));
        T1_ownerColumn.setCellValueFactory(new PropertyValueFactory<>("owner"));
        T1_addressColumn.setCellValueFactory(new PropertyValueFactory<>("address"));
        T1_residentsColumn.setCellValueFactory(new PropertyValueFactory<>("residents"));
        T1_areaColumn.setCellValueFactory(new PropertyValueFactory<>("area"));

        T2_id_counterColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        T2_nameObjColumn.setCellValueFactory(new PropertyValueFactory<>("object"));
        T2_serviceColumn.setCellValueFactory(new PropertyValueFactory<>("service"));
        T2_counterNameColumn.setCellValueFactory(new PropertyValueFactory<>("counterName"));
        T2_recentMeasureColumn.setCellValueFactory(new PropertyValueFactory<>("recentMeasure"));

        T3_id_serviceColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        T3_serviceNameColumn.setCellValueFactory(new PropertyValueFactory<>("serviceName"));
        T3_unitColumn.setCellValueFactory(new PropertyValueFactory<>("unit"));
        T3_rateColumn.setCellValueFactory(new PropertyValueFactory<>("rate"));
        T3_formPaymentsColumn.setCellValueFactory(new PropertyValueFactory<>("formPayments"));

        T4_id_paymentsColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        T4_objectColumn.setCellValueFactory(new PropertyValueFactory<>("object"));
        T4_serviceColumn.setCellValueFactory(new PropertyValueFactory<>("service"));
        T4_unitColumn.setCellValueFactory(new PropertyValueFactory<>("unit"));
        T4_volumeColumn.setCellValueFactory(new PropertyValueFactory<>("volume"));
        T4_rateColumn.setCellValueFactory(new PropertyValueFactory<>("rate"));
        T4_accruedColumn.setCellValueFactory(new PropertyValueFactory<>("accrued"));
        T4_paidColumn.setCellValueFactory(new PropertyValueFactory<>("paid"));
        T4_dateColumn.setCellValueFactory(new PropertyValueFactory<>("date"));

        reportObjCombo.valueProperty().addListener((observable, oldValue, newValue) -> drawCharts());
        reportMonthCombo.valueProperty().addListener((observable, oldValue, newValue) -> drawCharts());
        reportYearCombo.valueProperty().addListener((observable, oldValue, newValue) -> drawCharts());

        database.setConnection();
        fillTables();
        chartsInitialize();

    }

    @FXML
    public void objAccountAdd() {

        dialogWindow(tabPane.getScene().getWindow(), new ObjAddController(), "/by/javafx/communalPayments/fxml/objAccountDialog/objectAccountAdd.fxml",
                "Добавление объекта учета", 565, 350);

    }

    @FXML
    public void objAccountChange() {

        ObjectAccounting object = T1_objAccounting.getSelectionModel().getSelectedItem();

        if (object != null) {
            setSelectedObject(object);
            dialogWindow(tabPane.getScene().getWindow(), new ObjChangeController(this), "/by/javafx/communalPayments/fxml/objAccountDialog/objectAccountAdd.fxml",
                    "Изменение объекта учета", 565, 350);
        } else {
            printDialogError("Изменение объекта", "Не выбран изменяемый объект !", "Выберите объект для изменения.");
        }

    }

    @FXML
    public void objAccountDelete() {

        ObjectAccounting object = T1_objAccounting.getSelectionModel().getSelectedItem();

        if (object != null) {
            setSelectedObject(object);
            dialogWindow(tabPane.getScene().getWindow(), new ObjDeleteController(this), "/by/javafx/communalPayments/fxml/deleteObject.fxml",
                    "Удаление объекта", 380, 190);
        } else {
            printDialogError("Удаление объекта", "Ошибка удаления объекта !", "Не выбран удаляемый объект ! ");
        }
    }

    @FXML
    public void countersAdd() {

        if (checkAvailabilityObjects() && checkAvailabilityServices() && checkAvailabilityServicesForm()) {
            dialogWindow(tabPane.getScene().getWindow(), new CounterAddController(), "/by/javafx/communalPayments/fxml/countersDialog/CountersAdd.fxml",
                    "Добавление счетчика", 400, 305);
        }

    }

    @FXML
    public void countersChange() {

        Counters object = T2_counters.getSelectionModel().getSelectedItem();

        if (object != null) {
            setSelectedObject(object);
            dialogWindow(tabPane.getScene().getWindow(), new CounterChangeController(this), "/by/javafx/communalPayments/fxml/countersDialog/CountersAdd.fxml",
                    "Изменение счетчика", 400, 305);
        } else {
            printDialogError("Изменение объекта", "Ошибка изменения объекта !", "Не выбран изменяемый объект ! ");
        }

    }

    @FXML
    public void countersDelete() {

        Counters object = T2_counters.getSelectionModel().getSelectedItem();

        if (object != null) {
            setSelectedObject(object);
            dialogWindow(tabPane.getScene().getWindow(), new CounterDeleteController(this), "/by/javafx/communalPayments/fxml/deleteObject.fxml",
                    "Удаление объекта", 380, 190);
        } else {
            printDialogError("Удаление объекта", "Ошибка удаления объекта !", "Не выбран удаляемый объект ! ");
        }

    }

    @FXML
    public void serviceAdd() {

        dialogWindow(tabPane.getScene().getWindow(), new ServiceAddController(), "/by/javafx/communalPayments/fxml/servicesDialog/servicesAdd.fxml",
                "Добавление услуги", 450, 305);
    }

    @FXML
    public void serviceChange() {

        Services object = T3_service.getSelectionModel().getSelectedItem();

        if (object != null) {
            setSelectedObject(object);
            dialogWindow(tabPane.getScene().getWindow(), new ServiceChangeController(this), "/by/javafx/communalPayments/fxml/servicesDialog/servicesAdd.fxml",
                    "Изменение услуги", 450, 305);
        } else {
            printDialogError("Изменение объекта", "Ошибка изменения объекта!", "Не выбран изменяемый объект ! ");
        }

    }

    @FXML
    public void serviceDelete() {

        Services object = T3_service.getSelectionModel().getSelectedItem();

        if (object != null) {
            setSelectedObject(object);
            dialogWindow(tabPane.getScene().getWindow(), new ServiceDeleteController(this), "/by/javafx/communalPayments/fxml/deleteObject.fxml",
                    "Удаление объекта", 380, 190);
        } else {
            printDialogError("Удаление объекта", "Ошибка удаления объекта!", "Не выбран удаляемый объект ! ");
        }
    }

    @FXML
    public void paymentAdd() {

        if (checkAvailabilityObjects() && checkAvailabilityServices()) {
            dialogWindow(tabPane.getScene().getWindow(), new PaymentAddController(), "/by/javafx/communalPayments/fxml/paymentsDialog/PaymentsAdd.fxml",
                    "Добавление платежа", 400, 260);
        }

    }

    @FXML
    public void paymentDelete() {

        Payments object = T4_payments.getSelectionModel().getSelectedItem();

        if (object != null) {
            setSelectedObject(object);
            dialogWindow(tabPane.getScene().getWindow(), new PaymentsDeleteController(this), "/by/javafx/communalPayments/fxml/deleteObject.fxml",
                    "Удаление объекта", 380, 190);
        } else {
            printDialogError("Удаление объекта", "Ошибка удаления объекта!", "Не выбран удаляемый объект ! ");
        }
    }

    private void fillTables() {

        T1_objAccounting.setItems(database.getTableObject());
        T2_counters.setItems(database.getTableCounters());
        T3_service.setItems(database.getTableServices());
        T4_payments.setItems(database.getTablePayments());

    }

    private void chartsInitialize() {

        ObservableList<ObjectAccounting> tableObject = database.getTableObject();
        ObservableList<String> listObjects = FXCollections.observableArrayList();

        for (ObjectAccounting obj : tableObject) {
            listObjects.add(obj.getObjectName());
        }

        if (!listObjects.isEmpty()) {
            reportObjCombo.setItems(listObjects);
            reportObjCombo.setValue(listObjects.get(0));
        }

        ObservableList<Payments> tablePayments = database.getTablePayments();
        ObservableList<String> listMonth = FXCollections.observableArrayList();
        ObservableList<String> listYear = FXCollections.observableArrayList();

        Locale local = new Locale("ru", "RU");

        for (Payments obj : tablePayments) {
            LocalDate date = obj.getDate().toLocalDate();
            String month = String.valueOf(date.getMonth().getDisplayName(TextStyle.FULL_STANDALONE, local));

            if (listMonth.indexOf(month) == -1) {
                listMonth.add(month);
            }

            if (listYear.indexOf(String.valueOf(date.getYear())) == -1) {
                listYear.add(String.valueOf(date.getYear()));
            }
        }

        if (!listMonth.isEmpty()) {
            listMonth.sort(Collections.reverseOrder());
            reportMonthCombo.setItems(listMonth);
            reportMonthCombo.setValue(listMonth.get(0));
        }

        if (!listYear.isEmpty()) {
            Collections.sort(listYear);
            reportYearCombo.setItems(listYear);
            reportYearCombo.setValue(listYear.get(0));
        }

    }

    private void drawCharts() {

        report.drawPieChart();
        report.drawBarChart();
    }

    private boolean checkAvailabilityObjects() {

        if (database.getTableObject().isEmpty()) {
            printDialogError("Добавление объекта", "Объект не может быть добавлен !",
                    "  Отсутствуют объекты учета.");
            return false;
        }

        return true;
    }

    private boolean checkAvailabilityServices() {

        if (database.getTableServices().isEmpty()) {
            printDialogError("Добавление объекта", "Объект не может быть добавлен !",
                    "  Для добавления необходимо наличие услуг.");
            return false;
        }

        return true;
    }

    private boolean checkAvailabilityServicesForm() {

        boolean flag = false;

        for (Services obj : database.getTableServices()) {

            if (obj.getFormPayments() == 1) {
                flag = true;
            }
        }

        if (!flag) {
            printDialogError("Добавление объекта", "Объект не может быть добавлен !",
                    "Отсутствуют услуги с формой оплаты по счетчику.");
            return false;
        }

        return true;
    }

    protected void dialogWindow(Window window, MainController controller, String resource, String title, int width, int height) {

        Stage stage = new Stage();
        FXMLLoader fxmlLoader = new FXMLLoader();
        fxmlLoader.setLocation(getClass().getResource(resource));
        fxmlLoader.setController(controller);

        try {
            Parent root = fxmlLoader.load();
            stage.setTitle(title);
            stage.setScene(new Scene(root, width, height));
            stage.initModality(Modality.WINDOW_MODAL);
            stage.initOwner(window);
            javafx.scene.image.Image ico = new Image("/by/javafx/communalPayments/ico/icon.png");
            stage.getIcons().add(ico);
            stage.setResizable(false);
            stage.show();
        } catch (Throwable e) {
            printDialogError("Диалоговые окна", "Ошибка создания диалогового окна !", e.getMessage());
        }

    }

    public void printDialogError(String title, String headerText, String contentText) {

        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(headerText);
        alert.setContentText(contentText);
        Stage stage = (Stage) alert.getDialogPane().getScene().getWindow();
        stage.getIcons().add(new Image("/by/javafx/communalPayments/ico/icon.png"));
        alert.showAndWait();
        alert.close();
    }

    public MyObjects getSelectedObject() {
        return selectedObject;
    }

    private void setSelectedObject(MyObjects object) {
        selectedObject = object;
    }

    public TabPane getTabPane() {
        return tabPane;
    }

    public PieChart getPieChart() {
        return pieChart;
    }

    public BarChart<String, Double> getBarChart() {
        return barChart;
    }

    public ComboBox<String> getReportObjCombo() {
        return reportObjCombo;
    }

    public ComboBox<String> getReportMonthCombo() {
        return reportMonthCombo;
    }

    public ComboBox<String> getReportYearCombo() {
        return reportYearCombo;
    }

    @Override
    public void update() {
        fillTables();
        chartsInitialize();
    }

}