package by.javafx.communalPayments.objects;

import by.javafx.communalPayments.interfaces.IDatabase;
import com.healthmarketscience.jackcess.*;
import com.healthmarketscience.jackcess.impl.DatabaseImpl;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Collections;


public class AccessDatabase implements IDatabase {

    private DatabaseImpl open;
    private ArrayList<String> list = new ArrayList<String>();

    @Override
    public void setConnectDatabase(String connectionString) throws IOException {

        open = DatabaseImpl.open(new File(connectionString), false, null,
                Database.DEFAULT_AUTO_SYNC, null, null, null);

    }

    @Override
    public void closeConnect() throws IOException {
        if (open != null) open.close();
    }

    @Override
    public ArrayList<String> getData(String tableName) throws IOException {

        Table table = open.getTable(tableName);

        for (Row row : table) {
            list.add(String.valueOf(row.values()));
       }

       //open.close();

        return list;
    }

    public String getValueColumn(String tableName, String columnName, String value) throws IOException{
        Table table = open.getTable(tableName);
        String s = "";

        Row row = CursorBuilder.findRow(table, Collections.singletonMap(columnName, value));

        if(row != null) {
            s = String.valueOf(row.getString("form"));
        }

        return s;
    }

}

