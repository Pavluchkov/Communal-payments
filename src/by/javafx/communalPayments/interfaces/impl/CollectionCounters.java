package by.javafx.communalPayments.interfaces.impl;

import by.javafx.communalPayments.interfaces.ICounters;
import by.javafx.communalPayments.objects.Counters;

import java.util.ArrayList;

public class CollectionCounters implements ICounters {
    ArrayList<Counters> countersList = new ArrayList<Counters>();


    @Override
    public void add(Counters counters) {
        countersList.add(counters);
    }

    @Override
    public void change(Counters counters) {

    }

    @Override
    public void delete(Counters counters) {
        countersList.add(counters);
    }
}
