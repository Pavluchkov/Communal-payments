package by.javafx.communalPayments.objects;

public class Counters {
    private int id;
    private String Name;
    private String edIzm;
    private double currentReadings;

    public Counters(int id, String name, String edIzm, double currentReadings) {
        this.id = id;
        Name = name;
        this.edIzm = edIzm;
        this.currentReadings = currentReadings;
    }

    public Counters() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getEdIzm() {
        return edIzm;
    }

    public void setEdIzm(String edIzm) {
        this.edIzm = edIzm;
    }

    public double getCurrentReadings() {
        return currentReadings;
    }

    public void setCurrentReadings(double currentReadings) {
        this.currentReadings = currentReadings;
    }
}
