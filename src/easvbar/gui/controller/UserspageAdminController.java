package easvbar.gui.controller;

import easvbar.be.Event;
import easvbar.be.User;
import easvbar.be.Worker;
import easvbar.gui.model.UserModel;
import easvbar.gui.model.WorkerModel;
import io.github.palexdev.materialfx.controls.MFXButton;
import io.github.palexdev.materialfx.controls.MFXListView;
import io.github.palexdev.materialfx.controls.MFXTextField;
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
    private MFXTextField txtSearch;
    @FXML
    private MFXButton btnUpdatePersonal;
    @FXML
    private MFXButton btnCreateCustomer;
    @FXML
    private MFXButton btnDeleteCustomer;
    @FXML
    private MFXButton btnUpdateCustomer;
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
    private Worker selectedWorker = new Worker(-1);
    private User selectedUser = new User(-1);

    private boolean aproveUsers = false;
    private boolean aprovingUsers = false;

    public void setUp() {
        try {
            workerModel = new WorkerModel();
            userModel = new UserModel();
            listPersonal.setItems(workerModel.getAllWorkers());

            cclPersonalName.setCellValueFactory(new PropertyValueFactory<>("name"));
            cclPersonalRole.setCellValueFactory(new PropertyValueFactory<>("role"));
            aprovingUsers = false;
            aproveUsers = false;

            List<User> pendingUsers = new ArrayList<>();
            ObservableList<User> approvedUsers = FXCollections.observableArrayList();
            for (User user : userModel.getAllUsers()) {
                if (user.getPending() == 1) {
                    pendingUsers.add(user);
                } else {
                    approvedUsers.add(user);
                }
            }
            listUser.setItems(approvedUsers);

            cclUserName.setCellValueFactory(new PropertyValueFactory<>("name"));
            cclUserLastname.setCellValueFactory(new PropertyValueFactory<>("lastname"));

            // Add listener to search field
            txtSearch.textProperty().addListener((observable, oldValue, newValue) -> {
                handleSearch();
            });

            if (!pendingUsers.isEmpty()) {
                btnUpdateCustomer.setText("Waiting for approval");
                aproveUsers = true;
            }

        } catch (Exception e) {
            e.printStackTrace(); // Handle exception appropriately
        }
    }
    private void checkIfThereIsMorePendingUsers() {
        ObservableList<User> pendingUsers = FXCollections.observableArrayList();
        for (User user : userModel.getAllUsers()) {
            if (user.getPending() == 1) {
                pendingUsers.add(user);
            }
        }
        if (pendingUsers.isEmpty()) {
            btnDeleteCustomer.setText("Delete Customer");
            btnUpdateCustomer.setText("Update Customer");
            btnCreateCustomer.setText("Create Customers");
            aproveUsers = false;
            aprovingUsers = false;
            setUp();
        }
        if (!pendingUsers.isEmpty()) {
            listUser.setItems(pendingUsers);

            cclUserName.setCellValueFactory(new PropertyValueFactory<>("name"));
            cclUserLastname.setCellValueFactory(new PropertyValueFactory<>("lastname"));
        }

    }

    private void handleSearch() {
        String query = txtSearch.getText().toLowerCase().trim();

        // Filter users
        ObservableList<User> filteredUsers = userModel.getAllUsers().filtered(user ->
                user.getName().toLowerCase().contains(query) ||
                        user.getLastname().toLowerCase().contains(query)
        );

        // Filter workers
        ObservableList<Worker> filteredWorkers = workerModel.getAllWorkers().filtered(worker ->
                worker.getName().toLowerCase().contains(query) ||
                        worker.getRole().toLowerCase().contains(query)
        );

        // Update table views with filtered results
        listUser.setItems(filteredUsers);
        listPersonal.setItems(filteredWorkers);
    }
    @FXML
    private void handleDeletePersonal(ActionEvent actionEvent) {
        try {
            workerModel.deleteWorker(selectedWorker);
            selectedWorker = new Worker(-1);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @FXML
    private void handleUpdatePersonal(ActionEvent actionEvent) throws IOException {
        if (selectedWorker.getId() == -1) {
            btnUpdatePersonal.setText("Select a Personal");
        }
        else {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/CreateUpdatePersonal.fxml"));
            Parent secondWindow = loader.load();
            Stage newStage = new Stage();
            newStage.setTitle("Update Personal");
            Scene scene = new Scene(secondWindow);
            CreateUpdatePersonalController createUpdatePersonalController = loader.getController();
            createUpdatePersonalController.updatePersonal(selectedWorker);

            newStage.setScene(scene);
            newStage.showAndWait();
            selectedWorker = new Worker(-1);
            setUp();
        }
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
        if (aprovingUsers == false) {
            try {
                userModel.deleteUser(selectedUser);
                selectedUser = new User(-1);
                setUp();
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
        else if (aprovingUsers == true) {
            checkIfThereIsMorePendingUsers();
            setUp();
        }
    }

    @FXML
    private void handleUpdateCustomer(ActionEvent actionEvent) throws IOException {
        if (aprovingUsers == false) {
            if (aproveUsers == true) {

                checkIfThereIsMorePendingUsers();

                btnCreateCustomer.setText("Approve");
                btnUpdateCustomer.setText("Remove");
                btnDeleteCustomer.setText("Go back");
                aprovingUsers = true;


            }
        }
        if (aprovingUsers == true) {
            try {
                userModel.deleteUser(selectedUser);
                checkIfThereIsMorePendingUsers();
                selectedUser = new User(-1);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
        else {
            if (selectedUser.getId() == -1) {
                btnUpdateCustomer.setText("Select a Customer");
            } else {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/CreateUpdateUsers.fxml"));
                Parent secondWindow = loader.load();
                Stage newStage = new Stage();
                newStage.setTitle("Update User");
                Scene scene = new Scene(secondWindow);
                CreateUpdateUsersController controller = loader.getController();
                controller.updateUser(selectedUser);

                newStage.setScene(scene);
                newStage.showAndWait();
                selectedUser = new User(-1);
                setUp();
            }
        }
    }

    @FXML
    private void handleCreateCustomer(ActionEvent actionEvent) throws IOException {
        if (aprovingUsers = false) {
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
        else if (aprovingUsers = true) {
            try {
                selectedUser.setPending(2);

                userModel.updateUser(selectedUser);
                checkIfThereIsMorePendingUsers();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
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
        System.out.println(selectedUser);
        if (aprovingUsers == false || aproveUsers == false) {
            btnUpdateCustomer.setText("Update Customer");
            aproveUsers = false;
        }

    }

    @FXML
    private void handleSelectPersonal(MouseEvent mouseEvent) {
        selectedWorker = (Worker) listPersonal.getSelectionModel().getSelectedItem();
        btnUpdatePersonal.setText("Update Personal");
    }
}
