package by.javafx.communalPayments.controllers;

import by.javafx.communalPayments.controllers.counters.CountersController;
import by.javafx.communalPayments.controllers.objectAccounting.ObjAccountController;
import by.javafx.communalPayments.controllers.serviceList.ServiceListController;
import by.javafx.communalPayments.interfaces.IDatabase;
import by.javafx.communalPayments.objects.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TabPane;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.ArrayList;

public class MainController {

    private ObservableList<TabObjects> objectList = FXCollections.observableArrayList();
    private IDatabase database = new AccessDatabase();

    @FXML
    private TabPane tabPane;

    @FXML
    private TableView <TabObjects> T1_objAccounting;
    @FXML
    private TableColumn <TabObjects, String> T1_nameObjColumn;
    @FXML
    private TableColumn <TabObjects, String> T1_ownerColumn;
    @FXML
    private TableColumn <TabObjects, String> T1_addressColumn;
    @FXML
    private TableColumn <TabObjects, Integer> T1_residentsColumn;
    @FXML
    private TableColumn <TabObjects, Double> T1_areaColumn;

    @FXML
    private TableView <TabObjects> T2_counters;
    @FXML
    private TableColumn <TabObjects, String> T2_counterNameColumn;
    @FXML
    private TableColumn <TabObjects, String> T2_serviceColumn;
    @FXML
    private TableColumn <TabObjects, String> T2_nameObjColumn;

    @FXML
    private TableView <TabObjects> T3_service;
    @FXML
    private TableColumn <TabObjects, String> T3_serviceNameColumn;
    @FXML
    private TableColumn <TabObjects, String> T3_unitColumn;
    @FXML
    private TableColumn <TabObjects, Double> T3_rateColumn;
    @FXML
    private TableColumn <TabObjects, String> T3_formPaymentsColumn;

    @FXML
    private TableView <TabObjects> T4_payments;
    @FXML
    private TableColumn <TabObjects, Integer> T4_id_paymentsColumn;
    @FXML
    private TableColumn <TabObjects, String> T4_serviceColumn;
    @FXML
    private TableColumn <TabObjects, Double> T4_valuePaymentsColumn;
    @FXML
    private TableColumn <TabObjects, String> T4_datePaymentsColumn;

    @FXML
    private void initialize(){

// устанавливаем тип и значение которое должно хранится в колонке

        T1_nameObjColumn.setCellValueFactory(new PropertyValueFactory<TabObjects, String>("objectName"));
        T1_ownerColumn.setCellValueFactory(new PropertyValueFactory<TabObjects, String>("owner"));
        T1_addressColumn.setCellValueFactory(new PropertyValueFactory<TabObjects, String>("address"));
        T1_residentsColumn.setCellValueFactory(new PropertyValueFactory<TabObjects, Integer>("residents"));
        T1_areaColumn.setCellValueFactory(new PropertyValueFactory<TabObjects, Double>("area"));

        T2_counterNameColumn.setCellValueFactory(new PropertyValueFactory<TabObjects, String>("counterName"));
        T2_serviceColumn.setCellValueFactory(new PropertyValueFactory<TabObjects, String>("service"));
        T2_nameObjColumn.setCellValueFactory(new PropertyValueFactory<TabObjects, String>("object"));

        T3_serviceNameColumn.setCellValueFactory(new PropertyValueFactory<TabObjects, String>("serviceName"));
        T3_unitColumn.setCellValueFactory(new PropertyValueFactory<TabObjects, String>("unit"));
        T3_rateColumn.setCellValueFactory(new PropertyValueFactory<TabObjects, Double>("rate"));
        T3_formPaymentsColumn.setCellValueFactory(new PropertyValueFactory<TabObjects, String>("formPayments"));

        T4_id_paymentsColumn.setCellValueFactory(new PropertyValueFactory<TabObjects, Integer>("id_payments"));
        T4_serviceColumn.setCellValueFactory(new PropertyValueFactory<TabObjects, String>("service"));
        T4_valuePaymentsColumn.setCellValueFactory(new PropertyValueFactory<TabObjects, Double>("valuePayments"));
        T4_datePaymentsColumn.setCellValueFactory(new PropertyValueFactory<TabObjects, String>("datePayments"));
    }

