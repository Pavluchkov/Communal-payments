package by.javafx.communalPayments.interfaces.daoInterfaces;

import by.javafx.communalPayments.representationObjects.Counters;
import by.javafx.communalPayments.representationObjects.Measurement;

import java.sql.SQLException;

public interface MeasurementDao {
    void add(Measurement object) throws SQLException;

    void changeLastMeasure(Counters object, double lastMeasure) throws SQLException;
}
