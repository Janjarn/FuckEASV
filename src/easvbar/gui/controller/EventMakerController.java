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
    private EventMakerModel eventMakerModel;
    @FXML
    private MFXTextField eventName, eventLocation, eventStart, eventEnd, eventCreatedBy, eventFilePath, eventDescription, eventGuide;
    @FXML
    private DatePicker eventDate;
    @FXML
    private MFXButton createBtn, cancelBtn;
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
        eventDescription.setText(event.getEventDescription());
        eventGuide.setText(event.getEventGuide());
        background.setBackground(showImageClass.setBackGroundImage(event.getEventImage()));
        createBtn.setText("Update");


    }

    @FXML
    private void handleCreateEvent(ActionEvent actionEvent) throws Exception {
        if (createBtn.equals("Update")) {
            try {
                selectedEvent.setName(eventName.getText());
                selectedEvent.setDate(String.valueOf(eventDate.getValue()));
                selectedEvent.setEventEnd(eventEnd.getText());
                selectedEvent.setEventStart(eventStart.getText());
                selectedEvent.setLocation(eventLocation.getText());
                selectedEvent.setCreatedBy(eventCreatedBy.getText());
                selectedEvent.setEventImage(eventFilePath.getText());
                selectedEvent.setEventGuide(eventGuide.getText());
                selectedEvent.setEventDescription(eventDescription.getText());

                eventMakerModel.updateEvent(selectedEvent);
            }
            catch (Exception e) {
                displayError(e);
                e.printStackTrace();
            } finally {
                createBtn.getScene().getWindow().hide();
            }
        } else {
            Event event = new Event(-1, eventName.getText(), eventStart.getText(), eventEnd.getText(),
                    eventLocation.getText(), eventDate.getValue().toString(), eventCreatedBy.getText(),
                    eventFilePath.getText(), eventGuide.getText(), eventDescription.getText());
            try {
                eventMakerModel.createEvent(event);
            } catch (Exception e) {
                displayError(e);
                e.printStackTrace();
            } finally {
                createBtn.getScene().getWindow().hide();
            }
        }
    }

    @FXML
    private void handleCancelBtn(ActionEvent actionEvent) {
        Stage stage = (Stage) cancelBtn.getScene().getWindow();
        stage.close();
    }

    @FXML
    private void handleOnDragDropped(DragEvent dragEvent) {
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

    @FXML
    private void handleOnDragOver(DragEvent dragEvent) {
        if (dragEvent.getGestureSource() != this) {
            dragEvent.acceptTransferModes(TransferMode.COPY_OR_MOVE);
        }
        dragEvent.consume();
    }

    @FXML
    private void handleUploadPicture(ActionEvent actionEvent) {
        // Create a new FileChooser
        FileChooser fileChooser = new FileChooser();

        // Set the title for the FileChooser dialog
        fileChooser.setTitle("Select Image File");

        // Set the initial directory for the FileChooser (optional)
        File initialDirectory = new File(System.getProperty("user.home")); // For example, start from user's home directory
        fileChooser.setInitialDirectory(initialDirectory);

        // Add filters to only allow certain types of files to be selected (optional)
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("Image Files", "*.png", "*.jpg", "*.gif")
        );

        // Show the FileChooser dialog and wait for user selection
        File selectedFile = fileChooser.showOpenDialog(null);

        // If a file was selected, copy it to the destination folder
        if (selectedFile != null) {
            try {
                // Construct the destination path
                Path destinationPath = generateUniqueFilePath(Paths.get("resources/images"), selectedFile.getName());

                // Copy the file to the destination folder
                Files.copy(selectedFile.toPath(), destinationPath);

                // Optionally, update your UI elements accordingly
                eventFilePath.setText(destinationPath.getFileName().toString());
                background.setBackground(showImageClass.setBackGroundImage(destinationPath.getFileName().toString()));
            } catch (IOException e) {
                System.err.println("Error copying file: " + e.getMessage());
            }
        }
    }

    private Path generateUniqueFilePath(Path directory, String fileName) {
        // Check if the file already exists in the directory
        Path filePath = directory.resolve(fileName);
        if (!Files.exists(filePath)) {
            return filePath; // File does not exist, return the original file path
        }

        // If the file already exists, append a number to the file name until a unique name is found
        int count = 1;
        String baseFileName = fileName.substring(0, fileName.lastIndexOf('.'));
        String extension = fileName.substring(fileName.lastIndexOf('.'));
        while (true) {
            String numberedFileName = baseFileName + "_" + count + extension;
            Path numberedFilePath = directory.resolve(numberedFileName);
            if (!Files.exists(numberedFilePath)) {
                return numberedFilePath; // Found a unique file path
            }
            count++;
        }
    }

    protected void displayError(Throwable t) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(errorText);
        alert.setHeaderText(t.getMessage());
        alert.showAndWait();
    }
}