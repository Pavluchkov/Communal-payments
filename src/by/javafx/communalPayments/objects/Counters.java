package by.javafx.communalPayments.objects;

public class Counters extends TabObjects {

    private int id_counter;
    private int id_object;
    private int id_service;
    private String counterName;

    public Counters(int id_counter, int id_object, int id_service, String counterName) {
        this.id_counter = id_counter;
        this.id_object = id_object;
        this.id_service = id_service;
        this.counterName = counterName;
    }

    public Counters() {
    }

    public int getId_counter() {
        return id_counter;
    }

    public void setId_counter(int id_counter) {
        this.id_counter = id_counter;
    }

    public int getId_object() {
        return id_object;
    }

    public void setId_object(int id_object) {
        this.id_object = id_object;
    }

    public int getId_service() {
        return id_service;
    }

    public void setId_service(int id_service) {
        this.id_service = id_service;
    }

    public String getCounterName() {
        return counterName;
    }

    public void setCounterName(String counterName) {
        this.counterName = counterName;
    }
}