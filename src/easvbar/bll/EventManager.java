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


    public List<Event> getAllEvents(){
        return eventDAO.getAllEvents();
    }
    public Event createNewEvent (Event newEvent){
        return eventDAO.createEvent(newEvent);
    }
    public void deleteEvent(Event event){
        eventDAO.deleteEvent(event);
    }




}
