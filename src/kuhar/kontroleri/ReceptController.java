package kuhar.kontroleri;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import kuhar.KuharDAO;
import kuhar.izuzeci.NemateOvlastiIzuzetak;
import kuhar.modeli.*;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;

import static javafx.scene.layout.Region.USE_COMPUTED_SIZE;

public class ReceptController implements Initializable {

    public ChoiceBox kategorijaMeni;
    public Button odabirFotografijeBtn;
    public Label fotografija;
    public TextArea receptTextField;
    public Button dodajSastojakBtn;
    public Button urediSastojakBtn;
    public Button izbrisiSastojakBtn;

    public TableView sastojciTabela;
    public TableColumn kolicinaKolona;
    public TableColumn jedinicaKolona;
    public TableColumn nazivKolona;

    public static ObservableList<Sastojak> listaSastojaka;
    private KuharDAO dao;
    private ArrayList<Sastojak> sastojci = new ArrayList<>();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        dao = KuharDAO.getInstance();
        listaSastojaka = FXCollections.observableArrayList(sastojci);
        sastojciTabela.setItems(listaSastojaka);
        nazivKolona.setCellValueFactory(new PropertyValueFactory("naziv"));
        kolicinaKolona.setCellValueFactory(new PropertyValueFactory("kolicina"));
        jedinicaKolona.setCellValueFactory(new PropertyValueFactory("jedinica"));

    }

    public void dodajSastojak(ActionEvent actionEvent) throws IOException {
        Stage novi = new Stage();
        Parent root = null;
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/sastojak.fxml"));
        SastojakController sastojakController = new SastojakController();
        loader.setController(sastojakController);
        root = loader.load();
        novi.setTitle("Dodaj novi sastojak");
        novi.setScene(new Scene(root, USE_COMPUTED_SIZE, USE_COMPUTED_SIZE));
        novi.setResizable(false);
        novi.showAndWait();
    }

    public void urediSastojak(ActionEvent actionEvent) throws IOException {
        Sastojak sastojak = (Sastojak) sastojciTabela.getSelectionModel().getSelectedItem();
        if (sastojak == null)
            return;
        Stage novi = new Stage();
        Parent root = null;
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/sastojak.fxml"));
        SastojakController sastojakController = new SastojakController(sastojak);
        loader.setController(sastojakController);
        root = loader.load();
        novi.setTitle("Uredi sastojak");
        novi.setScene(new Scene(root, USE_COMPUTED_SIZE, USE_COMPUTED_SIZE));
        novi.setResizable(false);
        novi.showAndWait();
    }

    public void izbrisiSastojak(ActionEvent actionEvent) {
        Sastojak sastojak = (Sastojak) sastojciTabela.getSelectionModel().getSelectedItem();
        if (sastojak == null) return;
        listaSastojaka.remove(sastojak);
    }
}
