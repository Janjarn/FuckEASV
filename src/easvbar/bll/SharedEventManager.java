package easvbar.bll;

import easvbar.be.Event;
import easvbar.be.SharedEvent;
import easvbar.bll.util.EventSearcher;
import easvbar.dal.db.EventsDAO_DB;
import easvbar.dal.db.SharedEventsDAO_DB;

import java.io.IOException;
import java.util.List;

public class SharedEventManager {
    private SharedEventsDAO_DB eventDAO;

    public SharedEventManager() throws IOException {
        eventDAO = new SharedEventsDAO_DB();
    }
    public List<Event> getAllSharedEvents(int workerId) throws Exception {
        return eventDAO.getAllSharedEvents(workerId);
    }
    public List<SharedEvent> getAllSharedEvents() throws Exception {
        return eventDAO.getAllShared();
    }
    public SharedEvent createSharedEvent (SharedEvent newSharedEvent) throws Exception {
        return eventDAO.createSharedEvent(newSharedEvent);
    }
    public SharedEvent deleteSharedEvent(SharedEvent deleteSharedEvent) throws Exception {
        eventDAO.deleteSharedEvent(deleteSharedEvent);
        return deleteSharedEvent;
    }

}