package by.javafx.communalPayments.tests;

import by.javafx.communalPayments.interfaces.impl.CollectionCounters;
import by.javafx.communalPayments.objects.Counters;

public class Test {
    public static void main(String[] args) {
        CollectionCounters collectionCounters = new CollectionCounters();
        collectionCounters.add(new Counters(1, "Горячая вода", "м.куб", 2890.115));
    }
}
