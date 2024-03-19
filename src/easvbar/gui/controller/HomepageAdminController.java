package easvbar.gui.controller;

import io.github.palexdev.materialfx.controls.MFXButton;
import io.github.palexdev.materialfx.controls.MFXListView;
import io.github.palexdev.materialfx.controls.MFXTextField;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.io.IOException;

public class HomepageAdminController {
    @FXML
    private MFXButton btnUsers;
    @FXML
    private MFXButton btnAdminPanel;
    @FXML
    private MFXButton btnLogout;
    @FXML
    private MFXButton btnLogo;
    @FXML
    private MFXTextField txtFieldEventInfo;
    @FXML
    private MFXListView listViewFeaturedEvent;
    @FXML
    private MFXListView listViewUpcomingEvents;
    @FXML
    private void handleLogo(ActionEvent actionEvent) {
    }
    @FXML
    private void handleUsers(ActionEvent actionEvent) {
    }
    @FXML
    private void handleAdminPanel(ActionEvent actionEvent) {
    }
    @FXML
    private void handleLogout(ActionEvent actionEvent) throws IOException {

        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/LoginView.fxml"));
        Parent secondWindow = loader.load();
        Stage newStage = new Stage();
        newStage.setTitle("EASV Bar");
        Scene scene = new Scene(secondWindow);
        newStage.setScene(scene);
        newStage.show();
        btnLogout.getScene().getWindow().hide();

    }
    @FXML
    private void handleUpComingEvents(MouseEvent mouseEvent) {
    }
    @FXML
    private void handleFeaturedEvent(MouseEvent mouseEvent) {
    }
    @FXML
    private void handleEventInfo(ActionEvent actionEvent) {
    }
}
