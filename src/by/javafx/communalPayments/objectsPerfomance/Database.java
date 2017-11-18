package by.javafx.communalPayments.objectsPerfomance;

import by.javafx.communalPayments.controllers.MainController;
import by.javafx.communalPayments.daoImplements.MySQLDaoFactory;
import by.javafx.communalPayments.interfaces.connectionInterfaces.ConnectionBuilder;
import by.javafx.communalPayments.interfaces.daoInterfaces.*;
import by.javafx.communalPayments.interfaces.observerInterfaces.Observer;
import by.javafx.communalPayments.interfaces.observerInterfaces.Subject;
import by.javafx.communalPayments.connectionImplements.ConnectionBuilderFactory;
import javafx.collections.ObservableList;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;

public class Database implements Subject {

    private static ArrayList<Observer> observers = new ArrayList<>();
    private static Connection connection;
    private MainController mainController;
    private DaoFactory daoFactory = new MySQLDaoFactory();

    public Database(MainController mainController) {
        this.mainController = mainController;

    }

    public void setConnection() {

        ConnectionBuilder connectionBuilder = ConnectionBuilderFactory.getSimpleConnection();
        try {
            connection = connectionBuilder.getConnection();
        } catch (SQLException e) {
            mainController.printDialogError("Ошибка подключения", "Не удалось подключиться к серверу MySQL !", e.getMessage());
            System.exit(0);
        }

        try {
            connectionBuilder.availabilityCheckDatabase(connection);
        } catch (SQLException e) {
            mainController.printDialogError("Ошибка подключения", "Не удалось подключиться к БД !", e.getMessage());
            System.exit(0);
        }

    }

    public boolean addObject(ObjectAccounting object) {

        try {
            ObjectAccountDao objectAccountDao = daoFactory.getObjectAccountDao(connection);
            objectAccountDao.add(object);
            dataChange();

        } catch (SQLException e) {
            mainController.printDialogError("Работа с базой данных", "Ошибка добавления объекта учета в БД !", e.getMessage());
            return false;
        }

        return true;
    }

    public boolean addCounter(Counters counter) {
        try {

            CountersDao countersDao = daoFactory.getCountersDao(connection);
            countersDao.add(counter);
            dataChange();

        } catch (SQLException e) {
            mainController.printDialogError("Работа с базой данных", "Ошибка добавления счетчика в БД !", e.getMessage());
            return false;
        }

        return true;
    }

    public boolean addService(Services service) {
        try {
            ServicesDao servicesDao = daoFactory.getServicesDao(connection);
            servicesDao.add(service);
            dataChange();

        } catch (SQLException e) {
            mainController.printDialogError("Работа с базой данных", "Ошибка добавления услуги в БД !", e.getMessage());
            return false;
        }

        return true;
    }

    public boolean addPayment(Payments payment) {
        try {
            PaymentsDao paymentsDao = daoFactory.getPaymentsDao(connection);
            paymentsDao.add(payment);
            dataChange();

        } catch (SQLException e) {
            mainController.printDialogError("Работа с базой данных", "Ошибка добавления платежа в БД !", e.getMessage());
            return false;
        }

        return true;
    }

    public boolean addMeasurement(Measurement measurement) {
        try {
            MeasurementDao measurementDao = daoFactory.getMeasurementDao(connection);
            measurementDao.add(measurement);
            dataChange();

        } catch (SQLException e) {
            mainController.printDialogError("Работа с базой данных", "Ошибка добавления показаний в БД !", e.getMessage());
            return false;
        }

        return true;
    }

    public boolean changeObject(ObjectAccounting object, int id) {

        try {
            ObjectAccountDao objectAccountDao = daoFactory.getObjectAccountDao(connection);
            objectAccountDao.update(object, id);
            dataChange();

        } catch (SQLException e) {
            mainController.printDialogError("Работа с базой данных", "Ошибка изменения данных в objectAccounting !", e.getMessage());
            return false;
        }
        return true;
    }

