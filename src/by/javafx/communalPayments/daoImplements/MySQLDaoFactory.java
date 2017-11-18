package by.javafx.communalPayments.daoImplements;

import by.javafx.communalPayments.interfaces.daoInterfaces.*;

import java.sql.Connection;

public class MySQLDaoFactory implements DaoFactory {

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
