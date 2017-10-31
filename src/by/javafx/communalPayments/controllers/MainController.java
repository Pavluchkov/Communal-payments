package by.javafx.communalPayments.controllers;

import by.javafx.communalPayments.controllers.counters.CounterAddController;
import by.javafx.communalPayments.controllers.counters.CounterChangeController;
import by.javafx.communalPayments.controllers.counters.CounterDeleteController;
import by.javafx.communalPayments.controllers.objectAccounting.ObjAddController;
import by.javafx.communalPayments.controllers.objectAccounting.ObjChangeController;
import by.javafx.communalPayments.controllers.objectAccounting.ObjDeleteController;
import by.javafx.communalPayments.controllers.payments.PaymentAddController;
import by.javafx.communalPayments.controllers.payments.PaymentsDeleteController;
import by.javafx.communalPayments.controllers.services.ServiceAddController;
import by.javafx.communalPayments.controllers.services.ServiceChangeController;
import by.javafx.communalPayments.controllers.services.ServiceDeleteController;
import by.javafx.communalPayments.interfaces.IDatabase;
import by.javafx.communalPayments.interfaces.Observer;
import by.javafx.communalPayments.interfaces.Subject;
import by.javafx.communalPayments.objects.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.chart.*;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.Window;

import java.sql.Date;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;

public class MainController implements Observer {

    private IDatabase database = MySQLDatabase.getInstance();
    private MyObjects selectedObject;

    @FXML
    private TabPane tabPane;
    @FXML
    private PieChart pieChart;
    @FXML
    private BarChart barChart;
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

        Subject subject = MySQLDatabase.getInstance();// Устанавливаем наблюдателя
        subject.registerObserver(this);    //      за MySQLDatabase

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

        reportObjCombo.valueProperty().addListener((observable, oldValue, newValue) -> fillCharts());
        reportMonthCombo.valueProperty().addListener((observable, oldValue, newValue) -> fillCharts());
        reportYearCombo.valueProperty().addListener((observable, oldValue, newValue) -> fillCharts());

