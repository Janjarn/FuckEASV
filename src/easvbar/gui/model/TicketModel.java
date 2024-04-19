package easvbar.gui.model;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.WriterException;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;
import easvbar.be.Event;
import easvbar.be.Ticket;
import easvbar.bll.TicketManager;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.embed.swing.SwingFXUtils;
import javafx.scene.image.Image;
import net.sourceforge.barbecue.Barcode;
import net.sourceforge.barbecue.BarcodeFactory;
import net.sourceforge.barbecue.BarcodeImageHandler;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Base64;

public class TicketModel {
    private Ticket selectedTicket;
    private TicketManager ticketManager;
    private ObservableList<Ticket> tickets;

    public TicketModel() throws Exception {
        ticketManager = new TicketManager();
        tickets = FXCollections.observableArrayList();
        tickets.addAll(ticketManager.getAllTickets());
    }

    public ObservableList<Ticket> getObservableTickets() throws Exception {
        tickets.clear();
        tickets.addAll(ticketManager.getAllTickets());
        return tickets;
    }

    public void createTicket(Ticket ticket) throws Exception{
        Ticket t = ticketManager.createTicket(ticket);
        tickets.add(t);
    }
    public Ticket createTicketWithReturn(Ticket ticket) throws Exception{
        Ticket t = ticketManager.createTicket(ticket);
        tickets.add(t);
        return t;
    }

    public void deleteTicket(Ticket deletedTicket) throws Exception {
        ticketManager.deleteTicket(deletedTicket);
        getObservableTickets().clear();
        getObservableTickets().addAll(ticketManager.getAllTickets());
    }

    public void updateTicket(Ticket ticket) throws Exception {
        Ticket selectedTicket = new Ticket();
        selectedTicket.setTicketId(ticket.getTicketId());
        selectedTicket.setQrCode(ticket.getQrCode());
        selectedTicket.setBarcode(ticket.getBarcode());

        ticketManager.updateTicket(ticket);
    }

    public Ticket getSelectedTicket(){return selectedTicket;}

    public void setSelectedTicket(Ticket selectedTicket) {
        this.selectedTicket = selectedTicket;
    }


    public String generateQRCode(Ticket ticket, Event event, String userName,
                                 String userLastname) throws IOException {
        String qrCode = null;
        try {
            String qrData = "Ticket ID: " + ticket.getTicketId() +
                    "\nEvent ID: " + event.getId() +
                    "\nEvent Name: " + event.getName() +
                    "\nEvent Location: " + event.getLocation() +
                    "\nEvent Date: " + event.getDate() +
                    "\nUser Name: " + userName +
                    "\nUser Last Name: " + userLastname +
                    "\nVip Ticket: " + ticket.getVipTicket() +
                    "\nFirst Row: " + ticket.getFirstRow() +
                    "\nFood Ticket: " + ticket.getFoodTicket() +
                    "\nBeer Ticket: " + ticket.getBeerTicket();

            QRCodeWriter qrCodeWriter = new QRCodeWriter();
            BitMatrix bitMatrix = qrCodeWriter.encode(qrData, BarcodeFormat.QR_CODE, 200, 200);
            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            MatrixToImageWriter.writeToStream(bitMatrix, "PNG", outputStream);
            qrCode = Base64.getEncoder().encodeToString(outputStream.toByteArray());
        } catch (WriterException | IOException e) {
            e.printStackTrace();
        }
        return qrCode;
    }
    public String generateQRCodeStandalone(Ticket ticket, Event event) throws IOException {
        String qrCode = null;
        try {
            String qrData = "Ticket ID: " + ticket.getTicketId() +
                    "\nEvent ID: " + event.getId() +
                    "\nEvent Name: " + event.getName() +
                    "\nEvent Location: " + event.getLocation() +
                    "\nEvent Date: " + event.getDate() +
                    "\nVip Ticket: " + ticket.getVipTicket() +
                    "\nFirst Row: " + ticket.getFirstRow() +
                    "\nFood Ticket: " + ticket.getFoodTicket() +
                    "\nBeer Ticket: " + ticket.getBeerTicket();

            QRCodeWriter qrCodeWriter = new QRCodeWriter();
            BitMatrix bitMatrix = qrCodeWriter.encode(qrData, BarcodeFormat.QR_CODE, 200, 200);
            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            MatrixToImageWriter.writeToStream(bitMatrix, "PNG", outputStream);
            qrCode = Base64.getEncoder().encodeToString(outputStream.toByteArray());
        } catch (WriterException | IOException e) {
            e.printStackTrace();
        }
        return qrCode;
    }

