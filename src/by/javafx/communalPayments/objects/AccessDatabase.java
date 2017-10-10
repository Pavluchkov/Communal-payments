package by.javafx.communalPayments.objects;

import com.healthmarketscience.jackcess.*;
import com.healthmarketscience.jackcess.impl.DatabaseImpl;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;


public class AccessDatabase {

    private DatabaseImpl open;

    public void setConnectDatabase(String connectionString) throws IOException {

        open = DatabaseImpl.open(new File(connectionString), false, null,
                Database.DEFAULT_AUTO_SYNC, null, null, null);

    }

    public void closeConnect() throws IOException {
        if (open != null) open.close();
    }

    public ArrayList<String[]> getDataTable(String tableName) throws IOException {
        ArrayList<String[]> list = new ArrayList<String[]>();
        ArrayList<String> temp = new ArrayList<String>();

        Table table = open.getTable(tableName);

        for (Row row : table) {
            for (Column column : table.getColumns()) {
                String columnName = column.getName();
                Object value = row.get(columnName);
                temp.add(String.valueOf(value));
            }

            String[] sTemp = new String[temp.size()];
            int i = 0;

            for (String st : temp) {
                sTemp[i++] = st;
            }
            list.add(sTemp);
            temp.clear();
        }

        return list;
    }

    public void add() {

    }

//    private ArrayList<String[]> parseList(ArrayList<String> list) {
//
//        ArrayList<String[]> ls = new ArrayList<String[]>();
//
//        for (String s : list) {
//            s = s.replace("[", "");
//            s = s.replace("]", "");
//            String delims = ", ";
//            String[] str = s.split(delims);
//            ls.add(str);
//        }
//
//        return ls;
//    }

    public String getValueColumn(String tableName, String columnName, String value) throws IOException {
        Table table = open.getTable(tableName);
        String s = "";

        Row row = CursorBuilder.findRow(table, Collections.singletonMap(columnName, value));

        if (row != null) {
            s = String.valueOf(row.getString("form"));
        }

        return s;
    }

}

