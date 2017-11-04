package by.javafx.communalPayments.objects;

import by.javafx.communalPayments.controllers.MainController;
import by.javafx.communalPayments.interfaces.IDatabase;
import by.javafx.communalPayments.interfaces.Observer;
import by.javafx.communalPayments.interfaces.Subject;
import javafx.collections.ObservableList;

import java.sql.SQLException;
import java.util.ArrayList;

public class Database implements Subject {

    private static ArrayList<Observer> observers = new ArrayList<>();
    private IDatabase database = MySQLDatabase.getInstance();
    private MainController mainController;

    public Database(MainController mainController) {
        this.mainController = mainController;
    }

    public void setConnection() {

        try {
            database.setConnectDatabase("jdbc:mysql://localhost:3306");
        } catch (SQLException | ClassNotFoundException e) {
            mainController.printDialogError("Ошибка подключения", "Не удалось подключиться к серверу MySQL !", e.getMessage());
            System.exit(0);
        }

        try {
            database.availabilityCheckDatabase();
        } catch (SQLException e) {
            mainController.printDialogError("Ошибка подключения", "Не удалось подключиться к БД !", e.getMessage());
            System.exit(0);
        }

    }

    public boolean addObject(ObjectAccounting object) {

        try {

            database.add(object);
            dataChange();

        } catch (SQLException e) {
            mainController.printDialogError("Работа с базой данных", "Ошибка добавления объекта учета в БД !", e.getMessage());
            return false;
        }

        return true;
    }

    public boolean addCounter(Counters counter) {
        try {

            database.add(counter);
            dataChange();

        } catch (SQLException e) {
            mainController.printDialogError("Работа с базой данных", "Ошибка добавления счетчика в БД !", e.getMessage());
            return false;
        }

        return true;
    }

    public boolean addService(Services service) {
        try {

            database.add(service);
            dataChange();

        } catch (SQLException e) {
            mainController.printDialogError("Работа с базой данных", "Ошибка добавления услуги в БД !", e.getMessage());
            return false;
        }

        return true;
    }

    public boolean addPayment(Payments payment) {
        try {

            database.add(payment);
            dataChange();

        } catch (SQLException e) {
            mainController.printDialogError("Работа с базой данных", "Ошибка добавления платежа в БД !", e.getMessage());
            return false;
        }

        return true;
    }

    public boolean addMeasurement(Measurement measurement) {
        try {

            database.add(measurement);
            dataChange();

        } catch (SQLException e) {
            mainController.printDialogError("Работа с базой данных", "Ошибка добавления показаний в БД !", e.getMessage());
            return false;
        }

        return true;
    }

    public boolean changeCounter(Counters counter) {

        try {

            database.change(counter);
            dataChange();

        } catch (SQLException e) {
            mainController.printDialogError("Работа с базой данных", "Ошибка изменения данных в counters !", e.getMessage());
            return false;
        }

        return true;
    }

    public boolean changeService(Services service) {

        try {

            database.change(service);
            dataChange();

        } catch (SQLException e) {
            mainController.printDialogError("Работа с базой данных", "Ошибка изменения данных в services !", e.getMessage());
            return false;
        }

        return true;
    }

    public boolean changeObject(ObjectAccounting object, int id) {

        try {

            database.change(object, id);
            dataChange();

        } catch (SQLException e) {
            mainController.printDialogError("Работа с базой данных", "Ошибка изменения данных в objectAccounting !", e.getMessage());
            return false;
        }
        return true;
    }

    public boolean lastMeasureChange(Counters object, double lastMeasure) {

        try {

            database.changeLastMeasure(object, lastMeasure);
            dataChange();

        } catch (SQLException e) {
            mainController.printDialogError("Работа с базой данных", "Ошибка изменения данных в measurement !", e.getMessage());
            return false;
        }

        return true;
    }

    public boolean deleteObject(ObjectAccounting object) {

        try {

            database.delete(object);
            dataChange();

        } catch (SQLException e) {
            mainController.printDialogError("Работа с базой данных", "Ошибка удаления объекта учета из БД !", e.getMessage());
            return false;
        }

        return true;
    }

    public boolean deleteCounter(Counters counter) {

        try {

            database.delete(counter);
            dataChange();

        } catch (SQLException e) {
            mainController.printDialogError("Работа с базой данных", "Ошибка удаления счетчика из БД !", e.getMessage());
            return false;
        }

        return true;
    }

    public boolean deleteService(Services service) {

        try {

            database.delete(service);
            dataChange();

        } catch (SQLException e) {
            mainController.printDialogError("Работа с базой данных", "Ошибка удаления услуги из БД !", e.getMessage());
            return false;
        }

        return true;
    }

    public boolean deletePayment(Payments payment) {

        try {

            database.delete(payment);
            dataChange();

        } catch (SQLException e) {
            mainController.printDialogError("Работа с базой данных", "Ошибка удаления платежа из БД !", e.getMessage());
            return false;
        }

        return true;
    }

    public ObservableList<ObjectAccounting> getTableObject() {

        try {

            return database.getListObjects(new ObjectAccounting());

        } catch (SQLException e) {
            mainController.printDialogError("Работа с базой данных", "Ошибка чтения данных из objectAccounting !", e.getMessage());
            return null;
        }

    }

    public ObservableList<Counters> getTableCounters() {

        try {

            return database.getListObjects(new Counters());

        } catch (SQLException e) {
            mainController.printDialogError("Работа с базой данных", "Ошибка чтения данных из counters !", e.getMessage());
            return null;
        }

    }

    public ObservableList<Services> getTableServices() {

        try {

            return database.getListObjects(new Services());

        } catch (SQLException e) {
            mainController.printDialogError("Работа с базой данных", "Ошибка чтения данных из services !", e.getMessage());
            return null;
        }

    }

    public ObservableList<Payments> getTablePayments() {

        try {

            return database.getListObjects(new Payments());

        } catch (SQLException e) {
            mainController.printDialogError("Работа с базой данных", "Ошибка чтения данных из payments !", e.getMessage());
            return null;
        }

    }

    public ObservableList<FormPayments> getTableFormPayments() {

        try {

            return database.getListObjects(new FormPayments());

        } catch (SQLException e) {
            mainController.printDialogError("Работа с базой данных", "Ошибка чтения данных из formPayments !", e.getMessage());
            return null;
        }

    }

    @Override
    public void registerObserver(Observer o) {
        observers.add(o);
    }

    @Override
    public void removeObserver(Observer o) {
        int i = observers.indexOf(o);

        if (i >= 0) {
            observers.remove(i);
        }
    }

    @Override
    public void notifyObserver() {

        for (Observer o : observers) {
            o.update();
        }

    }

    private void dataChange() {
        notifyObserver();
    }
}
