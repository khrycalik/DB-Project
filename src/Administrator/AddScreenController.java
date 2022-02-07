package Administrator;

import SQLManagment.DBManagment;
import alerts.AlertBox;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import user.Admin;

import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class AddScreenController implements Initializable {
    Connection con = null;
    PreparedStatement pst = null;
    ResultSet res = null;
    ResultSet res1 = null;

    @FXML
    private TableView<Admin> a;
    @FXML
    private TableView<Admin> w;
    @FXML
    private TableView<Admin> k;
    @FXML
    private TableView<Admin> m;
    @FXML
    private TableView<Admin> j;
    @FXML
    private TableView<Admin> u;
    @FXML
    private TableColumn<Admin, String> autor;
    @FXML
    private TableColumn<Admin, String> wydawnictwo;
    @FXML
    private TableColumn<Admin, String> kategoria;
    @FXML
    private TableColumn<Admin, Integer> magazyn;
    @FXML
    private TableColumn<Admin, String> jezyk;
    @FXML
    private TableColumn<Admin, String> uniwersum;
    @FXML
    private TextField dodaj;

    @FXML
    private ComboBox<String> wybor;

    /**
     * Metoda, ustawiająca początkowe dane na ekranie
     */
    private void setData() {
        autor.setCellValueFactory(new PropertyValueFactory<>("pole"));
        wydawnictwo.setCellValueFactory(new PropertyValueFactory<>("pole"));
        magazyn.setCellValueFactory(new PropertyValueFactory<>("pole"));
        kategoria.setCellValueFactory(new PropertyValueFactory<>("pole"));
        jezyk.setCellValueFactory(new PropertyValueFactory<>("pole"));
        uniwersum.setCellValueFactory(new PropertyValueFactory<>("pole"));
        ObservableList<Admin> lw = FXCollections.observableArrayList();
        ObservableList<Admin> lk = FXCollections.observableArrayList();
        ObservableList<Admin> la = FXCollections.observableArrayList();
        ObservableList<Admin> lm = FXCollections.observableArrayList();
        ObservableList<Admin> lj = FXCollections.observableArrayList();
        ObservableList<Admin> lu = FXCollections.observableArrayList();

        try {
            con = DBManagment.connect();
            String sql = "select \"nazwa wydawnictwa\" from wydawnictwa";
            pst = con.prepareStatement(sql);
            res = pst.executeQuery();
            while (res.next()) {
                lw.add(new Admin(res.getString("nazwa wydawnictwa")));
            }
            res.close();
            pst.close();
            w.setItems(lw);

            sql = "select \"nazwa kategorii\" from kategorie;";
            pst = con.prepareStatement(sql);
            res = pst.executeQuery();
            while (res.next()) {
                lk.add(new Admin(res.getString("nazwa kategorii")));
            }
            res.close();
            pst.close();
            k.setItems(lk);

            sql = "select \"adres magazynu\" from magazyny;";
            pst = con.prepareStatement(sql);
            res = pst.executeQuery();
            while (res.next()) {
                lm.add(new Admin(res.getString("adres magazynu")));
            }
            res.close();
            pst.close();
            m.setItems(lm);

            sql = "select \"imie nazwisko\" from autorzy;";
            pst = con.prepareStatement(sql);
            res = pst.executeQuery();
            while (res.next()) {
                la.add(new Admin(res.getString("imie nazwisko")));
            }
            res.close();
            pst.close();
            a.setItems(la);

            sql = "select język from język;";
            pst = con.prepareStatement(sql);
            res = pst.executeQuery();
            while (res.next()) {
                lj.add(new Admin(res.getString("język")));
            }
            res.close();
            pst.close();
            j.setItems(lj);

            sql = "select \"nazwa uniwersum\" from uniwersum;";
            pst = con.prepareStatement(sql);
            res = pst.executeQuery();
            while (res.next()) {
                lu.add(new Admin(res.getString("nazwa uniwersum")));
            }
            res.close();
            pst.close();
            u.setItems(lu);
            con.close();

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    /**
     * Metoda pozwalająca na dodanie wybranych danych do bazy
     */
    @FXML
    private void addToBase() {
        try {
            con = DBManagment.connect();
            String sql;
            System.out.println(wybor.getSelectionModel().getSelectedItem());
            if (wybor.getSelectionModel().getSelectedItem().equals("Autor")) {
                sql = "insert into autorzy (\"imie nazwisko\") values (?);";
                pst = con.prepareStatement(sql);
                pst.setString(1, dodaj.getText());
                pst.executeUpdate();
            } else if (wybor.getSelectionModel().getSelectedItem().equals("Wydawnictwo")) {
                sql = "insert into wydawnictwa (\"nazwa wydawnictwa\") values (?);";
                pst = con.prepareStatement(sql);
                pst.setString(1, dodaj.getText());
                pst.executeUpdate();
                pst.close();
            } else if (wybor.getSelectionModel().getSelectedItem().equals("Kategoria")) {
                sql = "insert into kategorie (\"nazwa kategorii\") values (?);";
                pst = con.prepareStatement(sql);
                pst.setString(1, dodaj.getText());
                pst.executeUpdate();
                pst.close();
            } else if (wybor.getSelectionModel().getSelectedItem().equals("Magazyn")) {
                sql = "insert into magazyny (\"adres magazynu\") values (?);";
                pst = con.prepareStatement(sql);
                pst.setString(1, dodaj.getText());
                pst.executeUpdate();
                pst.close();
            } else if (wybor.getSelectionModel().getSelectedItem().equals("Uniwersum")) {
                sql = "insert into uniwersum (\"nazwa uniwersum\") values (?);";
                pst = con.prepareStatement(sql);
                pst.setString(1, dodaj.getText());
                pst.executeUpdate();
                pst.close();
            } else if (wybor.getSelectionModel().getSelectedItem().equals("Języki")) {
                sql = "insert into język (adres język) values (?);";
                pst = con.prepareStatement(sql);
                pst.setString(1, dodaj.getText());
                pst.executeUpdate();
                pst.close();
            }
            System.out.println("test");
            AlertBox.infoAlert("Dodano do bazy", "Dodano do bazy", "");
            pst.close();
            con.close();
        } catch (SQLException e) {
            AlertBox.errorAlert("Bląd", e.getMessage());
        } catch (Exception e) {
            AlertBox.errorAlert("Bląd", e.getMessage());
        }

    }

    /**
     * Jest to główna metoda wykorzystująca metodę setData() oraz dodająca opcje do
     * comboBoxa
     * 
     * @param location  [URL]
     * @param resources [ResourceBundle]
     */
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        // setDiseases();
        wybor.getItems().addAll("Autor", "Wydawnictwo", "Kategoria", "Magazyn", "Język", "Uniwersum");
        setData();
    }
}