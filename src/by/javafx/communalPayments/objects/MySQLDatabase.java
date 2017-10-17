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

        int idCounters;
        String counterName;
        int service;
        int objectId;
        double recentMeasure;

        while (rs.next()) {
            idCounters = rs.getInt(1);
            objectId = rs.getInt(2);
            service = rs.getInt(3);
            counterName = rs.getString(4);
            recentMeasure = rs.getDouble(5);
            objectList.add(new Counters(idCounters, objectId,service, counterName, recentMeasure));
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

        int id_payments;
        int object_id = 0;
        int service_id;
        double sum;
        String date;

        while (rs.next()) {
            id_payments = rs.getInt(1);
            object_id = rs.getInt(2);
            service_id = rs.getInt(3);
            sum = rs.getDouble(4);
            date = rs.getString(5);
            objectList.add(new Payments(id_payments, object_id, service_id, sum, date));
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
                "(counterName, service, object) VALUES (?, ?, ?)");

        stmt.setString(1, object.getCounterName());
        stmt.setInt(2, object.getService());
        stmt.setInt(3, object.getObject());

        stmt.execute();

        stmt.close();
        dataChange();
    }

    @Override
    public void add(Payments objects) throws SQLException {

    }

    @Override
    public void add(Services object) throws SQLException {
        PreparedStatement stmt = con.prepareStatement("INSERT INTO services" +
                "(serviceName, unit, rate, formPayments) VALUES (?, ?, ?, ?)");

        stmt.setString(1, object.getServiceName());
        stmt.setString(2, object.getUnit());
        stmt.setDouble(3, object.getRate());
        stmt.setInt(4, object.getFormPayments());

        stmt.execute();

        stmt.close();
        dataChange();
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
    public void delete(Payments objects) throws SQLException {
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
                " id=?, counterName=?, service=?, object=?" +
                " WHERE id=?");
        stmt.setInt(1, object.getId());
        stmt.setString(2, object.getCounterName());
        stmt.setInt(3, object.getService());
        stmt.setInt(4, object.getObject());
        stmt.setInt(5, object.getId());
        stmt.execute();

        stmt.close();
        dataChange();
    }

    @Override
    public void change(Payments objects) throws SQLException {
        dataChange();
    }

    @Override
    public void change(Services object) throws SQLException {
        PreparedStatement stmt = con.prepareStatement("UPDATE services SET" +
                " id=?, serviceName=?, unit=?, rate=?, formPayments=?" +
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
    public void registerObserver(Observer o) {
        observers.add(o);
    }

    @Override
    public void removeObserver(Observer o) {
        int i = observers.indexOf(o);

        if(i >= 0){
            observers.remove(i);
        }
    }

    @Override
    public void notifyObserver() {

        for (Observer o : observers){
            o.update();
        }

    }

    private void dataChange(){
        notifyObserver();
    }
}
