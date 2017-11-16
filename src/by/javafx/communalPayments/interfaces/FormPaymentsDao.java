package by.javafx.communalPayments.interfaces;

import by.javafx.communalPayments.objects.FormPayments;
import javafx.collections.ObservableList;

import java.sql.SQLException;

public interface FormPaymentsDao {
    ObservableList<FormPayments> getAll(FormPayments object) throws SQLException;
}
