package easvbar.gui.controller;

import easvbar.be.Event;
import easvbar.be.Worker;
import easvbar.gui.helperclases.ShowImageClass;
import easvbar.gui.model.EventModel;
import easvbar.gui.model.TicketModel;
import io.github.palexdev.materialfx.controls.MFXButton;
import io.github.palexdev.materialfx.controls.MFXTextField;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class KoordinatorPageController extends BaseController implements Initializable {

    @FXML
    private VBox vboxEventInfo;
    @FXML
    private ImageView profileImage;
    @FXML
    private MFXTextField txtSearch;
    @FXML
    private MFXButton btnLogo;
    @FXML
    private MenuButton btnProfile;
    @FXML
    private BorderPane background;
    @FXML
    private Button btnUpdateEvent;
    @FXML
    private TableColumn tbwcName;
    @FXML
    private TableColumn tbwcDate;
    @FXML
    private TableColumn tbwcStart;
    @FXML
    private TableColumn tbwcEnd;
    @FXML
    private TableColumn tbwcLocation;
    @FXML
    private TableColumn tbwcCreateBy;
    @FXML
    private TableView tableViewEvents;
    @FXML
    private Button btnLogout;
    @FXML
    private Button btnCreateEvent;
    private EventModel eventModel;
    private TicketModel ticketModel;
    private ShowImageClass showImageClass;
    private Worker operator = new Worker();

    public KoordinatorPageController(){
        try {
            showImageClass = new ShowImageClass();
            eventModel = new EventModel();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
    }

    @Override
    public void setup() throws Exception {
        try {
            btnLogo.getStyleClass().add("logo");
            btnLogo.setText("");

            tableViewEvents.setItems(eventModel.getAllEvents());

            tbwcName.setCellValueFactory(new PropertyValueFactory<>("name"));
            tbwcDate.setCellValueFactory(new PropertyValueFactory<>("date"));
            tbwcLocation.setCellValueFactory(new PropertyValueFactory<>("location"));
            tbwcEnd.setCellValueFactory(new PropertyValueFactory<>("eventEnd"));
            tbwcStart.setCellValueFactory(new PropertyValueFactory<>("eventStart"));
            tbwcCreateBy.setCellValueFactory(new PropertyValueFactory<>("createdBy"));

            // Add listener to search field
            txtSearch.textProperty().addListener((observable, oldValue, newValue) -> {
                handleSearch();
            });
            background.getStyleClass().add("background");
            btnCreateEvent.setText("Create Event");
            vboxEventInfo.getChildren().clear();

        } catch (Exception e) {
            e.printStackTrace(); // Handle exception appropriately
        }
    }

    private void handleSearch() {
        String query = txtSearch.getText().toLowerCase().trim();

        // Filter users
        ObservableList<Event> filteredEvents = eventModel.getAllEvents().filtered(event ->
                event.getName().toLowerCase().contains(query) ||
                event.getLocation().toLowerCase().contains(query) ||
                event.getEventEnd().toLowerCase().contains(query) ||
                event.getEventStart().toLowerCase().contains(query) ||
                event.getDate().toLowerCase().contains(query) ||
                event.getCreatedBy().toLowerCase().contains(query)
        );

        // Update table views with filtered results
        tableViewEvents.setItems(filteredEvents);
    }

    public void setOperator(Worker operator) {
        ShowImageClass showImageClass = new ShowImageClass();
        this.operator = operator;
        btnProfile.setText(operator.getName());
        profileImage.setImage(showImageClass.showImage(operator.getPicture()));
    }


    @FXML
    private void handleCreateEventWindow(ActionEvent actionEvent) throws Exception {

        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/EventMaker.fxml"));
        Parent secondWindow = loader.load();
        Stage newStage = new Stage();
        newStage.setTitle("Event Maker");
        Scene scene = new Scene(secondWindow);

        EventMakerController eventMakerController = loader.getController();
        eventMakerController.setOperator(operator);
        eventMakerController.setup();

        newStage.setScene(scene);
        newStage.initOwner(((Node) actionEvent.getSource()).getScene().getWindow());
        newStage.showAndWait();
        setup();

    }

    @FXML
    private void handleUpdateEvent(ActionEvent actionEvent) throws Exception {
        Event updateEvent = (Event) tableViewEvents.getSelectionModel().getSelectedItem();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/EventMaker.fxml"));
        Parent secondWindow = loader.load();
        Stage newStage = new Stage();
        newStage.setTitle("Event Maker");
        Scene scene = new Scene(secondWindow);

        EventMakerController eventMakerController = loader.getController();
        eventMakerController.updateEvent(updateEvent);

        newStage.setScene(scene);
        newStage.initOwner(((Node) actionEvent.getSource()).getScene().getWindow());
        newStage.showAndWait();
        setup();
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
    private void handleProfile(ActionEvent actionEvent) throws Exception {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/UpdateProfile.fxml"));
        Parent secondWindow = loader.load();
        Stage newStage = new Stage();
        newStage.setTitle(operator.getName() + " | Profile");
        Scene scene = new Scene(secondWindow);
        UpdateProfileController controller = loader.getController();
        controller.setOperator(operator);
        newStage.setScene(scene);
        newStage.showAndWait();
        setOperator(operator);
    }

    @FXML
    private void handleDeleteEvent(ActionEvent actionEvent) {
        Event event = (Event) tableViewEvents.getSelectionModel().getSelectedItem();
        try {
            eventModel.deleteEvent(event);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

    }
    @FXML
    private void handleSelectedEvent(MouseEvent mouseEvent) {
        vboxEventInfo.getChildren().clear();
        Event selectedEvent = (Event) tableViewEvents.getSelectionModel().getSelectedItem();
        background.setBackground(showImageClass.setBackGroundImage(selectedEvent.getEventImage()));

        Button addCoordinatorButton = new Button("Add A Coordinator");
        Button addTicket = new Button("Add A Ticket");
        Button sellTicket = new Button("Sell A Ticket");

        vboxEventInfo.getChildren().addAll(addCoordinatorButton, addTicket , sellTicket);
        for (Label label : eventModel.handleSelectedEvent(selectedEvent)) {
            vboxEventInfo.getChildren().addAll(label);
        }

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

            }  catch (Exception e) {
                throw new RuntimeException(e);
            }
        });

        addTicket.setOnAction(event -> {
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/TicketMaker.fxml"));
                Parent secondWindow = loader.load();
                Stage newStage = new Stage();
                newStage.setTitle("Create a ticket for | " + selectedEvent.getName());
                Scene scene = new Scene(secondWindow);
                TicketController controller = loader.getController();
                controller.setEvent(selectedEvent);
                controller.setup();
                newStage.setScene(scene);
                newStage.showAndWait();

            }  catch (Exception e) {
                throw new RuntimeException(e);
            }
        });

        sellTicket.setOnAction(event -> {
        });

    }

    @FXML
    private void handleLogo(ActionEvent actionEvent) throws IOException {
        setOperator(operator);
    }

    @FXML
    private void handleCreateTicket(ActionEvent actionEvent) throws IOException {
        Stage stage = new Stage();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/TicketMaker.fxml"));
        Parent popupWindow = loader.load();

        TicketController controller = loader.getController();
        controller.setTicketModel(ticketModel);
        try {
            controller.setup();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        Stage PopupWindow = new Stage();
        PopupWindow.setTitle("Create ticket");
        PopupWindow.initModality(Modality.APPLICATION_MODAL);
        PopupWindow.initOwner(((Node) actionEvent.getSource()).getScene().getWindow());

        PopupWindow.setScene(new Scene(popupWindow));
        PopupWindow.showAndWait();
    }
}