package easvbar.be;

public class Event {
    private int id;
    private String name;
    private String eventStart;
    private String eventEnd;
    private String location;
    private String date;
    private int soldTickets;
    private String createdBy;
    private String eventImage;

    public Event(int id, String name, String eventStart, String eventEnd, String location, String date, String createdBy, String eventImage) {
        this.id = id;
        this.name = name;
        this.eventStart = eventStart;
        this.eventEnd = eventEnd;
        this.location = location;
        this.date = date;
        this.createdBy = createdBy;
        this.eventImage = eventImage;
    }

    public int getId() {
        return id;
    }

    private void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    private void setName(String name) {
        this.name = name;
    }

    public String getEventStart() {
        return eventStart;
    }

    private void setEventStart(String eventStart) {
        this.eventStart = eventStart;
    }

    public String getEventEnd() {
        return eventEnd;
    }

    private void setEventEnd(String eventEnd) {
        this.eventEnd = eventEnd;
    }

    public String getLocation() {
        return location;
    }

    private void setLocation(String location) {
        this.location = location;
    }

    public String getDate() {
        return date;
    }

    private void setDate(String date) {
        this.date = date;
    }

    private int getSoldTickets() {
        return soldTickets;
    }

    private void setSoldTickets(int soldTickets) {
        this.soldTickets = soldTickets;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    private void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public String getEventImage() {
        return eventImage;
    }

    private void setEventImage(String eventImage) {
        this.eventImage = eventImage;
    }
}