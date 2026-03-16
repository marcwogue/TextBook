package textbook.dao;

import textbook.model.Enseignant;
import textbook.util.DatabaseUtil;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class EnseignantDao {

    public void insert(Enseignant enseignant) throws SQLException {
        String sql = "INSERT INTO enseignant (matricule, nom, prenom) VALUES (?, ?, ?)";
        try (Connection conn = DatabaseUtil.getAppConnection();
                PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, enseignant.getMatricule());
            pstmt.setString(2, enseignant.getNom());
            pstmt.setString(3, enseignant.getPrenom());
            pstmt.executeUpdate();
        }
    }

    public List<Enseignant> findAll() throws SQLException {
        List<Enseignant> enseignants = new ArrayList<>();
        String sql = "SELECT * FROM enseignant ORDER BY nom, prenom";
        try (Connection conn = DatabaseUtil.getAppConnection();
                Statement stmt = conn.createStatement();
                ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                enseignants.add(new Enseignant(
                        rs.getString("matricule"),
                        rs.getString("nom"),
                        rs.getString("prenom")));
            }
        }
        return enseignants;
    }

    public void delete(String matricule) throws SQLException {
        String sql = "DELETE FROM enseignant WHERE matricule = ?";
        try (Connection conn = DatabaseUtil.getAppConnection();
                PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, matricule);
            pstmt.executeUpdate();
        }
    }

    public void update(Enseignant enseignant) throws SQLException {
        String sql = "UPDATE enseignant SET nom = ?, prenom = ? WHERE matricule = ?";
        try (Connection conn = DatabaseUtil.getAppConnection();
                PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, enseignant.getNom());
            pstmt.setString(2, enseignant.getPrenom());
            pstmt.setString(3, enseignant.getMatricule());
            pstmt.executeUpdate();
        }
    }

    public boolean exists(String matricule) throws SQLException {
        String sql = "SELECT 1 FROM enseignant WHERE matricule = ?";
        try (Connection conn = DatabaseUtil.getAppConnection();
                PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, matricule);
            try (ResultSet rs = pstmt.executeQuery()) {
                return rs.next();
            }
        }
    }
}
