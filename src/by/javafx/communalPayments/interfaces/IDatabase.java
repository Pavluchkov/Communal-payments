package by.javafx.communalPayments.interfaces;

import javafx.collections.ObservableList;

import java.io.IOException;
import java.util.ArrayList;

public interface IDatabase {

    void setConnectDatabase(String connectionString) throws IOException;

    void closeConnect() throws IOException;

    ArrayList<String[]> getData(String tableName) throws IOException;

}
