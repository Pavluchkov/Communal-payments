package by.javafx.communalPayments.objects;

import java.util.ArrayList;

public class ObjectAccounting {
    private int id;
    private String objectName;
    private String fio;
    private String address;
    private int residentNumber;
    private double totalArea;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getObjectName() {
        return objectName;
    }

    public void setObjectName(String objectName) {
        this.objectName = objectName;
    }

    public String getFio() {
        return fio;
    }

    public void setFio(String fio) {
        this.fio = fio;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public int getResidentNumber() {
        return residentNumber;
    }

    public void setResidentNumber(int residentNumber) {
        this.residentNumber = residentNumber;
    }

    public double getTotalArea() {
        return totalArea;
    }

    public void setTotalArea(double totalArea) {
        this.totalArea = totalArea;
    }
}
