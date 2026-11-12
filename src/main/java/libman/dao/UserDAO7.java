package libman.dao;

import libman.entity.Librarian7;
import libman.entity.Manager7;
import libman.entity.Reader7;
import libman.entity.User7;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UserDAO7 extends DAO7 {

    public UserDAO7() {
        super();
    }

    public UserDAO7(Connection con) {
        super(con);
    }

    public User7 login(String username, String password) {
        String sql = "SELECT userId, username, password, fullName, dateOfBirth, address, email, phone, role " +
                     "FROM tblUser7 WHERE username = ? AND password = ?";
        try (PreparedStatement ps = getConnection().prepareStatement(sql)) {
            ps.setString(1, username);
            ps.setString(2, password);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    int userId = rs.getInt("userId");
                    String role = rs.getString("role");

                    if ("LIBRARIAN".equalsIgnoreCase(role)) {
                        Librarian7 lib = new Librarian7();
                        fillUserInfo(lib, rs);
                        loadLibrarianExtra(lib, userId);
                        return lib;
                    } else if ("READER".equalsIgnoreCase(role)) {
                        Reader7 reader = new Reader7();
                        fillUserInfo(reader, rs);
                        loadReaderExtra(reader, userId);
                        return reader;
                    } else if ("MANAGER".equalsIgnoreCase(role)) {
                        Manager7 mgr = new Manager7();
                        fillUserInfo(mgr, rs);
                        loadManagerExtra(mgr, userId);
                        return mgr;
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    private void fillUserInfo(User7 u, ResultSet rs) throws SQLException {
        u.setUserId(rs.getInt("userId"));
        u.setUsername(rs.getString("username"));
        u.setPassword(rs.getString("password"));
        u.setFullName(rs.getString("fullName"));
        u.setDateOfBirth(rs.getDate("dateOfBirth"));
        u.setAddress(rs.getString("address"));
        u.setEmail(rs.getString("email"));
        u.setPhone(rs.getString("phone"));
        u.setRole(rs.getString("role"));
    }

    private void loadLibrarianExtra(Librarian7 lib, int userId) {
        String sql = "SELECT staffCode, position FROM tblLibrarian7 WHERE userId = ?";
        try (PreparedStatement ps = getConnection().prepareStatement(sql)) {
            ps.setInt(1, userId);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    lib.setStaffCode(rs.getString("staffCode"));
                    lib.setPosition(rs.getString("position"));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void loadReaderExtra(Reader7 r, int userId) {
        String sql = "SELECT readerCode, faculty, readerType FROM tblReader7 WHERE userId = ?";
        try (PreparedStatement ps = getConnection().prepareStatement(sql)) {
            ps.setInt(1, userId);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    r.setReaderCode(rs.getString("readerCode"));
                    r.setFaculty(rs.getString("faculty"));
                    r.setReaderType(rs.getString("readerType"));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void loadManagerExtra(Manager7 mgr, int userId) {
        String sql = "SELECT managerCode FROM tblManager7 WHERE userId = ?";
        try (PreparedStatement ps = getConnection().prepareStatement(sql)) {
            ps.setInt(1, userId);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    mgr.setManagerCode(rs.getString("managerCode"));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}

