package by.javafx.communalPayments.representationObjects;

public class Counters extends MyObjects {
    private int id;
    private int object;
    private int service;
    private String counterName;
    private double recentMeasure;

    public Counters(int id, int object, int service, String counterName, double recentMeasure) {
        this.id = id;
        this.object = object;
        this.service = service;
        this.counterName = counterName;
        this.recentMeasure = recentMeasure;
    }

    public Counters() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getObject() {
        return object;
    }

    public void setObject(int object) {
        this.object = object;
    }

    public int getService() {
        return service;
    }

    public void setService(int service) {
        this.service = service;
    }

    public String getCounterName() {
        return counterName;
    }

    public void setCounterName(String counterName) {
        this.counterName = counterName;
    }

    public double getRecentMeasure() {
        return recentMeasure;
    }

    public void setRecentMeasure(double recentMeasure) {
        this.recentMeasure = recentMeasure;
    }
}