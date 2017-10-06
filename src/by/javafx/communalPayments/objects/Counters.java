package by.javafx.communalPayments.objects;

public class Counters extends TabObjects {

    private int object;
    private String service;
    private String counterName;

    public Counters(int object, String service, String counterName) {
        this.object = object;
        this.service = service;
        this.counterName = counterName;
    }

    public Counters() {
    }

    public int getObject() {
        return object;
    }

    public void setObject(int object) {
        this.object = object;
    }

    public String getService() {
        return service;
    }

    public void setService(String service) {
        this.service = service;
    }

    public String getCounterName() {
        return counterName;
    }

    public void setCounterName(String counterName) {
        this.counterName = counterName;
    }
}