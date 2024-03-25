package easvbar.gui.controller;

import io.github.palexdev.materialfx.controls.MFXButton;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class LoginViewController {
    private int toggle = 2;

    @FXML
    private MFXButton btnLogin;

    @FXML
    private void handleLogin(ActionEvent actionEvent) throws IOException {

        if (toggle == 1) {

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

        if (toggle == 2) {

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
