package easvbar.dal.db;

import com.microsoft.sqlserver.jdbc.SQLServerDataSource;
import com.microsoft.sqlserver.jdbc.SQLServerException;

import java.sql.Connection;
import java.sql.SQLException;

public class DatabaseConnector {

    private SQLServerDataSource dataSource;

    public DatabaseConnector() {

        dataSource = new SQLServerDataSource();
        dataSource.setServerName("");
        dataSource.setDatabaseName("");
        dataSource.setUser("");
        dataSource.setPassword("");
        dataSource.setTrustServerCertificate(true);
    }
    public Connection getConnection() throws SQLServerException
    {
        return dataSource.getConnection();
    }

//Test if there is an open connection.

    public static void main(String[] args) throws SQLException
    {
        DatabaseConnector databaseConnector = new DatabaseConnector();

        try (Connection connection = databaseConnector.getConnection())
        {
            System.out.println("Is it open? " + !connection.isClosed());
        }
    }
}