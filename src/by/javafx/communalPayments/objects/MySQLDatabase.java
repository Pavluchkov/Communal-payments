package by.javafx.communalPayments.objects;

import by.javafx.communalPayments.interfaces.IDatabase;

import java.io.IOException;
import java.sql.*;

public class MySQLDatabase implements IDatabase {
    private Connection con;
    private ResultSet rs;
    private PreparedStatement stmt;

    public void setConnectDatabase(String connectionString) throws IOException, ClassNotFoundException, SQLException {

        Class.forName("com.mysql.jdbc.Driver");
        String url = connectionString;
        //con = DriverManager.getConnection(url, "root", "root");
        con = DriverManager.getConnection(url, "root", "Nikitenko");

    }


    public void closeConnect() throws IOException, SQLException {
        if (con != null) {
            con.close();
        }

        if (rs != null) {
            rs.close();
        }

        if (stmt != null) {
            stmt.close();
        }

    }


    public ResultSet getDataTable(String tableName) throws IOException, SQLException {

        stmt = con.prepareStatement("SELECT * FROM " + tableName);//createStatement();
        //stmt.setString(1, tableName);
        rs = stmt.executeQuery();
        //rs = stmt.executeQuery("SELECT * FROM accountingObject");
        //while (rs.next()) {

//                String str = rs.getString(1) + " " + rs.getString(2) + rs.getString(3)
//                        + rs.getString(4) + rs.getString(5) + rs.getString(6);
//            System.out.println(str);

            //printString(str);
       //}

        return rs;
    }

}
