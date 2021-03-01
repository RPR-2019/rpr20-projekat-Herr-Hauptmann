package kuhar.kontroleri;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import kuhar.KuharDAO;
import kuhar.modeli.Korisnik;

import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

import static javafx.scene.layout.Region.USE_COMPUTED_SIZE;

public class AdminController implements Initializable {
    public TableView<Korisnik> korisniciTabela;
    public TableColumn idKolona;
    public TableColumn imeKolona;
    public TableColumn usernameKolona;
    public TableColumn adminKolona;
    private ObservableList<Korisnik> listaKorisnika;

    private KuharDAO dao;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        dao = KuharDAO.getInstance();
        listaKorisnika = FXCollections.observableArrayList(dao.korisnici());
        korisniciTabela.setItems(listaKorisnika);
        idKolona.setCellValueFactory(new PropertyValueFactory("id"));
        imeKolona.setCellValueFactory(new PropertyValueFactory("ime"));
        usernameKolona.setCellValueFactory(new PropertyValueFactory("username"));
        adminKolona.setCellValueFactory(new PropertyValueFactory("admin"));
    }

    public void dodajKorisnika() throws IOException {
        Stage novi = new Stage();
        Parent root = null;
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/korisnik.fxml"));
        KorisnikController korisnikController = new KorisnikController();
        loader.setController(korisnikController);
        root = loader.load();
        novi.setTitle("Dodaj novog korisnika");
        novi.setScene(new Scene(root, USE_COMPUTED_SIZE, USE_COMPUTED_SIZE));
        novi.setResizable(false);
        novi.showAndWait();
        listaKorisnika.setAll(dao.korisnici());
    }

    public void urediKorisnika(ActionEvent actionEvent) throws IOException {
        Korisnik korisnik = korisniciTabela.getSelectionModel().getSelectedItem();
        if (korisnik == null) return;

        Stage stage = new Stage();
        Parent root = null;
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/korisnik.fxml"));
        KorisnikController korisnikController = new KorisnikController(korisnik);
        loader.setController(korisnikController);
        root = loader.load();
        stage.setTitle("Uredi korisnika");
        stage.setScene(new Scene(root, USE_COMPUTED_SIZE, USE_COMPUTED_SIZE));
        stage.setResizable(false);
        stage.showAndWait();
        listaKorisnika.setAll(dao.korisnici());
    }

    public void izbrisiKorisnika(ActionEvent actionEvent) {
        Korisnik korisnik = korisniciTabela.getSelectionModel().getSelectedItem();
        if (korisnik == null) return;

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Potvrda brisanja");
        alert.setHeaderText("Brisanje korisnika "+korisnik.getUsername());
        alert.setContentText("Da li ste sigurni da želite obrisati korisnika " +korisnik.getUsername()+"?");
        alert.setResizable(true);

        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == ButtonType.OK){
            dao.izbrisiKorisnika(korisnik.getId());
            listaKorisnika.setAll(dao.korisnici());
        }
    }
}
