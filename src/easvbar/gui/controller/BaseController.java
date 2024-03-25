package easvbar.gui.controller;

import easvbar.gui.model.EventMakerModel;

public abstract class BaseController {
    private EventMakerModel eventMakerModel;

    public abstract void setup() throws Exception;

    public EventMakerModel getEventMakerModel() {
        return eventMakerModel;
    }

    public void setEventMakerModel(EventMakerModel eventMakerModel) {
        this.eventMakerModel = eventMakerModel;
    }
}