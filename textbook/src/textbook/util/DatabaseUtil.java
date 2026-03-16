package textbook.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseUtil {
    public static Connection getPostgresConnection() throws SQLException {
        return DriverManager.getConnection(DatabaseConfig.JDBC_URL_POSTGRES, DatabaseConfig.USER, DatabaseConfig.PASSWORD);
    }

    public static Connection getAppConnection() throws SQLException {
        return DriverManager.getConnection(DatabaseConfig.JDBC_URL_APP, DatabaseConfig.USER, DatabaseConfig.PASSWORD);
    }
}
