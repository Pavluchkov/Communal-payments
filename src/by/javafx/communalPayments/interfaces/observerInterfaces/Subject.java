package by.javafx.communalPayments.interfaces.observerInterfaces;

import by.javafx.communalPayments.interfaces.observerInterfaces.Observer;

public interface Subject {
    void registerObserver(Observer o);

    void removeObserver(Observer o);

    void notifyObserver();
}
