package by.javafx.communalPayments.interfaces.daoInterfaces;

import by.javafx.communalPayments.objects.Counters;
import by.javafx.communalPayments.objects.Measurement;

import java.sql.SQLException;

public interface MeasurementDao {
    void add(Measurement object) throws SQLException;

    void changeLastMeasure(Counters object, double lastMeasure) throws SQLException;
}
