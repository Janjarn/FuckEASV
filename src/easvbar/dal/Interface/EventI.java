package easvbar.dal.Interface;

import easvbar.be.Event;

import java.util.List;

public interface EventI {
    public List<Event> getAllEvents() throws Exception;
    public Event createEvent(Event event) throws Exception;

    public Event deleteEvent(Event event) throws Exception;

    public Event updateEvent(Event event) throws Exception;
}