    public void fillObjectsList(String tableName){
        ArrayList<String[]> list = new ArrayList<String[]>();

        objectList.clear();

        try {
            //accessDatabase.setConnectDatabase("/home/juanantonio/Database_CommunalPayments/communalPayments.mdb");
            database.setConnectDatabase("E:\\OneDrive\\Учеба ГГУ\\DB_CommunalPayments\\DB_New\\communalPayments.mdb");
            list = database.getDataTable(tableName);
            database.closeConnect();
        } catch (IOException e) {
            e.printStackTrace();
        }

        switch (tableName){
            case "accountingObject":
                for (String[] s : list){
                    objectList.add(new ObjectAccounting(s[0], s[1], s[2], Integer.parseInt(s[3]), Double.parseDouble(s[4])));
                }
                break;
            case "counters":
                for (String[] s : list){
                    objectList.add(new Counters(s[0], s[1], s[2]));
                }
                break;
            case "services":
                for (String[] s : list){
                    objectList.add(new ServiceList(s[0], s[1], Double.parseDouble(s[2]), s[3]));
                }
                break;
            case "payments":
                for (String[] s : list){
                    objectList.add(new Payments(Integer.parseInt(s[0]), s[2], Double.parseDouble(s[1]), s[3]));
                }
                break;
        }

    }

    @FXML
    public void tabObjAccountChange(){
        fillObjectsList("accountingObject");
        T1_objAccounting.setItems(objectList);
    }

    @FXML
    public void tabCountersChange(){
        fillObjectsList("counters");
        T2_counters.setItems(objectList);
    }

    @FXML
    public void tabServiceChange(){
        fillObjectsList("services");
        T3_service.setItems(objectList);
    }

    @FXML
    public void tabPaymentsChange(){
        fillObjectsList("payments");
        T4_payments.setItems(objectList);
    }

    @FXML
    public void objAccountAdd(){
        dialogWindow(new ObjAccountController(), "/by/javafx/communalPayments/fxml/objAccountDialog/addObjAccount.fxml",
                "Добавление объекта учета", 565, 350);
    }

    @FXML
    public void objAccountChange(){
        dialogWindow(new ObjAccountController(), "/by/javafx/communalPayments/fxml/objAccountDialog/changeObjAccount.fxml",
                "Изменение объекта учета", 570, 310);
    }
    @FXML
    public void objAccountDelete(){
        dialogWindow(new ObjAccountController(), "/by/javafx/communalPayments/fxml/objAccountDialog/deleteObjAccount.fxml",
                "Удаление объекта учета", 450, 190);
    }

    @FXML
    public void countersAdd(){
        dialogWindow(new CountersController(), "/by/javafx/communalPayments/fxml/countersDialog/addCounters.fxml",
                  "Добавление счетчика", 400, 265);
    }

    @FXML
    public void countersChange(){
        dialogWindow(new CountersController(), "/by/javafx/communalPayments/fxml/countersDialog/changeCounters.fxml",
                "Изменение счетчика", 520, 230);
    }

    @FXML
    public void countersDelete(){
        dialogWindow(new CountersController(), "/by/javafx/communalPayments/fxml/countersDialog/deleteCounters.fxml",
                "Удаление счетчика", 450, 190);
    }

    @FXML
    public void servListAdd(){
        dialogWindow(new ServiceListController(), "/by/javafx/communalPayments/fxml/serviceListDialog/addServiceList.fxml",
                "Добавление услуги", 400, 310);
    }

    @FXML
    public void servListChange() {
        dialogWindow(new ServiceListController(), "/by/javafx/communalPayments/fxml/serviceListDialog/changeServiceList.fxml",
                "Изменение услуги", 570, 225);
    }

    @FXML
    public void servListDelete(){
        dialogWindow(new ServiceListController(), "/by/javafx/communalPayments/fxml/serviceListDialog/deleteServiceList.fxml",
                "Удаление услуги", 450, 190);
    }

    @FXML
    public void inputValueCounterClicked(){
        dialogWindow(new CountersController(), "/by/javafx/communalPayments/fxml/countersDialog/inputCounterValue.fxml",
                "Ввод показаний счетчика", 400, 265);
    }

    public void dialogWindow(Controller controller, String resource, String title, int width, int height){
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
}
