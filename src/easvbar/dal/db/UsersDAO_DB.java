package easvbar.dal.db;

import easvbar.be.User;
import easvbar.be.Worker;
import easvbar.dal.Interface.IUser;

import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UsersDAO_DB implements IUser {
    private DatabaseConnector databaseConnector;

    public UsersDAO_DB() throws IOException {
        databaseConnector = new DatabaseConnector();
    }

    @Override
    public List<User> getAllUsers() throws Exception {
        ArrayList<User> allUsers = new ArrayList<>();

        try (Connection conn = databaseConnector.getConnection();
             Statement stmt = conn.createStatement())
        {
            String sql = "SELECT * FROM [FuckEASVBar].[dbo].[User]";;
            ResultSet rs = stmt.executeQuery(sql);

            // Loop through rows from the database result set
            while (rs.next()) {

                // Map DB row to Song object
                int id = rs.getInt("UserId");
                String name = rs.getString("UserName");
                String lastName = rs.getString("UserLastName");
                int pending = rs.getInt("UserPending");


                User user = new User (id,name,lastName,pending);
                allUsers.add(user);
            }
            return allUsers;
        }
        catch (SQLException ex){
            ex.printStackTrace();
            throw new Exception("Could not get Users from database", ex);
        }
    }

    @Override
    public User createUser(User user) throws Exception {
        String sql = "INSERT INTO [FuckEASVBar].[dbo].[User] (UserName, UserLastName, UserPending) VALUES (?,?,?);";

        try (Connection conn = databaseConnector.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS))
        {
            //bind our parameters
            stmt.setString(1,user.getName());
            stmt.setString(2,user.getLastname());
            stmt.setInt(3,user.getPending());

            // Run the specified SQL Statement
            stmt.executeUpdate();

            // Get the generated ID from the DB
            ResultSet rs = stmt.getGeneratedKeys();
            int id = 0;

            if (rs.next()) {
                id = rs.getInt(1);
            }

            // Create song object and send up the layers
            User createdUser = new User(id, user.getName(), user.getLastname(), user.getPending());

            return createdUser;
        }
        catch (SQLException ex) {
            ex.printStackTrace();
            throw new Exception("Could not add User", ex);
        }
    }

    @Override
    public User deleteUser(User user) throws Exception {

        String sql="DELETE FROM [FuckEASVBar].[dbo].[User] WHERE UserId = ?;";
        try (Connection conn = databaseConnector.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql))
        {
            stmt.setInt(1,user.getId());
            stmt.executeUpdate();
        }
        catch (SQLException ex)
        {
            ex.printStackTrace();
            throw new Exception("Could not delete User", ex);
        }
        return user;
    }

    @Override
    public User updateUser(User user) throws Exception {
        String sql = "UPDATE [FuckEASVBar].[dbo].[User] " +
                "SET UserName = ?, UserLastName = ?, UserPending = ? " + // Removed trailing comma
                "WHERE UserId = ?";

        try (Connection conn = databaseConnector.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1,user.getName());
            stmt.setString(2,user.getLastname());
            stmt.setInt(3,user.getPending());
            stmt.setInt(4,user.getId());
            stmt.executeUpdate();
        } catch (SQLException ex) {
            ex.printStackTrace();
            throw  new Exception("Could not Update User");
        }
        return user;
    }

}
