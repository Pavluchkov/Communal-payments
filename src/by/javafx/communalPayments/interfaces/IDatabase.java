package by.javafx.communalPayments.interfaces;

import by.javafx.communalPayments.objects.Counters;
import by.javafx.communalPayments.objects.ObjectAccounting;
import by.javafx.communalPayments.objects.Payments;
import by.javafx.communalPayments.objects.ServiceList;
import javafx.collections.ObservableList;

import java.io.IOException;
import java.sql.SQLException;

public interface IDatabase {

    void setConnectDatabase(String connectionString) throws IOException, ClassNotFoundException, SQLException;

    void closeConnect() throws SQLException;

    ObservableList<ObjectAccounting> getTableObjects() throws SQLException;

    ObservableList<Counters> getTableCounters() throws SQLException;

    ObservableList<Payments> getTablePayments() throws SQLException;

    ObservableList<ServiceList> getTableServices() throws SQLException;

    ObservableList<String> getColumn(String tableName, String columnName) throws SQLException;
    String getValueColumn(String tableName, String columnName, int rowIndex) throws SQLException;
//    ObservableList<String> getListServices() throws SQLException;
//    ObservableList<String> getListCounters() throws SQLException;
//    ObservableList<String> getListPayments() throws SQLException;

    void addObject(ObjectAccounting object) throws SQLException;

    void addCounter(Counters object) throws SQLException;

    void addPayment(Payments object) throws SQLException;

    void addService(ServiceList object) throws SQLException;

    void deleteObject(ObjectAccounting object) throws SQLException;

    void deleteCounter(Counters object) throws SQLException;

    void deletePayment(Payments object) throws SQLException;

    void deleteService(ServiceList object) throws SQLException;

    void changeObject(ObjectAccounting object, int id) throws SQLException;

    void changeCounter(Counters object) throws SQLException;

    void changePayment(Payments object) throws SQLException;

    void changeService(ServiceList object) throws SQLException;

}
