package by.javafx.communalPayments.interfaces;

import by.javafx.communalPayments.objects.*;
import javafx.collections.ObservableList;

import java.io.IOException;
import java.sql.SQLException;

public interface IDatabase {

    void setConnectDatabase(String connectionString) throws IOException, ClassNotFoundException, SQLException;

    void closeConnect() throws SQLException;

    ObservableList<ObjectAccounting> getObjectsList() throws SQLException;
    ObservableList<Counters> getCountersList() throws SQLException;
    ObservableList<Payments> getPaymentsList() throws SQLException;
    ObservableList<ServiceList> getServiceList() throws SQLException;

    void addObject(ObjectAccounting object) throws SQLException;
    void addCounter(Counters objects) throws SQLException;
    void addPayment(Payments objects) throws SQLException;
    void addService(ServiceList objects) throws SQLException;

    void deleteObject(ObjectAccounting object) throws SQLException;
    void deleteCounter(Counters objects) throws SQLException;
    void deletePayment(Payments objects) throws SQLException;
    void deleteService(ServiceList objects) throws SQLException;

}
