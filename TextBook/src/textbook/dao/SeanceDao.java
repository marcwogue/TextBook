package textbook.dao;

import textbook.model.Seance;
import textbook.util.DatabaseUtil;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class SeanceDao {

    public void insert(Seance seance) throws SQLException {
        String sql = "INSERT INTO seance (code_ue, matricule, date_seance, heure_debut, heure_fin, salle, resume) VALUES (?, ?, ?, ?, ?, ?, ?)";
        try (Connection conn = DatabaseUtil.getAppConnection();
                PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, seance.getCodeUE());
            pstmt.setString(2, seance.getMatricule());
            pstmt.setDate(3, seance.getDateSeance());
            pstmt.setTime(4, seance.getHeureDebut());
            pstmt.setTime(5, seance.getHeureFin());
            pstmt.setString(6, seance.getSalle());
            pstmt.setString(7, seance.getResume());
            pstmt.executeUpdate();
        }
    }

    public List<Seance> findAllWithDetails() throws SQLException {
        List<Seance> seances = new ArrayList<>();
        String sql = "SELECT s.*, u.intitule, e.nom, e.prenom " +
                "FROM seance s " +
                "JOIN ue u ON s.code_ue = u.code_ue " +
                "JOIN enseignant e ON s.matricule = e.matricule " +
                "ORDER BY s.date_seance DESC, s.heure_debut DESC";
        try (Connection conn = DatabaseUtil.getAppConnection();
                Statement stmt = conn.createStatement();
                ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                Seance s = new Seance(
                        rs.getInt("id_seance"),
                        rs.getString("code_ue"),
                        rs.getString("matricule"),
                        rs.getDate("date_seance"),
                        rs.getTime("heure_debut"),
                        rs.getTime("heure_fin"),
                        rs.getString("salle"),
                        rs.getString("resume"));
                s.setUeIntitule(rs.getString("intitule"));
                s.setEnseignantNomComplet(rs.getString("nom") + " " + rs.getString("prenom"));
                seances.add(s);
            }
        }
        return seances;
    }

    public void delete(int idSeance) throws SQLException {
        String sql = "DELETE FROM seance WHERE id_seance = ?";
        try (Connection conn = DatabaseUtil.getAppConnection();
                PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, idSeance);
            pstmt.executeUpdate();
        }
    }
}
