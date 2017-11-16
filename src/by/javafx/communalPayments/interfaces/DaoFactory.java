package by.javafx.communalPayments.interfaces;

import java.sql.Connection;
import java.sql.SQLException;

public interface DaoFactory {
    Connection getConnection() throws SQLException;
    ObjectAccountDao getObjectAccountDao();

}
