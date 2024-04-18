package easvbar.gui.controller;

import easvbar.be.Event;
import easvbar.be.Ticket;
import easvbar.bll.TicketManager;
import easvbar.gui.model.TicketModel;
import io.github.palexdev.materialfx.controls.MFXButton;
import io.github.palexdev.materialfx.controls.MFXCheckbox;
import io.github.palexdev.materialfx.controls.MFXTextField;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class TicketController extends BaseController implements Initializable {
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

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    public void setEvent(Event event) {
        this.selectedEvent = event;
    }

    @FXML
    private void handleCreateTicket(javafx.event.ActionEvent actionEvent) throws Exception {
        boolean vipTicketSelected = vipTicket.isSelected();
        boolean foodTicketSelected = foodTicket.isSelected();
        boolean beerTicketSelected = beerTicket.isSelected();
        boolean firstRowSelected = firstRow.isSelected();

        Stage stage = (Stage) createTicket.getScene().getWindow();
        stage.close();
        Ticket newTicket = new Ticket(-1, vipTicketSelected, foodTicketSelected,
                beerTicketSelected, firstRowSelected);
        try {
            ticketModel.createTicket(newTicket);
        } catch (Exception e) {
            e.printStackTrace();
        }

        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/TicketSeller.fxml"));
        Parent secondWindow = loader.load();
        Stage newStage = new Stage();
        Scene scene = new Scene(secondWindow);
        TicketSellController controller = loader.getController();
        controller.setTicket(newTicket);
        controller.fillTicketInformationAndGenerateImages(selectedEvent.getName(), selectedEvent.getLocation(),
                selectedEvent.getDate(), selectedEvent.getEventStart(), selectedEvent.getEventEnd(), txtUserName.getText(),
                txtUserLastName.getText());

        newStage.setScene(scene);
        newStage.showAndWait();
    }

    public void handleCancelTicket() {
        Stage stage = (Stage) cancelTicket.getScene().getWindow();
        stage.close();
    }
}