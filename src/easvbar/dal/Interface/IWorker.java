package easvbar.dal.Interface;

import easvbar.be.Event;
import easvbar.be.Worker;

import java.util.List;

public interface IWorker {
    public List<Worker> getAllWorkers() throws Exception;
    public Worker createWorker(Worker worker) throws Exception;

    public Worker deleteWorker(Worker worker) throws Exception;

    public Worker updateWorker(Worker worker) throws Exception;
}
