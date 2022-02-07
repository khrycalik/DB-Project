package checkinput;

import alerts.AlertBox;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

public class CheckTextField {

    /**
     * Funkcja, która sprawdza poprawność wprowadzonego imienia lub nazwiska
     */
    public static boolean checkFullnameField(TextField tmpField) {
        if (tmpField.getText().equals("")) {
            AlertBox.errorAlert("Puste pole!", "Puste pole, wprowadź dane");
            return false;
        } else {
            Pattern p = Pattern.compile("[a-zA-Z]+");
            Matcher m = p.matcher(tmpField.getText());

            if (m.find() && m.group().equals(tmpField.getText())) {
                return true;
            } else {
                AlertBox.errorAlert("Twoje pole z imieniem lub nazwiskiem jest nieprawidłowe",
                        "Proszę korzystać tylko ze znaków. Sprobuj jeszcze raz!");
                return false;
            }
        }

    }

    /**
     * Funkcja sprawdzająca poprawność pola numeru telefonu
     */
    public static boolean checkNumberField(TextField tmpField) {
        Pattern p = Pattern.compile("[0-9]+");
        Matcher m = p.matcher(tmpField.getText());

        if (m.find() && m.group().equals(tmpField.getText()) && tmpField.getText().length() == 9) {
            return true;
        } else {
            AlertBox.errorAlert("Twoje pole z numerem jest nieprawidłowe",
                    "Proszę wpisać tylko cyfry lub sprawdzić długość (wymagana długość 9). Sprobuj jeszcze raz!");
            return false;
        }
    }

    /**
     * Funkcja sprawdzająca poprawność loginu
     */
    public static boolean checkLogin(TextField tmpField) {
        if (tmpField.getText().equals("")) {
            AlertBox.errorAlert("Brak tekstu w pole login", "Wpisz swój login");
            return false;
        }
        return true;
    }

    /**
     * Funkcja sprawdzająca poprawność hasła
     */
    public static boolean checkPass(PasswordField tmpField) {
        if (tmpField.getText().equals("")) {
            AlertBox.errorAlert("Brak tekstu w pole hasło", "Wpisz swoje hasło");
            return false;
        }
        return true;
    }

}