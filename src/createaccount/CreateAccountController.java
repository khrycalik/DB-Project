package createaccount;

import SQLManagment.DBManagment;
import alerts.AlertBox;
import checkinput.CheckTextField;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;

import login.Main;

public class CreateAccountController implements Initializable {
    Connection con = null;
    PreparedStatement pst = null;
    ResultSet res = null;
    @FXML
    private TextField nameField;
    @FXML
    private TextField surnameField;
    @FXML
    private TextField numberField;
    @FXML
    private TextField emailField;
    @FXML
    private TextField loginField;
    @FXML
    private PasswordField passField;

    /**
     * Metoda, przekierowująca użytkownika do okna logowania
     */
    public void backToLogin(MouseEvent event) throws IOException {
        Main.myStage.getScene().setRoot(FXMLLoader.load(getClass().getResource("/login/LoginScreen.fxml")));
    }

    /**
     * Funkcja, która sprawdza mozliwość dodania kleinta do bazy
     */
    public boolean createKlient(String name, String surname, String email, String number, String login, String pass) {
        try {
            con = DBManagment.connect();
            String sql = "Select dodajKlienta(?,?,?,?,?,?);";
            pst = con.prepareStatement(sql);
            pst.setString(1, name);
            pst.setString(2, surname);
            pst.setString(3, email);
            pst.setString(4, number);
            pst.setString(5, login);
            pst.setString(6, pass);

            pst.execute();

            pst.close();
            con.close();
            return true;

        } catch (SQLException e) {
            System.out.println(e.getMessage());
            AlertBox.errorAlert("Konto nie utworzone",
                    "Twoje konto nie zostało utworzone! Sprawdź dane i sprobuj jeszcze raz" + e.getMessage());
        } finally {
            DBManagment.closeAll(con, res, pst);
        }
        return false;
    }

    /**
     * Metoda, dodająca klienta do bazy
     */
    public void newAccount(MouseEvent event) throws IOException {
        String name = nameField.getText();
        String surname = surnameField.getText();
        String email = emailField.getText();
        String number = numberField.getText();
        String login = loginField.getText();
        String pass = passField.getText();
        if (CheckTextField.checkFullnameField(nameField) && CheckTextField.checkFullnameField(surnameField)
                && CheckTextField.checkNumberField(numberField)
                && CheckTextField.checkLogin(loginField) &&
                CheckTextField.checkPass(passField)) {
            System.out.println("test");
            if (createKlient(name, surname, email, number, login, pass)) {
                AlertBox.infoAlert("Konto utworzone", "Konto utworzone.",
                        "Twoje konto zostało pomyślnie utworzone. Możesz zalogować się za pomocą logina i hasła");
                Main.myStage.getScene().setRoot(FXMLLoader.load(getClass().getResource("/login/LoginScreen.fxml")));
            }
        }
    }

    /**
     * Jest to główna metoda wykorzystująca metodę setData() oraz ustawia
     * ograniczenia dla pola numeru telefonu
     */
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        numberField.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue.matches("\\d*")) {
                numberField.setText(newValue.replaceAll("[^\\d]", ""));
            }
        });
    }
}