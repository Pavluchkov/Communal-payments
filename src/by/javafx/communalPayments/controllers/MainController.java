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
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.TabPane;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.Window;

import java.sql.Date;
import java.sql.SQLException;

public class MainController implements Observer {
    protected IDatabase database = new MySQLDatabase();
    private MyObjects selectedObject;

    @FXML
    private TabPane tabPane;

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

        Subject subject = new MySQLDatabase();// Устанавливаем наблюдателя
        subject.registerObserver(this);    //      за MySQLDatabase

        setConnection("jdbc:mysql://localhost:3306/communalpayments");

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

        update();
    }

    private void setConnection(String connectionString) {

        try {
            database.setConnectDatabase(connectionString);
        } catch (SQLException | ClassNotFoundException e) {
            printDialogError("Ошибка подключения", "Не удалось подключиться к БД !", e.getMessage());
        }
    }

    private void fillTable(MyObjects object) {
        try {
            if (object instanceof ObjectAccounting) {
                ObservableList<ObjectAccounting> list;
                list = database.getListObjects(new ObjectAccounting());
                T1_objAccounting.setItems(list);
            }

            if (object instanceof Counters) {
                ObservableList<Counters> list;
                list = database.getListObjects(new Counters());
                T2_counters.setItems(list);
            }

            if (object instanceof Services) {
                ObservableList<Services> list;
                list = database.getListObjects(new Services());
                T3_service.setItems(list);
            }

            if (object instanceof Payments) {
                ObservableList<Payments> list;
                list = database.getListObjects(new Payments());
                T4_payments.setItems(list);
            }


        } catch (SQLException e) {
            printDialogError("Ошибка получения данных", "Не удалось получить данные из БД !", e.getMessage());
        }
    }

    public MyObjects getSelectedObject() {
        return selectedObject;
    }

    private void setSelectedObject(MyObjects object) {
        selectedObject = object;
    }

    @FXML
    public void tabObjAccountChange() {

    }

    @FXML
    public void tabCountersChange() {

    }

    @FXML
    public void tabServiceChange() {

    }

    @FXML
    public void tabPaymentsChange() {

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
        dialogWindow(tabPane.getScene().getWindow(), new CounterAddController(), "/by/javafx/communalPayments/fxml/countersDialog/CountersAdd.fxml",
                "Добавление счетчика", 400, 305);
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
                "Добавление услуги", 400, 305);
    }

    @FXML
    public void serviceChange() {
        Services object = T3_service.getSelectionModel().getSelectedItem();

        if (object != null) {
            setSelectedObject(object);
            dialogWindow(tabPane.getScene().getWindow(), new ServiceChangeController(this), "/by/javafx/communalPayments/fxml/servicesDialog/servicesChange.fxml",
                    "Изменение услуги", 400, 305);
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
        dialogWindow(tabPane.getScene().getWindow(), new PaymentAddController(), "/by/javafx/communalPayments/fxml/paymentsDialog/PaymentsAdd.fxml",
                "Добавление платежа", 400, 260);
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
            System.out.println(e.getMessage());
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
            printDialogError("Работа с базой данных", "Ошибка записи данных в БД !", e.getMessage());
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
            printDialogError("Работа с базой данных", "Ошибка записи данных в БД !", e.getMessage());
            return false;
        }

        return true;
    }

    protected boolean objectChange(ObjectAccounting object, int id) {
        try {

            database.change(object, id);

        } catch (SQLException e) {
            printDialogError("Работа с базой данных", "Ошибка записи данных в БД !", e.getMessage());
            return false;
        }
        return true;
    }

    protected boolean lastMeasureChange(Counters object, double lastMeasure) {

        try {
            database.changeLastMeasure(object, lastMeasure);
        } catch (SQLException e) {
            printDialogError("Работа с базой данных", "Ошибка записи данных в БД !", e.getMessage());
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
            printDialogError("Работа с базой данных", "Ошибка записи данных в БД !", e.getMessage());
            return false;
        }

        return true;
    }

    protected ObservableList<ObjectAccounting> getTableObject(ObjectAccounting object) {

        try {
            return database.getListObjects(object);
        } catch (SQLException e) {
            printDialogError("Работа с базой данных", "Ошибка чтения данных из БД !", e.getMessage());
            return null;
        }

    }

    protected ObservableList<Counters> getTableObject(Counters object) {

        try {
            return database.getListObjects(object);
        } catch (SQLException e) {
            printDialogError("Работа с базой данных", "Ошибка чтения данных из БД !", e.getMessage());
            return null;
        }

    }

    protected ObservableList<Services> getTableObject(Services object) {

        try {
            return database.getListObjects(object);
        } catch (SQLException e) {
            printDialogError("Работа с базой данных", "Ошибка чтения данных из БД !", e.getMessage());
            return null;
        }

    }

    protected ObservableList<Payments> getTableObject(Payments object) {

        try {
            return database.getListObjects(object);
        } catch (SQLException e) {
            printDialogError("Работа с базой данных", "Ошибка чтения данных из БД !", e.getMessage());
            return null;
        }

    }

    protected ObservableList<FormPayments> getTableObject(FormPayments object) {

        try {
            return database.getListObjects(object);
        } catch (SQLException e) {
            printDialogError("Работа с базой данных", "Ошибка чтения данных из БД !", e.getMessage());
            return null;
        }

    }

    @Override
    public void update() {
        fillTable(new ObjectAccounting());
        fillTable(new Counters());
        fillTable(new Services());
        fillTable(new Payments());
    }
}
