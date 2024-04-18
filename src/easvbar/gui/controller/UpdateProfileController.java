package easvbar.gui.controller;

import easvbar.be.Worker;
import easvbar.bll.WorkerManager;
import easvbar.gui.helperclases.ShowImageClass;
import io.github.palexdev.materialfx.controls.MFXButton;
import io.github.palexdev.materialfx.controls.MFXTextField;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.input.DragEvent;
import javafx.scene.input.Dragboard;
import javafx.scene.input.TransferMode;
import javafx.scene.layout.AnchorPane;
import javafx.stage.FileChooser;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.Watchable;

public class UpdateProfileController {
    public MFXTextField txtPassword;
    public Label lblPassword;
    public MFXButton btnUpdate;
    public MFXButton btnCancel;
    public MFXTextField txtImagePath;
    public MFXButton btnUploadPicture;
    public AnchorPane background;

    private ShowImageClass showImageClass;
    private Worker operator = new Worker();
    private WorkerManager workerManager;

    public UpdateProfileController() {
        try {
            showImageClass = new ShowImageClass();
            workerManager = new WorkerManager();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void setOperator(Worker worker) {
        this.operator = worker;
    }

    public void handleUpdate(ActionEvent actionEvent) {
        try {
            operator.setPassword(txtPassword.getText());
            operator.setPicture(txtImagePath.getText());

            workerManager.updateWorker(operator);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            btnUpdate.getScene().getWindow().hide();
        }
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
                txtImagePath.setText(destinationPath.getFileName().toString());
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

    @FXML
    private void handleCancel(ActionEvent actionEvent) {
        btnCancel.getScene().getWindow().hide();
    }

    @FXML
    private void handleDragDrop(DragEvent dragEvent) {
        Dragboard dragboard = dragEvent.getDragboard();
        if (dragboard.hasFiles()) {
            for (File file : dragboard.getFiles()) {
                // Construct the destination path
                Path destinationPath = Paths.get("resources/images", file.getName());
                try {
                    // Copy the file to the destination folder
                    Files.copy(file.toPath(), destinationPath);
                    txtImagePath.setText(file.getName());
                    background.setBackground(showImageClass.setBackGroundImage(file.getName()));
                } catch (IOException e) {
                    System.err.println("Error saving file " + file.getName() + ": " + e.getMessage());
                }
            }
        }
    }

    @FXML
    private void handleDragOver(DragEvent dragEvent) {
        if (dragEvent.getGestureSource() != this) {
            dragEvent.acceptTransferModes(TransferMode.COPY_OR_MOVE);
        }
        dragEvent.consume();
    }
}
