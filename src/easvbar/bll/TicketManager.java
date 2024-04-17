package easvbar.bll;

import easvbar.be.Ticket;
import easvbar.dal.db.TicketDAO_DB;

import java.io.IOException;
import java.util.List;

public class TicketManager {
    private TicketDAO_DB ticketDAO;
    public TicketManager() throws IOException {
        ticketDAO = new TicketDAO_DB();
    }
    public List<Ticket> getAllTickets() throws Exception {
        return ticketDAO.getAllTickets();
    }
    public Ticket createTicket (Ticket ticket) throws Exception {
        return ticketDAO.createTicket(ticket);
    }
    public Ticket deleteTicket(Ticket ticket) throws Exception {
        ticketDAO.deleteTicket(ticket);
        return ticket;
    }

    public Ticket updateTicket(Ticket ticket) throws Exception {
        return ticketDAO.updateTicket(ticket);
    }
}