package easvbar.gui.controller;

import easvbar.be.Worker;
import easvbar.bll.WorkerManager;
import easvbar.gui.helperclases.ShowImageClass;
import io.github.palexdev.materialfx.controls.MFXButton;
import io.github.palexdev.materialfx.controls.MFXPasswordField;
import io.github.palexdev.materialfx.controls.MFXTextField;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class LoginViewController {
    @FXML
    private GridPane backGround;
    @FXML
    private MFXPasswordField txtPassword;
    @FXML
    private MFXTextField txtUsername;

    @FXML
    private MFXButton btnLogin;

    private WorkerManager workerManager = new WorkerManager();
    private ShowImageClass showImageClass = new ShowImageClass();

    public LoginViewController() throws IOException {
    }
    public void setUp() {
    }

    @FXML
    private void handleLogin(ActionEvent actionEvent) throws Exception {

        String userName = txtUsername.getText();
        String userPassword = txtPassword.getText();


        // here the method is run that validates the user credentials.
        Worker workerLogged = workerManager.validateUser(userName, userPassword);
        if (!(workerLogged == null)) {
            // if it matches you are logged in
            if (workerLogged.getRoleId() == 1) {

                FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/HomepageAdmin.fxml"));
                Parent secondWindow = loader.load();
                Stage newStage = new Stage();
                newStage.setTitle("Admin Page");
                Scene scene = new Scene(secondWindow);
                HomepageAdminController homepageAdminController = loader.getController();
                homepageAdminController.setUp();
                homepageAdminController.setOperator(workerLogged);
                newStage.setScene(scene);

                // Check if the current window is maximized
                Stage currentStage = (Stage) btnLogin.getScene().getWindow();
                if (currentStage.isMaximized()) {
                    newStage.setMaximized(true); // Maximize the new window
                }

                newStage.show();
                currentStage.hide();

            }
            if (workerLogged.getRoleId() == 2) {

                FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/KoordinatorPage.fxml"));
                Parent secondWindow = loader.load();
                Stage newStage = new Stage();
                newStage.setTitle("Koordinator Page");
                Scene scene = new Scene(secondWindow);
                KoordinatorPageController koordinatorPageController = loader.getController();
                koordinatorPageController.setUp();
                newStage.setScene(scene);

                // Check if the current window is maximized
                Stage currentStage = (Stage) btnLogin.getScene().getWindow();
                if (currentStage.isMaximized()) {
                    newStage.setMaximized(true); // Maximize the new window
                }

                newStage.show();
                currentStage.hide();

            }
        }else {
            txtUsername.setText("Wrong password or username");
        }
    }
}
