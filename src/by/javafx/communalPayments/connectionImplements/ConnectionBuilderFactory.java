package by.javafx.communalPayments.connectionImplements;

import by.javafx.communalPayments.interfaces.connectionInterfaces.ConnectionBuilder;

public class ConnectionBuilderFactory {
    public static ConnectionBuilder getSimpleConnection() {
        return new MySQLSimpleConnection();
    }
}
