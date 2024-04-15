package easvbar.dal.Interface;


import easvbar.be.User;

import java.util.List;

public interface IUser {
    public List<User> getAllUsers() throws Exception;
    public User createUser(User user) throws Exception;

    public User deleteUser(User user) throws Exception;

    public User updateUser(User user) throws Exception;
}
