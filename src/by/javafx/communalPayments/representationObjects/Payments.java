package by.javafx.communalPayments.representationObjects;

import java.sql.Date;

public class Payments extends MyObjects {
    private int id;
    private int object;
    private int service;
    private String unit;
    private double volume;
    private double rate;
    private double accrued;
    private double paid;
    private Date date;

    public Payments(int id, int object, int service, String unit, double volume, double rate, double accrued, double paid, Date date) {
        this.id = id;
        this.object = object;
        this.service = service;
        this.unit = unit;
        this.volume = volume;
        this.rate = rate;
        this.accrued = accrued;
        this.paid = paid;
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

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public double getVolume() {
        return volume;
    }

    public void setVolume(double volume) {
        this.volume = volume;
    }

    public double getRate() {
        return rate;
    }

    public void setRate(double rate) {
        this.rate = rate;
    }

    public double getAccrued() {
        return accrued;
    }

    public void setAccrued(double accrued) {
        this.accrued = accrued;
    }

    public double getPaid() {
        return paid;
    }

    public void setPaid(double paid) {
        this.paid = paid;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }
}
