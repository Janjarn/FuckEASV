package easvbar.gui.controller;

import easvbar.be.Event;
import easvbar.be.User;
import easvbar.be.Worker;
import easvbar.gui.model.UserModel;
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
    private TableView listUser;
    @FXML
    private TableColumn cclUserName;
    @FXML
    private TableColumn cclUserLastname;
    @FXML
    private TableColumn cclPersonalName;
    @FXML
    private TableColumn cclPersonalRole;
    @FXML
    private HBox hBoxPersonal;
    @FXML
    private TableView listPersonal;
    @FXML
    private Label lblPersonal;
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
    private UserModel userModel;
    private Worker selectedWorker = new Worker();
    private User selectedUser = new User();

    public void setUp() {
        try {
            workerModel = new WorkerModel();
            userModel = new UserModel();
            listPersonal.setItems(workerModel.getAllWorkers());

            cclPersonalName.setCellValueFactory(new PropertyValueFactory<>("name"));
            cclPersonalRole.setCellValueFactory(new PropertyValueFactory<>("role"));

            listUser.setItems(userModel.getAllUsers());
            System.out.println(userModel.getAllUsers());

            cclUserName.setCellValueFactory(new PropertyValueFactory<>("name"));
            cclUserLastname.setCellValueFactory(new PropertyValueFactory<>("lastname"));

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
        try {
            userModel.deleteUser(selectedUser);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @FXML
    private void handleUpdateCustomer(ActionEvent actionEvent) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/CreateUpdateUsers.fxml"));
        Parent secondWindow = loader.load();
        Stage newStage = new Stage();
        newStage.setTitle("Update User");
        Scene scene = new Scene(secondWindow);
        CreateUpdateUsersController controller = loader.getController();
        controller.updateUser(selectedUser);

        newStage.setScene(scene);
        newStage.showAndWait();
        setUp();
    }

    @FXML
    private void handleCreateCustomer(ActionEvent actionEvent) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/CreateUpdateUsers.fxml"));
        Parent secondWindow = loader.load();
        Stage newStage = new Stage();
        newStage.setTitle("Create User");
        Scene scene = new Scene(secondWindow);
        CreateUpdateUsersController controller = loader.getController();
        controller.createUser();
        newStage.setScene(scene);
        newStage.showAndWait();
        setUp();
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
        selectedUser = (User) listUser.getSelectionModel().getSelectedItem();
    }

    @FXML
    private void handleSelectPersonal(MouseEvent mouseEvent) {
        selectedWorker = (Worker) listPersonal.getSelectionModel().getSelectedItem();
    }
}
