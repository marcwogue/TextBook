package textbook.dao;

import textbook.model.UE;
import textbook.util.DatabaseUtil;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UEDao {

    public void insert(UE ue) throws SQLException {
        String sql = "INSERT INTO ue (code_ue, intitule) VALUES (?, ?)";
        try (Connection conn = DatabaseUtil.getAppConnection();
                PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, ue.getCodeUE());
            pstmt.setString(2, ue.getIntitule());
            pstmt.executeUpdate();
        }
    }

    public List<UE> findAll() throws SQLException {
        List<UE> ues = new ArrayList<>();
        String sql = "SELECT * FROM ue ORDER BY code_ue";
        try (Connection conn = DatabaseUtil.getAppConnection();
                Statement stmt = conn.createStatement();
                ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                ues.add(new UE(rs.getString("code_ue"), rs.getString("intitule")));
            }
        }
        return ues;
    }

    public void delete(String codeUE) throws SQLException {
        String sql = "DELETE FROM ue WHERE code_ue = ?";
        try (Connection conn = DatabaseUtil.getAppConnection();
                PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, codeUE);
            pstmt.executeUpdate();
        }
    }

    public void update(UE ue) throws SQLException {
        String sql = "UPDATE ue SET intitule = ? WHERE code_ue = ?";
        try (Connection conn = DatabaseUtil.getAppConnection();
                PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, ue.getIntitule());
            pstmt.setString(2, ue.getCodeUE());
            pstmt.executeUpdate();
        }
    }

    public boolean exists(String codeUE) throws SQLException {
        String sql = "SELECT 1 FROM ue WHERE code_ue = ?";
        try (Connection conn = DatabaseUtil.getAppConnection();
                PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, codeUE);
            try (ResultSet rs = pstmt.executeQuery()) {
                return rs.next();
            }
        }
    }
}