        setConnection();
        fillTables();
        chartsInitialize();
    }

    private void chartsInitialize() {
        ObservableList<ObjectAccounting> tableObject = getTableObject(new ObjectAccounting());

        ObservableList<String> listObjects = FXCollections.observableArrayList();

        for (ObjectAccounting obj : tableObject) {
            listObjects.add(obj.getObjectName());
        }

        reportObjCombo.setItems(listObjects);
        reportObjCombo.setValue(listObjects.get(0));

        ObservableList<Payments> tablePayments = getTableObject(new Payments());
        ObservableList<String> listMonth = FXCollections.observableArrayList();
        ObservableList<String> listYear = FXCollections.observableArrayList();

        for (Payments obj : tablePayments) {
            LocalDate date = obj.getDate().toLocalDate();

            if (listMonth.indexOf(String.valueOf(date.getMonth())) == -1) {

                listMonth.add(String.valueOf(date.getMonth()));
            }

            if (listYear.indexOf(String.valueOf(date.getYear())) == -1) {

                listYear.add(String.valueOf(date.getYear()));
            }
        }

        if (!listMonth.isEmpty()) {
            reportMonthCombo.setItems(listMonth);
            reportMonthCombo.setValue(listMonth.get(0));
        }

        if (!listYear.isEmpty()) {
            reportYearCombo.setItems(listYear);
            reportYearCombo.setValue(listYear.get(0));
        }

    }

    private void fillCharts() {
        setPieData();
        setBarData();
    }

    private void setPieData() {

        ObservableList<Payments> tablePayments = getTableObject(new Payments());
        ObservableList<Services> tableServices = getTableObject(new Services());
        ObservableList<PieChart.Data> pieChartData = FXCollections.observableArrayList();
        double sum = 0;

        int objectId = 0;
        ObservableList<ObjectAccounting> tableObject = getTableObject(new ObjectAccounting());

        for (ObjectAccounting obj : tableObject) {
            if (obj.getObjectName().equals(reportObjCombo.getValue())) {
                objectId = obj.getId();
            }
        }

        ArrayList<Payments> payments = new ArrayList<>();
        String monthCombo = reportMonthCombo.getValue();
        String yearCombo = reportYearCombo.getValue();

        if ((monthCombo != null) && (yearCombo != null)) {
            for (Payments obj : tablePayments) {

                if (obj.getObject() == objectId) {
                    if (monthCombo.equals(String.valueOf(obj.getDate().toLocalDate().getMonth()))) {
                        if (yearCombo.equals(String.valueOf(obj.getDate().toLocalDate().getYear()))) {
                            sum += obj.getPaid();
                            payments.add(obj);
                        }
                    }
                }

            }
        }

        for (Payments obj : payments) {
            for (Services services : tableServices) {
                if (services.getId() == obj.getService()) {
                    pieChartData.add(new PieChart.Data(services.getServiceName(), obj.getPaid() * sum / 100));
                }
            }
        }

        pieChart.setData(pieChartData);

        if (pieChartData.isEmpty()) {
            pieChart.setTitle("Нет данных");
        } else {
            pieChart.setTitle(reportMonthCombo.getValue() + ", " + reportYearCombo.getValue());
        }

    }

    private void setBarData() {

        ObservableList<String> listYear = FXCollections.observableArrayList();
        ObservableList<Payments> tablePayments = getTableObject(new Payments());
        ObservableList<ObjectAccounting> tableObject = getTableObject(new ObjectAccounting());
        ObservableList<Services> tableServices = getTableObject(new Services());

        int objectId = 0;

        for (ObjectAccounting obj : tableObject) {
            if (reportObjCombo.getValue().equals(obj.getObjectName())) {
                objectId = obj.getId();
            }
        }

        for (Payments obj : tablePayments) {
            if (obj.getObject() == objectId) {
                if (listYear.indexOf(String.valueOf(obj.getDate().toLocalDate().getYear())) == -1) {
                    listYear.add(String.valueOf(obj.getDate().toLocalDate().getYear()));
                }

            }

        }

        ObservableList<XYChart.Series> barChartData = FXCollections.observableArrayList();
        final CategoryAxis xAxis = new CategoryAxis();
        final NumberAxis yAxis = new NumberAxis();

        xAxis.setLabel("Услуга");
        yAxis.setLabel("Сумма");

        for (String year : listYear) {
            double sum = 0;
            XYChart.Series series = new XYChart.Series();
            series.setName(year);

            for (Services service : tableServices) {
                for (Payments obj : tablePayments) {

                    if (year.equals(String.valueOf(obj.getDate().toLocalDate().getYear()))) {
                        if (obj.getService() == service.getId()) {
                            if (obj.getObject() == objectId) {
                                sum += obj.getPaid();
                            }
                        }
                    }
                }

                if (sum != 0) {
                    series.getData().add(new XYChart.Data(service.getServiceName(), sum));
                    sum = 0;
                }
            }

            barChartData.add(series);
        }

        if (barChartData.isEmpty()) {
            barChart.setTitle("Нет данных");
        } else {
            barChart.setTitle("Годовой");
        }

        barChart.setData(barChartData);
    }

    @FXML
    public void objAccountAdd() {

        dialogWindow(tabPane.getScene().getWindow(), new ObjAddController(), "/by/javafx/communalPayments/fxml/objAccountDialog/objectAccountAdd.fxml",
                "Добавление объекта учета", 565, 350);

    }

    @FXML
    public void objAccountChange() {

        MyObjects object = T1_objAccounting.getSelectionModel().getSelectedItem();

        if (object != null) {
            setSelectedObject(object);
            dialogWindow(tabPane.getScene().getWindow(), new ObjChangeController(this), "/by/javafx/communalPayments/fxml/objAccountDialog/objectAccountChange.fxml",
                    "Изменение объекта учета", 565, 350);
        } else {
            printDialogError("Изменение объекта", "Не выбран изменяемый объект !", "Выберите объект для изменения.");
        }

    }

    @FXML
    public void objAccountDelete() {

        MyObjects object = T1_objAccounting.getSelectionModel().getSelectedItem();

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
            dialogWindow(tabPane.getScene().getWindow(), new CounterChangeController(this), "/by/javafx/communalPayments/fxml/countersDialog/CountersChange.fxml",
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
            dialogWindow(tabPane.getScene().getWindow(), new ServiceChangeController(this), "/by/javafx/communalPayments/fxml/servicesDialog/servicesChange.fxml",
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

    @Override
    public void update() {
        fillTables();
        chartsInitialize();
    }

    private void setConnection() {

        try {
            database.setConnectDatabase("jdbc:mysql://localhost:3306");
        } catch (SQLException | ClassNotFoundException e) {
            printDialogError("Ошибка подключения", "Не удалось подключиться к серверу MySQL !", e.getMessage());
            System.exit(0);
        }

        try {
            database.availabilityCheckDatabase();
        } catch (SQLException e) {
            printDialogError("Ошибка подключения", "Не удалось подключиться к БД !", e.getMessage());
            System.exit(0);
        }

    }

    private void fillTables() {

        T1_objAccounting.setItems(getTableObject(new ObjectAccounting()));
        T2_counters.setItems(getTableObject(new Counters()));
        T3_service.setItems(getTableObject(new Services()));
        T4_payments.setItems(getTableObject(new Payments()));

    }

    private boolean checkAvailabilityObjects() {

        if (getTableObject(new ObjectAccounting()).isEmpty()) {
            printDialogError("Добавление объекта", "Объект не может быть добавлен !",
                    "  Отсутствуют объекты учета.");
            return false;
        }

        return true;
    }

    private boolean checkAvailabilityServices() {

        if (getTableObject(new Services()).isEmpty()) {
            printDialogError("Добавление объекта", "Объект не может быть добавлен !",
                    "  Для добавления необходимо наличие услуг.");
            return false;
        }

        return true;
    }

    private boolean checkAvailabilityServicesForm() {

        boolean flag = false;

        for (Services obj : getTableObject(new Services())) {

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

    public MyObjects getSelectedObject() {
        return selectedObject;
    }

    private void setSelectedObject(MyObjects object) {
        selectedObject = object;
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

    protected void printDialogError(String title, String headerText, String contentText) {

        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(headerText);
        alert.setContentText(contentText);
        Stage stage = (Stage) alert.getDialogPane().getScene().getWindow();
        stage.getIcons().add(new Image("/by/javafx/communalPayments/ico/icon.png"));
        alert.showAndWait();
        alert.close();
    }

    protected boolean objectAdd(MyObjects object) {

        try {

            if (object instanceof ObjectAccounting) {
                database.add((ObjectAccounting) object);
            }

            if (object instanceof Counters) {
                database.add((Counters) object);
            }

            if (object instanceof Services) {
                database.add((Services) object);
            }

            if (object instanceof Payments) {
                database.add((Payments) object);
            }

            if (object instanceof Measurement) {
                database.add((Measurement) object);
            }

        } catch (SQLException e) {
            printDialogError("Работа с базой данных", "Ошибка добавления данных в БД !", e.getMessage());
            return false;
        }

        return true;
    }

    protected boolean objectChange(MyObjects object) {

        try {

            if (object instanceof Counters) {
                database.change((Counters) object);
            }

            if (object instanceof Services) {
                database.change((Services) object);
            }

        } catch (SQLException e) {
            printDialogError("Работа с базой данных", "Ошибка изменения данных в counters or services !", e.getMessage());
            return false;
        }

        return true;
    }

    protected boolean objectChange(ObjectAccounting object, int id) {

        try {

            database.change(object, id);

        } catch (SQLException e) {
            printDialogError("Работа с базой данных", "Ошибка изменения данных в objectAccounting !", e.getMessage());
            return false;
        }
        return true;
    }

    protected boolean lastMeasureChange(Counters object, double lastMeasure) {

        try {

            database.changeLastMeasure(object, lastMeasure);

        } catch (SQLException e) {
            printDialogError("Работа с базой данных", "Ошибка изменения данных в measurement !", e.getMessage());
            return false;
        }

        return true;
    }

    protected boolean objectDelete(MyObjects object) {

        try {

            if (object instanceof ObjectAccounting) {
                database.delete((ObjectAccounting) object);
            }

            if (object instanceof Counters) {
                database.delete((Counters) object);
            }

            if (object instanceof Services) {
                database.delete((Services) object);
            }

            if (object instanceof Payments) {
                database.delete((Payments) object);
            }

        } catch (SQLException e) {
            printDialogError("Работа с базой данных", "Ошибка удаления данных из БД !", e.getMessage());
            return false;
        }

        return true;
    }

    protected ObservableList<ObjectAccounting> getTableObject(ObjectAccounting object) {

        try {

            return database.getListObjects(object);

        } catch (SQLException e) {
            printDialogError("Работа с базой данных", "Ошибка чтения данных из objectAccounting !", e.getMessage());
            return null;
        }

    }

    protected ObservableList<Counters> getTableObject(Counters object) {

        try {

            return database.getListObjects(object);

        } catch (SQLException e) {
            printDialogError("Работа с базой данных", "Ошибка чтения данных из counters !", e.getMessage());
            return null;
        }

    }

    protected ObservableList<Services> getTableObject(Services object) {

        try {

            return database.getListObjects(object);

        } catch (SQLException e) {
            printDialogError("Работа с базой данных", "Ошибка чтения данных из services !", e.getMessage());
            return null;
        }

    }

    protected ObservableList<Payments> getTableObject(Payments object) {

        try {

            return database.getListObjects(object);

        } catch (SQLException e) {
            printDialogError("Работа с базой данных", "Ошибка чтения данных из payments !", e.getMessage());
            return null;
        }

    }

    protected ObservableList<FormPayments> getTableObject(FormPayments object) {

        try {

            return database.getListObjects(object);

        } catch (SQLException e) {
            printDialogError("Работа с базой данных", "Ошибка чтения данных из formPayments !", e.getMessage());
            return null;
        }

    }

}
