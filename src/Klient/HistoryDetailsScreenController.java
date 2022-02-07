package Klient;

import SQLManagment.DBManagment;
import alerts.AlertBox;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import login.LoginScreenController;
import user.Klient;

import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ResourceBundle;

public class HistoryDetailsScreenController implements Initializable {
    Connection con = null;
    PreparedStatement pst = null;
    ResultSet res = null;
    ResultSet res1 = null;

    @FXML
    private ComboBox<String> wybor;

    @FXML
    private TableView<Klient> historyTable;
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

    ObservableList<Klient> list = FXCollections.observableArrayList();

    /**
     * Metoda, ustawiająca początkowe dane na ekranie
     */
    private void setData() {

        tytul.setCellValueFactory(new PropertyValueFactory<>("tytul"));
        cena.setCellValueFactory(new PropertyValueFactory<>("cena"));
        statusZam.setCellValueFactory(new PropertyValueFactory<>("statusZam"));
        dataZam.setCellValueFactory(new PropertyValueFactory<>("dataZam"));
        statusPl.setCellValueFactory(new PropertyValueFactory<>("statusPl"));
        try {
            con = DBManagment.connect();
            wybor.getItems().add("Wypożyczenia");
            wybor.getItems().add("Zamówienia");
            String sql1 = "select * from informacjeWypozyczenia where id_klienta="
                    + LoginScreenController.acc + ";";

            pst = con.prepareStatement(sql1);
            res = pst.executeQuery();
            while (res.next()) {
                list.add(new Klient(res.getString("tytuł książki"), res.getString("cena Wypożyczenia"),
                        res.getString("status wypożyczenia"),
                        res.getString("termin zwrotu"), res.getString("status płatności"),
                        ""));
            }
            res.close();
            pst.close();
            con.close();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        historyTable.setItems(list);

    }

    /**
     * Metoda, która generuje listę operacji zamówienia/wypożyczenia w zależności od
     * wybranej warotści comboBoxa
     */
    @FXML
    private void historyList(MouseEvent event) {
        tytul.setCellValueFactory(new PropertyValueFactory<>("tytul"));
        cena.setCellValueFactory(new PropertyValueFactory<>("cena"));
        statusZam.setCellValueFactory(new PropertyValueFactory<>("statusZam"));
        dataZam.setCellValueFactory(new PropertyValueFactory<>("dataZam"));
        statusPl.setCellValueFactory(new PropertyValueFactory<>("statusPl"));

        try {

            String[] tmp = wybor.getSelectionModel().getSelectedItem().split(",");
            System.out.println(tmp[0]);
            con = DBManagment.connect();

            if (tmp[0] == "Zamówienia") {
                System.out.println("test");
                String sql1 = "select * from informacjeZamowienie where id_klienta="
                        + LoginScreenController.acc + ";";
                statusZam.setText("Status zamówienia");
                dataZam.setText("Data zamówienia");
                pst = con.prepareStatement(sql1);
                res = pst.executeQuery();
                list.clear();
                while (res.next()) {
                    System.out.println(
                            res.getString("tytuł książki") + res.getString("cena") + res.getString("data zamówienia")
                                    + res.getString("status płatności"));
                    list.add(new Klient(res.getString("tytuł książki"),
                            "" + (Double.parseDouble(res.getString("cena"))
                                    * Integer.parseInt(res.getString("psztuk"))),
                            res.getString("status zamówienia"),
                            res.getString("data zamówienia"), res.getString("status płatności"),
                            res.getString("adres dostawy")));
                }
            } else if (tmp[0] == "Wypożyczenia") {
                String sql1 = "select * from informacjeWypozyczenia where id_klienta="
                        + LoginScreenController.acc + ";";
                pst = con.prepareStatement(sql1);
                res = pst.executeQuery();
                list.clear();
                statusZam.setText("Status wypożyczenia");
                dataZam.setText("Termin zwrotu");
                while (res.next()) {
                    list.add(new Klient(res.getString("tytuł książki"), res.getString("cena Wypożyczenia"),
                            res.getString("status wypożyczenia"),
                            res.getString("termin zwrotu"), res.getString("status płatności"),
                            ""));
                }
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