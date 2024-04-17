package easvbar.gui.controller;

import easvbar.be.Event;
import easvbar.gui.helperclases.ShowImageClass;
import easvbar.gui.model.EventMakerModel;
import io.github.palexdev.materialfx.controls.MFXButton;
import io.github.palexdev.materialfx.controls.MFXTextField;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.DatePicker;
import javafx.scene.input.DragEvent;
import javafx.scene.input.Dragboard;
import javafx.scene.input.TransferMode;
import javafx.scene.layout.StackPane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.util.ResourceBundle;

public class EventMakerController extends BaseController implements Initializable {
    @FXML
    private StackPane background;
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
    private ShowImageClass showImageClass;


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        try {
            showImageClass= new ShowImageClass();
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
        background.setBackground(showImageClass.setBackGroundImage(event.getEventImage()));


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

    public void handleOnDragDropped(DragEvent dragEvent) {
        Dragboard dragboard = dragEvent.getDragboard();
        if (dragboard.hasFiles()) {
            for (File file : dragboard.getFiles()) {
                // Construct the destination path
                Path destinationPath = Paths.get("resources/images", file.getName());
                try {
                    // Copy the file to the destination folder
                    Files.copy(file.toPath(), destinationPath);
                    eventFilePath.setText(file.getName());
                    background.setBackground(showImageClass.setBackGroundImage(file.getName()));
                } catch (IOException e) {
                    System.err.println("Error saving file " + file.getName() + ": " + e.getMessage());
                }
            }
        }
    }

    public void handleOnDragOver(DragEvent dragEvent) {
        if (dragEvent.getGestureSource() != this) {
            dragEvent.acceptTransferModes(TransferMode.COPY_OR_MOVE);
        }
        dragEvent.consume();
    }

    @FXML
    private void handleUploadPicture(ActionEvent actionEvent) {
        FileChooser fc = new FileChooser();
        Stage stage = (Stage) cancelBtn.getScene().getWindow();
        File f = fc.showOpenDialog(stage);
        fc.getExtensionFilters().add(new FileChooser.ExtensionFilter("Images",
                "*.png", "*.jpg", "*.gif", "*.tif", "*.bmp"));
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