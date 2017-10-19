package by.javafx.communalPayments.objects;

import java.sql.Date;

public class Measurement extends MyObjects {
    private int id;
    private int counter;
    private double previousMeasure;
    private double measure;
    private Date date;

    public Measurement(int id, int counter, double previousMeasure, double measure, Date date) {
        this.id = id;
        this.counter = counter;
        this.previousMeasure = previousMeasure;
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

    public double getPreviousMeasure() {
        return previousMeasure;
    }

    public void setPreviosMeasure(double previosMeasure) {
        this.previousMeasure = previousMeasure;
    }

    public double getMeasure() {
        return measure;
    }

    public void setMeasure(double measure) {
        this.measure = measure;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }
}
