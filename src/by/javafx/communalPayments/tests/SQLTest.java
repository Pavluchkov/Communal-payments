package by.javafx.communalPayments.tests;

import by.javafx.communalPayments.objects.MySQLDatabase;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;

public class SQLTest {
    public static void main(String[] args) {

        MySQLDatabase mySQLDatabase = new MySQLDatabase();

        try {
            mySQLDatabase.setConnectDatabase("jdbc:mysql://localhost:3306/communalPayments");
            ResultSet rs = mySQLDatabase.getDataTable("accountingObject");

            while(rs.next()) {
                System.out.println(rs.getString(1) + " " + rs.getString(2) + " " + rs.getString(3)
                        + " " + rs.getString(4) + " " + rs.getString(5) + " " + rs.getString(6));
            }

            mySQLDatabase.closeConnect();

        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

}
