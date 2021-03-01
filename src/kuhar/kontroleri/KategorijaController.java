package kuhar.kontroleri;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import kuhar.KuharDAO;
import kuhar.izuzeci.NemateOvlastiIzuzetak;
import kuhar.modeli.Kategorija;

public class KategorijaController {
    public TextField nazivField;

    private KuharDAO dao;
    private Kategorija kategorija;

    public KategorijaController()
    {
        dao = KuharDAO.getInstance();
    }
    public KategorijaController(Kategorija k)
    {
        kategorija = k;
        dao = KuharDAO.getInstance();
    }

    @FXML
    public void initialize() {
        if (kategorija != null)
        {
            nazivField.setText(kategorija.getNaziv());
        }
    }

    public void okPritisnuto()
    {
        if (nazivField.getText().isEmpty()) {
            prikaziAlert("Niste unijeli naziv kategorije!");
            return;
        }

        try {
            if (kategorija == null)
                dao.dodajKategoriju(nazivField.getText());
            else
                dao.urediKategoriju(kategorija.getId(), nazivField.getText());

            Stage stage = (Stage) nazivField.getScene().getWindow();
            stage.close();
        }catch(NemateOvlastiIzuzetak izuzetak)
        {
            prikaziAlert(izuzetak.getMessage());
        }

    }

    private void prikaziAlert(String tekstAlerta)
    {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Greška pri kreiranju kategorije");
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
