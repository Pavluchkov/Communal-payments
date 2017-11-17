package by.javafx.communalPayments.interfaces.observerInterfaces;

public interface Subject {
    void registerObserver(Observer o);

    void removeObserver(Observer o);

    void notifyObserver();
}
