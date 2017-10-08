package by.javafx.communalPayments.objects;

public class ServiceList extends TabObjects{
    private int id;
    private String serviceName;
    private String unit;
    private double rate;
    private int formPayments;

    public ServiceList(int id, String serviceName, String unit, double rate, int formPayments) {
        this.id = id;
        this.serviceName = serviceName;
        this.unit = unit;
        this.rate = rate;
        this.formPayments = formPayments;
    }

    public ServiceList() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getServiceName() {
        return serviceName;
    }

    public void setServiceName(String serviceName) {
        this.serviceName = serviceName;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public double getRate() {
        return rate;
    }

    public void setRate(double rate) {
        this.rate = rate;
    }

    public int getFormPayments() {
        return formPayments;
    }

    public void setFormPayments(int formPayments) {
        this.formPayments = formPayments;
    }
}
