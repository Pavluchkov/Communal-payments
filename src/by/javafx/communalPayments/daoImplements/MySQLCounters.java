package by.javafx.communalPayments.daoImplements;

import by.javafx.communalPayments.interfaces.daoInterfaces.CountersDao;
import by.javafx.communalPayments.objects.Counters;
import javafx.collections.ObservableList;

import java.sql.Connection;
import java.sql.SQLException;

public class MySQLCounters implements CountersDao{
    private Connection connection;

    public MySQLCounters(Connection connection) {
        this.connection = connection;
    }

    @Override
    public void add(Counters object) throws SQLException {

    }

    @Override
    public void update(Counters object) throws SQLException {

    }

    @Override
    public void delete(Counters object) throws SQLException {

    }

    @Override
    public ObservableList<Counters> getAll(Counters object) throws SQLException {
        return null;
    }
}
