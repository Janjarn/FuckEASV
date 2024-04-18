package easvbar.gui.controller;

import easvbar.be.User;
import easvbar.be.Worker;
import easvbar.gui.helperclases.ShowImageClass;
import easvbar.gui.model.UserModel;
import easvbar.gui.model.WorkerModel;
import io.github.palexdev.materialfx.controls.MFXButton;
import io.github.palexdev.materialfx.controls.MFXTextField;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class UserspageAdminController {
    @FXML
    private ImageView profileImage;
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
    private TableView listPersonal;
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
    private Worker operator = new Worker();
    private String createCustomer = "Create Customer";
    private String updateCustomer = "Update Customer";
    private String deleteCustomer = "Delete Customer";
    private String pendingCreate = "Approve";
    private String pendingUpdate = "Remove";
    private String pendingDelete = "Go Back";
    private String waiting = "Waiting for approval";
    private String selectCustomer = "Select a Customer";

    public void setUp() {
        try {
            btnLogo.getStyleClass().add("logo");
            btnLogo.setText("");
            workerModel = new WorkerModel();
            userModel = new UserModel();
            listPersonal.setItems(workerModel.getAllWorkers());

            cclPersonalName.setCellValueFactory(new PropertyValueFactory<>("name"));
            cclPersonalRole.setCellValueFactory(new PropertyValueFactory<>("role"));
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
                btnUpdateCustomer.setText(waiting);
            }

        } catch (Exception e) {
            e.printStackTrace(); // Handle exception appropriately
        }
    }
    public void setOperator(Worker operator) {
        ShowImageClass showImageClass = new ShowImageClass();
        this.operator = operator;
        btnProfile.setText(operator.getName());
        profileImage.setImage(showImageClass.showImage(operator.getPicture()));
    }
    private void checkIfThereIsMorePendingUsers() {
        ObservableList<User> pendingUsers = FXCollections.observableArrayList();
        for (User user : userModel.getAllUsers()) {
            if (user.getPending() == 1) {
                pendingUsers.add(user);
            }
        }
        if (pendingUsers.isEmpty()) {
            btnDeleteCustomer.setText(deleteCustomer);
            btnUpdateCustomer.setText(updateCustomer);
            btnCreateCustomer.setText(createCustomer);
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
        if (btnDeleteCustomer.getText().equals(deleteCustomer)) {
            try {
                userModel.deleteUser(selectedUser);
                selectedUser = new User(-1);
                setUp();
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
        if (btnDeleteCustomer.getText().equals(pendingDelete)) {
            checkIfThereIsMorePendingUsers();
            setUp();
        }
    }

    @FXML
    private void handleUpdateCustomer(ActionEvent actionEvent) throws IOException {
        if (btnUpdateCustomer.getText().equals(waiting)) {
                checkIfThereIsMorePendingUsers();

                btnCreateCustomer.setText(pendingCreate);
                btnUpdateCustomer.setText(pendingUpdate);
                btnDeleteCustomer.setText(pendingDelete);
        }
        if (btnUpdateCustomer.equals(pendingUpdate)) {
            try {
                userModel.deleteUser(selectedUser);
                checkIfThereIsMorePendingUsers();
                selectedUser = new User(-1);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
        if (selectedUser.getId() == -1) {
            btnUpdateCustomer.setText("Select a Customer");
        }
        if (btnUpdateCustomer.getText().equals(updateCustomer)){
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

    @FXML
    private void handleCreateCustomer(ActionEvent actionEvent) throws IOException {
        if (btnCreateCustomer.getText().equals(createCustomer)) {
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
        if (btnCreateCustomer.getText().equals(pendingCreate)) {
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
    private void handleProfile(ActionEvent actionEvent) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/UpdateProfile.fxml"));
        Parent secondWindow = loader.load();
        Stage newStage = new Stage();
        newStage.setTitle(operator.getName() + " | Profile");
        Scene scene = new Scene(secondWindow);
        UpdateProfileController controller = loader.getController();
        controller.setOperator(operator);
        newStage.setScene(scene);
        newStage.showAndWait();
        setUp();
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
        homepageAdminController.setOperator(operator);
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

        homepageAdminController.setOperator(operator);
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
        if (btnUpdateCustomer.getText().equals(waiting) || btnUpdateCustomer.getText().equals(selectCustomer)) {
            btnUpdateCustomer.setText(updateCustomer);
        }

    }

    @FXML
    private void handleSelectPersonal(MouseEvent mouseEvent) {
        selectedWorker = (Worker) listPersonal.getSelectionModel().getSelectedItem();
        btnUpdatePersonal.setText("Update Personal");
    }
}
