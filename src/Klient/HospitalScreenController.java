package Klient;

// import SQLManagment.DBManagment;
// import alerts.AlertBox;
// import javafx.collections.FXCollections;
// import javafx.collections.ObservableList;
// import javafx.fxml.FXML;
// import javafx.fxml.Initializable;
// import javafx.scene.control.*;
// import javafx.scene.control.cell.PropertyValueFactory;
// import javafx.scene.input.MouseEvent;
// import javafx.scene.text.Text;
// import login.LoginScreenController;
// import user.Klient;

// import java.net.URL;
// import java.sql.Connection;
// import java.sql.PreparedStatement;
// import java.sql.ResultSet;
// import java.sql.SQLException;
// import java.time.LocalDate;
// import java.time.format.DateTimeFormatter;
// import java.util.Locale;
// import java.util.ResourceBundle;

// public class HospitalScreenController implements Initializable {
// Connection con = null;
// PreparedStatement pst = null;
// ResultSet res = null;
// ResultSet res1 = null;

// @FXML
// private ComboBox<String> hospitalsList;

// @FXML
// private TableView<Klient> specialistTable;
// // @FXML
// // private TableColumn<Klient, String> name;
// // @FXML
// // private TableColumn<Klient, String> surname;
// // @FXML
// // private TableColumn<Klient, String> specialization;
// // @FXML
// // private TableColumn<Klient, String> office;
// // @FXML
// // private TableColumn<Klient, String> price;
// // @FXML
// // private TableColumn<Klient, String> days;
// @FXML
// private TableColumn<Klient, String> tytul;
// @FXML
// private TableColumn<Klient, String> cena;
// @FXML
// private TableColumn<Klient, String> statusZam;
// @FXML
// private TableColumn<Klient, String> dataZam;
// @FXML
// private TableColumn<Klient, String> statusPl;

// @FXML
// private DatePicker dob;

// /**
// * Metoda, ustawiająca początkowe dane na ekranie
// *
// * @see Exception
// */
// private void setData() {
// ObservableList<Klient> list = FXCollections.observableArrayList();

// tytul.setCellValueFactory(new PropertyValueFactory<>("tytul"));
// cena.setCellValueFactory(new PropertyValueFactory<>("cena"));
// statusZam.setCellValueFactory(new PropertyValueFactory<>("statusZam"));
// dataZam.setCellValueFactory(new PropertyValueFactory<>("dataZam"));
// statusPl.setCellValueFactory(new PropertyValueFactory<>("statusPl"));
// try {
// con = DBManagment.connect();
// String sql1 = "select \"tytuł książki\", \"status wypożyczenia\", z.cena,
// \"termin zwrotu\", \"status płatności\" from przedmioty join (select * from
// Wypożyczenia join Przewyp using (id_wypożyczenia)) as z using(id_książki)
// where id_klienta="
// + LoginScreenController.acc + ";";
// ;

// pst = con.prepareStatement(sql1);
// res = pst.executeQuery();
// while (res.next()) {
// list.add(new Klient(res.getString("tytuł książki"), res.getString("cena"),
// res.getString("status wypożyczenia"),
// res.getString("termin zwrotu"), res.getString("status płatności"),
// ""));
// }
// res.close();
// pst.close();
// con.close();
// con.close();
// } catch (Exception e) {
// System.out.println(e.getMessage());
// }
// specialistTable.setItems(list);

// }

// /**
// * Metoda, która generuje listę specjalistów w zalezności od wybranej placówki
// *
// * @param event [MouseEvent] - zdarzenie
// * @see Exception
// */
// @FXML
// private void searchSpecialist(MouseEvent event) {
// // name.setCellValueFactory(new PropertyValueFactory<>("name"));
// // surname.setCellValueFactory(new PropertyValueFactory<>("surname"));
// // specialization.setCellValueFactory(new
// // PropertyValueFactory<>("specialization"));
// // office.setCellValueFactory(new PropertyValueFactory<>("office"));
// // price.setCellValueFactory(new PropertyValueFactory<>("price"));
// // days.setCellValueFactory(new PropertyValueFactory<>("days"));

// // ObservableList<Klient> list = FXCollections.observableArrayList();
// try {
// // if (hospitalsList.getSelectionModel().getSelectedItem() == null) {
// // dob.setDisable(true);
// // throw new Exception();
// // }
// // String[] tmp =
// // hospitalsList.getSelectionModel().getSelectedItem().split(",");
// con = DBManagment.connect();
// // String sql = "select * from informacjaSpecjalista where mejscowosc=? and
// // ulica=? and nr_budynku=?";
// // pst = con.prepareStatement(sql);
// // pst.setString(1, tmp[0]);
// // pst.setString(2, tmp[1]);
// // pst.setInt(3, Integer.parseInt(tmp[2]));
// // res = pst.executeQuery();
// // while (res.next()) {
// // String sql1 = "select * from dnipracyspecjalista dps where
// // dps.id_specjalista=?;";
// // pst = con.prepareStatement(sql1);
// // pst.setInt(1, res.getInt("id_specjalista"));
// // res1 = pst.executeQuery();
// // StringBuilder d = new StringBuilder();
// // while (res1.next()) {
// // d.append(res1.getString("dzien")).append(" ");
// // }
// // list.add(new Klient(res.getString("imie"), res.getString("nazwisko"),
// // res.getString("specjalizacja"), res.getString("id_gabinet"),
// // res.getString("cena_wizyty"), d.toString(), "cos"));
// // res1.close();
// // pst.close();
// // }
// // res.close();
// // pst.close();
// // con.close();
// // days.setCellFactory(param -> {
// // TableCell<Klient, String> cell = new TableCell<>();
// // Text text = new Text();
// // cell.setGraphic(text);
// // cell.setPrefHeight(Control.USE_COMPUTED_SIZE);
// // text.textProperty().bind(cell.itemProperty());
// // text.wrappingWidthProperty().bind(days.widthProperty());
// // return cell;
// // });
// dob.setDisable(false);
// } catch (Exception e) {
// AlertBox.errorAlert("Bląd", "Wybierz placówkę z listy");
// }
// // specialistTable.setItems(list);

