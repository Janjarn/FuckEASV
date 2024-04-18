package easvbar.dal.db;

import easvbar.be.Event;
import easvbar.be.SharedEvent;
import easvbar.dal.Interface.IEvent;
import easvbar.dal.Interface.ISharedEvent;

import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class SharedEventsDAO_DB implements ISharedEvent {
    private DatabaseConnector databaseConnector;

    public SharedEventsDAO_DB() throws IOException {
        databaseConnector = new DatabaseConnector();
    }

    public List<Event> getAllSharedEvents(int workerId) throws Exception {
        ArrayList<Event> allSharedEvents = new ArrayList<>();

        try (Connection conn = databaseConnector.getConnection();
             PreparedStatement stmt = conn.prepareStatement("SELECT se.*, e.* " +
                     "FROM SharedEvents se " +
                     "JOIN Event e ON se.EventId = e.EventId " +
                     "WHERE se.WorkerId = ?"))
        {
            stmt.setInt(1, workerId);
            ResultSet rs = stmt.executeQuery();

            // Loop through rows from the database result set
            while (rs.next()) {
                int eventId = rs.getInt("EventId");
                String eventName = rs.getString("EventName");
                String eventStart = rs.getString("EventTimeStart");
                String eventEnd = rs.getString("EventTimeEnd");
                String location = rs.getString("Location");
                String date = rs.getString("EventDate");
                String createdBy = rs.getString("CreatedBy");
                String eventImage = rs.getString("PicturePath");
                String guide = rs.getString("EventGuide");
                String description = rs.getString("EventDescription");

                Event sharedEvent = new Event(eventId, eventName, eventStart, eventEnd, location, date, createdBy, eventImage,guide,description);
                allSharedEvents.add(sharedEvent);
            }
            return allSharedEvents;
        }
        catch (SQLException ex){
            ex.printStackTrace();
            throw new Exception("Could not get SharedEvents from database", ex);
        }
    }
    public List<SharedEvent> getAllShared() throws Exception {
        ArrayList<SharedEvent> allShared = new ArrayList<>();

        try (Connection conn = databaseConnector.getConnection();
             Statement stmt = conn.createStatement())
        {
            String sql = "SELECT * FROM FuckEASVBar.dbo.SharedEvents";
            ResultSet rs = stmt.executeQuery(sql);

            // Loop through rows from the database result set
            while (rs.next()) {

                // Map DB row to Song object
                int eventId = rs.getInt("EventId");
                int workerId = rs.getInt("WorkerId");


                SharedEvent sharedEvent = new SharedEvent (eventId,workerId);
                allShared.add(sharedEvent);
            }
            return allShared;
        }
        catch (SQLException ex){
            ex.printStackTrace();
            throw new Exception("Could not get SharedEvents from database", ex);
        }
    }

    public SharedEvent createSharedEvent(SharedEvent sharedEvent) throws Exception {

        String sql = "INSERT INTO FuckEASVBar.dbo.SharedEvents (EventId, WorkerId) VALUES (?,?);";

        try (Connection conn = databaseConnector.getConnection()) {
            try (PreparedStatement stmt = conn.prepareStatement(sql)) {

                // Bind parameters
                stmt.setInt(1, sharedEvent.getEventId());
                stmt.setInt(2, sharedEvent.getWorkerId());

            // Run the specified SQL Statement
            stmt.executeUpdate();

            // Create SharedEvent object and send up the layers
                SharedEvent createdSharedEvent = new SharedEvent(sharedEvent.getEventId(),sharedEvent.getWorkerId());

            return createdSharedEvent;
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
            throw new Exception("Could not create add SharedEvent", ex);
        }
    }
    public SharedEvent deleteSharedEvent (SharedEvent sharedEvent) throws Exception {
        String sql = "DELETE FROM FuckEASVBar.dbo.SharedEvents WHERE EventId = ? AND WorkerId = ?";

        try (Connection conn = databaseConnector.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql))
        {
            stmt.setInt(1,sharedEvent.getEventId());
            stmt.setInt(2,sharedEvent.getWorkerId());
            stmt.executeUpdate();
        }
        catch (SQLException ex)
        {
            ex.printStackTrace();
            throw new Exception("Could not delete Event", ex);
        }
        return sharedEvent;
    }

}