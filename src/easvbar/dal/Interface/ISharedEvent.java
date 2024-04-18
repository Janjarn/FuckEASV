package easvbar.dal.Interface;

import easvbar.be.Event;
import easvbar.be.SharedEvent;

import java.util.List;

public interface ISharedEvent {
    public List<Event> getAllSharedEvents(int workerId) throws Exception;
    public List<SharedEvent> getAllShared() throws Exception;
    public SharedEvent createSharedEvent(SharedEvent sharedEvent) throws Exception;

    public SharedEvent deleteSharedEvent(SharedEvent sharedEvent) throws Exception;

}
