package easvbar.bll;

import easvbar.be.Event;
import easvbar.bll.util.EventSearcher;
import easvbar.dal.Interface.EventI;
import easvbar.dal.db.EventsDAO_DB;

import java.io.IOException;
import java.util.List;

public class EventManager {
    private EventSearcher eventSearcher = new EventSearcher();
    private EventI eventDAO;

    public EventManager() throws IOException {
        eventDAO = new EventsDAO_DB();
    }
    public List<Event> getAllEvents() throws Exception {
        return eventDAO.getAllEvents();
    }
    public Event createEvent (Event newEvent) throws Exception {
        return eventDAO.createEvent(newEvent);
    }
    public void deleteEvent(Event event) throws Exception {
        eventDAO.deleteEvent(event);
    }
}