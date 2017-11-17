package by.javafx.communalPayments.interfaces.daoInterfaces;

import java.sql.Connection;
import java.sql.SQLException;

public interface DaoFactory {
    Connection getConnection() throws SQLException;

    ObjectAccountDao getObjectAccountDao(Connection connection);

    CountersDao getCountersDao(Connection connection);

    ServicesDao getServicesDao(Connection connection);

    PaymentsDao getPaymentsDao(Connection connection);

    MeasurementDao getMeasurementDao(Connection connection);

    FormPaymentsDao getFormPaymentsDao(Connection connection);
}
