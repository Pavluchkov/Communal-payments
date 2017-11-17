package by.javafx.communalPayments.daoImplements;

import by.javafx.communalPayments.interfaces.daoInterfaces.FormPaymentsDao;
import by.javafx.communalPayments.objects.FormPayments;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class MySQLFormPayments implements FormPaymentsDao {
    private Connection connection;

    public MySQLFormPayments(Connection connection) {
        this.connection = connection;
    }

    @Override
    public ObservableList<FormPayments> getAll(FormPayments object) throws SQLException {
        ObservableList<FormPayments> objectList = FXCollections.observableArrayList();

        PreparedStatement stmt = connection.prepareStatement("SELECT * FROM " + "formpayments");
        ResultSet rs = stmt.executeQuery();

        while (rs.next()) {
            int id = rs.getInt(1);
            String form = rs.getString(2);
            objectList.add(new FormPayments(id, form));
        }

        rs.close();
        stmt.close();

        return objectList;
    }
}
