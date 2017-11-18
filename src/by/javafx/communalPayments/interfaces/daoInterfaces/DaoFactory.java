package by.javafx.communalPayments.interfaces.daoInterfaces;

import java.sql.Connection;

public interface DaoFactory {

    ObjectAccountDao getObjectAccountDao(Connection connection);

    CountersDao getCountersDao(Connection connection);

    ServicesDao getServicesDao(Connection connection);

    PaymentsDao getPaymentsDao(Connection connection);

    MeasurementDao getMeasurementDao(Connection connection);

    FormPaymentsDao getFormPaymentsDao(Connection connection);
}
