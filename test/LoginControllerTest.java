import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.DialogPane;
import javafx.stage.Stage;
import kuhar.KuharDAO;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.extension.ExtendWith;
import org.testfx.api.FxRobot;
import org.testfx.framework.junit5.ApplicationExtension;
import org.testfx.framework.junit5.Start;

import static javafx.scene.layout.Region.USE_COMPUTED_SIZE;
import static org.junit.jupiter.api.Assertions.*;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@ExtendWith(ApplicationExtension.class)

public class LoginControllerTest {
    Stage theStage;
    KuharDAO dao;

    @BeforeAll
    public void pripremi()
    {
        dao = KuharDAO.getInstance();
        dao.vratiBazuNaDefault();
    }

    @Test
    public void zatvoriProzor(FxRobot robot)
    {
        robot.clickOn("#usernameField");
        robot.write("admin");
        robot.clickOn("#passwordField");
        robot.write("admin");
        robot.clickOn("#cancelBtn");

        assertNull(dao.getUser());
    }

    @Start
    public void start(Stage stage) throws Exception {
        KuharDAO.removeInstance();
        dao = KuharDAO.getInstance();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/login.fxml"));
        Parent root = loader.load();
        stage.setTitle("Login");
        stage.setScene(new Scene(root, USE_COMPUTED_SIZE, USE_COMPUTED_SIZE));
        stage.setResizable(false);
        stage.show();
        stage.toFront();
        theStage = stage;
    }

    @Test
    public void testPraznaPolja(FxRobot robot) {
        robot.clickOn("#loginBtn");

        try {
            Thread.sleep(200);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        robot.lookup(".dialog-pane").tryQuery().isPresent();
        DialogPane dialogPane = robot.lookup(".dialog-pane").queryAs(DialogPane.class);
        assertNotNull(dialogPane.lookupAll("Unijeli ste pogrešan username ili password!!!"));
        Button okButton = (Button) dialogPane.lookupButton(ButtonType.OK);
        robot.clickOn(okButton);
    }

    @Test
    public void pogresanUsernamePogresanPassword(FxRobot robot){
        robot.clickOn("#usernameField");
        robot.write("tarik");
        robot.clickOn("#passwordField");
        robot.write("tarik");
        robot.clickOn("#");
        robot.clickOn("#loginBtn");

        try {
            Thread.sleep(200);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        robot.lookup(".dialog-pane").tryQuery().isPresent();
        DialogPane dialogPane = robot.lookup(".dialog-pane").queryAs(DialogPane.class);
        assertNotNull(dialogPane.lookupAll("Unijeli ste pogrešan username ili password!!!"));
        Button okButton = (Button) dialogPane.lookupButton(ButtonType.OK);
        robot.clickOn(okButton);
    }

    @Test
    public void pogresanPassword(FxRobot robot)
    {
        robot.clickOn("#usernameField");
        robot.write("admin");
        robot.clickOn("#passwordField");
        robot.write("tarik");
        robot.clickOn("#");
        robot.clickOn("#loginBtn");

        try {
            Thread.sleep(200);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        robot.lookup(".dialog-pane").tryQuery().isPresent();
        DialogPane dialogPane = robot.lookup(".dialog-pane").queryAs(DialogPane.class);
        assertNotNull(dialogPane.lookupAll("Unijeli ste pogrešan username ili password!!!"));
        Button okButton = (Button) dialogPane.lookupButton(ButtonType.OK);
        robot.clickOn(okButton);
    }

    @Test
    public void sveOK(FxRobot robot)
    {
        robot.clickOn("#usernameField");
        robot.write("admin");
        robot.clickOn("#passwordField");
        robot.write("admin");
        robot.clickOn("#");
        robot.clickOn("#loginBtn");

        assertEquals("Administrator", dao.getUser().getIme());
    }
}
