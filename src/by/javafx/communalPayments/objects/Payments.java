package by.javafx.communalPayments.objects;

public class Payments extends TabObjects{
    private int id_payments;
    private int service;
    private double valuePayments;
    private String datePayments;

    public Payments(int id_payments, int service, double valuePayments, String datePayments) {
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

    public int getService() {
        return service;
    }

    public void setService(int service) {
        this.service = service;
    }

    public double getValuePayments() {
        return valuePayments;
    }

    public void setValuePayments(double valuePayments) {
        this.valuePayments = valuePayments;
    }

    public String getDatePayments() {
        return datePayments;
    }

    public void setDatePayments(String datePayments) {
        this.datePayments = datePayments;
    }
}
