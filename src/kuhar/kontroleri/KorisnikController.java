package kuhar.kontroleri;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.CheckBox;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import kuhar.KuharDAO;
import kuhar.izuzeci.NemateOvlastiIzuzetak;
import kuhar.modeli.Korisnik;

public class KorisnikController {
    public TextField imeField;
    public TextField usernameField;
    public PasswordField passwordField;
    public CheckBox adminBox;

    private KuharDAO dao;
    private Korisnik user;
    public KorisnikController()
    {
        dao = KuharDAO.getInstance();
    }
    public KorisnikController(Korisnik korisnik)
    {
        user = korisnik;
        dao = KuharDAO.getInstance();
    }
    @FXML
    public void initialize() {
        if (user != null)
        {
            imeField.setText(user.getIme());
            usernameField.setText(user.getUsername());
            adminBox.setSelected(user.isAdmin());
        }
    }

    public void okPritisnuto()
    {
        if (imeField.getText().isEmpty()) {
            prikaziAlert("Niste unijeli ime i prezime korisnika!");
            return;
        }
        if (usernameField.getText().isEmpty())
        {
            prikaziAlert("Username polje ne smije biti prazno!");
            return;
        }
        if (user == null && dao.provjeriUsername(usernameField.getText()))
        {
            prikaziAlert("Već postoji korisnik s username-om "+ usernameField.getText() +"!");
            return;
        }
        if(!passwordField.getText().isEmpty() && passwordField.getText().length()<5 || user==null && passwordField.getText().length()<5)
        {
            prikaziAlert("Password mora biti duzi od 5 karaktera!");
            return;
        }
        try {
            if (user == null) {
                dao.dodajKorisnika(imeField.getText(), usernameField.getText(), passwordField.getText(), adminBox.isSelected());

            }
            else
            {
                if (passwordField.getText().isEmpty())
                    dao.urediKorisnika(user.getId(), imeField.getText(), usernameField.getText(), adminBox.isSelected());
                else
                    dao.urediKorisnika(user.getId(), imeField.getText(), usernameField.getText(), passwordField.getText(), adminBox.isSelected());
            }
            Stage stage = (Stage) usernameField.getScene().getWindow();
            stage.close();
        }catch(NemateOvlastiIzuzetak izuzetak)
        {
            prikaziAlert(izuzetak.getMessage());
        }
    }
    private void prikaziAlert(String tekstAlerta)
    {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Greška pri kreiranju korisnika");
        alert.setHeaderText(tekstAlerta);
        alert.setContentText("Pokušajte ponovno");
        alert.showAndWait();
    }
    public void cancelPritisnuto(ActionEvent actionEvent)
    {
        Node n = (Node) actionEvent.getSource();
        Stage stage = (Stage) n.getScene().getWindow();
        stage.close();
    }
}
