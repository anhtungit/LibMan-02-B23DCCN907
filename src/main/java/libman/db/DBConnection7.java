package libman.db;

import javax.swing.*;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.sql.*;
import java.util.Properties;

public class DBConnection7 {
    private static String url = "jdbc:mysql://localhost:3306/libman7_db?useSSL=false&allowPublicKeyRetrieval=true&serverTimezone=UTC&characterEncoding=UTF-8";
    private static String serverUrl = "jdbc:mysql://localhost:3306/?useSSL=false&allowPublicKeyRetrieval=true&serverTimezone=UTC&characterEncoding=UTF-8";
    private static String user = "root";
    private static String password = "12345";
    private static boolean isInitialized = false;

    static {
        loadConfig();
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public static void loadConfig() {
        try (InputStream in = DBConnection7.class.getClassLoader().getResourceAsStream("db.properties")) {
            if (in != null) {
                Properties props = new Properties();
                props.load(in);
                url = props.getProperty("db.url", url);
                user = props.getProperty("db.user", user);
                password = props.getProperty("db.password", password);

                // Suy ra serverUrl (kết nối trực tiếp tới MySQL Server khi chưa có database)
                int dbIndex = url.indexOf("/libman7_db");
                if (dbIndex != -1) {
                    serverUrl = url.substring(0, dbIndex + 1) + url.substring(dbIndex + "/libman7_db".length());
                }
            }
        } catch (Exception ignored) {}
    }

    public static synchronized Connection getConnection() throws SQLException {
        if (!isInitialized) {
            initializeDatabaseIfNecessary();
        }
        return DriverManager.getConnection(url, user, password);
    }

    public static synchronized void initializeDatabaseIfNecessary() {
        if (isInitialized) return;

        // 1. Thử kết nối trực tiếp đến database libman7_db
        try (Connection conn = DriverManager.getConnection(url, user, password)) {
            try (Statement st = conn.createStatement();
                 ResultSet rs = st.executeQuery("SHOW TABLES LIKE 'tblUser7'")) {
                if (rs.next()) {
                    isInitialized = true;
                    return; // Cơ sở dữ liệu và bảng đã có sẵn, dùng luôn
                }
            }
        } catch (SQLException ex) {
            // Chưa có CSDL hoặc lỗi mật khẩu -> tiến hành tự động tạo CSDL
        }

        // 2. Tự động kết nối máy chủ MySQL và khởi tạo
        autoCreateAndSeedDatabase();
        isInitialized = true;
    }

    private static void autoCreateAndSeedDatabase() {
        Connection serverConn = null;

        // Thử kết nối server với mật khẩu hiện tại
        try {
            serverConn = DriverManager.getConnection(serverUrl, user, password);
        } catch (SQLException e) {
            // Nếu lỗi mật khẩu (Access denied), cho phép người dùng nhập mật khẩu máy họ
            String inputPass = (String) JOptionPane.showInputDialog(
                    null,
                    "Không thể kết nối MySQL với mật khẩu mặc định '" + password + "'.\n" +
                    "Vui lòng nhập mật khẩu tài khoản 'root' MySQL trên máy của bạn:",
                    "Cấu hình kết nối MySQL",
                    JOptionPane.QUESTION_MESSAGE,
                    null,
                    null,
                    password
            );

            if (inputPass != null) {
                password = inputPass.trim();
                try {
                    serverConn = DriverManager.getConnection(serverUrl, user, password);
                } catch (SQLException ex) {
                    JOptionPane.showMessageDialog(null,
                            "Kết nối MySQL thất bại: " + ex.getMessage() +
                            "\nVui lòng kiểm tra lại dịch vụ MySQL Server!",
                            "Lỗi kết nối CSDL", JOptionPane.ERROR_MESSAGE);
                    return;
                }
            } else {
                return;
            }
        }

        if (serverConn != null) {
            try {
                // Tạo CSDL nếu chưa có
                try (Statement st = serverConn.createStatement()) {
                    st.executeUpdate("CREATE DATABASE IF NOT EXISTS libman7_db CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;");
                }
                serverConn.close();

                // Kết nối vào libman7_db vừa tạo để chạy Schema & Seed data
                try (Connection dbConn = DriverManager.getConnection(url, user, password)) {
                    executeSqlScript(dbConn, "sql/LibMan7_Schema.sql");
                    executeSqlScript(dbConn, "sql/seed_data.sql");
                    System.out.println(">>> Đã tự động khởi tạo và nạp dữ liệu mẫu vào CSDL libman7_db thành công!");
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public static void executeSqlScript(Connection conn, String resourcePath) {
        try (InputStream in = DBConnection7.class.getClassLoader().getResourceAsStream(resourcePath)) {
            if (in == null) {
                System.err.println("Không tìm thấy tài nguyên SQL: " + resourcePath);
                return;
            }

            try (BufferedReader reader = new BufferedReader(new InputStreamReader(in, StandardCharsets.UTF_8))) {
                StringBuilder sb = new StringBuilder();
                String line;
                while ((line = reader.readLine()) != null) {
                    String trimmed = line.trim();
                    if (trimmed.startsWith("--") || trimmed.isEmpty() || trimmed.startsWith("/*")) {
                        continue;
                    }
                    sb.append(line).append("\n");
                    if (trimmed.endsWith(";")) {
                        String sql = sb.toString().trim();
                        if (sql.endsWith(";")) {
                            sql = sql.substring(0, sql.length() - 1);
                        }
                        if (!sql.isEmpty()) {
                            try (Statement st = conn.createStatement()) {
                                st.execute(sql);
                            } catch (SQLException ignored) {
                                // Bỏ qua các lỗi xóa bảng khi chưa tồn tại
                            }
                        }
                        sb.setLength(0);
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
