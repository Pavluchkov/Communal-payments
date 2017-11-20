package by.javafx.communalPayments.interfaces.daoInterfaces;

import by.javafx.communalPayments.representationObjects.FormPayments;
import javafx.collections.ObservableList;

import java.sql.SQLException;

public interface FormPaymentsDao {
    ObservableList<FormPayments> getAll() throws SQLException;
}
