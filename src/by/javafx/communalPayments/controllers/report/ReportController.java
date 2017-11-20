package by.javafx.communalPayments.controllers.report;

import by.javafx.communalPayments.controllers.MainController;
import by.javafx.communalPayments.representationObjects.ObjectAccounting;
import by.javafx.communalPayments.representationObjects.Payments;
import by.javafx.communalPayments.representationObjects.Services;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.PieChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.stage.Popup;

import java.time.LocalDate;
import java.time.format.TextStyle;
import java.util.ArrayList;
import java.util.Locale;

public class ReportController extends MainController {

    private double sumServiceMonth;
    private MainController mainController;

    public ReportController(MainController mainController) {
        this.mainController = mainController;
    }

    public void drawPieChart() {

        ObservableList<Payments> tablePayments = database.getTablePayments();
        ObservableList<Services> tableServices = database.getTableServices();

        int objectId = 0;
        ObservableList<ObjectAccounting> tableObject = database.getTableObject();

        for (ObjectAccounting obj : tableObject) {
            if (obj.getObjectName().equals(mainController.getReportObjCombo().getValue())) {
                objectId = obj.getId();
            }
        }

        ArrayList<Payments> payments = new ArrayList<>();
        String monthCombo = mainController.getReportMonthCombo().getValue();
        String yearCombo = mainController.getReportYearCombo().getValue();
        double sum = 0;
        Locale local = new Locale("ru", "RU");

        if ((monthCombo != null) && (yearCombo != null)) {
            for (Payments obj : tablePayments) {

                if (obj.getObject() == objectId) {
                    LocalDate date = obj.getDate().toLocalDate();
                    String month = String.valueOf(date.getMonth().getDisplayName(TextStyle.FULL_STANDALONE, local));
                    if (monthCombo.equals(month)) {
                        if (yearCombo.equals(String.valueOf(obj.getDate().toLocalDate().getYear()))) {
                            sum += obj.getPaid();
                            payments.add(obj);
                        }
                    }
                }

            }
            sumServiceMonth = sum;
        }

        ObservableList<PieChart.Data> pieChartData = FXCollections.observableArrayList();

        if (!payments.isEmpty()) {

            for (Services services : tableServices) {
                double temp = 0;

                for (Payments obj : payments) {

                    if (services.getId() == obj.getService()) {
                        temp += obj.getPaid();
                    }
                }

                if (temp != 0) {
                    pieChartData.add(new PieChart.Data(services.getServiceName(), temp * 100 / sum));
                }

            }
        }

        mainController.getPieChart().setData(pieChartData);

        if (pieChartData.isEmpty()) {
            mainController.getPieChart().setTitle("Нет данных за " + monthCombo + ", " + yearCombo);
        } else {

            mainController.getPieChart().setTitle("Распределение расходов за " + monthCombo + ", " + yearCombo);

            final Popup popup = new Popup();
            popup.setAutoHide(true);
            final Label label = new Label("");
            popup.getContent().addAll(label);

            for (final PieChart.Data data : pieChartData) {

                data.getNode().addEventHandler(MouseEvent.MOUSE_PRESSED,
                        e -> {
                            label.setText(String.valueOf(Math.rint(data.getPieValue())) + "%" +
                                    "\n" + Math.rint(data.getPieValue() / 100 * sumServiceMonth * 100) / 100 + " руб.");
                            popup.setX(e.getScreenX());
                            popup.setY(e.getScreenY());
                            popup.show(mainController.getTabPane().getScene().getWindow());
                        });
            }
        }


    }

    public void drawBarChart() {

        ObservableList<String> listYear = FXCollections.observableArrayList();
        ObservableList<Payments> tablePayments = database.getTablePayments();

        ObservableList<Services> tableServices = database.getTableServices();

        int objectId = 0;
        ObservableList<ObjectAccounting> tableObject = database.getTableObject();

        for (ObjectAccounting obj : tableObject) {
            if (obj.getObjectName().equals(mainController.getReportObjCombo().getValue())) {
                objectId = obj.getId();
            }
        }

        for (Payments obj : tablePayments) {
            if (obj.getObject() == objectId) {
                if (listYear.indexOf(String.valueOf(obj.getDate().toLocalDate().getYear())) == -1) {
                    listYear.add(String.valueOf(obj.getDate().toLocalDate().getYear()));
                }

            }

        }

        ObservableList<XYChart.Series<String, Double>> barChartData = FXCollections.observableArrayList();
        final CategoryAxis xAxis = new CategoryAxis();
        final NumberAxis yAxis = new NumberAxis();

        xAxis.setLabel("Услуга");
        yAxis.setLabel("Сумма");

        for (String year : listYear) {
            double sum = 0;
            XYChart.Series<String, Double> series = new XYChart.Series<>();
            series.setName(year);

            for (Services service : tableServices) {
                for (Payments obj : tablePayments) {

                    if (year.equals(String.valueOf(obj.getDate().toLocalDate().getYear()))) {
                        if (obj.getService() == service.getId()) {
                            if (obj.getObject() == objectId) {
                                sum += obj.getPaid();
                            }
                        }
                    }
                }

                if (sum != 0) {
                    series.getData().add(new XYChart.Data<>(service.getServiceName(), sum));
                    sum = 0;
                }
            }

            barChartData.add(series);
        }

        if (barChartData.isEmpty()) {
            mainController.getBarChart().setTitle("Нет данных");
        } else {
            mainController.getBarChart().setTitle("Распределение расходов по годам");
        }

        mainController.getBarChart().setData(barChartData);
    }

}
