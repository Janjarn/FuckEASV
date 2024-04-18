package easvbar.gui.model;

import easvbar.be.Event;
import easvbar.be.SharedEvent;
import easvbar.bll.EventManager;
import easvbar.bll.SharedEventManager;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Label;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class SharedEventModel {
    private ObservableList<Event> allEvents;
    private ObservableList<SharedEvent> allSharedEvents;
    private SharedEventManager sharedEventManager;

    public SharedEventModel() throws Exception {
        sharedEventManager = new SharedEventManager();
        allEvents = FXCollections.observableArrayList();
        allSharedEvents = FXCollections.observableArrayList();
    }

    public ObservableList<Event> getAllSharedEvents(int workerId) throws Exception {
        allEvents.addAll(sharedEventManager.getAllSharedEvents(workerId));
        return allEvents;
    }
    public ObservableList<SharedEvent> getAllShared() throws Exception {
        allSharedEvents.addAll(sharedEventManager.getAllSharedEvents());
        return allSharedEvents;
    }

    public void deleteSharedEvent(SharedEvent sharedEvent) throws Exception {
        SharedEvent e = sharedEventManager.deleteSharedEvent(sharedEvent);
        allSharedEvents.remove(e);
    }

    public void createSharedEvent(SharedEvent sharedEvent) throws Exception{
        SharedEvent e = sharedEventManager.createSharedEvent(sharedEvent);
        allSharedEvents.add(e);
    }
}
