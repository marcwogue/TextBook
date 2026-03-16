package textbook.util;

public class DatabaseConfig {
    public static final String HOST = "localhost";
    public static final String PORT = "5432";
    public static final String DB_NAME = "textbook";
    public static final String USER = "postgres";
    public static final String PASSWORD = "admin"; // User to adjust if different
    
    public static final String JDBC_URL_POSTGRES = "jdbc:postgresql://" + HOST + ":" + PORT + "/postgres";
    public static final String JDBC_URL_APP = "jdbc:postgresql://" + HOST + ":" + PORT + "/" + DB_NAME;
}
