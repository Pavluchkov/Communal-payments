package by.javafx.communalPayments.daoImplements;

import by.javafx.communalPayments.interfaces.daoInterfaces.ObjectAccountDao;
import by.javafx.communalPayments.objects.ObjectAccounting;
import javafx.collections.ObservableList;

import java.sql.Connection;
import java.sql.PreparedStatement;
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

    }

    @Override
    public void delete(ObjectAccounting object) throws SQLException {

    }

    @Override
    public ObservableList<ObjectAccounting> getAll() throws SQLException {
        return null;
    }
}
