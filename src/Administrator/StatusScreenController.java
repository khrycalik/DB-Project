package Administrator;

import SQLManagment.DBManagment;
import alerts.AlertBox;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Text;
import login.LoginScreenController;
import user.Klient;

import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Locale;
import java.util.ResourceBundle;

public class StatusScreenController implements Initializable {
    Connection con = null;
    PreparedStatement pst = null;
    ResultSet res = null;
    ResultSet res1 = null;

    @FXML
    private ComboBox<String> wybor;
    @FXML
    private Button sz;
    @FXML
    private Button pz;
    @FXML
    private Button sw;
    @FXML
    private Button pw;

    @FXML
    private TableView<Klient> detailsTable;
    @FXML
    private TableColumn<Klient, String> id;
    @FXML
    private TableColumn<Klient, String> tytul;
    @FXML
    private TableColumn<Klient, String> cena;
    @FXML
    private TableColumn<Klient, String> statusZam;
    @FXML
    private TableColumn<Klient, String> dataZam;
    @FXML
    private TableColumn<Klient, String> statusPl;
    @FXML
    private TableColumn<Klient, String> kto;
    @FXML
    private TextField nowyStatus;

    ObservableList<Klient> list = FXCollections.observableArrayList();

    /**
     * Metoda, ustawiająca początkowe dane na ekranie
     */
    private void setData() {

        id.setCellValueFactory(new PropertyValueFactory<>("pole"));
        tytul.setCellValueFactory(new PropertyValueFactory<>("tytul"));
        cena.setCellValueFactory(new PropertyValueFactory<>("cena"));
        statusZam.setCellValueFactory(new PropertyValueFactory<>("statusZam"));
        dataZam.setCellValueFactory(new PropertyValueFactory<>("dataZam"));
        statusPl.setCellValueFactory(new PropertyValueFactory<>("statusPl"));
        kto.setCellValueFactory(new PropertyValueFactory<>("adresDost"));
        pz.setDisable(true);
        sz.setDisable(true);
        try {
            con = DBManagment.connect();
            wybor.getItems().add("Wypożyczenia");
            wybor.getItems().add("Zamówienia");
            String sql1 = "select * from informacjeWypozyczenia";

            pst = con.prepareStatement(sql1);
            res = pst.executeQuery();
            while (res.next()) {
                list.add(new Klient(res.getString("id_wypożyczenia"), res.getString("tytuł książki"),
                        res.getString("cena wypożyczenia"),
                        res.getString("status wypożyczenia"),
                        res.getString("termin zwrotu"), res.getString("status płatności"),
                        res.getString("imie") + " " + res.getString("nazwisko")));
            }
            res.close();
            pst.close();
            con.close();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        detailsTable.setItems(list);

    }

    /**
     * Metoda, zmieniająca status płatności dla wypożyczenia wybranego z tabeli
     */
    @FXML
    private void platnoscW(MouseEvent event) {
        try {
            con = DBManagment.connect();
            String sql = "update wypożyczenia set \"status płatości\"=? where id_wypożyczenia=?";
            pst = con.prepareStatement(sql);
            pst.setString(1, nowyStatus.getText());
            pst.setInt(2, Integer.parseInt(detailsTable.getSelectionModel().getSelectedItem().getPole()));
            pst.executeUpdate();
            pst.close();
            con.close();
            infoTable();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    /**
     * Metoda, zmieniająca status płatności dla zamówienia wybranego z tabeli
     */
    @FXML
    private void platnoscZ(MouseEvent event) {
        try {
            con = DBManagment.connect();
            String sql = "update zamówienia set \"status płatości\"=? where id_zamowienia=?";
            pst = con.prepareStatement(sql);
            pst.setString(1, nowyStatus.getText());
            pst.setInt(2, Integer.parseInt(detailsTable.getSelectionModel().getSelectedItem().getPole()));
            pst.executeUpdate();
            pst.close();
            con.close();
            infoTable();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    /**
     * Metoda, zmieniająca status wypożyczenia wybranego z tabeli rekordu
     */
    @FXML
    private void statusW(MouseEvent event) {
        try {
            con = DBManagment.connect();
            String sql = "update wypożyczenia set \"status wypożyczenia\"=? where id_wypożyczenia=?";
            pst = con.prepareStatement(sql);
            pst.setString(1, nowyStatus.getText());
            pst.setInt(2, Integer.parseInt(detailsTable.getSelectionModel().getSelectedItem().getPole()));
            pst.executeUpdate();
            pst.close();
            con.close();
            infoTable();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    /**
     * Metoda, zmieniająca status zamówenia wybranego z tabeli rekordu
     */
    @FXML
    private void statusZ(MouseEvent event) {
        try {
            con = DBManagment.connect();
            String sql = "update zamówienia set \"status zamówienia\"=? where id_zamowienia=?";
            pst = con.prepareStatement(sql);
            pst.setString(1, nowyStatus.getText());
            pst.setInt(2, Integer.parseInt(detailsTable.getSelectionModel().getSelectedItem().getPole()));
            pst.executeUpdate();
            pst.close();
            con.close();
            infoTable();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    /**
     * Metoda, która generuje listę zamówień/wypożyczeń w zależności od wybranej
     * wartośći w comboBoxie
     */
    @FXML
    private void infoTable() {

        tytul.setCellValueFactory(new PropertyValueFactory<>("tytul"));
        cena.setCellValueFactory(new PropertyValueFactory<>("cena"));
        statusZam.setCellValueFactory(new PropertyValueFactory<>("statusZam"));
        dataZam.setCellValueFactory(new PropertyValueFactory<>("dataZam"));
        statusPl.setCellValueFactory(new PropertyValueFactory<>("statusPl"));
        kto.setCellValueFactory(new PropertyValueFactory<>("adresDost"));

        try {

            String[] tmp = wybor.getSelectionModel().getSelectedItem().split(",");
            System.out.println(tmp[0]);
            con = DBManagment.connect();

            if (tmp[0] == "Zamówienia") {
                System.out.println("test");
                String sql1 = "select * from informacjeZamowienie";
                statusZam.setText("Status zamowienia");
                dataZam.setText("Adres dostawy");
                pst = con.prepareStatement(sql1);
                res = pst.executeQuery();
                list.clear();
                while (res.next()) {
                    System.out.println(
                            res.getString("tytuł książki") + res.getString("cena") + res.getString("data zamówienia")
                                    + res.getString("status płatności"));
                    list.add(new Klient(res.getString("id_zamowienia"), res.getString("tytuł książki"),
                            res.getString("cena"),
                            res.getString("status zamówienia"),
                            res.getString("adres dostawy"), res.getString("status płatności"),
                            res.getString("imie") + " " + res.getString("nazwisko")));
                }
                sw.setDisable(true);
                pw.setDisable(true);
                sz.setDisable(false);
                pz.setDisable(false);
            } else if (tmp[0] == "Wypożyczenia") {
                String sql1 = "select * from informacjeWypozyczenia;";
                pst = con.prepareStatement(sql1);
                res = pst.executeQuery();
                list.clear();
                statusZam.setText("Status wypożyczenia");
                dataZam.setText("Termin zwrotu");
                while (res.next()) {
                    list.add(new Klient(res.getString("id_wypożyczenia"), res.getString("tytuł książki"),
                            res.getString("cena wypożyczenia"),
                            res.getString("status wypożyczenia"),
                            res.getString("termin zwrotu"), res.getString("status płatności"),
                            res.getString("imie") + " " + res.getString("nazwisko")));
                }
                sz.setDisable(true);
                pz.setDisable(true);
                sw.setDisable(false);
                pw.setDisable(false);
            }

            res.close();
            pst.close();
            con.close();
            con.close();
        } catch (Exception e) {
            AlertBox.errorAlert("Bląd", e.getMessage());
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