    public boolean changeCounter(Counters counter) {

        try {
            CountersDao countersDao = daoFactory.getCountersDao(connection);
            countersDao.update(counter);
            dataChange();

        } catch (SQLException e) {
            mainController.printDialogError("Работа с базой данных", "Ошибка изменения данных в counters !", e.getMessage());
            return false;
        }

        return true;
    }

    public boolean changeService(Services service) {

        try {
            ServicesDao servicesDao = daoFactory.getServicesDao(connection);
            servicesDao.update(service);
            dataChange();

        } catch (SQLException e) {
            mainController.printDialogError("Работа с базой данных", "Ошибка изменения данных в services !", e.getMessage());
            return false;
        }

        return true;
    }

    public boolean lastMeasureChange(Counters object, double lastMeasure) {

        try {
            MeasurementDao measurementDao = daoFactory.getMeasurementDao(connection);
            measurementDao.changeLastMeasure(object, lastMeasure);
            dataChange();

        } catch (SQLException e) {
            mainController.printDialogError("Работа с базой данных", "Ошибка изменения данных в measurement !", e.getMessage());
            return false;
        }

        return true;
    }

    public boolean deleteObject(ObjectAccounting object) {

        try {
            ObjectAccountDao objectAccountDao = daoFactory.getObjectAccountDao(connection);
            objectAccountDao.delete(object);
            dataChange();

        } catch (SQLException e) {
            mainController.printDialogError("Работа с базой данных", "Ошибка удаления объекта учета из БД !", e.getMessage());
            return false;
        }

        return true;
    }

    public boolean deleteCounter(Counters counter) {

        try {
            CountersDao countersDao = daoFactory.getCountersDao(connection);
            countersDao.delete(counter);
            dataChange();

        } catch (SQLException e) {
            mainController.printDialogError("Работа с базой данных", "Ошибка удаления счетчика из БД !", e.getMessage());
            return false;
        }

        return true;
    }

    public boolean deleteService(Services service) {

        try {
            ServicesDao servicesDao = daoFactory.getServicesDao(connection);
            servicesDao.delete(service);
            dataChange();

        } catch (SQLException e) {
            mainController.printDialogError("Работа с базой данных", "Ошибка удаления услуги из БД !", e.getMessage());
            return false;
        }

        return true;
    }

    public boolean deletePayment(Payments payment) {

        try {
            PaymentsDao paymentsDao = daoFactory.getPaymentsDao(connection);
            paymentsDao.delete(payment);
            dataChange();

        } catch (SQLException e) {
            mainController.printDialogError("Работа с базой данных", "Ошибка удаления платежа из БД !", e.getMessage());
            return false;
        }

        return true;
    }

    public ObservableList<ObjectAccounting> getTableObject() {

        try {
            ObjectAccountDao objectAccountDao = daoFactory.getObjectAccountDao(connection);
            return objectAccountDao.getAll();

        } catch (SQLException e) {
            mainController.printDialogError("Работа с базой данных", "Ошибка чтения данных из objectAccounting !", e.getMessage());
            return null;
        }

    }

    public ObservableList<Counters> getTableCounters() {

        try {
            CountersDao countersDao = daoFactory.getCountersDao(connection);
            return countersDao.getAll();

        } catch (SQLException e) {
            mainController.printDialogError("Работа с базой данных", "Ошибка чтения данных из counters !", e.getMessage());
            return null;
        }

    }

    public ObservableList<Services> getTableServices() {

        try {
            ServicesDao servicesDao = daoFactory.getServicesDao(connection);
            return servicesDao.getAll();

        } catch (SQLException e) {
            mainController.printDialogError("Работа с базой данных", "Ошибка чтения данных из services !", e.getMessage());
            return null;
        }

    }

    public ObservableList<Payments> getTablePayments() {

        try {
            PaymentsDao paymentsDao = daoFactory.getPaymentsDao(connection);
            return paymentsDao.getAll();

        } catch (SQLException e) {
            mainController.printDialogError("Работа с базой данных", "Ошибка чтения данных из payments !", e.getMessage());
            return null;
        }

    }

    public ObservableList<FormPayments> getTableFormPayments() {

        try {
            FormPaymentsDao formPaymentsDao = daoFactory.getFormPaymentsDao(connection);
            return formPaymentsDao.getAll();

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
