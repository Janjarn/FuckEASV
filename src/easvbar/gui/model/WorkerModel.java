package easvbar.gui.model;

import easvbar.be.Event;
import easvbar.be.Worker;
import easvbar.bll.EventManager;
import easvbar.bll.WorkerManager;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class WorkerModel {
    private ObservableList<Worker> allWorkers;
    private WorkerManager workerManager;

    public WorkerModel() throws Exception {
        workerManager = new WorkerManager();
        allWorkers = FXCollections.observableArrayList();
        allWorkers.addAll(workerManager.getAllEvents());
    }

    public ObservableList<Worker> getAllEvents() {
        return allWorkers;
    }

    public void deleteEvent(Worker worker) throws Exception {
        Worker w = workerManager.deleteEvent(worker);
        allWorkers.remove(w);
    }
}
