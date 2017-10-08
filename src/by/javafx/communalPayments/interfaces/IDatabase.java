package by.javafx.communalPayments.interfaces;

import javafx.collections.ObservableList;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public interface IDatabase {

    void setConnectDatabase(String connectionString) throws IOException, ClassNotFoundException, SQLException;

    void closeConnect() throws IOException, SQLException;

    ResultSet getDataTable(String tableName) throws IOException, SQLException;

}
