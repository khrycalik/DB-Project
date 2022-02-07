package Administrator;

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

public class AdminDashboardController implements Initializable {
    Connection con = null;
    PreparedStatement pst = null;
    ResultSet res = null;
    ChangeScreen screen = new ChangeScreen();
    @FXML
    private Label nameLabel;
    @FXML
    private Pane mainArea;
    public static String specialistName;
    public static String specialistSurname;

    /**
     * Metoda, przekierowująca użytkownika do okna "Dodaj"
     */
    @FXML
    private void visitList(MouseEvent event) throws IOException {
        Parent fxml = FXMLLoader.load(getClass().getResource("AddScreen.fxml"));
        mainArea.getChildren().removeAll();
        mainArea.getChildren().addAll(fxml);
    }

    /**
     * Metoda, przekierowująca użytkownika do okna "Księgarnia"
     */
    @FXML
    private void ksiazki(MouseEvent event) throws IOException {
        Parent fxml = FXMLLoader.load(getClass().getResource("AdminBooksScreen.fxml"));
        mainArea.getChildren().removeAll();
        mainArea.getChildren().addAll(fxml);
    }

    /**
     * Metoda, przekierowująca użytkownika do okna "Statusy"
     */
    @FXML
    private void status(MouseEvent event) throws IOException {
        Parent fxml = FXMLLoader.load(getClass().getResource("StatusScreen.fxml"));
        mainArea.getChildren().removeAll();
        mainArea.getChildren().addAll(fxml);
    }

    /**
     * Metoda, przekierowująca użytkownika do okna "Szczegóły"
     */
    @FXML
    private void details(MouseEvent event) throws IOException {
        Parent fxml = FXMLLoader.load(getClass().getResource("DetailsScreen.fxml"));
        mainArea.getChildren().removeAll();
        mainArea.getChildren().addAll(fxml);
    }

    /**
     * Metoda, przekierowująca użytkownika do okna "Nowa książkę"
     */
    @FXML
    private void newBook(MouseEvent event) throws IOException {
        Parent fxml = FXMLLoader.load(getClass().getResource("NewBookScreen.fxml"));
        mainArea.getChildren().removeAll();
        mainArea.getChildren().addAll(fxml);
    }

    /**
     * Metoda, przekierowująca użytkownika do okna "Raporty"
     */
    @FXML
    private void raporty(MouseEvent event) throws IOException {
        Parent fxml = FXMLLoader.load(getClass().getResource("RaportScreen.fxml"));
        mainArea.getChildren().removeAll();
        mainArea.getChildren().addAll(fxml);
    }

    /**
     * Metoda, która wylogowuje użytkownika
     */
    public void logout(MouseEvent event) throws IOException {
        screen.logout(event);
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
    }
}