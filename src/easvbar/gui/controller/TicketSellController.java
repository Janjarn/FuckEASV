package easvbar.gui.controller;

import easvbar.be.Ticket;
import easvbar.gui.model.TicketModel;
import javafx.embed.swing.SwingFXUtils;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;

import javax.imageio.ImageIO;
import java.io.File;
import java.io.IOException;

public class TicketSellController {
    @FXML
    private AnchorPane background;

    @FXML
    private Label txtEventName;

    @FXML
    private Label txtEventLocation;

    @FXML
    private Label txtEventDate;

    @FXML
    private Label txtEventStart;

    @FXML
    private Label txtEventEnd;

    @FXML
    private Label txtUserName;

    @FXML
    private Label txtUserLastName;

    @FXML
    private ImageView ImageQRCode;

    @FXML
    private ImageView imageBarcode;

    private Ticket tickerSold = new Ticket();

    private void setUp() {

    }
    public void setTicket(Ticket ticket) {
        this.tickerSold = ticket;
    }

    // Method to fill ticket information and generate images
    public void fillTicketInformationAndGenerateImages(String eventName, String eventLocation,
    String eventDate, String eventStart,String eventEnd, String userName,String userLastName) throws Exception {

        // Fill label information
        txtEventName.setText(eventName);
        txtEventLocation.setText(eventLocation);
        txtEventDate.setText(eventDate);
        txtEventStart.setText(eventStart);
        txtEventEnd.setText(eventEnd);
        txtUserName.setText(userName);
        txtUserLastName.setText(userLastName);

        // Generate QR code and barcode
        TicketModel ticket = new TicketModel();
        String qrCode = ticket.generateQRCode(tickerSold);
        String barcode = ticket.generateBarcode(tickerSold);

        // Convert QR code string to image and set it to ImageView
        ImageQRCode.setImage(ticket.convertBase64ToImageQR(qrCode));

        // Convert barcode string to image and set it to ImageView
        imageBarcode.setImage(ticket.convertBase64ToImageBC(barcode));

        // Save ticket image to folder
        saveTicketImage();
    }

    // Method to save ticket image to a folder
    private void saveTicketImage() throws IOException {
        try {
            // Convert AnchorPane to image
            File ticketFile = new File("resources/ticketsSold/ticket.png");
            ImageIO.write(SwingFXUtils.fromFXImage(background.snapshot(null, null), null), "png", ticketFile);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
