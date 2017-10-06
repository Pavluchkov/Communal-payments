package by.javafx.communalPayments.objects;

import java.util.ArrayList;

public class ObjectAccounting extends TabObjects{
    private int personalAccount;
    private String objectName;
    private String owner;
    private String address;
    private int residents;
    private double area;

    public ObjectAccounting(int personalAccount, String objectName, String owner, String address, int residents, double area) {
        this.personalAccount = personalAccount;
        this.objectName = objectName;
        this.owner = owner;
        this.address = address;
        this.residents = residents;
        this.area = area;
    }

    public ObjectAccounting() {
    }

    public int getPersonalAccount() {
        return personalAccount;
    }

    public void setPersonalAccount(int personalAccount) {
        this.personalAccount = personalAccount;
    }

    public String getObjectName() {
        return objectName;
    }

    public void setObjectName(String objectName) {
        this.objectName = objectName;
    }

    public String getOwner() {
        return owner;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public int getResidents() {
        return residents;
    }

    public void setResidents(int residents) {
        this.residents = residents;
    }

    public double getArea() {
        return area;
    }

    public void setArea(double area) {
        this.area = area;
    }
}
