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
import user.Book;

import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.ResourceBundle;

public class BacketScreenController implements Initializable {
    Connection con = null;
    PreparedStatement pst = null;
    ResultSet res = null;
    ResultSet res1 = null;
    double sumka = 0;
    double sumaWyp = 0;

    @FXML
    private TextField book;
    @FXML
    private TextField ile;
    @FXML
    private TextField gdzie;

    @FXML
    private Label suma;
    @FXML
    private TableView<Book> backetTable;
    @FXML
    private TableColumn<Book, String> identyfikator;
    @FXML
    private TableColumn<Book, String> tytul;
    @FXML
    private TableColumn<Book, String> autor;
    @FXML
    private TableColumn<Book, String> wydawnictwo;
    @FXML
    private TableColumn<Book, String> sztuk;
    @FXML
    private TableColumn<Book, String> cena;
    @FXML
    private TableColumn<Book, String> cenaWyp;

    ObservableList<Book> list = FXCollections.observableArrayList();

    /**
     * Metoda, ustawiająca początkowe dane na ekranie
     */
    private void setData() {

        identyfikator.setCellValueFactory(new PropertyValueFactory<>("identyfikator"));
        tytul.setCellValueFactory(new PropertyValueFactory<>("tytul"));
        autor.setCellValueFactory(new PropertyValueFactory<>("autor"));
        wydawnictwo.setCellValueFactory(new PropertyValueFactory<>("wydawnictwo"));
        sztuk.setCellValueFactory(new PropertyValueFactory<>("sztuk"));
        cena.setCellValueFactory(new PropertyValueFactory<>("cena"));
        cenaWyp.setCellValueFactory(new PropertyValueFactory<>("cenaWyp"));

        sumka = 0;
        sumaWyp = 0;
        try {
            con = DBManagment.connect();
            for (int i : BooksScreenController.id_list.keySet()) {
                String sql1 = "select id_książki, \"tytuł książki\", \"imie nazwisko\", \"nazwa wydawnictwa\", \"data wydania\", sztuk, cena, opis, \"nazwa kategorii\", język, \"nazwa uniwersum\", \"ilość stron\", \"cena wypożyczenia\" from przedmioty as p join autorzy using (id_autora) join język using (id_języka) join kategorie using (id_kategorii) join wydawnictwa using (id_wydawnictwa) join uniwersum using (id_uniwersum) where id_książki="
                        + i + ";";
                pst = con.prepareStatement(sql1);
                res = pst.executeQuery();
                while (res.next()) {
                    list.add(new Book(res.getString("id_książki"), res.getString("tytuł książki"),
                            res.getString("imie nazwisko"),
                            res.getString("nazwa wydawnictwa"), "" + BooksScreenController.id_list.get(i),
                            res.getString("cena"),
                            res.getString("cena wypożyczenia")));
                    sumka += Double.valueOf(res.getString("cena")) * BooksScreenController.id_list.get(i);
                    sumaWyp += Double.valueOf(res.getString("cena wypożyczenia"));
                }
                res.close();
                pst.close();
            }
            con.close();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        suma.setText("Cena wypożyczenia: " + sumaWyp + "\ncena zakupu: " + sumka);
        backetTable.setItems(list);

    }

    /**
     * Metoda, która generuje listę przedmiotów w koszyku
     */
    @FXML
    private void backetList(MouseEvent event) {

        try {
            con = DBManagment.connect();
            String bookID = book.getText();
            boolean flag = false;
            if (Integer.parseInt(ile.getText()) < 0)
                throw new Exception("Wartość musi być nieujemna");
            if (!BooksScreenController.id_list.containsKey(Integer.parseInt(bookID)))
                throw new Exception("Książka o takim identyfikatorze nie znajduje się w koszyku");

            String sql1 = "select sztuk from przedmioty where id_książki=" + bookID + ";";
            pst = con.prepareStatement(sql1);
            res = pst.executeQuery();
            res.next();
            if (Integer.parseInt(res.getString("sztuk")) < Integer.parseInt(ile.getText()))
                throw new Exception(
                        "Nie ma tylu książek na stanie, dostępnych jest jedynie " + res.getString("sztuk"));
            BooksScreenController.id_list.replace(Integer.parseInt(bookID), Integer.parseInt(ile.getText()));
            res.close();
            pst.close();
            con.close();
            list.clear();
            setData();
            if (flag)
                throw new Exception("Książka o takim identyfikatorze nie znajduje się w koszyku");

        } catch (

        Exception e) {
            AlertBox.errorAlert("Bląd", e.getMessage());
        }

    }

    /**
     * Metoda, która pozwala kupić wybrane przedmioty
     */
    @FXML
    private void kup(MouseEvent event) {
        try {
            con = DBManagment.connect();
            System.out.println(gdzie.getText());
            if (BooksScreenController.id_list.isEmpty())
                throw new Exception("Koszyk jest pusty");
            if (gdzie.getText().isEmpty())
                throw new Exception("Proszę podać adres dostawy");
            for (int i : BooksScreenController.id_list.keySet()) {
                String sql1 = "select sztuk from przedmioty where id_książki=" + i + ";";
                pst = con.prepareStatement(sql1);
                res = pst.executeQuery();
                res.next();
                if (BooksScreenController.id_list.get(i) == 0)
                    continue;
                if (Integer.parseInt(res.getString("sztuk")) < BooksScreenController.id_list.get(i))
                    throw new Exception(
                            "Nie udało się zrealizować zakupu z powodu niewystarczającej ilości danej książki");
                int jest = Integer.parseInt(res.getString("sztuk")) - BooksScreenController.id_list.get(i);
                res.close();
                pst.close();

                sql1 = "update przedmioty set sztuk=" + jest + " where id_książki=" + i + ";";
                pst = con.prepareStatement(sql1);
                pst.executeUpdate();
                pst.close();
            }
            String sql1 = "insert into zamówienia (id_klienta, \"status zamówienia\", \"data zamówienia\", cena, \"status płatności\", \"adres dostawy\") values (?, 'zamówiono', ?, ?, 'nie zapłacono', ?) returning id_zamowienia";
            pst = con.prepareStatement(sql1);
            pst.setInt(1, LoginScreenController.acc);
            SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
            Date date = new Date();
            pst.setString(2, formatter.format(date));
            pst.setDouble(3, sumka);
            pst.setString(4, gdzie.getText());
            res = pst.executeQuery();
            res.next();
            int noweId = Integer.parseInt(res.getString("id_zamowienia"));
            System.out.println(noweId);
            res.close();
            pst.close();

            for (int insertId : BooksScreenController.id_list.keySet()) {
                sql1 = "insert into przezam (id_książki, id_zamowienia, sztuk) values (?,?,?)";
                pst = con.prepareStatement(sql1);
                pst.setInt(1, insertId);
                pst.setInt(2, noweId);
                pst.setInt(3, BooksScreenController.id_list.get(insertId));
                pst.executeUpdate();
                res.close();
                pst.close();
            }

            con.close();

            list.clear();
            BooksScreenController.id_list.clear();
            setData();
        } catch (

        Exception e) {
            AlertBox.errorAlert("Bląd", e.getMessage());
        }
    }

    /**
     * Metoda pozwala na wypożyczenie wbrancyh książek
     */
    @FXML
    private void wypozycz(MouseEvent event) {
        try {
            con = DBManagment.connect();
            for (int i : BooksScreenController.id_list.keySet()) {
                String sql1 = "select sztuk from przedmioty where id_książki=" + i + ";";
                pst = con.prepareStatement(sql1);
                res = pst.executeQuery();
                res.next();
                if (BooksScreenController.id_list.get(i) == 0)
                    continue;
                if (Integer.parseInt(res.getString("sztuk")) == 0)
                    throw new Exception(
                            "Nie udało się zrealizować zakupu z powodu niewystarczającej ilości danej książki");
                int jest = Integer.parseInt(res.getString("sztuk")) - 1;
                res.close();
                pst.close();

                sql1 = "update przedmioty set sztuk=" + jest + " where id_książki=" + i + ";";
                pst = con.prepareStatement(sql1);
                pst.executeUpdate();
                pst.close();
            }
            String sql1 = "insert into wypożyczenia (id_klienta, \"status wypożyczenia\", cena, \"status płatności\", \"termin zwrotu\") values (?, 'w przygotowaniu', ?, 'nie zapłacono', ?) returning id_wypożyczenia";
            pst = con.prepareStatement(sql1);
            pst.setInt(1, LoginScreenController.acc);
            SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
            Date date = new Date();
            Calendar c = Calendar.getInstance();
            c.setTime(date);
            c.add(Calendar.DATE, 7);

            pst.setDouble(2, sumaWyp);
            pst.setString(3, formatter.format(c.getTime()).toString());
            res = pst.executeQuery();
            res.next();
            int noweId = Integer.parseInt(res.getString("id_wypożyczenia"));
            System.out.println(noweId);
            res.close();
            pst.close();

            for (int insertId : BooksScreenController.id_list.keySet()) {
                sql1 = "insert into przewyp (id_książki, id_wypożyczenia) values (?,?)";
                pst = con.prepareStatement(sql1);
                pst.setInt(1, insertId);
                pst.setInt(2, noweId);
                pst.executeUpdate();
                res.close();
                pst.close();
            }

            con.close();

            list.clear();
            BooksScreenController.id_list.clear();
            setData();
        } catch (

        Exception e) {
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
