package libman.view;

import libman.dao.UserDAO7;
import libman.entity.Librarian7;
import libman.entity.Manager7;
import libman.entity.Reader7;
import libman.entity.User7;
import libman.view.manager.ManagerHomeView7;
import libman.view.reader.ReaderHomeView7;
import libman.view.staff.LibrarianHomeView7;

import javax.swing.*;
import java.awt.*;

public class LoginView7 extends JFrame {
    private JTextField txtUsername;
    private JPasswordField txtPassword;
    private JButton btnLogin;
    private JButton btnExit;
    private UserDAO7 userDAO;

    public LoginView7() {
        this.userDAO = new UserDAO7();
        initComponents();
    }

    private void initComponents() {
        setTitle("LibMan7 - Đăng Nhập Hệ Thống");
        setSize(480, 360);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout(10, 10));

        JPanel pnlHeader = new JPanel(new GridLayout(2, 1, 5, 5));
        pnlHeader.setBorder(BorderFactory.createEmptyBorder(20, 10, 10, 10));

        JLabel lblTitle = new JLabel("ĐĂNG NHẬP HỆ THỐNG", SwingConstants.CENTER);
        lblTitle.setFont(new Font("Arial", Font.BOLD, 18));
        lblTitle.setForeground(new Color(25, 65, 130));

        pnlHeader.add(lblTitle);
        add(pnlHeader, BorderLayout.NORTH);

        JPanel pnlForm = new JPanel(new GridBagLayout());
        pnlForm.setBorder(BorderFactory.createEmptyBorder(10, 30, 10, 30));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(8, 8, 8, 8);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        gbc.gridx = 0; gbc.gridy = 0; gbc.weightx = 0.3;
        pnlForm.add(new JLabel("Tên đăng nhập:"), gbc);

        gbc.gridx = 1; gbc.gridy = 0; gbc.weightx = 0.7;
        txtUsername = new JTextField(15);
        pnlForm.add(txtUsername, gbc);

        gbc.gridx = 0; gbc.gridy = 1; gbc.weightx = 0.3;
        pnlForm.add(new JLabel("Mật khẩu:"), gbc);

        gbc.gridx = 1; gbc.gridy = 1; gbc.weightx = 0.7;
        txtPassword = new JPasswordField(15);
        pnlForm.add(txtPassword, gbc);

        add(pnlForm, BorderLayout.CENTER);

        JPanel pnlButtons = new JPanel(new FlowLayout(FlowLayout.RIGHT, 15, 15));
        btnLogin = new JButton("Đăng nhập");
        btnExit = new JButton("Thoát");

        btnLogin.setFont(new Font("Arial", Font.BOLD, 13));
        btnLogin.addActionListener(e -> onLogin());
        txtPassword.addActionListener(e -> onLogin());
        txtUsername.addActionListener(e -> onLogin());

        btnExit.addActionListener(e -> System.exit(0));

        pnlButtons.add(btnLogin);
        pnlButtons.add(btnExit);
        add(pnlButtons, BorderLayout.SOUTH);
    }

    private void onLogin() {
        String username = txtUsername.getText().trim();
        String password = new String(txtPassword.getPassword()).trim();

        if (username.isEmpty() || password.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Vui lòng nhập đầy đủ tên đăng nhập và mật khẩu!", "Lỗi", JOptionPane.ERROR_MESSAGE);
            return;
        }

        User7 user = userDAO.login(username, password);
        if (user == null) {
            JOptionPane.showMessageDialog(this, "Tên đăng nhập hoặc mật khẩu không chính xác!", "Đăng nhập thất bại", JOptionPane.ERROR_MESSAGE);
            return;
        }

        dispose(); // Đóng màn hình login

        if ("LIBRARIAN".equalsIgnoreCase(user.getRole())) {
            new LibrarianHomeView7((Librarian7) user).setVisible(true);
        } else if ("READER".equalsIgnoreCase(user.getRole())) {
            new ReaderHomeView7((Reader7) user).setVisible(true);
        } else if ("MANAGER".equalsIgnoreCase(user.getRole())) {
            new ManagerHomeView7((Manager7) user).setVisible(true);
        } else {
            JOptionPane.showMessageDialog(null, "Vai trò không xác định: " + user.getRole(), "Thông báo", JOptionPane.WARNING_MESSAGE);
        }
    }

    public static void main(String[] args) {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception ignored) {}

        SwingUtilities.invokeLater(() -> new LoginView7().setVisible(true));
    }
}

