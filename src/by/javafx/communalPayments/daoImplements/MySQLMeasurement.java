package by.javafx.communalPayments.daoImplements;

import by.javafx.communalPayments.interfaces.daoInterfaces.MeasurementDao;
import by.javafx.communalPayments.objects.Counters;
import by.javafx.communalPayments.objects.Measurement;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class MySQLMeasurement implements MeasurementDao {
    private Connection connection;

    public MySQLMeasurement(Connection connection) {
        this.connection = connection;
    }

    @Override
    public void add(Measurement object) throws SQLException {
        PreparedStatement stmt = connection.prepareStatement("INSERT INTO measurement" +
                "(counter, measure, date) VALUES (?, ?, ?)");

        stmt.setInt(1, object.getCounter());
        stmt.setDouble(2, object.getMeasure());
        stmt.setDate(3, object.getDate());

        stmt.execute();

        stmt.close();
    }

    @Override
    public void changeLastMeasure(Counters object, double lastMeasure) throws SQLException {
        PreparedStatement stmt = connection.prepareStatement("UPDATE measurement SET" +
                " measure=?" + " WHERE counter=?" + " AND measure=?");

        stmt.setDouble(1, object.getRecentMeasure());
        stmt.setInt(2, object.getId());
        stmt.setDouble(3, lastMeasure);
        stmt.execute();

        stmt.close();
    }
}
