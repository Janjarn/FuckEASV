package easvbar.be;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.WriterException;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Base64;

public class Ticket {
    private int ticketId;
    private String qrCode;
    private String barcode;
    private boolean vipTicket;
    private boolean foodTicket;
    private boolean beerTicket;
    private boolean firstRow;
    private int eventId;

    public Ticket(int ticketId, String qrCode, String barcode, boolean vipTicket, boolean foodTicket,
                  boolean beerTicket, boolean firstRow, int eventId) {
        this.ticketId = ticketId;
        this.qrCode = qrCode;
        this.barcode = barcode;
        this.vipTicket = vipTicket;
        this.foodTicket = foodTicket;
        this.beerTicket = beerTicket;
        this.firstRow = firstRow;
        this.eventId = eventId;
    }

    public Ticket(int ticketId, boolean vipTicket, boolean foodTicket,
                  boolean beerTicket, boolean firstRow) {
        this.ticketId = ticketId;
        this.vipTicket = vipTicket;
        this.foodTicket = foodTicket;
        this.beerTicket = beerTicket;
        this.firstRow = firstRow;
    }

    public Ticket() {
    }

    public Ticket(int ticketId, boolean vipTicket, boolean foodTicket, boolean beerTicket, boolean firstRow, int eventId) {
        this.ticketId = ticketId;
        this.vipTicket = vipTicket;
        this.foodTicket = foodTicket;
        this.beerTicket = beerTicket;
        this.firstRow = firstRow;
        this.eventId = eventId;
    }

    public Ticket(int ticketId, String qrCode, String barcode) {
        this.ticketId = ticketId;
        this.barcode = barcode;
        this.qrCode = qrCode;
    }


    public int getTicketId() {
        return ticketId;
    }

    public void setTicketId(int ticketId) {
        this.ticketId = ticketId;
    }

    public String getQrCode() {
        return qrCode;
    }

    public void setQrCode(String qrCode) {
        this.qrCode = qrCode;
    }

    public String getBarcode() {
        return barcode;
    }

    public void setBarcode(String barcode) {
        this.barcode = barcode;
    }

    public boolean getVipTicket() {
        return vipTicket;
    }

    public void setVipTicket(boolean vipTicket) {
        this.vipTicket = vipTicket;
    }

    public boolean getFoodTicket() {
        return foodTicket;
    }

    public void setFoodTicket(boolean foodTicket) {
        this.foodTicket = foodTicket;
    }

    public boolean getBeerTicket() {
        return beerTicket;
    }

    public void setBeerTicket(boolean beerTicket) {
        this.beerTicket = beerTicket;
    }

    public boolean getFirstRow() {
        return firstRow;
    }

    public void setFirstRow(boolean firstRow) {
        this.firstRow = firstRow;
    }

    public int getEventId() {
        return eventId;
    }

    public void setEventId(int eventId) {
        this.eventId = eventId;
    }
}