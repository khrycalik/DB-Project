package Klient;

import SQLManagment.DBManagment;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import login.LoginScreenController;
import user.Klient;

import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ResourceBundle;

public class InformationScreenController implements Initializable {
    Connection con = null;
    PreparedStatement pst = null;
    ResultSet res = null;
    @FXML
    private Label name;
    @FXML
    private Label surname;
    @FXML
    private Label email;
    @FXML
    private Label telefon;

    /**
     * Metoda, ustawiająca początkowe dane na ekranie
     */
    private void setData() {
        try {
            con = DBManagment.connect();
            String sql = "SELECT * from klient where id_klienta=" + LoginScreenController.acc + ";";
            pst = con.prepareStatement(sql);
            res = pst.executeQuery();

            if (res.next()) {
                name.setText(res.getString("imie"));
                surname.setText(res.getString("nazwisko"));
                email.setText(res.getString("email"));
                telefon.setText(res.getString("telefon"));
            }

            res.close();
            pst.close();

            con.close();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    /**
     * Jest to główna metoda wykorzystująca metodę setData()
     */
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        setData();
    }
}