// }

// /**
// * Metoda, która pozwala zarezerwować wizytę u wybranego specjalisty
// *
// * @see NullPointerException
// * @see SQLException
// */
// @FXML
// private void reserve(MouseEvent event) {
// try {
// DateTimeFormatter dayOfWeekFormatter = DateTimeFormatter.ofPattern("EEE",
// Locale.ENGLISH);
// LocalDate date = LocalDate.of(dob.getValue().getYear(),
// dob.getValue().getMonth(),
// dob.getValue().getDayOfMonth());

// int dayOfReservation =
// PatientDashboardController.convertDay(date.format(dayOfWeekFormatter));
// System.out.println(dayOfReservation);
// ObservableList<Klient> picked;
// picked = specialistTable.getSelectionModel().getSelectedItems();

// String sName = picked.get(0).getName();
// String sSurname = picked.get(0).getSurname();
// // String sSpecialization = picked.get(0).getSpecialization();
// // int sOffice = picked.get(0).getOffice();
// // int sPrice = picked.get(0).getPrice();

// int sId = 0;
// con = DBManagment.connect();
// // String sql = "SELECT * from informacjaSpecjalista where imie=? and
// nazwisko=?
// // and specjalizacja=? and cena_wizyty=? and id_gabinet=?;";
// // pst = con.prepareStatement(sql);
// // pst.setString(1, sName);
// // pst.setString(2, sSurname);
// // pst.setString(3, sSpecialization);
// // pst.setInt(4, sPrice);
// // pst.setInt(5, sOffice);
// res = pst.executeQuery();
// if (res.next()) {
// sId = res.getInt("id_specjalista");
// }
// res.close();
// pst.close();

// // System.out.println(sName + " " + sSurname + " " + " " + sSpecialization +
// " "
// // + sOffice + " " + sPrice + " "
// // + sId + " " + dob.getValue().toString());

// String sql1 = "select * from plan_specjalisty where id_specjalista=? and
// id_dzien=?";
// pst = con.prepareStatement(sql1);
// pst.setInt(1, sId);
// pst.setInt(2, dayOfReservation);
// res = pst.executeQuery();
// if (res.next()) {
// int idVisit = 0;
// String sql2 = "insert into wizyta (kod_wizyty) values (?);";
// pst = con.prepareStatement(sql2);
// // pst.setString(1, sName + sSurname + sSpecialization +
// // dob.getValue().toString());
// int in = pst.executeUpdate();
// if (in > 0) {
// String sql3 = "select * from wizyta";
// pst = con.prepareStatement(sql3);
// res1 = pst.executeQuery();
// if (res1.next()) {
// idVisit = res1.getInt("id_wizyta");
// }
// res1.close();
// pst.close();
// }
// pst.close();
// String sql4 = "insert into pacjent_specjalista (id_wizyta, id_specjalista,
// id_pacjent, data) values (?, ? ,? , ?)";
// pst = con.prepareStatement(sql4);
// pst.setInt(1, idVisit);
// pst.setInt(2, sId);
// pst.setInt(3, LoginScreenController.acc);
// pst.setString(4, dob.getValue().toString());
// pst.executeUpdate();
// System.out.println("TRUUUUE");
// // AlertBox.infoAlert("Rezerwacja.", "Termin został zarezerwowany. Cena
// wizyty -
// // " + sPrice + " zl",
// // "Możesz sprawdzić termin w swoich rezerwacjach.");
// pst.close();
// } else {
// AlertBox.errorAlert("Bląd", "Specjalista nie jest dostępny w tym dniu.
// Wybierz inny dzień");
// System.out.println("FALSE");
// }
// res.close();
// pst.close();
// con.close();
// } catch (NullPointerException e) {
// AlertBox.errorAlert("Bląd", "Brak wybranej daty lub specjalisty");
// } catch (SQLException e) {
// AlertBox.errorAlert("Bląd", e.getMessage());
// } finally {
// DBManagment.closeAll(con, res, pst);
// }
// }

// /**
// * Jest to główna metoda wykorzystująca metodę setData() oraz synhronizuje
// * kalendarz z lokalnym czasem
// *
// * @param location [URL]
// * @param resources [ResourceBundle]
// */
// @Override
// public void initialize(URL location, ResourceBundle resources) {
// setData();
// dob.setDisable(true);
// dob.setDayCellFactory(picker -> new DateCell() {
// public void updateItem(LocalDate date, boolean empty) {
// super.updateItem(date, empty);
// LocalDate today = LocalDate.now();
// setDisable(empty || date.compareTo(today) < 0);
// }
// });
// }
// }