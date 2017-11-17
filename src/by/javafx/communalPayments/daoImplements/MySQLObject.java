package by.javafx.communalPayments.daoImplements;

import by.javafx.communalPayments.interfaces.daoInterfaces.ObjectAccountDao;
import by.javafx.communalPayments.objects.ObjectAccounting;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class MySQLObject implements ObjectAccountDao {
    private Connection connection;

    public MySQLObject(Connection connection) {
        this.connection = connection;
    }

    @Override
    public void add(ObjectAccounting object) throws SQLException {
        PreparedStatement stmt = connection.prepareStatement("INSERT INTO accountingobject" +
                "(personalAccount, objectName, owner, address, residents, area) VALUES (?, ?, ?, ?, ?, ?)");

        stmt.setInt(1, object.getId());
        stmt.setString(2, object.getObjectName());
        stmt.setString(3, object.getOwner());
        stmt.setString(4, object.getAddress());
        stmt.setInt(5, object.getResidents());
        stmt.setDouble(6, object.getArea());
        stmt.execute();

        stmt.close();
    }

    @Override
    public void update(ObjectAccounting object, int id) throws SQLException {
        PreparedStatement stmt = connection.prepareStatement("UPDATE accountingobject SET" +
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
    }

    @Override
    public void delete(ObjectAccounting object) throws SQLException {
        PreparedStatement stmt = connection.prepareStatement("DELETE FROM accountingobject WHERE personalAccount=?");
        stmt.setInt(1, object.getId());

        stmt.execute();

        stmt.close();
    }

    @Override
    public ObservableList<ObjectAccounting> getAll() throws SQLException {
        ObservableList<ObjectAccounting> objectList = FXCollections.observableArrayList();

        PreparedStatement stmt = connection.prepareStatement("SELECT * FROM " + "accountingobject");
        ResultSet rs = stmt.executeQuery();

        while (rs.next()) {
            int id = rs.getInt(1);
            String objectName = rs.getString(2);
            String owner = rs.getString(3);
            String address = rs.getString(4);
            int residents = rs.getInt(5);
            double area = rs.getDouble(6);
            objectList.add(new ObjectAccounting(id, objectName, owner, address, residents, area));
        }

        rs.close();
        stmt.close();

        return objectList;
    }
}
