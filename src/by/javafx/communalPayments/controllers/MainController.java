package by.javafx.communalPayments.controllers;

import by.javafx.communalPayments.controllers.counters.CounterAddController;
import by.javafx.communalPayments.controllers.counters.CounterChangeController;
import by.javafx.communalPayments.controllers.counters.CounterDeleteController;
import by.javafx.communalPayments.controllers.counters.MeasureController;
import by.javafx.communalPayments.controllers.objectAccounting.ObjAddController;
import by.javafx.communalPayments.controllers.objectAccounting.ObjChangeController;
import by.javafx.communalPayments.controllers.objectAccounting.ObjDeleteController;
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
import javafx.scene.control.Alert;
import javafx.scene.control.TabPane;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.stage.Modality;
import javafx.stage.Stage;

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
    private TableColumn<Payments, Double> T4_sumColumn;
    @FXML
    private TableColumn<Payments, Date> T4_datePaymentsColumn;

    @FXML
    private void initialize() {

        Subject subject = new MySQLDatabase();// Устанавливаем наблюдателя
        subject.registerObserver(this);    //      за MySQLDatabase

        setConnection("jdbc:mysql://localhost:3306/communalpayments");

// устанавливаем тип и значение которое должно хранится в колонке
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
        T4_sumColumn.setCellValueFactory(new PropertyValueFactory<>("sum"));
        T4_datePaymentsColumn.setCellValueFactory(new PropertyValueFactory<>("date"));

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
                ObservableList<ObjectAccounting> list = FXCollections.observableArrayList();
                list = database.getListObjects(new ObjectAccounting());
                T1_objAccounting.setItems(list);
            }

            if (object instanceof Counters) {
                ObservableList<Counters> list = FXCollections.observableArrayList();
                list = database.getListObjects(new Counters());
                T2_counters.setItems(list);
            }

            if (object instanceof Services) {
                ObservableList<Services> list = FXCollections.observableArrayList();
                list = database.getListObjects(new Services());
                T3_service.setItems(list);
            }

            if (object instanceof Payments) {
                ObservableList<Payments> list = FXCollections.observableArrayList();
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

        dialogWindow(new ObjAddController(this), "/by/javafx/communalPayments/fxml/objAccountDialog/objectAccountAdd.fxml",
                "Добавление объекта учета", 565, 350);

    }

    @FXML
    public void objAccountChange() {
        ObjectAccounting object = (ObjectAccounting) T1_objAccounting.getSelectionModel().getSelectedItem();

        if (object != null) {
            setSelectedObject(object);
            dialogWindow(new ObjChangeController(this), "/by/javafx/communalPayments/fxml/objAccountDialog/objectAccountChange.fxml",
                    "Изменение объекта учета", 565, 350);
        } else {
            printDialogError("Ошибка изменения объекта", "Не выбран изменяемый объект !", "Выберите объект для изменения.");
        }

    }

    @FXML
    public void objAccountDelete() {
        ObjectAccounting object = (ObjectAccounting) T1_objAccounting.getSelectionModel().getSelectedItem();

        if (object != null) {
            setSelectedObject(object);
            dialogWindow(new ObjDeleteController(this), "/by/javafx/communalPayments/fxml/deleteObject.fxml",
                    "Удаление объекта", 380, 190);
        } else {
            printDialogError("Ошибка удаления объекта", "Ошибка !", "Не выбран удаляемый объект ! ");
        }
    }

    @FXML
    public void countersAdd() {
        dialogWindow(new CounterAddController(this), "/by/javafx/communalPayments/fxml/countersDialog/CountersAdd.fxml",
                "Добавление счетчика", 400, 305);
    }

    @FXML
    public void countersChange() {
        Counters object = (Counters) T2_counters.getSelectionModel().getSelectedItem();

        if (object != null) {
            setSelectedObject(object);
            dialogWindow(new CounterChangeController(this), "/by/javafx/communalPayments/fxml/countersDialog/CountersChange.fxml",
                    "Изменение счетчика", 400, 305);
        } else {
            printDialogError("Ошибка изменения объекта", "Ошибка !", "Не выбран изменяемый объект ! ");
        }

    }

    @FXML
    public void countersDelete() {
        Counters object = (Counters) T2_counters.getSelectionModel().getSelectedItem();

        if (object != null) {
            setSelectedObject(object);
            dialogWindow(new CounterDeleteController(this), "/by/javafx/communalPayments/fxml/deleteObject.fxml",
                    "Удаление объекта", 380, 190);
        } else {
            printDialogError("Ошибка удаления объекта", "Ошибка !", "Не выбран удаляемый объект ! ");
        }

    }

    @FXML
    public void inputMeasureClicked() {
        Counters object = (Counters) T2_counters.getSelectionModel().getSelectedItem();

        if (object != null) {
            setSelectedObject(object);
            dialogWindow(new MeasureController(this), "/by/javafx/communalPayments/fxml/countersDialog/inputMeasure.fxml",
                    "Внесение показаний счетчика", 400, 305);
        } else {
            printDialogError("Внесение показаний", "Не выбран счетчик !", "Для внесения показаний выделите хотя бы один счетчик.");
        }
    }

    @FXML
    public void serviceAdd() {
        dialogWindow(new ServiceAddController(this), "/by/javafx/communalPayments/fxml/servicesDialog/servicesAdd.fxml",
                "Добавление услуги", 400, 305);
    }

    @FXML
    public void serviceChange() {
        Services object = (Services) T3_service.getSelectionModel().getSelectedItem();

        if (object != null) {
            setSelectedObject(object);
            dialogWindow(new ServiceChangeController(this), "/by/javafx/communalPayments/fxml/servicesDialog/servicesChange.fxml",
                    "Изменение услуги", 400, 305);
        } else {
            printDialogError("Ошибка изменения объекта", "Ошибка !", "Не выбран изменяемый объект ! ");
        }

    }

    @FXML
    public void serviceDelete() {
        Services object = (Services) T3_service.getSelectionModel().getSelectedItem();

        if (object != null) {
            setSelectedObject(object);
            dialogWindow(new ServiceDeleteController(this), "/by/javafx/communalPayments/fxml/deleteObject.fxml",
                    "Удаление объекта", 380, 190);
        } else {
            printDialogError("Ошибка удаления объекта", "Ошибка !", "Не выбран удаляемый объект ! ");
        }
    }

    private void dialogWindow(MainController controller, String resource, String title, int width, int height) {
        Stage stage = new Stage();
        FXMLLoader fxmlLoader = new FXMLLoader();
        fxmlLoader.setLocation(getClass().getResource(resource));
        fxmlLoader.setController(controller);
        Parent root = null;
        try {
            root = fxmlLoader.load();
            stage.setTitle(title);
            stage.setScene(new Scene(root, width, height));
            stage.initModality(Modality.WINDOW_MODAL);
            stage.initOwner(tabPane.getScene().getWindow());
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

    @Override
    public void update() {
        fillTable(new ObjectAccounting());
        fillTable(new Counters());
        fillTable(new Services());
        fillTable(new Payments());
    }
}
