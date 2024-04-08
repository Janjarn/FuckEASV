package easvbar.gui.controller;

import easvbar.be.Worker;
import easvbar.bll.WorkerManager;
import io.github.palexdev.materialfx.controls.MFXButton;
import io.github.palexdev.materialfx.controls.MFXPasswordField;
import io.github.palexdev.materialfx.controls.MFXTextField;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Properties;

public class LoginViewController {
    @FXML
    private MFXPasswordField txtPassword;
    @FXML
    private MFXTextField txtUsername;
    private int toggle = 2;

    @FXML
    private MFXButton btnLogin;

    private WorkerManager workerManager = new WorkerManager();

    public LoginViewController() throws IOException {
    }

    @FXML
    private void handleLogin(ActionEvent actionEvent) throws Exception {

        String userName = txtUsername.getText();
        String userPassword = txtPassword.getText();


        // here the method is run that validates the user credentials.
        Worker workerLogged = workerManager.validateUser(userName, userPassword);
        if (!(workerLogged != null)) {
            // if it matches you are logged in
            if (workerLogged.getRoleId() == 1) {

                FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/HomepageAdmin.fxml"));
                Parent secondWindow = loader.load();
                Stage newStage = new Stage();
                newStage.setTitle("Admin Page");
                Scene scene = new Scene(secondWindow);
                HomepageAdminController homepageAdminController = loader.getController();
                homepageAdminController.setUp();
                newStage.setScene(scene);
                newStage.show();
                btnLogin.getScene().getWindow().hide();

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
                newStage.show();
                btnLogin.getScene().getWindow().hide();

            }
        }else {
            txtUsername.setText("Wrong password or username");
        }
    }

    @FXML
    private void handleEvent(ActionEvent actionEvent) {
        toggle = 2;
    }

    @FXML
    private void handleAdmin(ActionEvent actionEvent) {
        toggle = 1;
    }
}
