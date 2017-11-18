package by.javafx.communalPayments.interfaces.connectionInterfaces;

import java.sql.Connection;
import java.sql.SQLException;

public interface ConnectionBuilder {
    Connection getConnection() throws SQLException;

    void availabilityCheckDatabase(Connection connection) throws SQLException;
}
