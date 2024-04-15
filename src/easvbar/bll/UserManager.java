package easvbar.bll;

import easvbar.be.User;
import easvbar.be.Worker;
import easvbar.dal.db.UsersDAO_DB;
import easvbar.dal.db.WorkersDAO_DB;

import java.io.IOException;
import java.util.List;

public class UserManager {
    private UsersDAO_DB usersDAO;

    public UserManager() throws IOException {
        usersDAO = new UsersDAO_DB();
    }
    public List<User> getAllUsers() throws Exception {
        return usersDAO.getAllUsers();
    }
    public User createUser (User newUser) throws Exception {
        return usersDAO.createUser(newUser);
    }
    public User deleteUser(User worker) throws Exception {
        usersDAO.deleteUser(worker);
        return worker;
    }

    public User updateUser(User selectedWorker) throws Exception {
        return usersDAO.updateUser(selectedWorker);
    }
}
