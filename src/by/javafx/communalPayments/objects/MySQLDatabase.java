package by.javafx.communalPayments.objects;

import by.javafx.communalPayments.interfaces.IDatabase;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.*;
import java.util.ArrayList;
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
    public ObservableList<MyObjects> getListObjects(MyObjects myObject) throws SQLException {

        ObservableList<MyObjects> objectList = FXCollections.observableArrayList();
        PreparedStatement stmt = null;
        ResultSet rs = null;

        if(myObject instanceof ObjectAccounting){
            int id;
            String objectName;
            String owner;
            String address;
            int residents;
            double area;

            stmt = con.prepareStatement("SELECT * FROM " + "accountingobject");
            rs = stmt.executeQuery();

            while (rs.next()) {
                id = rs.getInt(1);
                objectName = rs.getString(2);
                owner = rs.getString(3);
                address = rs.getString(4);
                residents = rs.getInt(5);
                area = rs.getDouble(6);
                objectList.add(new ObjectAccounting(id, objectName, owner, address, residents, area));
            }

        }

        if(myObject instanceof Counters){
            int idCounters;
            String counterName;
            int service;
            int object;

            stmt = con.prepareStatement("SELECT * FROM " + "counters");
            rs = stmt.executeQuery();

            while (rs.next()) {
                idCounters = rs.getInt(1);
                counterName = rs.getString(2);
                service = rs.getInt(3);
                object = rs.getInt(4);
                objectList.add(new Counters(idCounters, counterName, service, object));
            }
        }

        if(myObject instanceof Payments){
            int id_payments;
            int service_id;
            double valuePayments;
            String datePayments;

            stmt = con.prepareStatement("SELECT * FROM " + "payments");
            rs = stmt.executeQuery();

            while (rs.next()) {
                id_payments = rs.getInt(1);
                service_id = rs.getInt(2);
                valuePayments = rs.getDouble(3);
                datePayments = rs.getString(4);
                objectList.add(new Payments(id_payments, service_id, valuePayments, datePayments));
            }
        }

        if(myObject instanceof ServiceList){
            int idService;
            String serviceName;
            String unit;
            double rate;
            int formPayments;

            stmt = con.prepareStatement("SELECT * FROM " + "services");
            rs = stmt.executeQuery();

            while (rs.next()) {
                idService = rs.getInt(1);
                serviceName = rs.getString(2);
                unit = rs.getString(3);
                rate = rs.getDouble(4);
                formPayments = rs.getInt(5);
                objectList.add(new ServiceList(idService, serviceName, unit, rate, formPayments));
            }
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
    public ObservableList<String> getColumn(String tableName, String columnName) throws SQLException {
        ObservableList<String> column = FXCollections.observableArrayList();
        PreparedStatement stmt = con.prepareStatement("SELECT " + columnName + " FROM " + tableName);

        ResultSet rs = stmt.executeQuery();

        while (rs.next()) {
            column.add(rs.getString(1));
        }

        if (rs != null) {
            rs.close();
        }

        if (stmt != null) {
            stmt.close();
        }

        return column;
    }

    @Override
        public String getValueColumn(String tableName, String columnName, int rowIndex) throws SQLException {
        ArrayList<String> list = new ArrayList<>();
        PreparedStatement stmt = con.prepareStatement("SELECT " + columnName + " FROM " + tableName);

        ResultSet rs = stmt.executeQuery();

        while (rs.next()) {
            list.add(rs.getString(1));
        }

        if (rs != null) {
            rs.close();
        }

        if (stmt != null) {
            stmt.close();
        }

        return list.get(rowIndex);
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

        if (stmt != null) {
            stmt.close();
        }

    }

    @Override
    public void add(Counters object) throws SQLException {
        PreparedStatement stmt = con.prepareStatement("INSERT INTO counters" +
                "(counterName, service, object) VALUES (?, ?, ?)");

        stmt.setString(1, object.getCounterName());
        stmt.setInt(2, object.getService());
        stmt.setInt(3, object.getObject());

        stmt.execute();

        if (stmt != null) {
            stmt.close();
        }

    }

    @Override
    public void add(Payments objects) throws SQLException {

    }

    @Override
    public void add(ServiceList objects) throws SQLException {

    }

    @Override
    public void delete(ObjectAccounting object) throws SQLException {
        PreparedStatement stmt = con.prepareStatement("DELETE FROM accountingobject WHERE personalAccount=?");
        stmt.setInt(1, object.getId());

        stmt.execute();

        if (stmt != null) {
            stmt.close();
        }
    }

    @Override
    public void delete(Counters objects) throws SQLException {

    }

    @Override
    public void delete(Payments objects) throws SQLException {

    }

    @Override
    public void delete(ServiceList objects) throws SQLException {

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

        if (stmt != null) {
            stmt.close();
        }
    }

    @Override
    public void change(Counters objects) throws SQLException {

    }

    @Override
    public void change(Payments objects) throws SQLException {

    }

    @Override
    public void change(ServiceList objects) throws SQLException {

    }

}
