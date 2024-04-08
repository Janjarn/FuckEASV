package easvbar.dal.db;

import easvbar.be.Event;
import easvbar.be.Worker;
import easvbar.dal.Interface.IWorker;

import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class WorkersDAO_DB implements IWorker {

    private DatabaseConnector databaseConnector;

    public WorkersDAO_DB() throws IOException {
        databaseConnector = new DatabaseConnector();
    }

    @Override
    public List<Worker> getAllWorkers() throws Exception {
        ArrayList<Worker> allWorkers = new ArrayList<>();

        try (Connection conn = databaseConnector.getConnection();
             Statement stmt = conn.createStatement())
        {
            String sql = "SELECT * FROM FuckEASVBar.dbo.Worker";
            ResultSet rs = stmt.executeQuery(sql);

            // Loop through rows from the database result set
            while (rs.next()) {

                // Map DB row to Song object
                int id = rs.getInt("WorkerId");
                String name = rs.getString("WorkerName");
                String password = rs.getString("WorkerPassword");
                String role = rs.getString("WorkerRole");
                int roleId = rs.getInt("WorkerRoleId");


                Worker worker = new Worker (id,name,password,role,roleId);
                allWorkers.add(worker);
            }
            return allWorkers;
        }
        catch (SQLException ex){
            ex.printStackTrace();
            throw new Exception("Could not get Workers from database", ex);
        }
    }

    @Override
    public Worker createWorker(Worker worker) throws Exception {
        String sql = "INSERT INTO FuckEASVBar.dbo.Worker (WorkerName, WorkerPassword, WorkerRole, WorkerRoleId) VALUES (?,?,?,?);";

        try (Connection conn = databaseConnector.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS))
        {
            //bind our parameters
            stmt.setString(1,worker.getName());
            stmt.setString(2,worker.getPassword());
            stmt.setString(3,worker.getRole());
            stmt.setInt(4,worker.getRoleId());

            // Run the specified SQL Statement
            stmt.executeUpdate();

            // Get the generated ID from the DB
            ResultSet rs = stmt.getGeneratedKeys();
            int id = 0;

            if (rs.next()) {
                id = rs.getInt(1);
            }

            // Create song object and send up the layers
            Worker createdWorker = new Worker(id, worker.getName(), worker.getPassword(), worker.getRole(), worker.getRoleId());

            return createdWorker;
        }
        catch (SQLException ex) {
            ex.printStackTrace();
            throw new Exception("Could not add Worker", ex);
        }
    }

    @Override
    public Worker deleteWorker(Worker worker) throws Exception {

        String sql="DELETE FROM FuckEASVBar.dbo.Worker WHERE WorkerId = ?;";
        try (Connection conn = databaseConnector.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql))
        {
            stmt.setInt(1,worker.getId());
            stmt.executeUpdate();
        }
        catch (SQLException ex)
        {
            ex.printStackTrace();
            throw new Exception("Could not delete Worker", ex);
        }
        return worker;
    }

    @Override
    public Worker updateWorker(Worker worker) throws Exception {
        String sql = "UPDATE FuckEASVBar.dbo.Worker " +
                "SET WorkerName = ?, WorkerPassword = ?, WorkerRole = ?, WorkerId = ?, ";

        try (Connection conn = databaseConnector.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1,worker.getName());
            stmt.setString(2,worker.getPassword());
            stmt.setString(3,worker.getRole());
            stmt.setInt(4,worker.getRoleId());
            stmt.executeUpdate();
        } catch (SQLException ex) {
            ex.printStackTrace();
            throw  new Exception("Could not Update Worker");
        }
        return worker;
    }

    public Worker validateUser(String workerName, String workerPassword){
        Worker worker = null; // starts the user as null
        /**
         * get the userid from the user that is trying to log in, and is checking for
         * if the password  matches that user.
         */ // "SELECT * FROM FuckEASVBar.dbo.Worker
        String sql = "SELECT * FROM FuckEASVBar.dbo.Worker where WorkerName = ? and WorkerPassword = ?";

        try (Connection conn = databaseConnector.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)){

            stmt.setString(1, workerName);
            stmt.setString(2, workerPassword);

            ResultSet rs = stmt.executeQuery();
            if (rs.next()){
                int id = rs.getInt("WorkerId");
                int roleId = rs.getInt("WorkerRoleId");
                worker = new Worker(id, workerName, workerPassword, roleId);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return worker;
    }
}
