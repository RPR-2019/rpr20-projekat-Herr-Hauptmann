package kuhar.kontroleri;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import kuhar.KuharDAO;
import kuhar.modeli.Korisnik;

public class AdminController {
    public TableView<Korisnik> korisniciTabela;
    public TableColumn idKolona;
    public TableColumn imeKolona;
    public TableColumn usernameKolona;
    public TableColumn adminKolona;
    private ObservableList<Korisnik> listaKorisnika;

    private KuharDAO dao;

    AdminController(){
        dao = KuharDAO.getInstance();
        listaKorisnika = FXCollections.observableArrayList(dao.korisnici());
    }
}
