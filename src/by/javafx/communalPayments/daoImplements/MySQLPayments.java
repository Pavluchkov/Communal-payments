package by.javafx.communalPayments.daoImplements;

import by.javafx.communalPayments.interfaces.daoInterfaces.PaymentsDao;
import by.javafx.communalPayments.objects.Payments;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.*;

public class MySQLPayments implements PaymentsDao {
    private Connection connection;

    public MySQLPayments(Connection connection) {
        this.connection = connection;
    }

    @Override
    public void add(Payments object) throws SQLException {
        PreparedStatement stmt = connection.prepareStatement("INSERT INTO payments" +
                "(object, service, unit, volume, rate, accrued, paid, date) VALUES (?, ?, ?, ?, ?, ?, ?, ?)");

        stmt.setInt(1, object.getObject());
        stmt.setInt(2, object.getService());
        stmt.setString(3, object.getUnit());
        stmt.setDouble(4, object.getVolume());
        stmt.setDouble(5, object.getRate());
        stmt.setDouble(6, object.getAccrued());
        stmt.setDouble(7, object.getPaid());
        stmt.setDate(8, object.getDate());

        stmt.execute();

        stmt.close();

    }

    @Override
    public void delete(Payments object) throws SQLException {
        PreparedStatement stmt = connection.prepareStatement("DELETE FROM payments WHERE id=?");
        stmt.setInt(1, object.getId());

        stmt.execute();

        stmt.close();
    }

    @Override
    public ObservableList<Payments> getAll(Payments object) throws SQLException {
        ObservableList<Payments> objectList = FXCollections.observableArrayList();

        PreparedStatement stmt = connection.prepareStatement("SELECT * FROM " + "payments");
        ResultSet rs = stmt.executeQuery();

        while (rs.next()) {
            int id = rs.getInt(1);
            int objectId = rs.getInt(2);
            int service = rs.getInt(3);
            String unit = rs.getString(4);
            double volume = rs.getDouble(5);
            double rate = rs.getDouble(6);
            double accrued = rs.getDouble(7);
            double paid = rs.getDouble(8);
            Date date = rs.getDate(9);
            objectList.add(new Payments(id, objectId, service, unit, volume, rate, accrued, paid, date));
        }

        rs.close();
        stmt.close();

        return objectList;
    }
}
