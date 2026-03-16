package textbook.util;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class DatabaseInitializer {

    public static void initialize() {
        try {
            // Load Driver
            Class.forName("org.postgresql.Driver");

            checkAndCreateDatabase();
            checkAndCreateTables();

            System.out.println("Database initialization completed successfully.");
        } catch (Exception e) {
            System.err.println("Error during database initialization: " + e.getMessage());
            e.printStackTrace();
        }
    }

    private static void checkAndCreateDatabase() throws SQLException {
        try (Connection conn = DatabaseUtil.getPostgresConnection();
                Statement stmt = conn.createStatement()) {

            ResultSet rs = stmt
                    .executeQuery("SELECT 1 FROM pg_database WHERE datname = '" + DatabaseConfig.DB_NAME + "'");
            if (!rs.next()) {
                System.out.println("Database '" + DatabaseConfig.DB_NAME + "' does not exist. Creating...");
                stmt.executeUpdate("CREATE DATABASE " + DatabaseConfig.DB_NAME);
                System.out.println("Database created.");
            } else {
                System.out.println("Database already exists.");
            }
        }
    }

    private static void checkAndCreateTables() throws SQLException {
        try (Connection conn = DatabaseUtil.getAppConnection();
                Statement stmt = conn.createStatement()) {

            // Create UE table
            stmt.executeUpdate("CREATE TABLE IF NOT EXISTS ue (" +
                    "code_ue VARCHAR(20) PRIMARY KEY, " +
                    "intitule VARCHAR(100) NOT NULL" +
                    ")");

            // Create ENSEIGNANT table
            stmt.executeUpdate("CREATE TABLE IF NOT EXISTS enseignant (" +
                    "matricule VARCHAR(20) PRIMARY KEY, " +
                    "nom VARCHAR(50) NOT NULL, " +
                    "prenom VARCHAR(50) NOT NULL" +
                    ")");

            // Create SEANCE table
            stmt.executeUpdate("CREATE TABLE IF NOT EXISTS seance (" +
                    "id_seance SERIAL PRIMARY KEY, " +
                    "code_ue VARCHAR(20) NOT NULL REFERENCES ue(code_ue), " +
                    "matricule VARCHAR(20) NOT NULL REFERENCES enseignant(matricule), " +
                    "date_seance DATE NOT NULL, " +
                    "heure_debut TIME NOT NULL, " +
                    "heure_fin TIME NOT NULL, " +
                    "salle VARCHAR(20) NOT NULL, " +
                    "resume TEXT NOT NULL" +
                    ")");

            System.out.println("Tables checked/created.");
        }
    }
}
