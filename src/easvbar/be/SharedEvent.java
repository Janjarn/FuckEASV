package easvbar.be;

public class SharedEvent {
    private int eventId;
    private int workerId;

    public SharedEvent(int eventId,int workerId) {
        this.eventId = eventId;
        this.workerId = workerId;
    }

    public SharedEvent() {

    }

    public int getEventId() {
        return eventId;
    }

    public void setEventId(int eventId) {
        this.eventId = eventId;
    }

    public int getWorkerId() {
        return workerId;
    }

    public void setWorkerId(int workerId) {
        this.workerId = workerId;
    }

}