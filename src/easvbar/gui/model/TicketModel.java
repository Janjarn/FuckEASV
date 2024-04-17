package easvbar.gui.model;

import easvbar.be.Ticket;
import easvbar.bll.TicketManager;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

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
}