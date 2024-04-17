package easvbar.gui.controller;

import easvbar.be.Ticket;
import easvbar.bll.TicketManager;
import easvbar.gui.model.TicketModel;
import io.github.palexdev.materialfx.controls.MFXButton;
import io.github.palexdev.materialfx.controls.MFXCheckbox;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class TicketController extends BaseController implements Initializable {
    private TicketManager ticketManager;
    private TicketModel ticketModel;
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
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @FXML
    private void handleCreateTicket(javafx.event.ActionEvent actionEvent){
        String qrCode = qrCodeGenerator();
        String barcode = barcodeGenerator();
        boolean vipTicketSelected = vipTicket.isSelected();
        boolean foodTicketSelected = foodTicket.isSelected();
        boolean beerTicketSelected = beerTicket.isSelected();
        boolean firstRowSelected = firstRow.isSelected();

        Stage stage = (Stage) createTicket.getScene().getWindow();
        stage.close();

        try {
            Ticket newTicket = new Ticket(-1, qrCode, barcode, vipTicketSelected, foodTicketSelected,
                    beerTicketSelected, firstRowSelected);
            ticketModel.createTicket(newTicket);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void handleCancelTicket() {
        Stage stage = (Stage) cancelTicket.getScene().getWindow();
        stage.close();
    }

    public String qrCodeGenerator() {
        return null;
    }

    public String barcodeGenerator() {
        return null;
    }
}