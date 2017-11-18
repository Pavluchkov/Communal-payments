package by.javafx.communalPayments.interfaces.daoInterfaces;

import by.javafx.communalPayments.objectsPerfomance.FormPayments;
import javafx.collections.ObservableList;

import java.sql.SQLException;

public interface FormPaymentsDao {
    ObservableList<FormPayments> getAll() throws SQLException;
}
