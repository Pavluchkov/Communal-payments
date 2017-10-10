package by.javafx.communalPayments.objects;

import by.javafx.communalPayments.interfaces.IDatabase;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.*;
import java.util.Properties;

public class MySQLDatabase implements IDatabase {
    private static Connection con;

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
    public ObservableList<ObjectAccounting> getObjectsList() throws SQLException {

        ObservableList<ObjectAccounting> objectList = FXCollections.observableArrayList();

        PreparedStatement stmt = con.prepareStatement("SELECT * FROM accountingobject");
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


        if (rs != null) {
            rs.close();
        }

        if (stmt != null) {
            stmt.close();
        }

        return objectList;
    }

    @Override
    public ObservableList<Counters> getCountersList() throws SQLException {
        ObservableList<Counters> objectList = FXCollections.observableArrayList();
        PreparedStatement stmt = con.prepareStatement("SELECT * FROM counters");
        ResultSet rs = stmt.executeQuery();

        int idCounters;
        String counterName;
        int service;
        int object;

        while (rs.next()) {
            idCounters = rs.getInt(1);
            counterName = rs.getString(2);
            service = rs.getInt(3);
            object = rs.getInt(4);
            objectList.add(new Counters(idCounters, counterName, service, object));
        }

        if (rs != null) {
            rs.close();
        }

        if (stmt != null) {
            stmt.close();
        }

        return objectList;
    }

    @Override
    public ObservableList<Payments> getPaymentsList() throws SQLException {
        ObservableList<Payments> objectList = FXCollections.observableArrayList();
        PreparedStatement stmt = con.prepareStatement("SELECT * FROM payments");
        ResultSet rs = stmt.executeQuery();

        int id_payments;
        int service_id;
        double valuePayments;
        String datePayments;

        while (rs.next()) {
            id_payments = rs.getInt(1);
            service_id = rs.getInt(2);
            valuePayments = rs.getDouble(3);
            datePayments = rs.getString(4);
            objectList.add(new Payments(id_payments, service_id, valuePayments, datePayments));
        }

        if (rs != null) {
            rs.close();
        }

        if (stmt != null) {
            stmt.close();
        }

        return objectList;
    }

    @Override
    public ObservableList<ServiceList> getServiceList() throws SQLException {
        ObservableList<ServiceList> objectList = FXCollections.observableArrayList();
        PreparedStatement stmt = con.prepareStatement("SELECT * FROM services");
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
            objectList.add(new ServiceList(idService, serviceName, unit, rate, formPayments));
        }

        if (rs != null) {
            rs.close();
        }

        if (stmt != null) {
            stmt.close();
        }

        return objectList;
    }

    @Override
    public void addObject(ObjectAccounting object) throws SQLException {

        PreparedStatement stmt = con.prepareStatement("INSERT INTO accountingobject" +
                "(personalAccount, objectName, owner, address, residents, area) VALUES (?, ?, ?, ?, ?, ?)");

        stmt.setInt(1, object.getId());
        stmt.setString(2, object.getObjectName());
        stmt.setString(3, object.getOwner());
        stmt.setString(4, object.getAddress());
        stmt.setInt(5, object.getResidents());
        stmt.setDouble(6, object.getArea());
        stmt.execute();

        if (stmt != null) {
            stmt.close();
        }

    }

    @Override
    public void addCounter(Counters objects) throws SQLException {

    }

    @Override
    public void addPayment(Payments objects) throws SQLException {

    }

    @Override
    public void addService(ServiceList objects) throws SQLException {

    }

    @Override
    public void deleteObject(ObjectAccounting object) throws SQLException {
        PreparedStatement stmt = con.prepareStatement("DELETE FROM accountingobject WHERE personalAccount=?");
        stmt.setInt(1, object.getId());

        stmt.execute();

        if (stmt != null) {
            stmt.close();
        }
    }

    @Override
    public void deleteCounter(Counters objects) throws SQLException {

    }

    @Override
    public void deletePayment(Payments objects) throws SQLException {

    }

    @Override
    public void deleteService(ServiceList objects) throws SQLException {

    }

    @Override
    public void changeObject(ObjectAccounting object, int id) throws SQLException {
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

        if (stmt != null) {
            stmt.close();
        }
    }

    @Override
    public void changeCounter(Counters objects) throws SQLException {

    }

    @Override
    public void changePayment(Payments objects) throws SQLException {

    }

    @Override
    public void changeService(ServiceList objects) throws SQLException {

    }

}
