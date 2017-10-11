package by.javafx.communalPayments.tests;

import by.javafx.communalPayments.objects.Counters;
import by.javafx.communalPayments.objects.MyObjects;
import by.javafx.communalPayments.objects.ObjectAccounting;

public class Test {
    public static void main(String[] args) {
        MyObjects myObjects = new ObjectAccounting();

        if(myObjects instanceof Counters){
            System.out.println("Yes");
        } else System.out.println("No");
    }
}
