package by.javafx.communalPayments.objects;

import by.javafx.communalPayments.interfaces.*;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class MySQLDaoFactory implements DaoFactory{

    public MySQLDaoFactory() {
        try {
            Class.forName("com.mysql.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    @Override
    public Connection getConnection() throws SQLException {

        Properties properties = new Properties();
        properties.setProperty("user", "root");
        properties.setProperty("password", "root");
        properties.setProperty("useUnicode", "true");
        properties.setProperty("characterEncoding", "UTF-8");

        return DriverManager.getConnection("jdbc:mysql://localhost:3306", properties);
    }

    @Override
    public ObjectAccountDao getObjectAccountDao(Connection connection) {
        return null;
    }

    @Override
    public CountersDao getCountersDao(Connection connection) {
        return null;
    }

    @Override
    public ServicesDao getServicesDao(Connection connection) {
        return null;
    }

    @Override
    public PaymentsDao getPaymentsDao(Connection connection) {
        return null;
    }

    @Override
    public MeasurementDao getMeasurementDao(Connection connection) {
        return null;
    }

    @Override
    public FormPaymentsDao getFormPaymentsDao(Connection connection) {
        return null;
    }
}
