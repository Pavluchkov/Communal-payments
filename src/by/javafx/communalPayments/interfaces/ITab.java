package by.javafx.communalPayments.interfaces;

import by.javafx.communalPayments.objects.TabObject;

public interface ITab {
    void add(TabObject tabObject);
    void change(TabObject tabObject);
    void delete(TabObject tabObject);
    void show(TabObject tabObject);
}
