package by.javafx.communalPayments.daoImplements;

import by.javafx.communalPayments.interfaces.daoInterfaces.*;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class MySQLDaoFactory implements DaoFactory {

    private static Connection connection;

    public MySQLDaoFactory() {
        try {
            Class.forName("com.mysql.jdbc.Driver");

            Properties properties = new Properties();
            properties.setProperty("user", "root");
            properties.setProperty("password", "root");
            properties.setProperty("useUnicode", "true");
            properties.setProperty("characterEncoding", "UTF-8");

            connection = DriverManager.getConnection("jdbc:mysql://localhost:3306", properties);
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }

    }

    @Override
    public Connection getConnection() throws SQLException {
        return connection;
    }

    @Override
    public ObjectAccountDao getObjectAccountDao(Connection connection) {
        return new MySQLObject(connection);
    }

    @Override
    public CountersDao getCountersDao(Connection connection) {
        return new MySQLCounters(connection);
    }

    @Override
    public ServicesDao getServicesDao(Connection connection) {
        return new MySQLServices(connection);
    }

    @Override
    public PaymentsDao getPaymentsDao(Connection connection) {
        return new MySQLPayments(connection);
    }

    @Override
    public MeasurementDao getMeasurementDao(Connection connection) {
        return new MySQLMeasurement(connection);
    }

    @Override
    public FormPaymentsDao getFormPaymentsDao(Connection connection) {
        return new MySQLFormPayments(connection);
    }

}
