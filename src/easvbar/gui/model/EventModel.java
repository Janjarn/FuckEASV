package easvbar.gui.model;

import easvbar.be.Event;
import easvbar.bll.EventManager;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class EventModel {
    private ObservableList<Event> allEvents;
    private EventManager eventManager;

    public EventModel() throws Exception {
        eventManager = new EventManager();
        allEvents = FXCollections.observableArrayList();
        allEvents.addAll(eventManager.getAllEvents());
    }

    public ObservableList<Event> getAllEvents() {
        return allEvents;
    }

    public void deleteEvent(Event event) throws Exception {
        Event e = eventManager.deleteEvent(event);
        allEvents.remove(e);
    }
}
