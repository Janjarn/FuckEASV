package easvbar.gui.controller;

import easvbar.be.Event;
import easvbar.gui.model.EventMakerModel;
import io.github.palexdev.materialfx.controls.MFXButton;
import io.github.palexdev.materialfx.controls.MFXTextField;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.DatePicker;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ResourceBundle;

public class EventMakerController extends BaseController implements Initializable {
    private EventMakerModel eventMakerModel;
    @FXML
    private MFXTextField eventName, eventLocation, eventTicketsSold, eventStart, eventEnd, eventCreatedBy, eventFilePath;
    @FXML
    private DatePicker eventDate;
    @FXML
    private MFXButton createBtn, cancelBtn, uploadBtn;
    private String errorText;


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        try {
            eventMakerModel = new EventMakerModel();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void setup() throws Exception {
    }

    public void handleCreateEvent(ActionEvent actionEvent) throws Exception {
        Event event = new Event(-1, eventName.getText(), eventLocation.getText(), eventStart.getText(),
                eventEnd.getText(), eventDate.getValue().toString(), eventCreatedBy.getText(), eventFilePath.getText());
        try {
            eventMakerModel.createEvent(event);
        }
        catch (Exception e) {
            displayError(e);
            e.printStackTrace();
        } finally {
            createBtn.getScene().getWindow().hide();
        }
    }

    public void handleCancelBtn(ActionEvent actionEvent) {
        Stage stage = (Stage) cancelBtn.getScene().getWindow();
        stage.close();
    }

    @FXML
    private void handleUploadPicture(ActionEvent actionEvent) {
    }

    protected void displayError(Throwable t) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(errorText);
        alert.setHeaderText(t.getMessage());
        alert.showAndWait();
    }
}