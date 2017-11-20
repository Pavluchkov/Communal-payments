package by.javafx.communalPayments.daoImplements;

import by.javafx.communalPayments.interfaces.daoInterfaces.ServicesDao;
import by.javafx.communalPayments.representationObjects.Services;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class MySQLServices implements ServicesDao {
    private Connection connection;

    public MySQLServices(Connection connection) {
        this.connection = connection;
    }

    @Override
    public void add(Services object) throws SQLException {
        PreparedStatement stmt = connection.prepareStatement("INSERT INTO services" +
                "(serviceName, unit, rate, formPayment) VALUES (?, ?, ?, ?)");

        stmt.setString(1, object.getServiceName());
        stmt.setString(2, object.getUnit());
        stmt.setDouble(3, object.getRate());
        stmt.setInt(4, object.getFormPayments());

        stmt.execute();

        stmt.close();

    }

    @Override
    public void update(Services object) throws SQLException {
        PreparedStatement stmt = connection.prepareStatement("UPDATE services SET" +
                " id=?, serviceName=?, unit=?, rate=?, formPayment=?" +
                " WHERE id=?");

        stmt.setInt(1, object.getId());
        stmt.setString(2, object.getServiceName());
        stmt.setString(3, object.getUnit());
        stmt.setDouble(4, object.getRate());
        stmt.setInt(5, object.getFormPayments());
        stmt.setInt(6, object.getId());
        stmt.execute();

        stmt.close();
    }

    @Override
    public void delete(Services object) throws SQLException {
        PreparedStatement stmt = connection.prepareStatement("DELETE FROM services WHERE id=?");
        stmt.setInt(1, object.getId());

        stmt.execute();

        stmt.close();
    }

    @Override
    public ObservableList<Services> getAll() throws SQLException {
        ObservableList<Services> objectList = FXCollections.observableArrayList();

        PreparedStatement stmt = connection.prepareStatement("SELECT * FROM " + "services");
        ResultSet rs = stmt.executeQuery();

        while (rs.next()) {
            int idService = rs.getInt(1);
            String serviceName = rs.getString(2);
            String unit = rs.getString(3);
            double rate = rs.getDouble(4);
            int formPayments = rs.getInt(5);
            objectList.add(new Services(idService, serviceName, unit, rate, formPayments));
        }

        rs.close();
        stmt.close();

        return objectList;
    }
}
