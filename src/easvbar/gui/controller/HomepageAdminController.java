package easvbar.gui.controller;

import easvbar.be.Event;
import easvbar.be.Worker;
import easvbar.gui.helperclases.ShowImageClass;
import easvbar.gui.model.EventModel;
import easvbar.gui.model.SharedEventModel;
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
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.MenuButton;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.List;

public class HomepageAdminController {
    @FXML
    private VBox vboxSelectedEventInfo;
    @FXML
    private ImageView profileImage;
    @FXML
    private MenuButton btnProfile;
    @FXML
    private MFXTextField txtSearch;
    @FXML
    private ImageView imageViewShowImage;
    @FXML
    private MFXButton btnUsers;
    @FXML
    private MFXButton btnLogo;
    @FXML
    private MFXTextField txtFieldEventInfo;
    @FXML
    private MFXListView listViewFeaturedEvent;
    @FXML
    private ListView listViewUpcomingEvents;

    private EventModel eventModel = new EventModel();
    private SharedEventModel sharedEventModel = new SharedEventModel();
    private ShowImageClass showImageClass = new ShowImageClass();
    private Worker operator = new Worker();
    private Event selectedEvent = new Event();

    public HomepageAdminController() throws Exception {
    }

    public void setUp() {
        try {
            btnLogo.getStyleClass().add("logo");
            btnLogo.setText("");
            // Get all events
            List<Event> allEvents = eventModel.getAllEvents();

            // Decide which events are featured (for demonstration, let's just use the first event)
            ObservableList<Event> featuredEvents = FXCollections.observableArrayList();
            if (!allEvents.isEmpty()) {
                featuredEvents.add(allEvents.get(0));
            }

            Event event = featuredEvents.get(0);
            imageViewShowImage.setImage(showImageClass.showImage(event.getEventImage()));
            listViewFeaturedEvent.setItems(featuredEvents);

            // For upcoming events, let's display all events except the featured one
            ObservableList<Event> upcomingEvents = FXCollections.observableArrayList(allEvents);
            upcomingEvents.remove(0); // Remove the featured event
            listViewUpcomingEvents.setItems(upcomingEvents);

            // Add listener to search field
            txtSearch.textProperty().addListener((observable, oldValue, newValue) -> {
                handleSearch();
            });
            vboxSelectedEventInfo.getChildren().clear();
        } catch (Exception e) {
            e.printStackTrace(); // Handle exception appropriately
        }
    }
    private void handleSearch() {
        String query = txtSearch.getText().toLowerCase().trim();

        // Filter Events
        ObservableList<Event> filteredEvents = eventModel.getAllEvents().filtered(event ->
                event.getName().toLowerCase().contains(query) ||
                        event.getLocation().toLowerCase().contains(query)
        );

        // Update table views with filtered results
        listViewUpcomingEvents.setItems(filteredEvents);
    }
    @FXML
    private void handleLogo(ActionEvent actionEvent) {
        setUp();
    }
    @FXML
    private void handleUsers(ActionEvent actionEvent) throws IOException {

        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/UserspageAdmin.fxml"));
        Parent secondWindow = loader.load();
        Stage newStage = new Stage();
        newStage.setTitle("Users Page");
        Scene scene = new Scene(secondWindow);
        UserspageAdminController userspageAdminController = loader.getController();
        userspageAdminController.setUp();
        userspageAdminController.setOperator(operator);
        newStage.setScene(scene);

        // Check if the current window is maximized
        Stage currentStage = (Stage) btnUsers.getScene().getWindow();
        if (currentStage.isMaximized()) {
            newStage.setMaximized(true); // Maximize the new window
        }

        newStage.show();
        currentStage.hide();
    }
    public void setOperator(Worker operator) {
        this.operator = operator;
        btnProfile.setText(operator.getName());
        profileImage.setImage(showImageClass.showImage(operator.getPicture()));
    }
    @FXML
    private void handleAdminPanel(ActionEvent actionEvent) {
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
    private void handleUpComingEvents(MouseEvent mouseEvent) {
        selectedEvent = (Event) listViewUpcomingEvents.getSelectionModel().getSelectedItem();

        Button deleteButton = new Button("Delete Event");
        Button addCoordinatorButton = new Button("Add A Coordinator");

        vboxSelectedEventInfo.getChildren().addAll(deleteButton,addCoordinatorButton);
        for (Label label : eventModel.handleSelectedEvent(selectedEvent)) {
            vboxSelectedEventInfo.getChildren().addAll(label);
        }

        // Handle button actions if needed
        deleteButton.setOnAction(event -> {
            try {
                eventModel.deleteEvent(selectedEvent);
                setUp();
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        });


        addCoordinatorButton.setOnAction(event -> {
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/AddSharedEvent.fxml"));
                Parent secondWindow = loader.load();
                Stage newStage = new Stage();
                newStage.setTitle(selectedEvent.getName() + " | Invite Coordinators");
                Scene scene = new Scene(secondWindow);
                AddSharedEventController controller = loader.getController();
                controller.setUp(selectedEvent);
                newStage.setScene(scene);
                newStage.showAndWait();
                setUp();

            }  catch (Exception e) {
                throw new RuntimeException(e);
            }
        });

        if (selectedEvent.getEventImage() != null) {
            imageViewShowImage.setImage(showImageClass.showImage(selectedEvent.getEventImage()));
        }

    }
    @FXML
    private void handleFeaturedEvent(MouseEvent mouseEvent) {
        ObservableMap<?, ?> selectedMap = listViewFeaturedEvent.getSelectionModel().getSelection();
        Event events = (Event) selectedMap.get(0);
        // Create buttons
        Button deleteButton = new Button("Delete Event");
        Button addCoordinatorButton = new Button("Add A Coordinator");

        vboxSelectedEventInfo.getChildren().addAll(deleteButton,addCoordinatorButton);
        for (Label label : eventModel.handleSelectedEvent(events)) {
            vboxSelectedEventInfo.getChildren().addAll(label);
        }

        // Handle button actions if needed
        deleteButton.setOnAction(event -> {
            try {
                eventModel.deleteEvent(events);
                setUp();
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        });

        addCoordinatorButton.setOnAction(event -> {
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/AddSharedEvent.fxml"));
                Parent secondWindow = loader.load();
                Stage newStage = new Stage();
                newStage.setTitle(events.getName() + " | Invite Coordinators");
                Scene scene = new Scene(secondWindow);
                AddSharedEventController controller = loader.getController();
                controller.setUp(events);
                newStage.setScene(scene);
                newStage.showAndWait();

            }  catch (Exception e) {
                throw new RuntimeException(e);
            }
        });

        if (events.getEventImage() != null) {
            imageViewShowImage.setImage(showImageClass.showImage(events.getEventImage()));
        }
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
}
