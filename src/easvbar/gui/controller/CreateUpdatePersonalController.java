package easvbar.gui.controller;

import easvbar.be.Event;
import easvbar.be.Worker;
import easvbar.gui.model.WorkerModel;
import io.github.palexdev.materialfx.controls.MFXButton;
import io.github.palexdev.materialfx.controls.MFXTextField;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;

public class CreateUpdatePersonalController {
    @FXML
    private Label lblRole;
    @FXML
    private MFXTextField txtName;
    @FXML
    private MFXTextField txtPassword;
    @FXML
    private MFXButton btnCreateUpdate;
    @FXML
    private MFXButton btnCancel;

    private int adminOrKoordinator = 0;
    private boolean update = false;
    private Worker selectedWorker = new Worker();

    public void createPersonal() {
        btnCreateUpdate.setText("Create");
    }
    public void updatePersonal(Worker worker) {
        btnCreateUpdate.setText("Update");
        txtName.setText(worker.getName());
        txtPassword.setText(worker.getPassword());
        selectedWorker = worker;
        update = true;
    }

    protected void displayError(Throwable t) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("errorText");
        alert.setHeaderText(t.getMessage());
        alert.showAndWait();
    }

    private Boolean checkIfAbleToCreateOrUpdate() {
        if (txtName.getText().isEmpty() || txtPassword.getText().isEmpty() || adminOrKoordinator == 0) {
            return false;
        } else {
            return true;
        }
    }
    private void missingInformation() {
        if (txtName.getText().isEmpty()) {
            txtName.setPromptText("Please fill in a name");
        }
        if (txtPassword.getText().isEmpty()) {
            txtPassword.setPromptText("Please fill in a password");
        }
        if (adminOrKoordinator == 0) {
            lblRole.setText("Please select Either Admin or Koordinator");
        }
    }

    @FXML
    private void handleAdmin(ActionEvent actionEvent) {
        adminOrKoordinator = 1;
    }

    @FXML
    private void handleKoordinator(ActionEvent actionEvent) {
        adminOrKoordinator = 2;
    }


    @FXML
    private void handleCreateUpdate(ActionEvent actionEvent) throws Exception {
        WorkerModel workerModel = new WorkerModel();
        if (checkIfAbleToCreateOrUpdate() == true) {
            if (update == false) {
                if (adminOrKoordinator == 1) {
                    Worker worker = new Worker(-1, txtName.getText(), txtPassword.getText(), "Admin", 1);
                    try {
                        workerModel.createWorker(worker);
                    } catch (Exception e) {
                        displayError(e);
                        e.printStackTrace();
                    } finally {
                        btnCreateUpdate.getScene().getWindow().hide();
                    }
                }
                if (adminOrKoordinator == 2) {
                    Worker worker = new Worker(-1, txtName.getText(), txtPassword.getText(), "Koordinator", 2);
                    try {
                        workerModel.createWorker(worker);
                    } catch (Exception e) {
                        displayError(e);
                        e.printStackTrace();
                    } finally {
                        btnCreateUpdate.getScene().getWindow().hide();
                    }
                }
            }
            if (update == true) {
                if (adminOrKoordinator == 1) {
                    try {
                        selectedWorker.setName(txtName.getText());
                        selectedWorker.setPassword(txtPassword.getText());
                        selectedWorker.setRole("Admin");
                        selectedWorker.setRoleId(1);

                        workerModel.updateWorker(selectedWorker);
                    } catch (Exception e) {
                        displayError(e);
                        e.printStackTrace();
                    } finally {
                        btnCreateUpdate.getScene().getWindow().hide();
                    }
                }
                if (adminOrKoordinator == 2) {
                    try {
                        selectedWorker.setName(txtName.getText());
                        selectedWorker.setPassword(txtPassword.getText());
                        selectedWorker.setRole("Koordinator");
                        selectedWorker.setRoleId(2);

                        workerModel.updateWorker(selectedWorker);
                    } catch (Exception e) {
                        displayError(e);
                        e.printStackTrace();
                    } finally {
                        btnCreateUpdate.getScene().getWindow().hide();
                    }
                }
            }
        }
        if (checkIfAbleToCreateOrUpdate() == false){
            missingInformation();
        }
    }

    @FXML
    private void handleCancel(ActionEvent actionEvent) throws IOException {
        btnCancel.getScene().getWindow().hide();
    }
}
