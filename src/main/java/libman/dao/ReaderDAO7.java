package libman.dao;

import libman.entity.Reader7;
import java.sql.*;

public class ReaderDAO7 extends DAO7 {

    public ReaderDAO7() {
        super();
    }

    public ReaderDAO7(Connection con) {
        super(con);
    }

    public boolean existsByUsernameOrReaderCode(String username, String readerCode) {
        String sql = "SELECT u.userId FROM tblUser7 u " +
                     "LEFT JOIN tblReader7 r ON u.userId = r.userId " +
                     "WHERE u.username = ? OR r.readerCode = ?";
        try (PreparedStatement ps = getConnection().prepareStatement(sql)) {
            ps.setString(1, username);
            ps.setString(2, readerCode);
            try (ResultSet rs = ps.executeQuery()) {
                return rs.next();
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean addReader(Reader7 reader) {
        String insertUser = "INSERT INTO tblUser7 (username, password, fullName, dateOfBirth, address, email, phone, role) " +
                            "VALUES (?, ?, ?, ?, ?, ?, ?, 'READER')";
        String insertReader = "INSERT INTO tblReader7 (userId, readerCode, faculty, readerType) VALUES (?, ?, ?, ?)";

        Connection conn = getConnection();
        boolean autoCommitOrig = true;
        try {
            autoCommitOrig = conn.getAutoCommit();
            conn.setAutoCommit(false);

            int generatedUserId = -1;
            try (PreparedStatement psUser = conn.prepareStatement(insertUser, Statement.RETURN_GENERATED_KEYS)) {
                psUser.setString(1, reader.getUsername());
                psUser.setString(2, reader.getPassword());
                psUser.setString(3, reader.getFullName());
                if (reader.getDateOfBirth() != null) {
                    psUser.setDate(4, new java.sql.Date(reader.getDateOfBirth().getTime()));
                } else {
                    psUser.setNull(4, Types.DATE);
                }
                psUser.setString(5, reader.getAddress());
                psUser.setString(6, reader.getEmail());
                psUser.setString(7, reader.getPhone());
                int rows = psUser.executeUpdate();
                if (rows == 0) {
                    conn.rollback();
                    return false;
                }
                try (ResultSet keys = psUser.getGeneratedKeys()) {
                    if (keys.next()) {
                        generatedUserId = keys.getInt(1);
                        reader.setUserId(generatedUserId);
                    }
                }
            }

            if (generatedUserId <= 0) {
                conn.rollback();
                return false;
            }

            try (PreparedStatement psReader = conn.prepareStatement(insertReader)) {
                psReader.setInt(1, generatedUserId);
                psReader.setString(2, reader.getReaderCode());
                psReader.setString(3, reader.getFaculty());
                psReader.setString(4, reader.getReaderType());
                int rows2 = psReader.executeUpdate();
                if (rows2 == 0) {
                    conn.rollback();
                    return false;
                }
            }

            conn.commit();
            return true;
        } catch (SQLException e) {
            try { conn.rollback(); } catch (SQLException ignored) {}
            e.printStackTrace();
            return false;
        } finally {
            try { conn.setAutoCommit(autoCommitOrig); } catch (SQLException ignored) {}
        }
    }

    public Reader7 getReaderByCode(String code) {
        String sql = "SELECT u.userId, u.username, u.password, u.fullName, u.dateOfBirth, u.address, u.email, u.phone, " +
                     "r.readerCode, r.faculty, r.readerType " +
                     "FROM tblReader7 r " +
                     "JOIN tblUser7 u ON r.userId = u.userId " +
                     "LEFT JOIN tblReaderCard7 c ON r.userId = c.userId " +
                     "WHERE r.readerCode = ? OR c.cardCode = ? OR u.username = ?";
        try (PreparedStatement ps = getConnection().prepareStatement(sql)) {
            ps.setString(1, code);
            ps.setString(2, code);
            ps.setString(3, code);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return new Reader7(
                        rs.getInt("userId"),
                        rs.getString("username"),
                        rs.getString("password"),
                        rs.getString("fullName"),
                        rs.getDate("dateOfBirth"),
                        rs.getString("address"),
                        rs.getString("email"),
                        rs.getString("phone"),
                        rs.getString("readerCode"),
                        rs.getString("faculty"),
                        rs.getString("readerType")
                    );
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
}

