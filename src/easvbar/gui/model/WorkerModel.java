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
        allWorkers.addAll(workerManager.getAllWorkers());
    }

    public ObservableList<Worker> getAllWorkers() {
        return allWorkers;
    }

    public void deleteWorker(Worker worker) throws Exception {
        Worker w = workerManager.deleteWorker(worker);
        allWorkers.remove(w);
    }

    public void createWorker(Worker worker) throws Exception{
        Worker w = workerManager.createWorker(worker);
        allWorkers.add(w);
    }

    public void updateWorker(Worker worker) throws Exception {
        Worker selectedworker = new Worker();
        selectedworker.setId(worker.getId());
        selectedworker.setName(worker.getName());
        selectedworker.setRole(worker.getRole());
        selectedworker.setPassword(worker.getPassword());
        selectedworker.setRoleId(worker.getRoleId());
        selectedworker.setPicture(worker.getPicture());

        workerManager.updateWorker(selectedworker);
    }
}
