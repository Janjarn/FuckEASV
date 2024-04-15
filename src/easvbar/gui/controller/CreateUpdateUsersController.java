package easvbar.gui.controller;

import easvbar.be.User;
import easvbar.gui.model.UserModel;
import io.github.palexdev.materialfx.controls.MFXButton;
import io.github.palexdev.materialfx.controls.MFXTextField;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;

import java.io.IOException;

public class CreateUpdateUsersController {
    @FXML
    private MFXTextField txtName;
    @FXML
    private MFXTextField txtLastname;
    @FXML
    private MFXButton btnCreateUpdate;
    @FXML
    private MFXButton btnCancel;

    private boolean update = false;

    private User selectedUser = new User();

    public void createUser() {
        btnCreateUpdate.setText("Create");
    }
    public void updateUser(User user) {
        btnCreateUpdate.setText("Update");
        txtName.setText(user.getName());
        txtLastname.setText(user.getLastname());
        selectedUser = user;
        update = true;
    }

    protected void displayError(Throwable t) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("errorText");
        alert.setHeaderText(t.getMessage());
        alert.showAndWait();
    }

    private Boolean checkIfAbleToCreateOrUpdate() {
        if (txtName.getText().isEmpty() || txtLastname.getText().isEmpty()) {
            return false;
        } else {
            return true;
        }
    }
    private void missingInformation() {
        if (txtName.getText().isEmpty()) {
            txtName.setPromptText("Please fill in a name");
        }
        if (txtLastname.getText().isEmpty()) {
            txtLastname.setPromptText("Please fill in a lastname");
        }
    }
    @FXML
    private void handleCreateUpdate(ActionEvent actionEvent) throws Exception {
        UserModel userModel = new UserModel();
        if (checkIfAbleToCreateOrUpdate() == true) {
            if (update == false) {

                User user = new User(-1, txtName.getText(), txtLastname.getText(), 2);
                try {
                    userModel.createUser(user);
                } catch (Exception e) {
                    displayError(e);
                    e.printStackTrace();
                } finally {
                    btnCreateUpdate.getScene().getWindow().hide();
                }
            }
            if (update == true) {
                try {
                    selectedUser.setName(txtName.getText());
                    selectedUser.setLastname(txtLastname.getText());
                    selectedUser.setPending(2);

                    userModel.updateUser(selectedUser);
                } catch (Exception e) {
                    displayError(e);
                    e.printStackTrace();
                } finally {
                    btnCreateUpdate.getScene().getWindow().hide();
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
