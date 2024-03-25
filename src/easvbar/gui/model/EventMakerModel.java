package easvbar.gui.model;

import easvbar.be.Event;
import easvbar.bll.EventManager;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class EventMakerModel {
    private Event selectedEvent;
    private EventManager eventManager;
    private ObservableList<Event> events;

    public EventMakerModel() throws Exception {
        eventManager = new EventManager();
        events = FXCollections.observableArrayList();
        events.addAll(eventManager.getAllEvents());
    }

    public ObservableList<Event> getObservableEvents() throws Exception {
        events.clear();
        events.addAll(eventManager.getAllEvents());
        return events;
    }

    public void createEvent(Event event) throws Exception{
        Event e = eventManager.createEvent(event);
        events.add(e);
    }

    public void deleteEvent(Event deletedEvent) throws Exception {
        eventManager.deleteEvent(deletedEvent);
        getObservableEvents().clear();
        getObservableEvents().addAll(eventManager.getAllEvents());
    }

    public Event getSelectedEvent(){return selectedEvent;}

    public void setSelectedEvent(Event selectedEvent) {
        this.selectedEvent = selectedEvent;
    }
}