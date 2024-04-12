package easvbar.gui.controller;

import easvbar.be.Event;
import easvbar.be.Worker;
import easvbar.gui.model.WorkerModel;
import io.github.palexdev.materialfx.controls.MFXButton;
import io.github.palexdev.materialfx.controls.MFXListView;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.ObservableMap;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class UserspageAdminController {
    @FXML
    private TableColumn cclPersonalName;
    @FXML
    private TableColumn cclPersonalRole;
    @FXML
    private MFXButton btnDeletePersonal;
    @FXML
    private MFXButton btnUpdatePersonal;
    @FXML
    private MFXButton btnCreatePersonal;
    @FXML
    private HBox hBoxPersonal;
    @FXML
    private TableView listPersonal;
    @FXML
    private Label lblPersonal;
    @FXML
    private MFXListView listCustomers;
    @FXML
    private MFXButton btnDeleteCustomer;
    @FXML
    private MFXButton btnUpdateCustomer;
    @FXML
    private MFXButton btnCreateCustomer;
    @FXML
    private HBox hBoxCustomers;
    @FXML
    private Label lblCustomers;
    @FXML
    private MenuItem btnLogout;
    @FXML
    private MenuButton btnProfile;
    @FXML
    private MFXButton btnEvents;
    @FXML
    private MFXButton btnLogo;


    private WorkerModel workerModel;
    private Worker selectedWorker = new Worker();

    public void setUp() {
        try {
            workerModel = new WorkerModel();
            listPersonal.setItems(workerModel.getAllWorkers());

            cclPersonalName.setCellValueFactory(new PropertyValueFactory<>("name"));
            cclPersonalRole.setCellValueFactory(new PropertyValueFactory<>("role"));

        } catch (Exception e) {
            e.printStackTrace(); // Handle exception appropriately
        }
    }
    @FXML
    private void handleDeletePersonal(ActionEvent actionEvent) {
        try {
            workerModel.deleteWorker(selectedWorker);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @FXML
    private void handleUpdatePersonal(ActionEvent actionEvent) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/CreateUpdatePersonal.fxml"));
        Parent secondWindow = loader.load();
        Stage newStage = new Stage();
        newStage.setTitle("Update Personal");
        Scene scene = new Scene(secondWindow);
        CreateUpdatePersonalController createUpdatePersonalController = loader.getController();
        createUpdatePersonalController.updatePersonal(selectedWorker);

        newStage.setScene(scene);
        newStage.showAndWait();
        setUp();
    }

    @FXML
    private void handleCreatePersonal(ActionEvent actionEvent) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/CreateUpdatePersonal.fxml"));
        Parent secondWindow = loader.load();
        Stage newStage = new Stage();
        newStage.setTitle("Create Personal");
        Scene scene = new Scene(secondWindow);
        CreateUpdatePersonalController createUpdatePersonalController = loader.getController();
        createUpdatePersonalController.createPersonal();
        newStage.setScene(scene);
        newStage.showAndWait();
        setUp();

    }

    @FXML
    private void handleDeleteCustomer(ActionEvent actionEvent) {
    }

    @FXML
    private void handleUpdateCustomer(ActionEvent actionEvent) {
    }

    @FXML
    private void handleCreateCustomer(ActionEvent actionEvent) {
    }

    @FXML
    private void handleLogout(ActionEvent actionEvent) throws IOException {

        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/LoginView.fxml"));
        Parent secondWindow = loader.load();
        Stage newStage = new Stage();
        newStage.setTitle("EASV Bar");
        Scene scene = new Scene(secondWindow);
        newStage.setScene(scene);

        // Check if the current window is maximized
        Stage currentStage = (Stage) btnProfile.getScene().getWindow();
        if (currentStage.isMaximized()) {
            newStage.setMaximized(true); // Maximize the new window
        }

        newStage.show();
        currentStage.hide();
    }

    @FXML
    private void handleProfile(ActionEvent actionEvent) {
    }

    @FXML
    private void handleEvents(ActionEvent actionEvent) throws IOException {

        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/HomepageAdmin.fxml"));
        Parent secondWindow = loader.load();
        Stage newStage = new Stage();
        newStage.setTitle("Admin Page");
        Scene scene = new Scene(secondWindow);
        HomepageAdminController homepageAdminController = loader.getController();
        homepageAdminController.setUp();
        newStage.setScene(scene);

        // Check if the current window is maximized
        Stage currentStage = (Stage) btnEvents.getScene().getWindow();
        if (currentStage.isMaximized()) {
            newStage.setMaximized(true); // Maximize the new window
        }

        newStage.show();
        currentStage.hide();
    }

    @FXML
    private void handleLogo(ActionEvent actionEvent) throws IOException {

        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/HomepageAdmin.fxml"));
        Parent secondWindow = loader.load();
        Stage newStage = new Stage();
        newStage.setTitle("Admin Page");
        Scene scene = new Scene(secondWindow);
        HomepageAdminController homepageAdminController = loader.getController();
        homepageAdminController.setUp();
        newStage.setScene(scene);

        // Check if the current window is maximized
        Stage currentStage = (Stage) btnLogo.getScene().getWindow();
        if (currentStage.isMaximized()) {
            newStage.setMaximized(true); // Maximize the new window
        }

        newStage.show();
        currentStage.hide();
    }

    @FXML
    private void handleSelectUser(MouseEvent mouseEvent) {
    }

    @FXML
    private void handleSelectPersonal(MouseEvent mouseEvent) {
        selectedWorker = (Worker) listPersonal.getSelectionModel().getSelectedItem();
    }
}
