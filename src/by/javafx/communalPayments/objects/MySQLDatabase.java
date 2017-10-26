package by.javafx.communalPayments.objects;

import by.javafx.communalPayments.interfaces.IDatabase;
import by.javafx.communalPayments.interfaces.Observer;
import by.javafx.communalPayments.interfaces.Subject;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.*;
import java.util.ArrayList;
import java.util.Properties;

public class MySQLDatabase implements IDatabase, Subject {
    private static Connection con;
    private static ArrayList<Observer> observers = new ArrayList<>();
    private static MySQLDatabase instance = new MySQLDatabase();

    private MySQLDatabase() {
    }

    public static MySQLDatabase getInstance() {
        return instance;
    }

    @Override
    public void setConnectDatabase(String connectionString) throws ClassNotFoundException, SQLException {

        Class.forName("com.mysql.jdbc.Driver");
        Properties properties = new Properties();
        properties.setProperty("user", "root");
        properties.setProperty("password", "Nikitenko");
        properties.setProperty("useUnicode", "true");
        properties.setProperty("characterEncoding", "UTF-8");
        con = DriverManager.getConnection(connectionString, properties);
    }

    @Override
    public void closeConnect() throws SQLException {

        if (con != null) {
            con.close();
        }
    }

    @Override
    public void availabilityCheckDatabase() throws SQLException {

        PreparedStatement stmt = con.prepareStatement("CREATE SCHEMA IF NOT EXISTS `communalpayments`");
        stmt.execute();
        stmt = con.prepareStatement("USE `communalpayments`");
        stmt.execute();

        stmt = con.prepareStatement("CREATE TABLE IF NOT EXISTS `accountingobject` (" +
                "`personalAccount` INT(11) NOT NULL," +
                "`objectName` VARCHAR(50) NOT NULL," +
                "`owner` VARCHAR(255) DEFAULT NULL," +
                "`address` VARCHAR(255) DEFAULT NULL," +
                "`residents` INT(11) DEFAULT NULL," +
                "`area` DOUBLE DEFAULT NULL," +
                "PRIMARY KEY (`personalAccount`)," +
                "UNIQUE KEY `objectName_UNIQUE` (`objectName`)) ENGINE=InnoDB DEFAULT CHARSET=utf8;");
        stmt.execute();

        stmt = con.prepareStatement("CREATE TABLE IF NOT EXISTS `formpayments` (" +
                "`id` INT(11) NOT NULL AUTO_INCREMENT," +
                "`form` VARCHAR(255) NOT NULL," +
                "PRIMARY KEY (`id`)) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8;");
        stmt.execute();

        stmt = con.prepareStatement("SELECT 1 FROM formpayments;");
        ResultSet rs = stmt.executeQuery();

        if (!rs.next()) {
            stmt = con.prepareStatement("INSERT INTO formpayments(id, form) VALUES (?, ?) ");
            stmt.setInt(1, 1);
            stmt.setString(2, "По счетчику");
            stmt.execute();
            stmt = con.prepareStatement("INSERT INTO formpayments(id, form) VALUES (?, ?)");
            stmt.setInt(1, 2);
            stmt.setString(2, "По площади");
            stmt.execute();
            stmt = con.prepareStatement("INSERT INTO formpayments(id, form) VALUES (?, ?)");
            stmt.setInt(1, 3);
            stmt.setString(2, "По количеству жильцов");
            stmt.execute();

        }


        stmt = con.prepareStatement("CREATE TABLE IF NOT EXISTS `services` (" +
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

        stmt = con.prepareStatement("CREATE TABLE IF NOT EXISTS `counters` (" +
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

        stmt = con.prepareStatement("CREATE TABLE IF NOT EXISTS `measurement` (" +
                "`id` INT(11) NOT NULL AUTO_INCREMENT," +
                "`counter` INT(11) NOT NULL," +
                "`measure` DOUBLE NOT NULL," +
                "`date` DATE NOT NULL," +
                "PRIMARY KEY (`id`)," +
                "KEY `measurement-counters_idx` (`counter`)," +
                "CONSTRAINT `measurement-counters` FOREIGN KEY (`counter`) REFERENCES `counters` (`id`) ON DELETE CASCADE ON UPDATE CASCADE) " +
                "ENGINE=InnoDB AUTO_INCREMENT=75 DEFAULT CHARSET=utf8;");
        stmt.execute();

        stmt = con.prepareStatement("CREATE TABLE IF NOT EXISTS `payments` (" +
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

    @Override
    public ObservableList<ObjectAccounting> getListObjects(ObjectAccounting object) throws SQLException {
        ObservableList<ObjectAccounting> objectList = FXCollections.observableArrayList();
        PreparedStatement stmt = con.prepareStatement("SELECT * FROM " + "accountingobject");
        ResultSet rs = stmt.executeQuery();

        int id;
        String objectName;
        String owner;
        String address;
        int residents;
        double area;

        while (rs.next()) {
            id = rs.getInt(1);
            objectName = rs.getString(2);
            owner = rs.getString(3);
            address = rs.getString(4);
            residents = rs.getInt(5);
            area = rs.getDouble(6);
            objectList.add(new ObjectAccounting(id, objectName, owner, address, residents, area));
        }

        rs.close();
        stmt.close();

        return objectList;

    }

    @Override
    public ObservableList<Counters> getListObjects(Counters object) throws SQLException {
        ObservableList<Counters> objectList = FXCollections.observableArrayList();
        PreparedStatement stmt = con.prepareStatement("SELECT * FROM " + "counters");
        ResultSet rs = stmt.executeQuery();

        int id;
        String counterName;
        int service;
        int objectId;
        double recentMeasure;

        while (rs.next()) {
            id = rs.getInt(1);
            objectId = rs.getInt(2);
            service = rs.getInt(3);
            counterName = rs.getString(4);
            recentMeasure = rs.getDouble(5);
            objectList.add(new Counters(id, objectId, service, counterName, recentMeasure));
        }

        rs.close();
        stmt.close();

        return objectList;
    }

    @Override
    public ObservableList<Payments> getListObjects(Payments object) throws SQLException {
        ObservableList<Payments> objectList = FXCollections.observableArrayList();
        PreparedStatement stmt = con.prepareStatement("SELECT * FROM " + "payments");
        ResultSet rs = stmt.executeQuery();

        int id;
        int objectId;
        int service;
        String unit;
        double volume;
        double rate;
        double accrued;
        double paid;
        Date date;

        while (rs.next()) {
            id = rs.getInt(1);
            objectId = rs.getInt(2);
            service = rs.getInt(3);
            unit = rs.getString(4);
            volume = rs.getDouble(5);
            rate = rs.getDouble(6);
            accrued = rs.getDouble(7);
            paid = rs.getDouble(8);
            date = rs.getDate(9);
            objectList.add(new Payments(id, objectId, service, unit, volume, rate, accrued, paid, date));
        }

        rs.close();
        stmt.close();

        return objectList;
    }

    @Override
    public ObservableList<Services> getListObjects(Services object) throws SQLException {
        ObservableList<Services> objectList = FXCollections.observableArrayList();
        PreparedStatement stmt = con.prepareStatement("SELECT * FROM " + "services");
        ResultSet rs = stmt.executeQuery();

        int idService;
        String serviceName;
        String unit;
        double rate;
        int formPayments;

        while (rs.next()) {
            idService = rs.getInt(1);
            serviceName = rs.getString(2);
            unit = rs.getString(3);
            rate = rs.getDouble(4);
            formPayments = rs.getInt(5);
            objectList.add(new Services(idService, serviceName, unit, rate, formPayments));
        }

        rs.close();
        stmt.close();

        return objectList;
    }

    @Override
    public ObservableList<FormPayments> getListObjects(FormPayments object) throws SQLException {
        ObservableList<FormPayments> objectList = FXCollections.observableArrayList();
        PreparedStatement stmt = con.prepareStatement("SELECT * FROM " + "formpayments");
        ResultSet rs = stmt.executeQuery();

        int id;
        String form;

        while (rs.next()) {
            id = rs.getInt(1);
            form = rs.getString(2);
            objectList.add(new FormPayments(id, form));
        }

        rs.close();
        stmt.close();

        return objectList;
    }

    @Override
    public void add(ObjectAccounting object) throws SQLException {

        PreparedStatement stmt = con.prepareStatement("INSERT INTO accountingobject" +
                "(personalAccount, objectName, owner, address, residents, area) VALUES (?, ?, ?, ?, ?, ?)");

        stmt.setInt(1, object.getId());
        stmt.setString(2, object.getObjectName());
        stmt.setString(3, object.getOwner());
        stmt.setString(4, object.getAddress());
        stmt.setInt(5, object.getResidents());
        stmt.setDouble(6, object.getArea());
        stmt.execute();

        stmt.close();
        dataChange();
    }

    @Override
    public void add(Counters object) throws SQLException {
        PreparedStatement stmt = con.prepareStatement("INSERT INTO counters" +
                "(object, service, counterName, recentMeasure) VALUES (?, ?, ?, ?)");

        stmt.setInt(1, object.getObject());
        stmt.setInt(2, object.getService());
        stmt.setString(3, object.getCounterName());
        stmt.setDouble(4, object.getRecentMeasure());

        stmt.execute();

        stmt.close();
        dataChange();
    }

    @Override
    public void add(Payments object) throws SQLException {
        PreparedStatement stmt = con.prepareStatement("INSERT INTO payments" +
                "(object, service, unit, volume, rate, accrued, paid, date) VALUES (?, ?, ?, ?, ?, ?, ?, ?)");

        stmt.setInt(1, object.getObject());
        stmt.setInt(2, object.getService());
        stmt.setString(3, object.getUnit());
        stmt.setDouble(4, object.getVolume());
        stmt.setDouble(5, object.getRate());
        stmt.setDouble(6, object.getAccrued());
        stmt.setDouble(7, object.getPaid());
        stmt.setDate(8, object.getDate());

        stmt.execute();

        stmt.close();
        dataChange();
    }

    @Override
    public void add(Services object) throws SQLException {
        PreparedStatement stmt = con.prepareStatement("INSERT INTO services" +
                "(serviceName, unit, rate, formPayment) VALUES (?, ?, ?, ?)");

        stmt.setString(1, object.getServiceName());
        stmt.setString(2, object.getUnit());
        stmt.setDouble(3, object.getRate());
        stmt.setInt(4, object.getFormPayments());

        stmt.execute();

        stmt.close();
        dataChange();
    }

    @Override
    public void add(Measurement object) throws SQLException {

        PreparedStatement stmt = con.prepareStatement("INSERT INTO measurement" +
                "(counter, measure, date) VALUES (?, ?, ?)");

        stmt.setInt(1, object.getCounter());
        stmt.setDouble(2, object.getMeasure());
        stmt.setDate(3, object.getDate());

        stmt.execute();

        stmt.close();
    }

    @Override
    public void delete(ObjectAccounting object) throws SQLException {
        PreparedStatement stmt = con.prepareStatement("DELETE FROM accountingobject WHERE personalAccount=?");
        stmt.setInt(1, object.getId());

        stmt.execute();

        stmt.close();
        dataChange();
    }

    @Override
    public void delete(Counters object) throws SQLException {
        PreparedStatement stmt = con.prepareStatement("DELETE FROM counters WHERE id=?");
        stmt.setInt(1, object.getId());

        stmt.execute();

        stmt.close();
        dataChange();
    }

    @Override
    public void delete(Payments object) throws SQLException {
        PreparedStatement stmt = con.prepareStatement("DELETE FROM payments WHERE id=?");
        stmt.setInt(1, object.getId());

        stmt.execute();

        stmt.close();
        dataChange();
    }

    @Override
    public void delete(Services object) throws SQLException {
        PreparedStatement stmt = con.prepareStatement("DELETE FROM services WHERE id=?");
        stmt.setInt(1, object.getId());

        stmt.execute();

        stmt.close();
        dataChange();
    }

    @Override
    public void change(ObjectAccounting object, int id) throws SQLException {
        PreparedStatement stmt = con.prepareStatement("UPDATE accountingobject SET" +
                " personalAccount=?, objectName=?, owner=?, address=?, residents=?, area=?" +
                " WHERE personalAccount=?");
        stmt.setInt(1, object.getId());
        stmt.setString(2, object.getObjectName());
        stmt.setString(3, object.getOwner());
        stmt.setString(4, object.getAddress());
        stmt.setInt(5, object.getResidents());
        stmt.setDouble(6, object.getArea());
        stmt.setInt(7, id);
        stmt.execute();

        stmt.close();
        dataChange();
    }

    @Override
    public void change(Counters object) throws SQLException {
        PreparedStatement stmt = con.prepareStatement("UPDATE counters SET" +
                " id=?, object=?, service=?, counterName=?, recentMeasure=?" +
                " WHERE id=?");
        stmt.setInt(1, object.getId());
        stmt.setInt(2, object.getObject());
        stmt.setInt(3, object.getService());
        stmt.setString(4, object.getCounterName());
        stmt.setDouble(5, object.getRecentMeasure());
        stmt.setInt(6, object.getId());

        stmt.execute();

        dataChange();
    }

    @Override
    public void change(Services object) throws SQLException {
        PreparedStatement stmt = con.prepareStatement("UPDATE services SET" +
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
        dataChange();
    }

    @Override
    public void changeLastMeasure(Counters object, double lastMeasure) throws SQLException {
//        PreparedStatement stmt = con.prepareStatement("UPDATE measurement SET" +
//                " measure=?" + " WHERE counter=? AND id=LAST_INSERT_ID()");
        PreparedStatement stmt = con.prepareStatement("UPDATE measurement SET" +
                " measure=?" + " WHERE counter=?" + " AND measure=?");
        stmt.setDouble(1, object.getRecentMeasure());
        stmt.setInt(2, object.getId());
        stmt.setDouble(3, lastMeasure);
        stmt.execute();

        stmt.close();

    }

    @Override
    public void registerObserver(Observer o) {
        observers.add(o);
    }

    @Override
    public void removeObserver(Observer o) {
        int i = observers.indexOf(o);

        if (i >= 0) {
            observers.remove(i);
        }
    }

    @Override
    public void notifyObserver() {

        for (Observer o : observers) {
            o.update();
        }

    }

    private void dataChange() {
        notifyObserver();
    }
}
