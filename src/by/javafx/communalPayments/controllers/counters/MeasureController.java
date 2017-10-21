package by.javafx.communalPayments.controllers.counters;

import by.javafx.communalPayments.controllers.MainController;
import by.javafx.communalPayments.controllers.payments.PaymentAddController;
import by.javafx.communalPayments.objects.Counters;
import by.javafx.communalPayments.objects.Measurement;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.sql.Date;
import java.sql.SQLException;
import java.time.LocalDate;

public class MeasureController extends MainController {
    private Counters object;
    private PaymentAddController paymentAddController;

    @FXML
    private Label textLabel;
    @FXML
    private TextField previousMeasureField;
    @FXML
    private TextField measureField;
    @FXML
    private DatePicker datePicker;
    @FXML
    private Button btnCancel;

    public MeasureController(PaymentAddController paymentAddController, Counters counter) {
        this.object = counter;
        this.paymentAddController = paymentAddController;
    }

    public MeasureController() {
    }

    @FXML
    public void initialize() {

        textLabel.setText(object.getCounterName());
        previousMeasureField.setText(String.valueOf(object.getRecentMeasure()));
        previousMeasureField.setEditable(false);
        datePicker.setValue(LocalDate.now());

    }

    @FXML
    public void btnOkClicked() {

        if ((measureField.getText().isEmpty())) {
            printDialogError("Внесение показаний счетчика", "Ошибка внесения показаний!",
                    "Поле <Текущие показания> должно быть заполнено");
            return;
        }

        double rate;

        try {
            rate = database.getRate(object.getService());
        } catch (SQLException e) {
            printDialogError("Работа с базой данных", "Ошибка чтения данных из БД !", e.getMessage());
            return;
        }

        double sum;

        try {
            sum = (Double.parseDouble(measureField.getText()) - object.getRecentMeasure()) * rate;
        } catch (NumberFormatException e) {
            printDialogError("Ввод данных", "Ошибка ввода данных !", e.getMessage());
            return;
        }

        if (sum < 0) {
            printDialogError("Ввод данных", "Ошибка ввода данных !", "Текущие показания меньше предыдущих.");
            return;
        }

        object.setRecentMeasure(Double.parseDouble(measureField.getText()));
        paymentAddController.setCounter(object);
        Measurement measure = new Measurement(0, object.getId(), Double.parseDouble(measureField.getText()),
                Date.valueOf(datePicker.getValue()));
        paymentAddController.setMeasure(measure);

        paymentAddController.setLayout(true);

        paymentAddController.setSum(sum);

        btnCancelClicked();
    }

    @FXML
    private void btnCancelClicked() {
        Stage stage = (Stage) btnCancel.getScene().getWindow();
        stage.close();
    }
}
