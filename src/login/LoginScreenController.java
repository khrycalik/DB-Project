package login;

import SQLManagment.DBManagment;
import alerts.AlertBox;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.PasswordField;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;

import changeScreen.ChangeScreen;

public class LoginScreenController implements Initializable {
    public static Stage myStage = null;
    public static int acc;
    Connection con = null;
    PreparedStatement pst = null;
    ResultSet res = null;
    ChangeScreen screen = new ChangeScreen();
    @FXML
    private Pane mainArea;
    @FXML
    private TextField loginField;
    @FXML
    private PasswordField passField;
    @FXML
    private RadioButton accountA;
    @FXML
    private RadioButton accountK;

    /**
     * Funkcja, która sprawdza mozliwość logowania pacjenta do aplikacji
     */
    public boolean checkKlientLoginData(String login, String pass) {
        try {
            con = DBManagment.connect();
            String sql = "Select zalogujKlienta(?,?);";
            pst = con.prepareStatement(sql);
            pst.setString(1, login);
            pst.setString(2, pass);
            pst.execute();

            pst.close();
            con.close();
            return true;

        } catch (SQLException e) {
            AlertBox.errorAlert("Nie udało sie zalogować!", "Nie udało sie zalogować " +
                    e.getMessage());
        } finally {
            DBManagment.closeAll(con, res, pst);
        }
        return false;
    }

    /**
     * Funkcja, która sprawdza mozliwość logowania administratora do aplikacji
     */
    public boolean checkAdminLoginData(String login, String pass) {
        try {
            con = DBManagment.connect();
            String sql = "Select zalogujadmina(?,?);";
            pst = con.prepareStatement(sql);
            pst.setString(1, login);
            pst.setString(2, pass);
            pst.execute();

            pst.close();
            con.close();
            return true;

        } catch (SQLException e) {
            AlertBox.errorAlert("Nie udało sie zalogować!", "Nie udało sie zalogować " +
                    e.getMessage());
        } finally {
            DBManagment.closeAll(con, res, pst);
        }
        return false;
    }

    /**
     * Funkcja która loguje uzytkownika w zalezności od wybranej roli
     */
    public void loginAccount(MouseEvent event) throws IOException {
        String login = loginField.getText();
        String pass = passField.getText();
        // System.out.println(email);
        if (accountK.isSelected()) {
            if (checkKlientLoginData(login, pass)) {
                try {
                    con = DBManagment.connect();
                    String sql = "select * from \"Dane konta\" where login=?;";

                    pst = con.prepareStatement(sql);
                    pst.setString(1, loginField.getText());
                    res = pst.executeQuery();

                    if (res.next()) {
                        acc = res.getInt("id_klienta");
                        System.out.println(acc);
                    }
                    res.close();
                    pst.close();
                    con.close();
                    screen.loadScreen("/Klient/KlientDashboard.fxml", event, myStage);
                } catch (Exception e) {
                    System.out.println(e.getMessage());
                }
            }
        } else if (accountA.isSelected()) {
            if (checkAdminLoginData(login, pass)) {
                try {
                    con = DBManagment.connect();
                    String sql = "select id_admina from administrator where login=? and hasło=?;";
                    pst = con.prepareStatement(sql);
                    pst.setString(1, loginField.getText());
                    pst.setString(2, passField.getText());
                    res = pst.executeQuery();

                    if (res.next()) {
                        acc = res.getInt("id_admina");
                        System.out.println(acc);
                    }
                    res.close();
                    pst.close();
                    con.close();
                    screen.loadScreen("/Administrator/AdminDashboard.fxml", event, myStage);
                } catch (Exception e) {
                    System.out.println(e.getMessage());
                }
            }
        }
    }

    /**
     * Metoda, przekierowująca użytkownika do okna Rejestracji konta
     */
    @FXML
    private void createAccount(MouseEvent event) throws IOException {
        Parent fxml = FXMLLoader.load(getClass().getResource("/createaccount/CreateAccount.fxml"));
        mainArea.getChildren().removeAll();
        mainArea.getChildren().addAll(fxml);
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
    }
}