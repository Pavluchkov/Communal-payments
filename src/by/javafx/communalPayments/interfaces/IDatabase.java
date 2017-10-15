package by.javafx.communalPayments.interfaces;

import by.javafx.communalPayments.objects.*;
import javafx.collections.ObservableList;

import java.io.IOException;
import java.sql.SQLException;

public interface IDatabase {

    void setConnectDatabase(String connectionString) throws ClassNotFoundException, SQLException;

    void closeConnect() throws SQLException;

    ObservableList<ObjectAccounting> getListObjects(ObjectAccounting object) throws SQLException;
    ObservableList<Counters> getListObjects(Counters object) throws SQLException;
    ObservableList<Payments> getListObjects(Payments object) throws SQLException;
    ObservableList<Services> getListObjects(Services object) throws SQLException;
    ObservableList<FormPayment> getListObjects(FormPayment object) throws SQLException;

    void add(ObjectAccounting object) throws SQLException;
    void add(Counters object) throws SQLException;
    void add(Payments object) throws SQLException;
    void add(Services object) throws SQLException;

    void delete(ObjectAccounting object) throws SQLException;
    void delete(Counters object) throws SQLException;
    void delete(Payments object) throws SQLException;
    void delete(Services object) throws SQLException;

    void change(ObjectAccounting object, int id) throws SQLException;
    void change(Counters object) throws SQLException;
    void change(Payments object) throws SQLException;
    void change(Services object) throws SQLException;

}
