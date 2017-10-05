package by.javafx.communalPayments.objects;

public class Payments {
    private int id_payments;
    private String service;
    private double valuePayments;
    private double datePayments;

    public Payments(int id_payments, String service, double valuePayments, double datePayments) {
        this.id_payments = id_payments;
        this.service = service;
        this.valuePayments = valuePayments;
        this.datePayments = datePayments;
    }

    public Payments() {
    }

    public int getId_payments() {
        return id_payments;
    }

    public void setId_payments(int id_payments) {
        this.id_payments = id_payments;
    }

    public String getService() {
        return service;
    }

    public void setService(String service) {
        this.service = service;
    }

    public double getValuePayments() {
        return valuePayments;
    }

    public void setValuePayments(double valuePayments) {
        this.valuePayments = valuePayments;
    }

    public double getDatePayments() {
        return datePayments;
    }

    public void setDatePayments(double datePayments) {
        this.datePayments = datePayments;
    }
}
