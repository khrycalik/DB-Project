package Klient;

import SQLManagment.DBManagment;
import changeScreen.ChangeScreen;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import login.LoginScreenController;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ResourceBundle;

public class KlientDashboardController implements Initializable {
    Connection con = null;
    PreparedStatement pst = null;
    ResultSet res = null;
    ChangeScreen screen = new ChangeScreen();
    @FXML
    private Label nameLabel;
    @FXML
    private Pane mainArea;

    /**
     * Metoda, ustawiająca początkowe dane na ekranie
     */
    public void setData() {
        try {
            con = DBManagment.connect();
            String sql = "select * from klient where id_klienta=?;";
            pst = con.prepareStatement(sql);
            pst.setInt(1, LoginScreenController.acc);
            res = pst.executeQuery();

            if (res.next()) {
                StringBuilder tmp = new StringBuilder();
                tmp.append(res.getString("imie")).append(" ").append(res.getString("nazwisko"));
                nameLabel.setText(tmp.toString());
            }
            System.out.println(res);
            res.close();
            pst.close();
            con.close();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    /**
     * Metoda, która wylogowuje użytkownika
     */
    public void logout(MouseEvent event) throws IOException {
        screen.logout(event);
    }

    /**
     * Metoda, przekierowująca użytkownika do okna "Moje dane"
     */
    @FXML
    private void information(MouseEvent event) throws IOException {
        Parent fxml = FXMLLoader.load(getClass().getResource("InformationScreen.fxml"));
        mainArea.getChildren().removeAll();
        mainArea.getChildren().addAll(fxml);
    }

    /**
     * Metoda, przekierowująca użytkownika do okna "Wypożyczenia i zamówienia"
     */
    @FXML
    private void wypozyczenia(MouseEvent event) throws IOException {
        Parent fxml = FXMLLoader.load(getClass().getResource("HistoryDetailsScreen.fxml"));
        mainArea.getChildren().removeAll();
        mainArea.getChildren().addAll(fxml);
    }

    /**
     * Metoda, przekierowująca użytkownika do okna "Księgarnia"
     */
    @FXML
    private void books(MouseEvent event) throws IOException {
        Parent fxml = FXMLLoader.load(getClass().getResource("BooksScreen.fxml"));
        mainArea.getChildren().removeAll();
        mainArea.getChildren().addAll(fxml);
    }

    /**
     * Metoda, przekierowująca użytkownika do okna "Koszyk"
     */
    @FXML
    private void backet(MouseEvent event) throws IOException {
        Parent fxml = FXMLLoader.load(getClass().getResource("BacketScreen.fxml"));
        mainArea.getChildren().removeAll();
        mainArea.getChildren().addAll(fxml);
    }

    /**
     * Jest to główna metoda wykorzystująca metodę setData()
     */
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        setData();
    }
}