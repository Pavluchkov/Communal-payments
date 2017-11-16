package by.javafx.communalPayments.interfaces;

import by.javafx.communalPayments.objects.Services;
import javafx.collections.ObservableList;

import java.sql.SQLException;

public interface ServicesDao {
    void add(Services object) throws SQLException;
    void update(Services object) throws SQLException;
    void delete(Services object) throws SQLException;
    ObservableList<Services> getAll(Services object) throws SQLException;
}
