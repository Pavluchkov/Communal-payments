package by.javafx.communalPayments.objects;

public class ServiceList extends TabObjects{
    private String serviceName;
    private String unit;
    private double rate;
    private String formPayments;

    public ServiceList(String serviceName, String unit, double rate, String formPayments) {
        this.serviceName = serviceName;
        this.unit = unit;
        this.rate = rate;
        this.formPayments = formPayments;
    }

    public ServiceList() {
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

    public String getFormPayments() {
        return formPayments;
    }

    public void setFormPayments(String formPayments) {
        this.formPayments = formPayments;
    }
}
