package by.javafx.communalPayments.tests;

import by.javafx.communalPayments.objects.AccessDatabase;
import by.javafx.communalPayments.objects.ObjectAccounting;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;

public class Test{
    public static void main(String[] args) {
        
        AccessDatabase accessDatabase = new AccessDatabase();
        ArrayList<String[]> list = new ArrayList<String[]>();
        ArrayList<ObjectAccounting> objectAccountings = new ArrayList<ObjectAccounting>();

        try {
            accessDatabase.setConnectDatabase("/home/juanantonio/Database_CommunalPayments/communalPayments.mdb");
            //accessDatabase.setConnectDatabase("E:\\OneDrive\\Учеба ГГУ\\DB_CommunalPayments\\communalPayments.mdb");
        } catch (IOException e) {
            e.printStackTrace();
        }

        try {
            list = accessDatabase.getData("accountingObject");
            //list = accessDatabase.getData("counters");
        } catch (IOException e) {
            e.printStackTrace();
        }

        for(String[] s: list){
            for (int i = 0; i < s.length; i++)
                System.out.print(s[i] + " ");
            System.out.println("");
        }

        String s = "";
        try {
            s = accessDatabase.getValueColumn("formPayment", "id", "2");
        } catch (IOException e) {
            e.printStackTrace();
        }

        System.out.println(s);
    }

}
