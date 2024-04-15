package easvbar.gui.model;

import easvbar.be.User;
import easvbar.be.Worker;
import easvbar.bll.UserManager;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class UserModel {
    private ObservableList<User> allUsers;
    private UserManager userManager;

    public UserModel() throws Exception {
        userManager = new UserManager();
        allUsers = FXCollections.observableArrayList();
        allUsers.addAll(userManager.getAllUsers());
    }

    public ObservableList<User> getAllUsers() {
        return allUsers;
    }

    public void deleteUser(User user) throws Exception {
        User u = userManager.deleteUser(user);
        allUsers.remove(u);
    }

    public void createUser(User user) throws Exception{
        User u = userManager.createUser(user);
        allUsers.add(u);
    }

    public void updateUser(User user) throws Exception {
        User selectedUser = new User();
        selectedUser.setId(user.getId());
        selectedUser.setName(user.getName());
        selectedUser.setLastname(user.getLastname());
        selectedUser.setPending(user.getPending());

        userManager.updateUser(selectedUser);
    }
}
