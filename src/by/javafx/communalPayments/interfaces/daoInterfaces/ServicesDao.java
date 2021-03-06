package by.javafx.communalPayments.interfaces.daoInterfaces;

import by.javafx.communalPayments.representationObjects.Services;
import javafx.collections.ObservableList;

import java.sql.SQLException;

public interface ServicesDao {
    void add(Services object) throws SQLException;

    void update(Services object) throws SQLException;

    void delete(Services object) throws SQLException;

    ObservableList<Services> getAll() throws SQLException;
}
