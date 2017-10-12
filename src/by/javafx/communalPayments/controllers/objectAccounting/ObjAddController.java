package by.javafx.communalPayments.controllers.objectAccounting;

import by.javafx.communalPayments.controllers.MainController;
import by.javafx.communalPayments.objects.ObjectAccounting;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.control.TextFormatter;
import javafx.stage.Stage;

import java.sql.SQLException;
import java.util.function.UnaryOperator;

public class ObjAddController extends MainController {

    @FXML
    private TextField personalAccount;
    @FXML
    private TextField nameObject;
    @FXML
    private TextField owner;
    @FXML
    private TextField address;
    @FXML
    private TextField residents;
    @FXML
    private TextField area;

    @FXML
    private Button btnCancel;

    private UnaryOperator<TextFormatter.Change> textFilter(String regexString) {
        UnaryOperator<TextFormatter.Change> Filter = change -> {
            String text = change.getText();

            if (text.matches(regexString)) {
                return change;
            }

            return null;
        };

        return Filter;
    }

    @FXML
    private void initialize() {

        TextFormatter<String> textFormatterPersonal = new TextFormatter<>(textFilter("[0-9]*"));
        TextFormatter<String> textFormatterResident = new TextFormatter<>(textFilter("[0-9]*"));
        TextFormatter<String> textFormatterArea = new TextFormatter<>(textFilter("[0-9.]*"));
        personalAccount.setTextFormatter(textFormatterPersonal);
        residents.setTextFormatter(textFormatterResident);
        area.setTextFormatter(textFormatterArea);

//        personalAccount.addEventFilter(KeyEvent.KEY_TYPED, new EventHandler<KeyEvent>() {
//            @Override public void handle(KeyEvent keyEvent) {
//                if (!"0123456789".contains(keyEvent.getCharacter())) {
//                    keyEvent.consume();
//                }
//            }
//        });
    }

    @FXML
    public void BtnOkClicked() {

        ObjectAccounting object = new ObjectAccounting(Integer.parseInt(personalAccount.getText()), nameObject.getText(),
                owner.getText(), address.getText(), Integer.parseInt(residents.getText()), Double.parseDouble(area.getText()));

        try {
            database.add(object);
        } catch (SQLException e) {
            e.getMessage();
        }

        btnCancelClicked();

    }

    @FXML
    public void btnCancelClicked() {

        Stage stage = new Stage();
        stage = (Stage) btnCancel.getScene().getWindow();
        stage.close();
    }
}
