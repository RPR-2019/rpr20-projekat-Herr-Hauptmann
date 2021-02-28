package kuhar;

import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.TilePane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import static javafx.scene.layout.Region.USE_COMPUTED_SIZE;

public class GlavnaController implements Initializable {
    public TilePane sadrzaj;
    public ScrollPane scrolpane;

    private KuharDAO dao;

    public GlavnaController()
    {
        dao = KuharDAO.getInstance();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        scrolpane.setFitToWidth(true);
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

    private void obradiLogin() {
        if (dao.getUser() == null)
            System.out.println("Nije logovan");
        else
            System.out.println("Logovan je: " + dao.getUser().getIme());
    }

}
