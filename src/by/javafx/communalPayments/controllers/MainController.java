package by.javafx.communalPayments.controllers;

import by.javafx.communalPayments.controllers.counters.CountersAddController;
import by.javafx.communalPayments.controllers.objectAccounting.ObjAddController;
import by.javafx.communalPayments.controllers.objectAccounting.ObjChangeController;
import by.javafx.communalPayments.controllers.objectAccounting.ObjDeleteController;
import by.javafx.communalPayments.controllers.serviceList.ServiceListController;
import by.javafx.communalPayments.objects.Counters;
import by.javafx.communalPayments.objects.ObjectAccounting;
import by.javafx.communalPayments.objects.Payments;
import by.javafx.communalPayments.objects.ServiceList;
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

public class MainController extends Controller {

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
    private TableView<ServiceList> T3_service;
    @FXML
    private TableColumn<ServiceList, Integer> T3_id_serviceColumn;
    @FXML
    private TableColumn<ServiceList, String> T3_serviceNameColumn;
    @FXML
    private TableColumn<ServiceList, String> T3_unitColumn;
    @FXML
    private TableColumn<ServiceList, Double> T3_rateColumn;
    @FXML
    private TableColumn<ServiceList, Integer> T3_formPaymentsColumn;

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

        T3_id_serviceColumn.setCellValueFactory(new PropertyValueFactory<ServiceList, Integer>("id"));
        T3_serviceNameColumn.setCellValueFactory(new PropertyValueFactory<ServiceList, String>("serviceName"));
        T3_unitColumn.setCellValueFactory(new PropertyValueFactory<ServiceList, String>("unit"));
        T3_rateColumn.setCellValueFactory(new PropertyValueFactory<ServiceList, Double>("rate"));
        T3_formPaymentsColumn.setCellValueFactory(new PropertyValueFactory<ServiceList, Integer>("formPayments"));

        T4_id_paymentsColumn.setCellValueFactory(new PropertyValueFactory<Payments, Integer>("id_payments"));
        T4_serviceColumn.setCellValueFactory(new PropertyValueFactory<Payments, Integer>("service"));
        T4_valuePaymentsColumn.setCellValueFactory(new PropertyValueFactory<Payments, Double>("valuePayments"));
        T4_datePaymentsColumn.setCellValueFactory(new PropertyValueFactory<Payments, String>("datePayments"));
    }

    @FXML
    public void tabObjAccountChange() {

        try {
            T1_objAccounting.setItems(database.getTableObjects());

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @FXML
    public void tabCountersChange() {

        try {
            T2_counters.setItems(database.getTableCounters());
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @FXML
    public void tabServiceChange() {

        try {
            T3_service.setItems(database.getTableServices());
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @FXML
    public void tabPaymentsChange() {

        try {
            T4_payments.setItems(database.getTablePayments());
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @FXML
    public void objAccountAdd() {
        dialogWindow(new ObjAddController(), "/by/javafx/communalPayments/fxml/objAccountDialog/addObjAccount.fxml",
                "Добавление объекта учета", 565, 350);
    }

    @FXML
    public void objAccountChange() {
        ObjectAccounting object = (ObjectAccounting) T1_objAccounting.getSelectionModel().getSelectedItem();

        if (object != null) {
            dialogWindow(new ObjChangeController(object), "/by/javafx/communalPayments/fxml/objAccountDialog/changeObjAccount.fxml",
                    "Изменение объекта учета", 565, 350);
        } else {
            printDialogError("Ошибка изменения объекта", "Ошибка !", "Не выбран изменяемый объект ! ");
        }

    }

    @FXML
    public void objAccountDelete() {
        ObjectAccounting object = (ObjectAccounting) T1_objAccounting.getSelectionModel().getSelectedItem();

        if (object != null) {
            dialogWindow(new ObjDeleteController(object), "/by/javafx/communalPayments/fxml/objAccountDialog/deleteObjAccount.fxml",
                    "Удаление объекта учета", 380, 200);
        } else {
            printDialogError("Ошибка удаления объекта", "Ошибка !", "Не выбран удаляемый объект ! ");
        }
    }

    @FXML
    public void countersAdd() {
        dialogWindow(new CountersAddController(), "/by/javafx/communalPayments/fxml/countersDialog/addCounters.fxml",
                "Добавление счетчика", 400, 265);
    }

    @FXML
    public void countersChange() {
        dialogWindow(new CountersAddController(), "/by/javafx/communalPayments/fxml/countersDialog/changeCounters.fxml",
                "Изменение счетчика", 520, 230);
    }

    @FXML
    public void countersDelete() {
        dialogWindow(new CountersAddController(), "/by/javafx/communalPayments/fxml/countersDialog/deleteCounters.fxml",
                "Удаление счетчика", 450, 190);
    }

    @FXML
    public void servListAdd() {
        dialogWindow(new ServiceListController(), "/by/javafx/communalPayments/fxml/serviceListDialog/addServiceList.fxml",
                "Добавление услуги", 400, 310);
    }

    @FXML
    public void servListChange() {
        dialogWindow(new ServiceListController(), "/by/javafx/communalPayments/fxml/serviceListDialog/changeServiceList.fxml",
                "Изменение услуги", 570, 225);
    }

    @FXML
    public void servListDelete() {
        dialogWindow(new ServiceListController(), "/by/javafx/communalPayments/fxml/serviceListDialog/deleteServiceList.fxml",
                "Удаление услуги", 450, 190);
    }

    @FXML
    public void inputValueCounterClicked() {
        dialogWindow(new CountersAddController(), "/by/javafx/communalPayments/fxml/countersDialog/inputCounterValue.fxml",
                "Ввод показаний счетчика", 400, 265);
    }

    public void dialogWindow(Controller controller, String resource, String title, int width, int height) {
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
