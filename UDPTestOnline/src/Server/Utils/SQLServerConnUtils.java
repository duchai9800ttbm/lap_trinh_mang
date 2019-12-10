package Server.Utils;

import java.sql.*;

public class SQLServerConnUtils {
    public static Connection connectionMSSQL() throws ClassNotFoundException, SQLException {
        String hostName = "localhost";
        String sqlInstanceName = "SQLEXPRESS";
        String database = "TestOnline";
        String userName = "sa";
        String password = "123";

        Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");

        String connectionURL = "jdbc:sqlserver://" + hostName + ":1433"
                + ";instance=" + sqlInstanceName + ";databaseName=" + database;

        return DriverManager.getConnection(connectionURL, userName, password);
    }
}
