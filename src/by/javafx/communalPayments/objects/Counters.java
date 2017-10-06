package by.javafx.communalPayments.objects;

public class Counters extends TabObjects {

    private String counterName;
    private String service;
    private String object;

    public Counters(String counterName, String service, String object) {
        this.object = object;
        this.service = service;
        this.counterName = counterName;
    }

    public Counters() {
    }

    public String getObject() {
        return object;
    }

    public void setObject(String object) {
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