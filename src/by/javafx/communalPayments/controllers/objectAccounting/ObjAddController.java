package by.javafx.communalPayments.controllers.objectAccounting;

import by.javafx.communalPayments.controllers.MainController;
import by.javafx.communalPayments.objects.ObjectAccounting;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.sql.SQLException;

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


//    private UnaryOperator<TextFormatter.Change> textFilter(String regexString) {
//        UnaryOperator<TextFormatter.Change> Filter = change -> {
//            String text = change.getText();
//
//            if (text.matches(regexString)) {
//                return change;
//            }
//
//            return null;
//        };
//
//        return Filter;
//    }

    @FXML
    private void initialize() {

//        TextFormatter<String> textFormatterPersonal = new TextFormatter<>(textFilter("[0-9]*"));
//        TextFormatter<String> textFormatterResident = new TextFormatter<>(textFilter("[0-9]*"));
//        TextFormatter<String> textFormatterArea = new TextFormatter<>(textFilter("[0-9.]*"));
//        personalAccount.setTextFormatter(textFormatterPersonal);
//        residents.setTextFormatter(textFormatterResident);
//        area.setTextFormatter(textFormatterArea);

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

        try {
            ObjectAccounting object = new ObjectAccounting();
            object.setId(Integer.parseInt(personalAccount.getText()));
            object.setObjectName(nameObject.getText());
            object.setOwner(owner.getText());
            object.setAddress(address.getText());
            object.setResidents(Integer.parseInt(residents.getText()));
            object.setArea(Double.parseDouble(area.getText()));
            database.add(object);
        } catch (NumberFormatException e) {
            printDialogError("Ввод данных", "Ошибка ввода данных !", e.getMessage());
            return;
        } catch (SQLException e) {
            printDialogError("Работа с базой данных", "Ошибка записи данных в БД !", e.getMessage());
            return;
        }

        btnCancelClicked();

    }

    @FXML
    private void btnCancelClicked() {
        Stage stage = (Stage) btnCancel.getScene().getWindow();
        stage.close();
    }
}
