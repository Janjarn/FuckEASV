package easvbar.dal.db;

import easvbar.be.Event;
import easvbar.dal.Interface.EventI;

import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class EventsDAO_DB implements EventI {
    private DatabaseConnector databaseConnector;

    public EventsDAO_DB() throws IOException {
        databaseConnector = new DatabaseConnector();
    }

    public List<Event> getAllEvents() throws Exception {
        ArrayList<Event> allSongs = new ArrayList<>();

        try (Connection conn = databaseConnector.getConnection();
             Statement stmt = conn.createStatement())
        {
            String sql = "SELECT * FROM FuckEASVBar.Event";
            ResultSet rs = stmt.executeQuery(sql);

            // Loop through rows from the database result set
            while (rs.next()) {

                // Map DB row to Song object
                int id = rs.getInt("EventId");
                String name = rs.getString("EventName");
                String eventStart = rs.getString("EventTimeStart");
                String eventEnd = rs.getString("EventTimeEnd");
                String location = rs.getString("Location");
                String date = rs.getString("EventDate");
                String createdBy = rs.getString("CreatedBy");
                String eventImage = rs.getString("PicturePath");


                Event event = new Event (id,name,eventStart,eventEnd,location,date,createdBy,eventImage);
                allSongs.add(event);
            }
            return allSongs;
        }
        catch (SQLException ex){
            ex.printStackTrace();
            throw new Exception("Could not get songs from database", ex);
        }
    }

    public Event createEvent(Event event) throws Exception {

        String sql = "INSERT INTO FuckEASVBar.Event (name, eventStart, eventEnd,location, date, createdBy,eventImage) VALUES (?,?,?,?,?,?,?);";

        try (Connection conn = databaseConnector.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS))
        {
            //bind our parameters
            stmt.setString(1,event.getName());
            stmt.setString(2,event.getEventStart());
            stmt.setString(3,event.getEventEnd());
            stmt.setString(4,event.getLocation());
            stmt.setString(5,event.getDate());
            stmt.setString(6,event.getCreatedBy());
            stmt.setString(7,event.getEventImage());

            // Run the specified SQL Statement
            stmt.executeUpdate();

            // Get the generated ID from the DB
            ResultSet rs = stmt.getGeneratedKeys();
            int id = 0;

            if (rs.next()) {
                id = rs.getInt(1);
            }

            // Create song object and send up the layers
            Event createdEvent = new Event(id, event.getName(), event.getEventStart(), event.getEventEnd(), event.getLocation(), event.getDate(), event.getCreatedBy(), event.getEventImage());

            return createdEvent;
        }
        catch (SQLException ex) {
            ex.printStackTrace();
            throw new Exception("Could not add event", ex);
        }
    }
    public Event deleteEvent (Event event) throws Exception {
        String sql="DELETE FROM FuckEASVBar.Event WHERE EventId = ?;";
        try (Connection conn = databaseConnector.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql))
        {
            stmt.setInt(1,event.getId());
            stmt.executeUpdate();
        }
        catch (SQLException ex)
        {
            ex.printStackTrace();
            throw new Exception("Could not delete Event", ex);
        }
        return event;
    }

}