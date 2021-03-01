package kuhar.kontroleri;

import javafx.collections.ObservableList;
import javafx.fxml.Initializable;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import kuhar.modeli.*;

import java.net.URL;
import java.util.ResourceBundle;

import static kuhar.modeli.Jedinice.*;

public class SastojakController implements Initializable {
    public TextField nazivField;
    public TextField kolicinaField;
    public ChoiceBox<Jedinice> tipField;

    private Sastojak sastojak;
    private ObservableList<String> jedinice;
    public SastojakController(){};
    public SastojakController(Sastojak s)
    {
        sastojak = s;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        tipField.getItems().setAll(Jedinice.values());
        if (sastojak != null) {
            nazivField.setText(sastojak.getNaziv());
            kolicinaField.setText(Double.toString(sastojak.getKolicina()));
        }
        tipField.getSelectionModel().selectFirst();
    }

    public void okPritisnuto()
    {
        if (sastojak != null) {
            ReceptController.listaSastojaka.remove(sastojak);
        }
        switch(tipField.getValue())
        {
            case kg: ReceptController.listaSastojaka.add(new PraškastiSastojak(nazivField.getText(), Double.parseDouble(kolicinaField.getText()), kg)); break;
            case gr: ReceptController.listaSastojaka.add(new PraškastiSastojak(nazivField.getText(), Double.parseDouble(kolicinaField.getText()), gr)); break;
            case dl: ReceptController.listaSastojaka.add(new TečniSastojak(nazivField.getText(), Double.parseDouble(kolicinaField.getText()), dl)); break;
            case ml:ReceptController.listaSastojaka.add(new TečniSastojak(nazivField.getText(), Double.parseDouble(kolicinaField.getText()), ml)); break;
            case l: ReceptController.listaSastojaka.add(new TečniSastojak(nazivField.getText(), Double.parseDouble(kolicinaField.getText()), l)); break;
            case komad: ReceptController.listaSastojaka.add(new JediničniSastojak(nazivField.getText(), Double.parseDouble(kolicinaField.getText()), komad)); break;
            case pakovanje:ReceptController.listaSastojaka.add(new JediničniSastojak(nazivField.getText(), Double.parseDouble(kolicinaField.getText()), pakovanje)); break;
        }
        Stage stage = (Stage) nazivField.getScene().getWindow();
        stage.close();
    }
    public void cancelPritisnuto()
    {
        Stage stage = (Stage) nazivField.getScene().getWindow();
        stage.close();
    }

}
