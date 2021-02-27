package kuhar;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollBar;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.TilePane;
import javafx.scene.layout.VBox;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

import static javafx.geometry.NodeOrientation.INHERIT;
import static javafx.geometry.Pos.TOP_CENTER;

public class GlavnaController implements Initializable {
    public TilePane sadrzaj;
    public ScrollPane scrolpane;
    @FXML
    private ScrollBar skrol;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        scrolpane.setFitToWidth(true);
    }

    private void ubaciPrikazeRecepata()
    {
        //Svi recepti
        ArrayList<VBox> recepti = new ArrayList<>();

        //Uzimanje roditeljske velicine
        double velicina = 3;

        //Kreiranje prikaza slike
        ImageView slikaKontejner = new ImageView(new Image("img/grah.jpg"));
        slikaKontejner.setFitHeight(velicina);
        slikaKontejner.setFitWidth(velicina);
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
//        prikazRecepta.getChildren().addAll(recepti);
    }
}
