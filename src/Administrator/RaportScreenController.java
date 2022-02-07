package Administrator;

import SQLManagment.DBManagment;
import alerts.AlertBox;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import java.util.ResourceBundle;

public class RaportScreenController implements Initializable {
    Connection con = null;
    PreparedStatement pst = null;
    ResultSet res = null;
    ResultSet res1 = null;

    @FXML
    private Label wartosc;
    @FXML
    private Label iloscd;
    @FXML
    private Label iloscs;
    @FXML
    private Label ilosct;
    @FXML
    private Label krotkie;
    @FXML
    private Label srednie;
    @FXML
    private Label dlugie;

    /**
     * Metoda, ustawiająca początkowe dane na ekranie
     */
    private void setData() {
        try {
            con = DBManagment.connect();
            String sql1 = "select count(*) from (select * from przedmioty group by id_książki having \"ilość stron\"<100) as c;";
            pst = con.prepareStatement(sql1);
            res = pst.executeQuery();
            if (res.next() && res.getInt("count") > 0) {
                krotkie.setText("Ilość książek ktrótkich: " + res.getString("count"));
            } else {
                krotkie.setText("Ilość książek ktrótkich: brak");
            }
            res.close();
            pst.close();
            sql1 = "select count(*) from (select * from przedmioty group by id_książki having \"ilość stron\"<450 and \"ilość stron\">=100) as c;";
            pst = con.prepareStatement(sql1);
            res = pst.executeQuery();
            if (res.next() && res.getInt("count") > 0) {
                srednie.setText("Ilość książek średnio długich: " + res.getString("count"));
            } else {
                srednie.setText("Ilość książek średnio długich: brak");
            }
            res.close();
            pst.close();
            sql1 = "select count(*) from (select * from przedmioty group by id_książki having \"ilość stron\">=450) as c;";
            pst = con.prepareStatement(sql1);
            res = pst.executeQuery();
            if (res.next() && res.getInt("count") > 0) {
                dlugie.setText("Ilość książek długich: " + res.getString("count"));
            } else {
                dlugie.setText("Ilość książek długich: brak");
            }
            res.close();
            pst.close();
            // cena //
            sql1 = "select count(*) from (select * from przedmioty group by id_książki having cena>=80) as c;";
            pst = con.prepareStatement(sql1);
            res = pst.executeQuery();
            if (res.next() && res.getInt("count") > 0) {
                iloscd.setText("Ilość książek drogich: " + res.getString("count"));
            } else {
                iloscd.setText("Ilość książek drogich: brak");
            }
            res.close();
            pst.close();

            sql1 = "select count(*) from (select * from przedmioty group by id_książki having cena>=45 and cena<80) as c;";
            pst = con.prepareStatement(sql1);
            res = pst.executeQuery();
            if (res.next() && res.getInt("count") > 0) {
                iloscs.setText("Ilość książek średnio drogich: " + res.getString("count"));
            } else {
                iloscs.setText("Ilość książek średnio drogich: brak");
            }
            res.close();
            pst.close();

            sql1 = "select count(*) from (select * from przedmioty group by id_książki having cena<45) as c;";
            pst = con.prepareStatement(sql1);
            res = pst.executeQuery();
            if (res.next() && res.getInt("count") > 0) {
                ilosct.setText("Ilość książek tanich: " + res.getString("count"));
            } else {
                ilosct.setText("Ilość książek tanich: brak");
            }
            res.close();
            pst.close();

            sql1 = "select sum(cena * sztuk) from przedmioty;";
            pst = con.prepareStatement(sql1);
            res = pst.executeQuery();
            if (res.next()) {
                wartosc.setText("Wartość magaynu: " + res.getString("sum"));
            }
            res.close();
            pst.close();
            con.close();
        } catch (Exception e) {
            AlertBox.errorAlert("Bląd", e.getMessage());
        }

    }

    /**
     * zapisuje raport wypożyczeń do pliku
     */
    @FXML
    private void raportW() {
        File file = new File("raport wypożyczeń.txt");
        try {
            con = DBManagment.connect();

            String sql1 = "select * from informacjewypozyczenia";
            pst = con.prepareStatement(sql1);
            res = pst.executeQuery();
            FileOutputStream fos = new FileOutputStream(file);
            OutputStreamWriter osw = new OutputStreamWriter(fos, StandardCharsets.UTF_8);
            BufferedWriter writer = new BufferedWriter(osw);

            while (res.next()) {
                writer.append(res.getString("tytuł książki") + " ; " + res.getString("cena wypożyczenia") + " ; "
                        + res.getString("status wypożyczenia") + " ; " + res.getString("termin zwrotu") + " ; "
                        + res.getString("status płatności") + " ; " + res.getString("imie") + " ; "
                        + res.getString("nazwisko") + "\n");
            }
            writer.close();
            con.close();
            pst.close();
            res.close();
        } catch (Exception e) {
            AlertBox.errorAlert("Bląd", e.getMessage());

        }
    }

    /**
     * zapisuje raport zamówień do pliku
     */
    @FXML
    private void raportZ() {
        File file = new File("raport zamówień.txt");
        try {
            con = DBManagment.connect();

            String sql1 = "select * from informacjezamowienie";
            pst = con.prepareStatement(sql1);
            res = pst.executeQuery();
            FileOutputStream fos = new FileOutputStream(file);
            OutputStreamWriter osw = new OutputStreamWriter(fos, StandardCharsets.UTF_8);
            BufferedWriter writer = new BufferedWriter(osw);

            while (res.next()) {
                System.out.println(
                        res.getString("tytuł książki") + res.getString("cena") + res.getString("data zamówienia")
                                + res.getString("status płatności"));
                writer.append(res.getString("tytuł książki") + " ; " + res.getString("cena") + " ; "
                        + res.getString("status zamówienia") + " ; " + res.getString("adres dostawy") + " ; "
                        + res.getString("status płatności") + " ; " + res.getString("imie") + " ; "
                        + res.getString("nazwisko") + "\n");
            }
            writer.close();
            con.close();
            pst.close();
            res.close();
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