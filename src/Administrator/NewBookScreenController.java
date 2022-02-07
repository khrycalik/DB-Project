package Administrator;

import SQLManagment.DBManagment;
import alerts.AlertBox;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.util.StringConverter;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.DatePicker;
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
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ResourceBundle;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class NewBookScreenController implements Initializable {
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
    private TextField tytul;
    @FXML
    private DatePicker dataWyd;
    @FXML
    private TextField sztuk;
    @FXML
    private TextField cena;
    @FXML
    private TextField cenaWyp;
    @FXML
    private TextField opis;
    @FXML
    private TextField iloscStr;

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
     * Metoda pozwalająca na dodanie nowej książki do księgarni
     */
    @FXML
    private void addToBase() {
        try {
            con = DBManagment.connect();
            String sql;
            if (a.getSelectionModel().getSelectedItem() == null) {
                throw new Exception("Nie wybrano autora");
            }
            if (w.getSelectionModel().getSelectedItem() == null) {
                throw new Exception("Nie wybrano wydawnictwa");
            }
            if (k.getSelectionModel().getSelectedItem() == null) {
                throw new Exception("Nie wybrano kategorii");
            }
            if (m.getSelectionModel().getSelectedItem() == null) {
                throw new Exception("Nie wybrano magazynu");
            }
            if (j.getSelectionModel().getSelectedItem() == null) {
                throw new Exception("Nie wybrano języka");
            }
            if (u.getSelectionModel().getSelectedItem() == null) {
                throw new Exception("Nie wybrano uniwersum");
            }

            if (tytul.getText().isEmpty() || sztuk.getText().isEmpty()
                    || cena.getText().isEmpty() || cenaWyp.getText().isEmpty() || opis.getText().isEmpty()
                    || iloscStr.getText().isEmpty())
                throw new Exception("Proszę wypełnić wszystkie pola");
            if (dataWyd.getValue() == null)
                throw new Exception("Proszę wybrać datę");

            Pattern p = Pattern.compile("[0-9]{4}[-]{1}[0-9]{2}[-]{1}[0-9]{2}");
            Matcher isMatching = p.matcher(dataWyd.getValue().toString());
            System.out.println(dataWyd.getValue().toString());
            if (!isMatching.matches()) {
                throw new Exception("Podano datę w złym formacie");
            }

            p = Pattern.compile("[0-9]{1,}[.]{1}[0-9]{2}");
            Matcher priceM = p.matcher(cena.getText());
            if (!priceM.matches())
                throw new Exception("Podano cenę w złym formacie");

            priceM = p.matcher(cenaWyp.getText());
            if (!priceM.matches())
                throw new Exception("Podano cenę wypożyczeniaadmin w złym formacie");

            Pattern p2 = Pattern.compile("[0-9]{1,}");
            priceM = p2.matcher(iloscStr.getText());
            if (!priceM.matches())
                throw new Exception("Podana liczba stron jest nieprawidłowa");
            priceM = p2.matcher(sztuk.getText());
            if (!priceM.matches())
                throw new Exception("Podana liczba sztuk jest nieprawidłowa");

            System.out.println(tytul.getText());
            sql = "select * from (select id_uniwersum from uniwersum where \"nazwa uniwersum\"=?) as u, (select id_autora from autorzy where \"imie nazwisko\"=?) as a, (select id_wydawnictwa from wydawnictwa where \"nazwa wydawnictwa\"=?) as w, (select id_magazynu from magazyny where \"adres magazynu\"=?) as m, (select id_języka from język where język=?) as j, (select id_kategorii from kategorie where \"nazwa kategorii\"=?) as k;";
            pst = con.prepareStatement(sql);
            pst.setString(1, u.getSelectionModel().getSelectedItem().getPole());
            pst.setString(2, a.getSelectionModel().getSelectedItem().getPole());
            pst.setString(3, w.getSelectionModel().getSelectedItem().getPole());
            pst.setString(4, m.getSelectionModel().getSelectedItem().getPole());
            pst.setString(5, j.getSelectionModel().getSelectedItem().getPole());
            pst.setString(6, k.getSelectionModel().getSelectedItem().getPole());
            res = pst.executeQuery();
            if (res.next()) {
                System.out.println(res.getString("id_uniwersum") + res.getString("id_autora")
                        + res.getString("id_wydawnictwa") + res.getString("id_kategorii") + res.getString("id_języka")
                        + res.getString("id_magazynu"));
            }
            sql = "insert into przedmioty (\"tytuł książki\", id_autora, id_wydawnictwa, \"data wydania\", sztuk, cena, opis, id_kategorii, id_języka, id_uniwersum, \"ilość stron\", id_magazynu, \"cena wypożyczenia\") values (?,?,?,?,?,?,?,?,?,?,?,?,?);";
            pst = con.prepareStatement(sql);
            pst.setString(1, tytul.getText());
            pst.setInt(2, res.getInt("id_autora"));
            pst.setInt(3, res.getInt("id_wydawnictwa"));
            pst.setString(4, dataWyd.getValue().toString());
            pst.setInt(5, Integer.parseInt(sztuk.getText()));
            pst.setDouble(6, Double.parseDouble(cena.getText()));
            pst.setString(7, opis.getText());
            pst.setInt(8, res.getInt("id_kategorii"));
            pst.setInt(9, res.getInt("id_języka"));
            pst.setInt(10, res.getInt("id_uniwersum"));
            pst.setInt(11, Integer.parseInt(iloscStr.getText()));
            pst.setInt(12, res.getInt("id_magazynu"));
            pst.setDouble(13, Double.parseDouble(cenaWyp.getText()));
            res.close();
            pst.executeUpdate();
            pst.close();
            con.close();
        } catch (SQLException e) {
            AlertBox.errorAlert("Bląd", e.getMessage());
        } catch (Exception e) {
            AlertBox.errorAlert("Bląd", e.getMessage());
        }

    }

    /**
     * Jest to główna metoda wykorzystująca metodę setData() oraz konwerter do
     * datePicker
     */
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        setData();
        String pattern = "yyyy-MM-dd";
        dataWyd.setConverter(new StringConverter<LocalDate>() {
            DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern(pattern);

            @Override
            public String toString(LocalDate date) {
                if (date != null) {
                    return dateFormatter.format(date);
                } else {
                    return "";
                }
            }

            @Override
            public LocalDate fromString(String string) {
                if (string != null && !string.isEmpty()) {
                    return LocalDate.parse(string, dateFormatter);
                } else {
                    return null;
                }
            }
        });
    }
}