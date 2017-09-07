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

}
