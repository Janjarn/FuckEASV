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
    private String eventDescription;
    private String eventGuide;

    public Event(int id, String name, String eventStart, String eventEnd, String location, String date, String createdBy, String eventImage,String eventGuide, String eventDescription) {
        this.id = id;
        this.name = name;
        this.eventStart = eventStart;
        this.eventEnd = eventEnd;
        this.location = location;
        this.date = date;
        this.createdBy = createdBy;
        this.eventImage = eventImage;
        this.eventGuide = eventGuide;
        this.eventDescription = eventDescription;
    }

    public Event() {

    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEventStart() {
        return eventStart;
    }

    public void setEventStart(String eventStart) {
        this.eventStart = eventStart;
    }

    public String getEventEnd() {
        return eventEnd;
    }

    public void setEventEnd(String eventEnd) {
        this.eventEnd = eventEnd;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public int getSoldTickets() {
        return soldTickets;
    }

    public void setSoldTickets(int soldTickets) {
        this.soldTickets = soldTickets;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public String getEventImage() {
        return eventImage;
    }

    public void setEventImage(String eventImage) {
        this.eventImage = eventImage;
    }

    public String getEventDescription() {
        return eventDescription;
    }

    public void setEventDescription(String eventDescription) {
        this.eventDescription = eventDescription;
    }

    public String getEventGuide() {
        return eventGuide;
    }

    public void setEventGuide(String eventGuide) {
        this.eventGuide = eventGuide;
    }

    @Override
    public String toString() {
        return
                "name= '" + name + '\'' +
                ", Start= '" + eventStart + '\'' +
                ", End= '" + eventEnd + '\'' +
                ", location= '" + location + '\'' +
                ", date= '" + date + '\'' +
                ", by '" + createdBy + '\'';
    }
}