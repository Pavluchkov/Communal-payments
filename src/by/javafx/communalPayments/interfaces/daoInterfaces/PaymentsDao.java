package by.javafx.communalPayments.interfaces.daoInterfaces;

import by.javafx.communalPayments.objectsPerfomance.Payments;
import javafx.collections.ObservableList;

import java.sql.SQLException;

public interface PaymentsDao {
    void add(Payments object) throws SQLException;

    void delete(Payments object) throws SQLException;

    ObservableList<Payments> getAll() throws SQLException;
}
