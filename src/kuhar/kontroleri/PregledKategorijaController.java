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
import kuhar.izuzeci.NemateOvlastiIzuzetak;
import kuhar.modeli.Kategorija;

import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

import static javafx.scene.layout.Region.USE_COMPUTED_SIZE;

public class PregledKategorijaController implements Initializable {
    public TableView<Kategorija> kategorijeTabela;
    public TableColumn idKolona;
    public TableColumn nazivKolona;

    private ObservableList<Kategorija> listaKategorija;
    private KuharDAO dao;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        dao = KuharDAO.getInstance();
        listaKategorija = FXCollections.observableArrayList(dao.kategorije());
        kategorijeTabela.setItems(listaKategorija);
        idKolona.setCellValueFactory(new PropertyValueFactory("id"));
        nazivKolona.setCellValueFactory(new PropertyValueFactory("naziv"));
    }

    public void dodajKategoriju() throws IOException {
        Stage novi = new Stage();
        Parent root = null;
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/kategorija.fxml"));
        KategorijaController kategorijaController = new KategorijaController();
        loader.setController(kategorijaController);
        root = loader.load();
        novi.setTitle("Dodaj novu kategoriju");
        novi.setScene(new Scene(root, USE_COMPUTED_SIZE, USE_COMPUTED_SIZE));
        novi.setResizable(false);
        novi.showAndWait();
        listaKategorija.setAll(dao.kategorije());
    }

    public void urediKategoriju(ActionEvent actionEvent) throws IOException {
        Kategorija kategorija = kategorijeTabela.getSelectionModel().getSelectedItem();
        if (kategorija == null) return;

        Stage stage = new Stage();
        Parent root = null;
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/kategorija.fxml"));
        KategorijaController kategorijaController = new KategorijaController(kategorija);
        loader.setController(kategorijaController);
        root = loader.load();
        stage.setTitle("Uredi kategoriju");
        stage.setScene(new Scene(root, USE_COMPUTED_SIZE, USE_COMPUTED_SIZE));
        stage.setResizable(false);
        stage.showAndWait();
        listaKategorija.setAll(dao.kategorije());
    }

    public void izbrisiKategoriju(ActionEvent actionEvent) {
        Kategorija kategorija = kategorijeTabela.getSelectionModel().getSelectedItem();
        if (kategorija == null) return;

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Potvrda brisanja");
        alert.setHeaderText("Brisanje kategorije "+kategorija.getNaziv());
        alert.setContentText("Da li ste sigurni da želite obrisati kategoriju " +kategorija.getNaziv()+"?");

        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == ButtonType.OK){
            try{
                dao.izbrisiKategoriju(kategorija.getId());
            }catch(NemateOvlastiIzuzetak e)
            {
                Alert alarm = new Alert(Alert.AlertType.ERROR);
                alarm.setTitle("Greška pri brisanju kategorije");
                alarm.setHeaderText(e.getMessage());
                alarm.setContentText("Pokušajte ponovno :(");
                alarm.showAndWait();
            }

            listaKategorija.setAll(dao.kategorije());
        }
    }

}
