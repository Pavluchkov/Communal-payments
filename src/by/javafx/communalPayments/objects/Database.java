package by.javafx.communalPayments.objects;

import by.javafx.communalPayments.controllers.MainController;
import by.javafx.communalPayments.interfaces.IDatabase;
import javafx.collections.ObservableList;

import java.sql.SQLException;

public class Database {

    private IDatabase database = MySQLDatabase.getInstance();
    private MainController mainController;

    public Database(MainController mainController) {
        this.mainController = mainController;
    }

    private void setConnection() {

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

    private boolean addObject(ObjectAccounting object) {

        try {

            database.add(object);

        } catch (SQLException e) {
            mainController.printDialogError("Работа с базой данных", "Ошибка добавления объекта учета в БД !", e.getMessage());
            return false;
        }

        return true;
    }

    private boolean addCounter(Counters counter) {
        try {

            database.add(counter);

        } catch (SQLException e) {
            mainController.printDialogError("Работа с базой данных", "Ошибка добавления счетчика в БД !", e.getMessage());
            return false;
        }

        return true;
    }

    private boolean addService(Services service) {
        try {

            database.add(service);

        } catch (SQLException e) {
            mainController.printDialogError("Работа с базой данных", "Ошибка добавления услуги в БД !", e.getMessage());
            return false;
        }

        return true;
    }

    private boolean addPayment(Payments payment) {
        try {

            database.add(payment);

        } catch (SQLException e) {
            mainController.printDialogError("Работа с базой данных", "Ошибка добавления платежа в БД !", e.getMessage());
            return false;
        }

        return true;
    }

    private boolean addMeasurement(Measurement measurement) {
        try {

            database.add(measurement);

        } catch (SQLException e) {
            mainController.printDialogError("Работа с базой данных", "Ошибка добавления показаний в БД !", e.getMessage());
            return false;
        }

        return true;
    }

    private boolean changeCounter(Counters counter) {

        try {

            database.change(counter);

        } catch (SQLException e) {
            mainController.printDialogError("Работа с базой данных", "Ошибка изменения данных в counters !", e.getMessage());
            return false;
        }

        return true;
    }

    private boolean changeService(Services service) {

        try {

            database.change(service);

        } catch (SQLException e) {
            mainController.printDialogError("Работа с базой данных", "Ошибка изменения данных в counters !", e.getMessage());
            return false;
        }

        return true;
    }

    private boolean changeObject(ObjectAccounting object, int id) {

        try {

            database.change(object, id);

        } catch (SQLException e) {
            mainController.printDialogError("Работа с базой данных", "Ошибка изменения данных в objectAccounting !", e.getMessage());
            return false;
        }
        return true;
    }

    private boolean lastMeasureChange(Counters object, double lastMeasure) {

        try {

            database.changeLastMeasure(object, lastMeasure);

        } catch (SQLException e) {
            mainController.printDialogError("Работа с базой данных", "Ошибка изменения данных в measurement !", e.getMessage());
            return false;
        }

        return true;
    }

    private boolean deleteObject(ObjectAccounting object) {

        try {

            database.delete(object);

        } catch (SQLException e) {
            mainController.printDialogError("Работа с базой данных", "Ошибка удаления объекта учета из БД !", e.getMessage());
            return false;
        }

        return true;
    }

    private boolean deleteCounter(Counters counter) {

        try {

            database.delete(counter);

        } catch (SQLException e) {
            mainController.printDialogError("Работа с базой данных", "Ошибка удаления счетчика из БД !", e.getMessage());
            return false;
        }

        return true;
    }

    private boolean deleteService(Services service) {

        try {

            database.delete(service);

        } catch (SQLException e) {
            mainController.printDialogError("Работа с базой данных", "Ошибка удаления услуги из БД !", e.getMessage());
            return false;
        }

        return true;
    }

    private boolean deletePayment(Payments payment) {

        try {

            database.delete(payment);

        } catch (SQLException e) {
            mainController.printDialogError("Работа с базой данных", "Ошибка удаления платежа из БД !", e.getMessage());
            return false;
        }

        return true;
    }

    private ObservableList<ObjectAccounting> getTableObject() {

        try {

            return database.getListObjects(new ObjectAccounting());

        } catch (SQLException e) {
            mainController.printDialogError("Работа с базой данных", "Ошибка чтения данных из objectAccounting !", e.getMessage());
            return null;
        }

    }

    private ObservableList<Counters> getTableCounters() {

        try {

            return database.getListObjects(new Counters());

        } catch (SQLException e) {
            mainController.printDialogError("Работа с базой данных", "Ошибка чтения данных из counters !", e.getMessage());
            return null;
        }

    }

    private ObservableList<Services> getTableServices() {

        try {

            return database.getListObjects(new Services());

        } catch (SQLException e) {
            mainController.printDialogError("Работа с базой данных", "Ошибка чтения данных из services !", e.getMessage());
            return null;
        }

    }

    private ObservableList<Payments> getTablePayments() {

        try {

            return database.getListObjects(new Payments());

        } catch (SQLException e) {
            mainController.printDialogError("Работа с базой данных", "Ошибка чтения данных из payments !", e.getMessage());
            return null;
        }

    }

    private ObservableList<FormPayments> getTableFormPayments() {

        try {

            return database.getListObjects(new FormPayments());

        } catch (SQLException e) {
            mainController.printDialogError("Работа с базой данных", "Ошибка чтения данных из formPayments !", e.getMessage());
            return null;
        }

    }

}
