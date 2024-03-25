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
import java.time.LocalDate;
import java.util.ResourceBundle;

public class EventMakerController extends BaseController implements Initializable {
    @FXML
    private MFXButton btnUpdate;
    private EventMakerModel eventMakerModel;
    @FXML
    private MFXTextField eventName, eventLocation, eventTicketsSold, eventStart, eventEnd, eventCreatedBy, eventFilePath;
    @FXML
    private DatePicker eventDate;
    @FXML
    private MFXButton createBtn, cancelBtn, uploadBtn;
    private String errorText;
    private Event selectedEvent;


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

    public void updateEvent(Event event) {
        selectedEvent = event;
        eventEnd.setText(event.getEventEnd());
        eventStart.setText(event.getEventStart());
        eventDate.setValue(LocalDate.parse(event.getDate()));
        eventLocation.setText(event.getLocation());
        eventCreatedBy.setText(event.getCreatedBy());
        eventName.setText(event.getName());
        eventFilePath.setText(event.getEventImage());

    }

    public void handleCreateEvent(ActionEvent actionEvent) throws Exception {
        Event event = new Event(-1, eventName.getText(), eventStart.getText(), eventEnd.getText(),
                eventLocation.getText(), eventDate.getValue().toString(), eventCreatedBy.getText(), eventFilePath.getText());
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

    @FXML
    private void handleUpdateEvent(ActionEvent actionEvent) {
        try {
            selectedEvent.setName(eventName.getText());
            selectedEvent.setDate(String.valueOf(eventDate.getValue()));
            selectedEvent.setEventEnd(eventEnd.getText());
            selectedEvent.setEventStart(eventStart.getText());
            selectedEvent.setLocation(eventLocation.getText());
            selectedEvent.setCreatedBy(eventCreatedBy.getText());
            selectedEvent.setEventImage(eventFilePath.getText());

            eventMakerModel.updateEvent(selectedEvent);
        }
        catch (Exception e) {
            displayError(e);
            e.printStackTrace();
        } finally {
            createBtn.getScene().getWindow().hide();
        }
    }
}