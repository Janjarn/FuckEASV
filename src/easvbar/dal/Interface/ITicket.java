package easvbar.dal.Interface;

import easvbar.be.Ticket;

import java.util.List;

public interface ITicket {
    public List<Ticket> getAllTickets() throws Exception;
    public Ticket createTicket(Ticket ticket) throws Exception;

    public Ticket deleteTicket(Ticket ticket) throws Exception;

    public Ticket updateTicket(Ticket ticket) throws Exception;
}