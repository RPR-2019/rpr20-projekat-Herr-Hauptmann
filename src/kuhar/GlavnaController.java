package kuhar;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.control.Label;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

import static javafx.geometry.NodeOrientation.INHERIT;
import static javafx.geometry.Pos.TOP_CENTER;

public class GlavnaController implements Initializable {
    @FXML
    private HBox prikazRecepta;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        ubaciPrikazeRecepata();
    }

    private void ubaciPrikazeRecepata()
    {
        //Svi recepti
        ArrayList<VBox> recepti = new ArrayList<>();

        //Kreiranje prikaza slike
        ImageView slikaKontejner = new ImageView(new Image("img/grah.jpg"));
        slikaKontejner.setFitHeight(150);
        slikaKontejner.setFitWidth(150);
        slikaKontejner.setNodeOrientation(INHERIT);
        slikaKontejner.setPreserveRatio(true);

        //Kreiranje kontejnera
        VBox prikaz = new VBox();
        prikaz.getChildren().add(slikaKontejner);
        prikaz.getChildren().add(new Label("Grah"));
        prikaz.setAlignment(TOP_CENTER);
        prikaz.setMinHeight(-1);
        prikaz.setPadding(new Insets(5));

        //Ubaci u sveRecepte
        recepti.add(prikaz);

        //Ucitaj recepte
        prikazRecepta.getChildren().addAll(recepti);

    }
}
