package easvbar.bll;

import easvbar.be.Worker;
import easvbar.dal.db.WorkersDAO_DB;

import java.io.IOException;
import java.util.List;

public class WorkerManager {
    private WorkersDAO_DB workersDAO;

    public WorkerManager() throws IOException {
        workersDAO = new WorkersDAO_DB();
    }
    public List<Worker> getAllEvents() throws Exception {
        return workersDAO.getAllWorkers();
    }
    public Worker createEvent (Worker newWorker) throws Exception {
        return workersDAO.createWorker(newWorker);
    }
    public Worker deleteEvent(Worker worker) throws Exception {
        workersDAO.deleteWorker(worker);
        return worker;
    }

    public Worker updateEvent(Worker selectedWorker) throws Exception {
        return workersDAO.updateWorker(selectedWorker);
    }

    public Worker validateUser(String userName, String password) throws Exception {
        return workersDAO.validateUser(userName, password);
    }
}
