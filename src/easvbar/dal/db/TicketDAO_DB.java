package easvbar.dal.db;

import easvbar.be.Ticket;
import easvbar.dal.Interface.ITicket;

import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class TicketDAO_DB implements ITicket {
    private DatabaseConnector databaseConnector;

    public TicketDAO_DB() throws IOException {
        databaseConnector = new DatabaseConnector();
    }

    public List<Ticket> getAllTickets() throws Exception {
        ArrayList<Ticket> allTickets = new ArrayList<>();

        try (Connection conn = databaseConnector.getConnection();
             Statement stmt = conn.createStatement())
        {
            String sql = "SELECT * FROM FuckEASVBar.dbo.Ticket";
            ResultSet rs = stmt.executeQuery(sql);

            // Loop through rows from the database result set
            while (rs.next()) {

                // Map DB row to Ticket object
                int ticketId = rs.getInt("TicketId");
                String qrCode = rs.getString("QrCode");
                String barcode = rs.getString("Barcode");
                boolean vipTicket = rs.getBoolean("VipTicket");
                boolean foodTicket = rs.getBoolean("FoodTicket");
                boolean beerTicket = rs.getBoolean("BeerTicket");
                boolean firstRow = rs.getBoolean("FirstRow");
                int eventId = rs.getInt("EventId");


                Ticket ticket = new Ticket (ticketId,qrCode,barcode,vipTicket,foodTicket,beerTicket,firstRow,eventId);
                allTickets.add(ticket);
            }
            return allTickets;
        }
        catch (SQLException ex){
            ex.printStackTrace();
            throw new Exception("Could not get Tickets from database", ex);
        }
    }

    public Ticket createTicket(Ticket ticket) throws Exception {
        String sql = "INSERT INTO FuckEASVBar.dbo.Ticket (QrCode,Barcode,VipTicket,FoodTicket,BeerTicket,FirstRow,EventId) VALUES (?,?,?,?,?,?,?);";

        try (Connection conn = databaseConnector.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS))
        {
            //bind our parameters
            stmt.setString(1,ticket.getQrCode());
            stmt.setString(2,ticket.getBarcode());
            stmt.setBoolean(3,ticket.getVipTicket());
            stmt.setBoolean(4,ticket.getFoodTicket());
            stmt.setBoolean(5,ticket.getBeerTicket());
            stmt.setBoolean(6,ticket.getFirstRow());
            stmt.setInt(7,ticket.getEventId());

            // Run the specified SQL Statement
            stmt.executeUpdate();

            // Get the generated ID from the DB
            ResultSet rs = stmt.getGeneratedKeys();
            int ticketId = 0;

            if (rs.next()) {
                ticketId = rs.getInt(1);
            }

            // Create ticket object and send up the layers
            Ticket createdTicket = new Ticket(ticketId, ticket.getQrCode(), ticket.getBarcode(),
                    ticket.getVipTicket(), ticket.getFoodTicket(),ticket.getBeerTicket(),ticket.getFirstRow()
                    ,ticket.getEventId());

            return createdTicket;
        }
        catch (SQLException ex) {
            ex.printStackTrace();
            throw new Exception("Could not create ticket", ex);
        }
    }

    public Ticket deleteTicket(Ticket ticket) throws Exception {
        String sql="DELETE FROM FuckEASVBar.dbo.Ticket WHERE TicketId = ?;";
        try (Connection conn = databaseConnector.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql))
        {
            stmt.setInt(1,ticket.getTicketId());
            stmt.executeUpdate();
        }
        catch (SQLException ex)
        {
            ex.printStackTrace();
            throw new Exception("Could not delete Ticket", ex);
        }
        return ticket;
    }

    public Ticket updateTicket(Ticket ticket) throws Exception {
        String sql = "UPDATE FuckEASVBar.dbo.Ticket " +
                "SET TicketType = ?, QrCode = ?, Barcode = ? " +
                "WHERE TicketId = ?";

        try (Connection conn = databaseConnector.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1,ticket.getTicketType());
            stmt.setString(2,ticket.getQrCode());
            stmt.setString(3,ticket.getBarcode());
            stmt.setInt(4, ticket.getTicketId());
            stmt.executeUpdate();
        } catch (SQLException ex) {
            ex.printStackTrace();
            throw  new Exception("Could not Update Ticket");
        }
        return ticket;
    }
}