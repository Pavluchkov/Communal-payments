package by.javafx.communalPayments.objects;

public class Counters extends TabObjects {
    private int id;
    private String counterName;
    private int service;
    private int object;

    public Counters(int id, String counterName, int service, int object) {
        this.id = id;
        this.counterName = counterName;
        this.service = service;
        this.object = object;
    }

    public Counters() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCounterName() {
        return counterName;
    }

    public void setCounterName(String counterName) {
        this.counterName = counterName;
    }

    public int getService() {
        return service;
    }

    public void setService(int service) {
        this.service = service;
    }

    public int getObject() {
        return object;
    }

    public void setObject(int object) {
        this.object = object;
    }
}