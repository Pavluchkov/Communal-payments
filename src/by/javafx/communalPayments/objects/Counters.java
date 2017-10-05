package by.javafx.communalPayments.objects;

public class Counters {

    private int id_counter;
    private String objectName;
    private String serviceName;
    private String counterName;

    public Counters(int id_counter, String objectName, String serviceName, String counterName) {
        this.id_counter = id_counter;
        this.objectName = objectName;
        this.serviceName = serviceName;
        this.counterName = counterName;
    }

    public Counters(){

    }

    public int getId_counter() {
        return id_counter;
    }

    public void setId_counter(int id_counter) {
        this.id_counter = id_counter;
    }

    public String getObjectName() {
        return objectName;
    }

    public void setObjectName(String objectName) {
        this.objectName = objectName;
    }

    public String getServiceName() {
        return serviceName;
    }

    public void setServiceName(String serviceName) {
        this.serviceName = serviceName;
    }

    public String getCounterName() {
        return counterName;
    }

    public void setCounterName(String counterName) {
        this.counterName = counterName;
    }
}
