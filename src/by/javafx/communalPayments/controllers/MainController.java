package by.javafx.communalPayments.controllers;

import by.javafx.communalPayments.controllers.counters.CounterAddController;
import by.javafx.communalPayments.controllers.counters.CounterChangeController;
import by.javafx.communalPayments.controllers.counters.CounterDeleteController;
import by.javafx.communalPayments.controllers.objectAccounting.ObjAddController;
import by.javafx.communalPayments.controllers.objectAccounting.ObjChangeController;
import by.javafx.communalPayments.controllers.objectAccounting.ObjDeleteController;
import by.javafx.communalPayments.controllers.services.ServiceAddController;
import by.javafx.communalPayments.controllers.services.ServiceChangeController;
import by.javafx.communalPayments.controllers.services.ServiceDeleteController;
import by.javafx.communalPayments.interfaces.IDatabase;
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

import java.io.IOException;
import java.sql.SQLException;

public class MainController {
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
    private TableColumn<Counters, String> T2_counterNameColumn;
    @FXML
    private TableColumn<Counters, Integer> T2_serviceColumn;
    @FXML
    private TableColumn<Counters, Integer> T2_nameObjColumn;

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
    private TableColumn<Payments, Integer> T4_serviceColumn;
    @FXML
    private TableColumn<Payments, Double> T4_valuePaymentsColumn;
    @FXML
    private TableColumn<Payments, String> T4_datePaymentsColumn;

    public MainController() {

        String connectionString = "jdbc:mysql://localhost:3306/communalPayments";

        try {
            database.setConnectDatabase(connectionString);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void initialize() {

// устанавливаем тип и значение которое должно хранится в колонке

        T1_personalAccountColumn.setCellValueFactory(new PropertyValueFactory<ObjectAccounting, Integer>("id"));
        T1_nameObjColumn.setCellValueFactory(new PropertyValueFactory<ObjectAccounting, String>("objectName"));
        T1_ownerColumn.setCellValueFactory(new PropertyValueFactory<ObjectAccounting, String>("owner"));
        T1_addressColumn.setCellValueFactory(new PropertyValueFactory<ObjectAccounting, String>("address"));
        T1_residentsColumn.setCellValueFactory(new PropertyValueFactory<ObjectAccounting, Integer>("residents"));
        T1_areaColumn.setCellValueFactory(new PropertyValueFactory<ObjectAccounting, Double>("area"));

        T2_id_counterColumn.setCellValueFactory(new PropertyValueFactory<Counters, Integer>("id"));
        T2_counterNameColumn.setCellValueFactory(new PropertyValueFactory<Counters, String>("counterName"));
        T2_serviceColumn.setCellValueFactory(new PropertyValueFactory<Counters, Integer>("service"));
        T2_nameObjColumn.setCellValueFactory(new PropertyValueFactory<Counters, Integer>("object"));

        T3_id_serviceColumn.setCellValueFactory(new PropertyValueFactory<Services, Integer>("id"));
        T3_serviceNameColumn.setCellValueFactory(new PropertyValueFactory<Services, String>("serviceName"));
        T3_unitColumn.setCellValueFactory(new PropertyValueFactory<Services, String>("unit"));
        T3_rateColumn.setCellValueFactory(new PropertyValueFactory<Services, Double>("rate"));
        T3_formPaymentsColumn.setCellValueFactory(new PropertyValueFactory<Services, Integer>("formPayments"));

        T4_id_paymentsColumn.setCellValueFactory(new PropertyValueFactory<Payments, Integer>("id_payments"));
        T4_serviceColumn.setCellValueFactory(new PropertyValueFactory<Payments, Integer>("service"));
        T4_valuePaymentsColumn.setCellValueFactory(new PropertyValueFactory<Payments, Double>("valuePayments"));
        T4_datePaymentsColumn.setCellValueFactory(new PropertyValueFactory<Payments, String>("datePayments"));
    }

    public void fillTable(MyObjects object) {
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
            e.printStackTrace();
        }
    }

    public MyObjects getSelectedObject() {
        return selectedObject;
    }

    public void setSelectedObject(MyObjects object) {
        selectedObject = object;
    }

    @FXML
    public void tabObjAccountChange() {
        fillTable(new ObjectAccounting());
    }

    @FXML
    public void tabCountersChange() {
        fillTable(new Counters());
    }

    @FXML
    public void tabServiceChange() {
        fillTable(new Services());
    }

    @FXML
    public void tabPaymentsChange() {
        fillTable(new Payments());
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
            printDialogError("Ошибка изменения объекта", "Ошибка !", "Не выбран изменяемый объект ! ");
        }

    }

    @FXML
    public void objAccountDelete() {
        ObjectAccounting object = (ObjectAccounting) T1_objAccounting.getSelectionModel().getSelectedItem();

        if (object != null) {
            setSelectedObject(object);
            dialogWindow(new ObjDeleteController(this), "/by/javafx/communalPayments/fxml/deleteObject.fxml",
                    "Удаление объекта", 380, 200);
        } else {
            printDialogError("Ошибка удаления объекта", "Ошибка !", "Не выбран удаляемый объект ! ");
        }
    }

    @FXML
    public void countersAdd() {
        dialogWindow(new CounterAddController(this), "/by/javafx/communalPayments/fxml/countersDialog/CountersAdd.fxml",
                "Добавление счетчика", 400, 265);
    }

    @FXML
    public void countersChange() {
        Counters object = (Counters) T2_counters.getSelectionModel().getSelectedItem();

        if (object != null) {
            setSelectedObject(object);
            dialogWindow(new CounterChangeController(this), "/by/javafx/communalPayments/fxml/countersDialog/CountersChange.fxml",
                    "Изменение счетчика", 400, 265);
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
                    "Удаление объекта", 380, 200);
        } else {
            printDialogError("Ошибка удаления объекта", "Ошибка !", "Не выбран удаляемый объект ! ");
        }

    }

    @FXML
    public void inputValueCounterClicked() {
        dialogWindow(new CounterAddController(), "/by/javafx/communalPayments/fxml/countersDialog/inputCounterValue.fxml",
                "Ввод показаний счетчика", 400, 265);
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
                    "Удаление объекта", 380, 200);
        } else {
            printDialogError("Ошибка удаления объекта", "Ошибка !", "Не выбран удаляемый объект ! ");
        }
    }

    public void dialogWindow(MainController controller, String resource, String title, int width, int height) {
        Stage stage = new Stage();
        FXMLLoader fxmlLoader = new FXMLLoader();
        fxmlLoader.setLocation(getClass().getResource(resource));
        fxmlLoader.setController(controller);
        Parent root = null;
        try {
            root = fxmlLoader.load();

        } catch (Throwable e) {
            System.out.println(e.getMessage());
        }
        stage.setTitle(title);
        stage.setScene(new Scene(root, width, height));
        stage.initModality(Modality.WINDOW_MODAL);
        stage.initOwner(tabPane.getScene().getWindow());
        javafx.scene.image.Image ico = new Image("/by/javafx/communalPayments/ico/icon.png");
        stage.getIcons().add(ico);
        stage.setResizable(false);
        stage.show();
    }

    void printDialogError(String title, String headerText, String contentText) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(headerText);
        alert.setContentText(contentText);
        Stage stage = (Stage) alert.getDialogPane().getScene().getWindow();
        stage.getIcons().add(new Image("/by/javafx/communalPayments/ico/icon.png"));
        alert.showAndWait();
        alert.close();
    }
}
