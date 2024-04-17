package easvbar.gui.controller;

import easvbar.gui.model.EventMakerModel;
import easvbar.gui.model.TicketModel;

public abstract class BaseController {
    private EventMakerModel eventMakerModel;
    private TicketModel ticketModel;

    public abstract void setup() throws Exception;

    public EventMakerModel getEventMakerModel() {
        return eventMakerModel;
    }

    public void setEventMakerModel(EventMakerModel eventMakerModel) {
        this.eventMakerModel = eventMakerModel;
    }

    public TicketModel getTicketModel() {
        return ticketModel;
    }

    public void setTicketModel(TicketModel ticketModel) {
        this.ticketModel = ticketModel;
    }
}