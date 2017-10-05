package by.javafx.communalPayments.tests;

import by.javafx.communalPayments.objects.AccessDatabase;
import by.javafx.communalPayments.objects.ObjectAccounting;

import java.io.IOException;
import java.util.ArrayList;

public class Test{
    public static void main(String[] args) {
        
        AccessDatabase accessDatabase = new AccessDatabase();
        ArrayList<String> list = new ArrayList<String>();
        ArrayList<ObjectAccounting> objectAccountings = new ArrayList<ObjectAccounting>();

        try {
            accessDatabase.setConnectDatabase("/home/juanantonio/Database_CommunalPayments/communalPayments.mdb");
        } catch (IOException e) {
            e.printStackTrace();
        }

        try {
            list = accessDatabase.getData("accountingObject");
            //list = accessDatabase.getData("counters");
        } catch (IOException e) {
            e.printStackTrace();
        }

        System.out.println(list.get(0));
        //String str = new StringBuilder(list.get(0)).deleteCharAt(0).toString();
        String str = list.get(0).replace("[", "");
        str = str.replace("]", "");

        String delims = ",";
        String[] tokens = str.split(delims);
        for (int i = 0; i < tokens.length; i++)
            System.out.println(tokens[i]);


    }

}
