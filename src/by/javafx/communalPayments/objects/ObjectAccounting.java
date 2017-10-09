package by.javafx.communalPayments.objects;

public class ObjectAccounting {
    private int id;
    private String objectName;
    private String owner;
    private String address;
    private int residents;
    private double area;

    public ObjectAccounting(int id, String objectName, String owner, String address, int residents, double area) {
        this.id = id;
        this.objectName = objectName;
        this.owner = owner;
        this.address = address;
        this.residents = residents;
        this.area = area;
    }

    public ObjectAccounting() {
    }

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
