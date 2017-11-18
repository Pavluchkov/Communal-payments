package by.javafx.communalPayments.connectionImplements;

import by.javafx.communalPayments.interfaces.connectionInterfaces.ConnectionBuilder;

import java.sql.*;
import java.util.Properties;

public class MySQLSimpleConnection implements ConnectionBuilder {

    public MySQLSimpleConnection() {
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
    public void availabilityCheckDatabase(Connection connection) throws SQLException {
        PreparedStatement stmt = connection.prepareStatement("CREATE SCHEMA IF NOT EXISTS `communalpayments`");
        stmt.execute();

        stmt = connection.prepareStatement("USE `communalpayments`");
        stmt.execute();

        stmt = connection.prepareStatement("CREATE TABLE IF NOT EXISTS `accountingobject` (" +
                "`personalAccount` INT(11) NOT NULL," +
                "`objectName` VARCHAR(50) NOT NULL," +
                "`owner` VARCHAR(255) DEFAULT NULL," +
                "`address` VARCHAR(255) DEFAULT NULL," +
                "`residents` INT(11) DEFAULT NULL," +
                "`area` DOUBLE DEFAULT NULL," +
                "PRIMARY KEY (`personalAccount`)," +
                "UNIQUE KEY `objectName_UNIQUE` (`objectName`)) ENGINE=InnoDB DEFAULT CHARSET=utf8;");
        stmt.execute();

        stmt = connection.prepareStatement("CREATE TABLE IF NOT EXISTS `formpayments` (" +
                "`id` INT(11) NOT NULL AUTO_INCREMENT," +
                "`form` VARCHAR(255) NOT NULL," +
                "PRIMARY KEY (`id`)) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8;");
        stmt.execute();

        stmt = connection.prepareStatement("SELECT 1 FROM formpayments;");
        ResultSet rs = stmt.executeQuery();

        if (!rs.next()) {
            stmt = connection.prepareStatement("INSERT INTO formpayments(id, form) VALUES (?, ?) ");
            stmt.setInt(1, 1);
            stmt.setString(2, "По счетчику");
            stmt.execute();
            stmt = connection.prepareStatement("INSERT INTO formpayments(id, form) VALUES (?, ?)");
            stmt.setInt(1, 2);
            stmt.setString(2, "По площади");
            stmt.execute();
            stmt = connection.prepareStatement("INSERT INTO formpayments(id, form) VALUES (?, ?)");
            stmt.setInt(1, 3);
            stmt.setString(2, "По количеству жильцов");
            stmt.execute();

        }

        stmt = connection.prepareStatement("CREATE TABLE IF NOT EXISTS `services` (" +
                "`id` INT(11) NOT NULL AUTO_INCREMENT," +
                "`serviceName` VARCHAR(255) NOT NULL," +
                "`unit` VARCHAR(20) NOT NULL," +
                "`rate` DOUBLE DEFAULT NULL," +
                "`formPayment` INT(11) NOT NULL," +
                "PRIMARY KEY (`id`)," +
                "UNIQUE KEY `serviceName_UNIQUE` (`serviceName`)," +
                "KEY `services-formPayments_idx` (`formPayment`)," +
                "CONSTRAINT `services-formPayments` FOREIGN KEY (`formPayment`) REFERENCES `formpayments` (`id`) ON DELETE CASCADE ON UPDATE CASCADE) " +
                "ENGINE=InnoDB AUTO_INCREMENT=14 DEFAULT CHARSET=utf8;");
        stmt.execute();

        stmt = connection.prepareStatement("CREATE TABLE IF NOT EXISTS `counters` (" +
                "`id` INT(11) NOT NULL AUTO_INCREMENT," +
                "`object` INT(11) NOT NULL," +
                "`service` INT(11) NOT NULL," +
                "`counterName` VARCHAR(50) NOT NULL," +
                "`recentMeasure` DOUBLE NOT NULL," +
                "PRIMARY KEY (`id`)," +
                "UNIQUE KEY `counterName_UNIQUE` (`counterName`)," +
                "KEY `counters-object_idx` (`object`)," +
                "KEY `counters-services_idx` (`service`)," +
                "CONSTRAINT `counters-object` FOREIGN KEY (`object`) REFERENCES `accountingobject` (`personalAccount`) ON DELETE CASCADE ON UPDATE CASCADE," +
                "CONSTRAINT `counters-services` FOREIGN KEY (`service`) REFERENCES `services` (`id`) ON DELETE CASCADE ON UPDATE CASCADE) " +
                "ENGINE=InnoDB AUTO_INCREMENT=23 DEFAULT CHARSET=utf8;");
        stmt.execute();

        stmt = connection.prepareStatement("CREATE TABLE IF NOT EXISTS `measurement` (" +
                "`id` INT(11) NOT NULL AUTO_INCREMENT," +
                "`counter` INT(11) NOT NULL," +
                "`measure` DOUBLE NOT NULL," +
                "`date` DATE NOT NULL," +
                "PRIMARY KEY (`id`)," +
                "KEY `measurement-counters_idx` (`counter`)," +
                "CONSTRAINT `measurement-counters` FOREIGN KEY (`counter`) REFERENCES `counters` (`id`) ON DELETE CASCADE ON UPDATE CASCADE) " +
                "ENGINE=InnoDB AUTO_INCREMENT=75 DEFAULT CHARSET=utf8;");
        stmt.execute();

        stmt = connection.prepareStatement("CREATE TABLE IF NOT EXISTS `payments` (" +
                "`id` INT(11) NOT NULL AUTO_INCREMENT," +
                "`object` INT(11) NOT NULL," +
                "`service` INT(11) NOT NULL," +
                "`unit` VARCHAR(20) NOT NULL," +
                "`volume` DOUBLE NOT NULL," +
                "`rate` DOUBLE NOT NULL," +
                "`accrued` DOUBLE NOT NULL," +
                "`paid` DOUBLE NOT NULL," +
                "`date` DATE NOT NULL," +
                "PRIMARY KEY (`id`)," +
                "KEY `payments-object_idx` (`object`)," +
                "KEY `payments-service_idx` (`service`)," +
                "CONSTRAINT `payments-object` FOREIGN KEY (`object`) REFERENCES `accountingobject` (`personalAccount`) ON DELETE CASCADE ON UPDATE CASCADE," +
                "CONSTRAINT `payments-service` FOREIGN KEY (`service`) REFERENCES `services` (`id`) ON DELETE CASCADE ON UPDATE CASCADE) " +
                "ENGINE=InnoDB AUTO_INCREMENT=36 DEFAULT CHARSET=utf8;");
        stmt.execute();

        stmt.close();
    }
}
