package easvbar.gui.controller;

import easvbar.be.Event;
import easvbar.be.Ticket;
import easvbar.gui.helperclases.ShowImageClass;
import easvbar.gui.model.TicketModel;
import javafx.embed.swing.SwingFXUtils;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;

import javax.imageio.ImageIO;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

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
    private ShowImageClass showImageClass = new ShowImageClass();
    private Event ticketEvent = new Event();

    private void setUp() {

    }
    public void setTicketAndEvent(Ticket ticket,Event event) {
        this.tickerSold = ticket;
        this.ticketEvent = event;
    }

    // Method to fill ticket information and generate images
    public void fillTicketInformationAndGenerateImages(Event event, String userName,
                                                       String userLastName, Ticket ticket) throws Exception {

        // Fill label information
        txtEventName.setText(event.getName());
        txtEventLocation.setText(event.getLocation());
        txtEventDate.setText(event.getDate());
        txtEventStart.setText(event.getEventStart());
        txtEventEnd.setText(event.getEventEnd());
        txtUserName.setText(userName);
        txtUserLastName.setText(userLastName);

        BackgroundSize backgroundSize = new BackgroundSize(1.0, 1.0, true, true, true, false);
        BackgroundImage backgroundImage = new BackgroundImage(showImageClass.showImage(ticketEvent.getEventImage()),
                BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT,
                BackgroundPosition.DEFAULT, backgroundSize);

        Background backgroundimage = new Background(backgroundImage);
        background.setBackground(backgroundimage);


        // Generate QR code and barcode
        TicketModel ticketModel = new TicketModel();

        // Assuming tickerSold contains the ticket information
        String qrCode = ticketModel.generateQRCode(tickerSold, ticketEvent, userName, userLastName);
        String barcode = ticketModel.generateBarcode(tickerSold, ticketEvent, userName, userLastName);

        // Convert QR code string to image and set it to ImageView
        ImageQRCode.setImage(ticketModel.convertBase64ToImageQR(qrCode));

        // Convert barcode string to image and set it to ImageView
        imageBarcode.setImage(ticketModel.convertBase64ToImageBC(barcode));

        // Save ticket image to folder
        saveTicketImage(event.getName(), userName, userLastName);
        Ticket updateTicket = new Ticket(ticket.getTicketId(), qrCode,barcode);
        ticketModel.updateTicket(updateTicket);
    }

    // Method to save ticket image to a folder
    private void saveTicketImage(String eventName, String userFirstName, String userLastName) throws IOException {
        try {
            // Generate unique file name
            String fileName = eventName + "_" + userFirstName + "_" + userLastName + ".png";

            // Get the directory to save the image
            Path directory = Paths.get("resources/ticketsSold");

            // Generate unique file path
            Path filePath = generateUniqueFilePath(directory, fileName);

            // Convert AnchorPane to image and save
            File ticketFile = filePath.toFile();
            ImageIO.write(SwingFXUtils.fromFXImage(background.snapshot(null, null), null), "png", ticketFile);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private Path generateUniqueFilePath(Path directory, String fileName) throws IOException {
        // Check if the file already exists in the directory
        Path filePath = directory.resolve(fileName);
        if (!Files.exists(filePath)) {
            return filePath; // File does not exist, return the original file path
        }

        // If the file already exists, append a number to the file name until a unique name is found
        int count = 1;
        String baseFileName = fileName.substring(0, fileName.lastIndexOf('.'));
        String extension = fileName.substring(fileName.lastIndexOf('.'));
        while (true) {
            String numberedFileName = baseFileName + "_" + count + extension;
            Path numberedFilePath = directory.resolve(numberedFileName);
            if (!Files.exists(numberedFilePath)) {
                return numberedFilePath; // Found a unique file path
            }
            count++;
        }
    }

    public void fillTicketInformationAndGenerateImagesStandalone(Ticket ticket, Event event) throws Exception {
        // Fill label information
        txtEventName.setText("");
        txtEventLocation.setText("");
        txtEventDate.setText("");
        txtEventStart.setText("");
        txtEventEnd.setText("");
        txtUserName.setText("");
        txtUserLastName.setText("");

        BackgroundSize backgroundSize = new BackgroundSize(1.0, 1.0, true, true, true, false);
        BackgroundImage backgroundImage = new BackgroundImage(showImageClass.showImage(ticketEvent.getEventImage()),
                BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT,
                BackgroundPosition.DEFAULT, backgroundSize);

        Background backgroundimage = new Background(backgroundImage);
        background.setBackground(backgroundimage);


        // Generate QR code and barcode
        TicketModel ticketModel = new TicketModel();

        // Assuming tickerSold contains the ticket information
        String qrCode = ticketModel.generateQRCodeStandalone(tickerSold, ticketEvent);
        String barcode = ticketModel.generateBarcodeStandalone(tickerSold, ticketEvent);

        // Convert QR code string to image and set it to ImageView
        ImageQRCode.setImage(ticketModel.convertBase64ToImageQR(qrCode));

        // Convert barcode string to image and set it to ImageView
        imageBarcode.setImage(ticketModel.convertBase64ToImageBC(barcode));

        // Save ticket image to folder
        saveTicketImage(ticket, event);
        Ticket updateTicket = new Ticket(ticket.getTicketId(), qrCode,barcode);
        ticketModel.updateTicket(updateTicket);
    }

    private void saveTicketImage(Ticket ticket, Event event) {
        try {
            // Generate unique file name
            String fileName = event.getName() + "_" + ticket.getBeerTicket() + "_" + ticket.getVipTicket()+ "_" + ticket.getFirstRow()+ "_" + ticket.getFoodTicket() + ".png";

            // Get the directory to save the image
            Path directory = Paths.get("resources/standalonetickets");

            // Generate unique file path
            Path filePath = generateUniqueFilePath(directory, fileName);

            // Convert AnchorPane to image and save
            File ticketFile = filePath.toFile();
            ImageIO.write(SwingFXUtils.fromFXImage(background.snapshot(null, null), null), "png", ticketFile);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
