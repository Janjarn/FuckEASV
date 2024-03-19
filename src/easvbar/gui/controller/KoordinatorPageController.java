package easvbar.gui.controller;

import io.github.palexdev.materialfx.controls.MFXButton;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class KoordinatorPageController {

    @FXML
    private MFXButton btnLogout;
    @FXML
    private MFXButton btnCreateEvent;

    @FXML
    private void handleCreateEventWindow(ActionEvent actionEvent) throws IOException {

        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/EventMaker.fxml"));
        Parent secondWindow = loader.load();
        Stage newStage = new Stage();
        newStage.setTitle("Event Maker");
        Scene scene = new Scene(secondWindow);
        newStage.setScene(scene);
        newStage.show();

    }

    public void handleLogout(ActionEvent actionEvent) throws IOException {

        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/LoginView.fxml"));
        Parent secondWindow = loader.load();
        Stage newStage = new Stage();
        newStage.setTitle("EASV Bar");
        Scene scene = new Scene(secondWindow);
        newStage.setScene(scene);
        newStage.show();
        btnLogout.getScene().getWindow().hide();
    }
}
