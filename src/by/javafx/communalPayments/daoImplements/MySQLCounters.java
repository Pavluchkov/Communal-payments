package by.javafx.communalPayments.daoImplements;

import by.javafx.communalPayments.interfaces.daoInterfaces.CountersDao;
import by.javafx.communalPayments.representationObjects.Counters;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class MySQLCounters implements CountersDao {
    private Connection connection;

    public MySQLCounters(Connection connection) {
        this.connection = connection;
    }

    @Override
    public void add(Counters object) throws SQLException {
        PreparedStatement stmt = connection.prepareStatement("INSERT INTO counters" +
                "(object, service, counterName, recentMeasure) VALUES (?, ?, ?, ?)");

        stmt.setInt(1, object.getObject());
        stmt.setInt(2, object.getService());
        stmt.setString(3, object.getCounterName());
        stmt.setDouble(4, object.getRecentMeasure());

        stmt.execute();

        stmt.close();
    }

    @Override
    public void update(Counters object) throws SQLException {
        PreparedStatement stmt = connection.prepareStatement("UPDATE counters SET" +
                " id=?, object=?, service=?, counterName=?, recentMeasure=?" +
                " WHERE id=?");

        stmt.setInt(1, object.getId());
        stmt.setInt(2, object.getObject());
        stmt.setInt(3, object.getService());
        stmt.setString(4, object.getCounterName());
        stmt.setDouble(5, object.getRecentMeasure());
        stmt.setInt(6, object.getId());

        stmt.execute();
    }

    @Override
    public void delete(Counters object) throws SQLException {
        PreparedStatement stmt = connection.prepareStatement("DELETE FROM counters WHERE id=?");
        stmt.setInt(1, object.getId());

        stmt.execute();

        stmt.close();
    }

    @Override
    public ObservableList<Counters> getAll() throws SQLException {
        ObservableList<Counters> objectList = FXCollections.observableArrayList();

        PreparedStatement stmt = connection.prepareStatement("SELECT * FROM " + "counters");
        ResultSet rs = stmt.executeQuery();

        while (rs.next()) {
            int id = rs.getInt(1);
            int objectId = rs.getInt(2);
            int service = rs.getInt(3);
            String counterName = rs.getString(4);
            double recentMeasure = rs.getDouble(5);
            objectList.add(new Counters(id, objectId, service, counterName, recentMeasure));
        }

        rs.close();
        stmt.close();

        return objectList;
    }
}
