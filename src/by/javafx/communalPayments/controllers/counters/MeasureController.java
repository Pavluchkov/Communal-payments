package by.javafx.communalPayments.controllers.counters;

import by.javafx.communalPayments.controllers.MainController;
import by.javafx.communalPayments.controllers.payments.PaymentAddController;
import by.javafx.communalPayments.objects.Counters;
import by.javafx.communalPayments.objects.Measurement;
import by.javafx.communalPayments.objects.Payments;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.sql.Date;
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

        double rate = paymentAddController.getRate(object.getService());

        double volume;

        try {

            volume = Double.parseDouble(measureField.getText()) - object.getRecentMeasure();

        } catch (NumberFormatException e) {
            printDialogError("Ввод данных", "Ошибка ввода данных !", e.getMessage());
            return;
        }

        double sum = volume * rate;

        if (sum < 0) {
            printDialogError("Ввод данных", "Ошибка ввода данных !", "Текущие показания меньше предыдущих.");
            return;
        }

        Payments payment = new Payments();
        payment.setId(0);
        payment.setObject(object.getObject());
        payment.setService(object.getService());
        payment.setUnit("");
        payment.setVolume(Math.rint(volume *1000) /1000);
        payment.setRate(rate);
        payment.setAccrued(0);
        payment.setPaid(0);
        payment.setDate(Date.valueOf(LocalDate.now()));

        paymentAddController.setPayment(payment);

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
