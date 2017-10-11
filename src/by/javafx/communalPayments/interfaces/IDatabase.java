package by.javafx.communalPayments.interfaces;

import by.javafx.communalPayments.objects.*;
import javafx.collections.ObservableList;

import java.io.IOException;
import java.sql.SQLException;

public interface IDatabase {

    void setConnectDatabase(String connectionString) throws IOException, ClassNotFoundException, SQLException;

    void closeConnect() throws SQLException;

    ObservableList<MyObjects> getTable(MyObjects myObject) throws SQLException;

    ObservableList<String> getColumn(String tableName, String columnName) throws SQLException;
    String getValueColumn(String tableName, String columnName, int rowIndex) throws SQLException;

    void add(ObjectAccounting object) throws SQLException;

    void add(Counters object) throws SQLException;

    void add(Payments object) throws SQLException;

    void add(ServiceList object) throws SQLException;

    void delete(ObjectAccounting object) throws SQLException;

    void delete(Counters object) throws SQLException;

    void delete(Payments object) throws SQLException;

    void delete(ServiceList object) throws SQLException;

    void change(ObjectAccounting object, int id) throws SQLException;

    void change(Counters object) throws SQLException;

    void change(Payments object) throws SQLException;

    void change(ServiceList object) throws SQLException;

}
