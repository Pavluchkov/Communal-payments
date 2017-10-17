package by.javafx.communalPayments.objects;

public class Measurement extends MyObjects {
    private int id;
    private int counter;
    private double measure;
    private String date;

    public Measurement(int id, int counter, double measure, String date) {
        this.id = id;
        this.counter = counter;
        this.measure = measure;
        this.date = date;
    }

    public Measurement() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getCounter() {
        return counter;
    }

    public void setCounter(int counter) {
        this.counter = counter;
    }

    public double getMeasure() {
        return measure;
    }

    public void setMeasure(double measure) {
        this.measure = measure;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
