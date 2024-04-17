package easvbar.be;

public class Ticket {
    private int ticketId;
    private String ticketType;
    private String qrCode;
    private String barcode;
    private boolean vipTicket;
    private boolean foodTicket;
    private boolean beerTicket;
    private boolean firstRow;

    public Ticket(int ticketId, String qrCode, String barcode, boolean vipTicket, boolean foodTicket,
                  boolean beerTicket, boolean firstRow) {
        this.ticketId = ticketId;
        this.qrCode = qrCode;
        this.barcode = barcode;
        this.vipTicket = vipTicket;
        this.foodTicket = foodTicket;
        this.beerTicket = beerTicket;
        this.firstRow = firstRow;
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



    public int getTicketId() {
        return ticketId;
    }

    public void setTicketId(int ticketId) {
        this.ticketId = ticketId;
    }

    public String getTicketType() {
        return ticketType;
    }

    public void setTicketType(String ticketType) {
        this.ticketType = ticketType;
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
}