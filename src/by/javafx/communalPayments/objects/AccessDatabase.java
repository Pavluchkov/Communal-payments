package by.javafx.communalPayments.objects;

import by.javafx.communalPayments.interfaces.IDatabase;
import com.healthmarketscience.jackcess.Database;
import com.healthmarketscience.jackcess.DatabaseBuilder;
import com.healthmarketscience.jackcess.Row;
import com.healthmarketscience.jackcess.Table;
import com.healthmarketscience.jackcess.impl.DatabaseImpl;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.ArrayList;


public class AccessDatabase implements IDatabase {

    private DatabaseImpl open;
    private ArrayList<String> list = new ArrayList<>();

    @Override
    public void setConnectDatabase(String connectionString) throws IOException {

        open = DatabaseImpl.open(new File(connectionString), true, null,
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
            //s = s + String.valueOf(row.values()) + '\n';
            list.add(String.valueOf(row.values()));
//            if (row.get("Shifr").equals(shifrString)) {
//                shifr = row.getString("Shifr");
//
//                if (row.get("Ima") != null) {
//                    name = row.getString("Ima");
//                } else name = "null";
//
//                if ((row.get("Nsk") != null) && (row.get("Vsk") != null)) {
//                    nsk = row.getInt("Nsk");
//                    vsk = row.getInt("Vsk");
//                    scal = nsk + " - " + vsk;
//                } else scal = "0";
//
//                if (row.get("Ed") != null) {
//                    eizm = row.getString("Ed");
//                } else eizm = "null";
//
//                if (row.get("TznAbs") != null) {
//                    int tznabs = row.getInt("TznAbs");
//                    tzn = String.valueOf(new DecimalFormat("#0.00").format((double) tznabs * (vsk - nsk) / 255));
//                    temp = (double) (tznabs) * 100 / 255;
//                    tznp = String.valueOf(new DecimalFormat("#0.00").format(temp));
//                    temp = (double) (tznabs) * 5 / 255;
//                    tznmA = String.valueOf(new DecimalFormat("#0.00").format(temp));
//                } else {
//                    tznmA = "0";
//                    tznp = "0";
//                }
//            }
       }

       open.close();
        return list;
    }

}

