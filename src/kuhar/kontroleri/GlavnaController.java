package kuhar.kontroleri;

import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.TilePane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import kuhar.KuharDAO;
import kuhar.modeli.Recept;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

import static javafx.scene.layout.Region.USE_COMPUTED_SIZE;

public class GlavnaController implements Initializable {
    public TilePane sadrzaj;
    public ScrollPane scrolpane;
    public Button dodajReceptBtn;
    public Button urediReceptBtn;
    public Button adminBtn;
    public Button logoutBtn;
    public Button loginBtn;

    private KuharDAO dao;
    private ArrayList<Recept> recepti;

    public GlavnaController()
    {
        dao = KuharDAO.getInstance();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        scrolpane.setFitToWidth(true);
        obradiLogin();
    }

    public void otvoriAdminKonzolu() throws IOException {
        Stage admin = new Stage();
        Parent root = FXMLLoader.load(getClass().getResource("/fxml/admin.fxml"));
        admin.setTitle("Admin");
        admin.setScene(new Scene(root, USE_COMPUTED_SIZE, USE_COMPUTED_SIZE));
        admin.show();
    }

    public void otvoriLogin() throws IOException {
        Stage login = new Stage();
        Parent root = FXMLLoader.load(getClass().getResource("/fxml/login.fxml"));
        login.setTitle("Login");
        login.setScene(new Scene(root, USE_COMPUTED_SIZE, USE_COMPUTED_SIZE));
        login.setResizable(false);
        login.setAlwaysOnTop(true);
        login.initModality(Modality.APPLICATION_MODAL);
        login.showAndWait();
        obradiLogin();
    }

    public void logout(){
        dao.setUser(null);
        obradiLogin();
    }

    private void obradiLogin() {
        if (dao.getUser() == null)
        {
            upaliDugme(loginBtn);
            ugasiDugme(adminBtn);
            ugasiDugme(logoutBtn);
            ugasiDugme(dodajReceptBtn);
            ugasiDugme(urediReceptBtn);
        }
        else if (dao.getUser().isAdmin())
        {
            ugasiDugme(loginBtn);
            upaliDugme(adminBtn);
            upaliDugme(logoutBtn);
            upaliDugme(dodajReceptBtn);
            upaliDugme(urediReceptBtn);
        }
        else
        {
            ugasiDugme(loginBtn);
            ugasiDugme(adminBtn);
            upaliDugme(logoutBtn);
            upaliDugme(dodajReceptBtn);
            upaliDugme(urediReceptBtn);
        }
    }

    private void upaliDugme(Button dugme) {
        dugme.setVisible(true);
        dugme.setManaged(true);
    }
    private void ugasiDugme(Button dugme) {
        dugme.setVisible(false);
        dugme.setManaged(false);
    }

    public void otvoriRecept()
    {
        System.out.println("Klik!");
    }

    public void otvoriKategorije() throws IOException {
        Stage admin = new Stage();
        Parent root = FXMLLoader.load(getClass().getResource("/fxml/pregled_kategorija.fxml"));
        admin.setTitle("Kategorije");
        admin.setScene(new Scene(root, USE_COMPUTED_SIZE, USE_COMPUTED_SIZE));
        admin.show();
    }
}
