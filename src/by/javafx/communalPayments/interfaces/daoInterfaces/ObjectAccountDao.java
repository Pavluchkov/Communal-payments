package by.javafx.communalPayments.interfaces.daoInterfaces;

import by.javafx.communalPayments.objects.ObjectAccounting;
import javafx.collections.ObservableList;

import java.sql.SQLException;

public interface ObjectAccountDao {
    void add(ObjectAccounting object) throws SQLException;

    void update(ObjectAccounting object, int id) throws SQLException;

    void delete(ObjectAccounting object) throws SQLException;

    ObservableList<ObjectAccounting> getAll() throws SQLException;
}
