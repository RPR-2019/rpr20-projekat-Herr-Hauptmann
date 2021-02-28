package kuhar;

import javafx.event.ActionEvent;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class LoginController {
    public PasswordField passwordField;
    public TextField usernameField;
    private KuharDAO dao;

    public LoginController()
    {
        dao = KuharDAO.getInstance();
    }

    public void obradiLoginPokusaj()
    {
        Korisnik korisnik = dao.dajKorisnika(usernameField.getText(), passwordField.getText());
        if (korisnik == null)
        {
            Stage stage = (Stage) usernameField.getScene().getWindow();
            stage.setAlwaysOnTop(false);
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Greška pri logiranju");
            alert.setHeaderText("Unijeli ste pogrešan username ili password!!!");
            alert.setContentText("Pokušajte ponovno");
            alert.showAndWait();
            stage.setAlwaysOnTop(true);
            passwordField.clear();
            usernameField.clear();
        }
        else {
            GlavnaController.korisnik = korisnik;
            Stage stage = (Stage) usernameField.getScene().getWindow();
            stage.close();
        }
    }

    public void zatvoriProzor(ActionEvent actionEvent)
    {
        Node n = (Node) actionEvent.getSource();
        Stage stage = (Stage) n.getScene().getWindow();
        stage.close();
    }
}