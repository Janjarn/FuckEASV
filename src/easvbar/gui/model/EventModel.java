package easvbar.gui.model;

import easvbar.be.Event;
import easvbar.bll.EventManager;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;

import javax.swing.text.html.ImageView;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

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

    public List<Label> handleSelectedEvent(Event event) {
        List<Label> labels = new ArrayList<>();

        // Create labels for each piece of event information
        Label nameLabel = new Label("Name: " + event.getName());
        Label startLabel = new Label("Event Start Time: " + event.getEventStart());
        Label endLabel = new Label("Event End Time: " + event.getEventEnd());
        Label locationLabel = new Label("Location: " + event.getLocation());
        Label dateLabel = new Label("Date: " + event.getDate());
        Label createdByLabel = new Label("Created By: " + event.getCreatedBy());

        // Apply style class to the labels
        nameLabel.getStyleClass().add("flowLabel");
        startLabel.getStyleClass().add("flowLabel");
        endLabel.getStyleClass().add("flowLabel");
        locationLabel.getStyleClass().add("flowLabel");
        dateLabel.getStyleClass().add("flowLabel");
        createdByLabel.getStyleClass().add("flowLabel");

        // Add labels to the list
        labels.addAll(Arrays.asList(nameLabel, startLabel, endLabel, locationLabel, dateLabel, createdByLabel));

        return labels;
    }
}
