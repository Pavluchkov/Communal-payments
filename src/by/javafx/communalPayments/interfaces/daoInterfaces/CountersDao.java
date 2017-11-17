package by.javafx.communalPayments.interfaces.daoInterfaces;

import by.javafx.communalPayments.objects.Counters;
import javafx.collections.ObservableList;

import java.sql.SQLException;

public interface CountersDao {
    void add(Counters object) throws SQLException;
    void update(Counters object) throws SQLException;
    void delete(Counters object) throws SQLException;
    ObservableList<Counters> getAll(Counters object) throws SQLException;
}
