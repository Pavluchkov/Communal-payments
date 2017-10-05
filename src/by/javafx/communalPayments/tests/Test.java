package by.javafx.communalPayments.tests;

import by.javafx.communalPayments.objects.AccessDatabase;
import javafx.collections.ObservableList;

import java.io.IOException;
import java.util.ArrayList;

public class Test{
    public static void main(String[] args) {

        ArrayList<String> list = new ArrayList<String>();
        
        AccessDatabase accessDatabase = new AccessDatabase();
//        try {
//            accessDatabase.setConnectDatabase("/home/juanantonio/Database_CommunalPayments/communalPayments.accdb");
//        } catch (IOException e) {
//            e.toString();
//        }

        try {
            list = accessDatabase.getData("accountingObject");
        } catch (IOException e) {
            e.toString();
        }

        for (String s:list) {
            System.out.print(s + " ");
        }
    }

}
