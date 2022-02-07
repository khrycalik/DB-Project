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
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Locale;
import java.util.ResourceBundle;

public class BooksScreenController implements Initializable {
    Connection con = null;
    PreparedStatement pst = null;
    ResultSet res = null;
    ResultSet res1 = null;
    public static HashMap<Integer, Integer> id_list = new HashMap<>();

    @FXML
    private TextField book;

    @FXML
    private TableView<Book> bookTable;
    @FXML
    private TableColumn<Book, String> identyfikator;
    @FXML
    private TableColumn<Book, String> tytul;
    @FXML
    private TableColumn<Book, String> autor;
    @FXML
    private TableColumn<Book, String> wydawnictwo;
    @FXML
    private TableColumn<Book, String> dataWyda;
    @FXML
    private TableColumn<Book, String> sztuk;
    @FXML
    private TableColumn<Book, String> cena;
    @FXML
    private TableColumn<Book, String> opis;
    @FXML
    private TableColumn<Book, String> kategoria;
    @FXML
    private TableColumn<Book, String> jezyk;
    @FXML
    private TableColumn<Book, String> uniwersum;
    @FXML
    private TableColumn<Book, String> strony;
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
        dataWyda.setCellValueFactory(new PropertyValueFactory<>("dataWyda"));
        sztuk.setCellValueFactory(new PropertyValueFactory<>("sztuk"));
        cena.setCellValueFactory(new PropertyValueFactory<>("cena"));
        opis.setCellValueFactory(new PropertyValueFactory<>("opis"));
        kategoria.setCellValueFactory(new PropertyValueFactory<>("kategoria"));
        jezyk.setCellValueFactory(new PropertyValueFactory<>("jezyk"));
        uniwersum.setCellValueFactory(new PropertyValueFactory<>("uniwersum"));
        strony.setCellValueFactory(new PropertyValueFactory<>("strony"));
        cenaWyp.setCellValueFactory(new PropertyValueFactory<>("cenaWyp"));
        try {
            con = DBManagment.connect();
            String sql1 = "select id_książki, \"tytuł książki\", \"imie nazwisko\", \"nazwa wydawnictwa\", \"data wydania\", sztuk, cena, opis, \"nazwa kategorii\", język, \"nazwa uniwersum\", \"ilość stron\", \"cena wypożyczenia\" from przedmioty as p join autorzy using (id_autora) join język using (id_języka) join kategorie using (id_kategorii) join wydawnictwa using (id_wydawnictwa) join uniwersum using (id_uniwersum)";
            pst = con.prepareStatement(sql1);
            res = pst.executeQuery();
            while (res.next()) {
                list.add(new Book(res.getString("id_książki"), res.getString("tytuł książki"),
                        res.getString("imie nazwisko"),
                        res.getString("nazwa wydawnictwa"),
                        res.getString("data wydania"), res.getString("sztuk"), res.getString("cena"),
                        res.getString("opis"), res.getString("nazwa kategorii"), res.getString("język"),
                        res.getString("nazwa uniwersum"), res.getString("ilość stron"),
                        res.getString("cena wypożyczenia")));
            }
            res.close();
            pst.close();
            con.close();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        bookTable.setItems(list);

    }

    /**
     * Metoda, która generuje listę książek w księgarni
     */
    @FXML
    private void bookList(MouseEvent event) {

        try {
            String bookID = book.getText();
            System.out.println(bookID);
            con = DBManagment.connect();
            String sql1 = "select * from przedmioty where id_książki=" + bookID + ";";
            pst = con.prepareStatement(sql1);
            res = pst.executeQuery();

            if (!res.next())
                throw new Exception("Książka o takim identyfikatorze nie istnieje");

            if (id_list.containsKey(Integer.parseInt(bookID)))
                throw new Exception("Książka o tym indeksie jest już w koszyku");

            id_list.put(Integer.parseInt(bookID), 1);
            System.out.println(id_list.toString());
            res.close();
            pst.close();
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