package by.javafx.communalPayments.objects;

public class Payments extends MyObjects{
    private int id;
    private int object;
    private int service;
    private double sum;
    private String date;

    public Payments(int id, int object, int service, double sum, String date) {
        this.id = id;
        this.object = object;
        this.service = service;
        this.sum = sum;
        this.date = date;
    }

    public Payments() {
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

    public double getSum() {
        return sum;
    }

    public void setSum(double sum) {
        this.sum = sum;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
