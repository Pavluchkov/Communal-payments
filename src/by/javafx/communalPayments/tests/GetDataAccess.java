package by.javafx.communalPayments.tests;

import by.javafx.communalPayments.objects.AccessDatabase;

import java.io.IOException;
import java.util.ArrayList;

public class GetDataAccess {
    public static void main(String[] args) {

        AccessDatabase accessDatabase = new AccessDatabase();
        ArrayList<String[]> list = new ArrayList<String[]>();

        try {
            //accessDatabase.setConnectDatabase("/home/juanantonio/Database_CommunalPayments/communalPayments.mdb");
            accessDatabase.setConnectDatabase("E:\\OneDrive\\Учеба ГГУ\\DB_CommunalPayments\\DB_New\\communalPayments.mdb");
        } catch (IOException e) {
            e.printStackTrace();
        }

        try {
            list = accessDatabase.getDataTable("payments");
            //list = accessDatabase.getData("counters");
        } catch (IOException e) {
            e.printStackTrace();
        }

        for (String[] s : list) {
            for (String str : s) {
                System.out.print(str + " ");
            }

            System.out.println("\n");
        }

        String s = "";
        try {
            s = accessDatabase.getValueColumn("formPayment", "form", "По счетчику");
        } catch (IOException e) {
            e.printStackTrace();
        }

        System.out.println(s);

    }

}
