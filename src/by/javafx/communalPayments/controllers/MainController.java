package by.javafx.communalPayments.controllers;

import by.javafx.communalPayments.controllers.counters.CountersController;
import by.javafx.communalPayments.controllers.objectAccounting.ObjAccountController;
import by.javafx.communalPayments.controllers.serviceList.ServiceListController;
import by.javafx.communalPayments.objects.AccessDatabase;
import by.javafx.communalPayments.objects.Counters;
import by.javafx.communalPayments.objects.ObjectAccounting;
import by.javafx.communalPayments.objects.TabObjects;
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

    @FXML
    private TabPane tabPane;
    @FXML
    private TableView <TabObjects> T1_objAccounting;
    @FXML
    private TableColumn <TabObjects, Integer> T1_personalAccountColumn;
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
    private TableColumn <TabObjects, Integer> T2_id_objAccountColumn;
    @FXML
    private TableColumn <TabObjects, String> T2_serviceColumn;
    @FXML
    private TableColumn <TabObjects, String> T2_counterNameColumn;

    @FXML
    private void initialize(){

// устанавливаем тип и значение которое должно хранится в колонке
        T1_personalAccountColumn.setCellValueFactory(new PropertyValueFactory<TabObjects, Integer>("personalAccount"));
        T1_nameObjColumn.setCellValueFactory(new PropertyValueFactory<TabObjects, String>("objectName"));
        T1_ownerColumn.setCellValueFactory(new PropertyValueFactory<TabObjects, String>("owner"));
        T1_addressColumn.setCellValueFactory(new PropertyValueFactory<TabObjects, String>("address"));
        T1_residentsColumn.setCellValueFactory(new PropertyValueFactory<TabObjects, Integer>("residents"));
        T1_areaColumn.setCellValueFactory(new PropertyValueFactory<TabObjects, Double>("area"));

        T2_id_objAccountColumn.setCellValueFactory(new PropertyValueFactory<TabObjects, Integer>("object"));
        T2_serviceColumn.setCellValueFactory(new PropertyValueFactory<TabObjects, String>("service"));
        T2_counterNameColumn.setCellValueFactory(new PropertyValueFactory<TabObjects, String>("counterName"));

    }

    public void fillObjectsList(String tableName){
        AccessDatabase accessDatabase = new AccessDatabase();
        ArrayList<String[]> list = new ArrayList<String[]>();

        objectList.clear();

        try {
            accessDatabase.setConnectDatabase("/home/juanantonio/Database_CommunalPayments/communalPayments.mdb");
            //accessDatabase.setConnectDatabase("E:\\OneDrive\\Учеба ГГУ\\DB_CommunalPayments\\communalPayments.mdb");
            list = accessDatabase.getData(tableName);
            accessDatabase.closeConnect();
        } catch (IOException e) {
            e.printStackTrace();
        }

        switch (tableName){
            case "accountingObject":
                for (String[] s : list){
                    objectList.add(new ObjectAccounting(Integer.parseInt(s[0]), s[1], s[2], s[3], Integer.parseInt(s[4]), Double.parseDouble(s[5])));
                }
                break;
            case "counters":
                for (String[] s : list){
                    objectList.add(new Counters(Integer.parseInt(s[0]), s[2], s[1]));
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
