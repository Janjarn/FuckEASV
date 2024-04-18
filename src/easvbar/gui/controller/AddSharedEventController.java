package easvbar.gui.controller;

import easvbar.be.Event;
import easvbar.be.SharedEvent;
import easvbar.be.User;
import easvbar.be.Worker;
import easvbar.gui.model.SharedEventModel;
import easvbar.gui.model.WorkerModel;
import io.github.palexdev.materialfx.controls.MFXButton;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.List;

public class AddSharedEventController {
    @FXML
    private AnchorPane background;
    @FXML
    private MFXButton btnDone;
    @FXML
    private MFXButton btnCancel;
    @FXML
    private MFXButton btnInvite;
    @FXML
    private ListView listAllCoordinators;
    @FXML
    private ListView listInvitedCoordinators;
    @FXML
    private TextField txtSearch;

    private SharedEventModel sharedEventModel;
    private WorkerModel workerModel;
    private Event selectedEvent = new Event();
    private Worker selectedWorker = new Worker();

    private ObservableList<Worker> noAccess;
    private ObservableList<Worker> hasAccess;
    private ObservableList<Worker> allWorkers;
    private ObservableList<SharedEvent> allShared;
    private List<SharedEvent> addedAccess = new ArrayList<>();

    public void setUp(Event event) {
        try {
            sharedEventModel = new SharedEventModel();
            workerModel = new WorkerModel();
            selectedEvent = event;
            noAccess = FXCollections.observableArrayList();
            hasAccess = FXCollections.observableArrayList();
            allWorkers = FXCollections.observableArrayList();
            allShared = FXCollections.observableArrayList();
            allWorkers.addAll(workerModel.getAllWorkers());
            allShared.addAll(sharedEventModel.getAllShared());

            filterWorkers(allWorkers);

            txtSearch.textProperty().addListener((observable, oldValue, newValue) -> {
                handleSearch();
            });

            listAllCoordinators.setItems(noAccess);
            listInvitedCoordinators.setItems(hasAccess);


        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private void handleSearch() {
        noAccess.clear();
        hasAccess.clear();
        String query = txtSearch.getText().toLowerCase().trim();

        // Filter users
        ObservableList<Worker> filteredWorkers = workerModel.getAllWorkers().filtered(worker ->
                worker.getName().toLowerCase().contains(query)
        );
        filterWorkers(filteredWorkers);

        listInvitedCoordinators.setItems(hasAccess);
        listAllCoordinators.setItems(noAccess);
    }
    private void filterWorkers(ObservableList<Worker> listToBeFiltered) {
        for (Worker worker : listToBeFiltered) {
            if (worker.getRoleId() == 2) {
                // Check if the worker already has access to the SharedEvent
                boolean alreadyHasAccess = false;
                for (SharedEvent sharedEvent : allShared) { // Assuming you have a list of SharedEvent
                    if (sharedEvent.getWorkerId() == worker.getId() && sharedEvent.getEventId() == selectedEvent.getId()) {
                        alreadyHasAccess = true;
                        break;
                    }
                }
                // Add worker to the appropriate list based on access status
                if (alreadyHasAccess == true) {
                    hasAccess.add(worker);
                } else {
                    noAccess.add(worker);
                }
            }
        }
    }

    @FXML
    private void handleCancel(ActionEvent actionEvent) throws Exception {
        for (SharedEvent sharedEvent : addedAccess) {
                sharedEventModel.deleteSharedEvent(sharedEvent);
        }
        Stage stage = (Stage) btnCancel.getScene().getWindow();
        stage.close();
    }

    @FXML
    private void handleInviteRemove(ActionEvent actionEvent) {
        if (btnInvite.getText() == "Invite") {
            SharedEvent sharedEvent = new SharedEvent(selectedEvent.getId(), selectedWorker.getId());
            try {
                sharedEventModel.createSharedEvent(sharedEvent);
                setUp(selectedEvent);
                addedAccess.add(sharedEvent);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        if (btnInvite.getText() == "Remove") {
            SharedEvent deleteSharedEvent = new SharedEvent(selectedEvent.getId(), selectedWorker.getId());
            try {
                sharedEventModel.deleteSharedEvent(deleteSharedEvent);
                setUp(selectedEvent);
                addedAccess.remove(sharedEventModel);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
    }

    @FXML
    private void handleSelectedWorker(MouseEvent mouseEvent) {

        ListView<?> sourceList = (ListView<?>) mouseEvent.getSource();

        if (sourceList == listAllCoordinators) {
            selectedWorker = (Worker) listAllCoordinators.getSelectionModel().getSelectedItem();
            btnInvite.setText("Invite");
        }
        if (sourceList == listInvitedCoordinators) {
            selectedWorker = (Worker) listInvitedCoordinators.getSelectionModel().getSelectedItem();
            btnInvite.setText("Remove");
        }
    }

    @FXML
    private void handleDone(ActionEvent actionEvent) {
        Stage stage = (Stage) btnDone.getScene().getWindow();
        stage.close();
    }
}