    public Image convertBase64ToImageQR(String qrCodeBase64) throws IOException {
        // Decode the Base64 string to byte array
        byte[] qrCodeBytes = Base64.getDecoder().decode(qrCodeBase64);

        // Convert byte array to JavaFX Image
        ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(qrCodeBytes);
        BufferedImage bufferedImage = ImageIO.read(byteArrayInputStream);
        Image qrCodeImage = SwingFXUtils.toFXImage(bufferedImage, null);

        return qrCodeImage;
    }


    public String generateBarcode(Ticket ticket, Event event, String userName, String userLastname) throws IOException {
        String barcodeBase64 = null;
        try {
            // Construct barcode data string
            String barcodeData = "Ticket ID: " + ticket.getTicketId() +
                    "\nEvent ID: " + event.getId() +
                    "\nEvent Name: " + event.getName() +
                    "\nEvent Location: " + event.getLocation() +
                    "\nEvent Date: " + event.getDate() +
                    "\nUser Name: " + userName +
                    "\nUser Last Name: " + userLastname +
                    "\nVip Ticket: " + ticket.getVipTicket() +
                    "\nFirst Row: " + ticket.getFirstRow() +
                    "\nFood Ticket: " + ticket.getFoodTicket() +
                    "\nBeer Ticket: " + ticket.getBeerTicket();

            // Create the barcode
            Barcode barcode = BarcodeFactory.createCode128(barcodeData);

            // Scale the barcode
            barcode.setBarWidth(2);
            barcode.setBarHeight(50);

            // Convert the barcode to image
            BufferedImage bufferedImage = BarcodeImageHandler.getImage(barcode);

            // Convert the image to Base64 string
            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            ImageIO.write(bufferedImage, "png", outputStream);
            barcodeBase64 = Base64.getEncoder().encodeToString(outputStream.toByteArray());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return barcodeBase64;
    }

    public String generateBarcodeStandalone(Ticket ticket, Event event) throws IOException {
        String barcodeBase64 = null;
        try {
            // Construct barcode data string
            String barcodeData = "Ticket ID: " + ticket.getTicketId() +
                    "\nEvent ID: " + event.getId() +
                    "\nEvent Name: " + event.getName() +
                    "\nEvent Location: " + event.getLocation() +
                    "\nEvent Date: " + event.getDate() +
                    "\nVip Ticket: " + ticket.getVipTicket() +
                    "\nFirst Row: " + ticket.getFirstRow() +
                    "\nFood Ticket: " + ticket.getFoodTicket() +
                    "\nBeer Ticket: " + ticket.getBeerTicket();

            // Create the barcode
            Barcode barcode = BarcodeFactory.createCode128(barcodeData);

            // Scale the barcode
            barcode.setBarWidth(2);
            barcode.setBarHeight(50);

            // Convert the barcode to image
            BufferedImage bufferedImage = BarcodeImageHandler.getImage(barcode);

            // Convert the image to Base64 string
            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            ImageIO.write(bufferedImage, "png", outputStream);
            barcodeBase64 = Base64.getEncoder().encodeToString(outputStream.toByteArray());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return barcodeBase64;
    }

    // Convert Base64 string to JavaFX Image
    public Image convertBase64ToImageBC(String barcodeBase64) throws IOException {
        // Decode the Base64 string to byte array
        byte[] barcodeBytes = Base64.getDecoder().decode(barcodeBase64);

        // Convert byte array to JavaFX Image
        ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(barcodeBytes);
        BufferedImage bufferedImage = ImageIO.read(byteArrayInputStream);
        Image barcodeImage = SwingFXUtils.toFXImage(bufferedImage, null);

        return barcodeImage;
    }
}