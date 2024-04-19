package easvbar.gui.controller;

import easvbar.be.Event;
import easvbar.be.Ticket;
import easvbar.be.User;
import easvbar.bll.TicketManager;
import easvbar.gui.model.TicketModel;
import easvbar.gui.model.UserModel;
import io.github.palexdev.materialfx.controls.MFXButton;
import io.github.palexdev.materialfx.controls.MFXCheckbox;
import io.github.palexdev.materialfx.controls.MFXTextField;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ListView;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.Objects;
import java.util.ResourceBundle;

public class TicketController extends BaseController implements Initializable {
    @FXML
    private MFXButton btnCreateStandalone;
    @FXML
    private ListView listUsers;
    @FXML
    private MFXTextField txtUserName;
    @FXML
    private MFXTextField txtUserLastName;
    @FXML
    private MFXTextField txtEventStart;
    @FXML
    private MFXTextField txtEventEnd;
    @FXML
    private MFXTextField txtEventLocation;
    @FXML
    private MFXTextField txtEventDate;
    private TicketManager ticketManager;
    private TicketModel ticketModel;
    private UserModel userModel = new UserModel();
    private Event selectedEvent = new Event();
    @FXML
    private MFXButton createTicket,cancelTicket;
    @FXML
    private MFXCheckbox vipTicket,foodTicket,beerTicket,firstRow;

    public TicketController() throws Exception{
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
    }

    @Override
    public void setup() throws Exception {
        try {
            ticketManager = new TicketManager();
            ticketModel = new TicketModel();
            txtEventDate.setText(selectedEvent.getDate());
            txtEventEnd.setText(selectedEvent.getEventEnd());
            txtEventLocation.setText(selectedEvent.getLocation());
            txtEventStart.setText(selectedEvent.getEventStart());

            listUsers.setItems(userModel.getAllUsers());

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    public void setEvent(Event event) {
        this.selectedEvent = event;
    }

    @FXML
    private void handleCreateTicket(javafx.event.ActionEvent actionEvent) throws Exception {
        if (!txtUserName.getText().isEmpty() && !txtUserLastName.getText().isEmpty()) {
            boolean vipTicketSelected = vipTicket.isSelected();
            boolean foodTicketSelected = foodTicket.isSelected();
            boolean beerTicketSelected = beerTicket.isSelected();
            boolean firstRowSelected = firstRow.isSelected();

            Stage stage = (Stage) createTicket.getScene().getWindow();
            stage.close();
            Ticket newTicket = new Ticket(-1, vipTicketSelected, foodTicketSelected,
                    beerTicketSelected, firstRowSelected, selectedEvent.getId());
            ObservableList<User> allUsers = FXCollections.observableArrayList();
            allUsers.addAll(userModel.getAllUsers());
            User newUser = new User();
            newUser.setName(txtUserName.getText());
            newUser.setLastname(txtUserLastName.getText());
            newUser.setPending(1);

            boolean userExists = false;

            for (User user : allUsers) {
                if (Objects.equals(user.getName(), newUser.getName()) && Objects.equals(user.getLastname(), newUser.getLastname())) {
                    userExists = true; // User with same name and last name found
                    break; // Exit the loop, no need to check further
                }
            }

            if (!userExists) {
                userModel.createUser(newUser); // Add the new user only if no user with same name and last name exists
            }

            try {
                Ticket ticket = ticketModel.createTicketWithReturn(newTicket);
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/TicketSeller.fxml"));
                Parent secondWindow = loader.load();
                Stage newStage = new Stage();
                Scene scene = new Scene(secondWindow);
                TicketSellController controller = loader.getController();
                controller.setTicketAndEvent(ticket, selectedEvent);
                controller.fillTicketInformationAndGenerateImages(selectedEvent, txtUserName.getText(),
                        txtUserLastName.getText(), ticket);

                newStage.setScene(scene);
            } catch (Exception e) {
                e.printStackTrace();
            }


        } else {
            txtUserName.setPromptText("Please fill in a Customer First Name");
            txtUserLastName.setPromptText("Please fill in a Customer Last Name");
        }
    }

    @FXML
    private void handleCancelTicket() {
        Stage stage = (Stage) cancelTicket.getScene().getWindow();
        stage.close();
    }

    @FXML
    private void handleSelectedUser(MouseEvent mouseEvent) {
        User user = (User) listUsers.getSelectionModel().getSelectedItem();
        txtUserName.setText(user.getName());
        txtUserLastName.setText(user.getLastname());
    }

    @FXML
    private void handleCreateStandaloneTicket(ActionEvent actionEvent) {
        boolean vipTicketSelected = vipTicket.isSelected();
        boolean foodTicketSelected = foodTicket.isSelected();
        boolean beerTicketSelected = beerTicket.isSelected();
        boolean firstRowSelected = firstRow.isSelected();

        Stage stage = (Stage) btnCreateStandalone.getScene().getWindow();
        stage.close();
        Ticket newTicket = new Ticket(-1, vipTicketSelected, foodTicketSelected,
                beerTicketSelected, firstRowSelected, selectedEvent.getId());


        try {
            Ticket ticket = ticketModel.createTicketWithReturn(newTicket);
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/TicketSeller.fxml"));
            Parent secondWindow = loader.load();
            Stage newStage = new Stage();
            Scene scene = new Scene(secondWindow);
            TicketSellController controller = loader.getController();
            controller.setTicketAndEvent(ticket, selectedEvent);
            controller.fillTicketInformationAndGenerateImagesStandalone(ticket,selectedEvent);

            newStage.setScene(scene);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}