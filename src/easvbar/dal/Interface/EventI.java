package easvbar.dal.Interface;

import easvbar.be.Event;

import java.util.List;

public interface EventI {
    public List<Event> getAllEvents();
    public Event createEvent(Event event);

    public Event deleteEvent(Event event);

}
