package libman.view.reader;

import libman.entity.Reader7;
import libman.view.LoginView7;

import javax.swing.*;
import java.awt.*;

public class ReaderHomeView7 extends JFrame {
    private Reader7 reader;

    public ReaderHomeView7(Reader7 reader) {
        this.reader = reader;
        initComponents();
    }

    private void initComponents() {
        setTitle("LibMan7 - Cổng thông tin bạn đọc");
        setSize(720, 480);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout(15, 15));

        // JMenuBar
        JMenuBar menuBar = new JMenuBar();

        // 1. Hệ thống
        JMenu mnuSystem = new JMenu("Hệ thống");
        JMenuItem mniLogout = new JMenuItem("Đăng xuất");
        JMenuItem mniExit = new JMenuItem("Thoát");
        mniLogout.addActionListener(e -> logout());
        mniExit.addActionListener(e -> System.exit(0));
        mnuSystem.add(mniLogout);
        mnuSystem.addSeparator();
        mnuSystem.add(mniExit);

        // 2. Dịch vụ thẻ
        JMenu mnuCard = new JMenu("Dịch vụ thẻ");
        JMenuItem mniRegisterCard = new JMenuItem("Đăng ký làm thẻ bạn đọc trực tuyến");
        mniRegisterCard.addActionListener(e -> openRegisterCardModule());
        mnuCard.add(mniRegisterCard);

        // 3. Tra cứu & Mượn trả
        JMenu mnuBorrow = new JMenu("Tra cứu & Mượn trả");
        JMenuItem mniSearchDocs = new JMenuItem("Tìm kiếm thông tin tài liệu");
        JMenuItem mniBorrowDocs = new JMenuItem("Mượn tài liệu");
        JMenuItem mniReturnDocs = new JMenuItem("Trả tài liệu trực tiếp với nhân viên");
        JMenuItem mniHistory = new JMenuItem("Lịch sử mượn trả");

        mniSearchDocs.addActionListener(e -> showUnderDevelopmentMessage("Tìm kiếm thông tin tài liệu"));
        mniBorrowDocs.addActionListener(e -> showUnderDevelopmentMessage("Mượn tài liệu"));
        mniReturnDocs.addActionListener(e -> showUnderDevelopmentMessage("Trả tài liệu trực tiếp với nhân viên"));
        mniHistory.addActionListener(e -> showUnderDevelopmentMessage("Lịch sử mượn trả"));

        mnuBorrow.add(mniSearchDocs);
        mnuBorrow.add(mniBorrowDocs);
        mnuBorrow.add(mniReturnDocs);
        mnuBorrow.add(mniHistory);

        menuBar.add(mnuSystem);
        menuBar.add(mnuCard);
        menuBar.add(mnuBorrow);
        setJMenuBar(menuBar);

        // Header Panel
        JPanel pnlHeader = new JPanel(new GridLayout(2, 1, 5, 5));
        pnlHeader.setBorder(BorderFactory.createEmptyBorder(15, 20, 5, 20));

        JLabel lblTitle = new JLabel("CỔNG THÔNG TIN BẠN ĐỌC - LIBMAN7", SwingConstants.CENTER);
        lblTitle.setFont(new Font("Arial", Font.BOLD, 18));
        lblTitle.setForeground(new Color(25, 65, 130));

        String welcomeText = reader != null ?
                String.format("Xin chào: %s (Mã ĐG: %s | Khoa: %s | Đối tượng: %s)",
                        reader.getFullName(), reader.getReaderCode(),
                        reader.getFaculty() != null ? reader.getFaculty() : "N/A",
                        reader.getReaderType() != null ? reader.getReaderType() : "Sinh viên") :
                "Xin chào Bạn đọc";
        JLabel lblWelcome = new JLabel(welcomeText, SwingConstants.CENTER);
        lblWelcome.setFont(new Font("Arial", Font.PLAIN, 13));

        pnlHeader.add(lblTitle);
        pnlHeader.add(lblWelcome);
        add(pnlHeader, BorderLayout.NORTH);

        // Center Dashboard Grid
        JPanel pnlCenter = new JPanel(new GridLayout(2, 2, 20, 20));
        pnlCenter.setBorder(BorderFactory.createEmptyBorder(15, 35, 15, 35));

        JButton btnRegisterCard = createMenuButton("Đăng ký làm thẻ bạn đọc trực tuyến", e -> openRegisterCardModule());

        // Các chức năng khác
        JButton btnSearch = createMenuButton("Tìm kiếm thông tin tài liệu", e ->
                showUnderDevelopmentMessage("Tìm kiếm thông tin tài liệu"));

        JButton btnBorrow = createMenuButton("Mượn tài liệu trực tiếp", e ->
                showUnderDevelopmentMessage("Mượn tài liệu trực tiếp"));

        JButton btnReturn = createMenuButton("Trả tài liệu trực tiếp với nhân viên", e ->
                showUnderDevelopmentMessage("Trả tài liệu trực tiếp với nhân viên"));

        pnlCenter.add(btnRegisterCard);
        pnlCenter.add(btnSearch);
        pnlCenter.add(btnBorrow);
        pnlCenter.add(btnReturn);
        add(pnlCenter, BorderLayout.CENTER);

        // Footer Panel
        JPanel pnlFooter = new JPanel(new BorderLayout());
        pnlFooter.setBorder(BorderFactory.createEmptyBorder(5, 35, 15, 35));

        JButton btnHistory = new JButton("Xem lịch sử mượn trả tài liệu");
        btnHistory.setFont(new Font("Arial", Font.PLAIN, 12));
        btnHistory.addActionListener(e -> showUnderDevelopmentMessage("Xem lịch sử mượn trả tài liệu"));

        JButton btnLogout = new JButton("Đăng xuất");
        btnLogout.addActionListener(e -> logout());

        pnlFooter.add(btnHistory, BorderLayout.WEST);
        pnlFooter.add(btnLogout, BorderLayout.EAST);
        add(pnlFooter, BorderLayout.SOUTH);
    }

    private JButton createMenuButton(String title, java.awt.event.ActionListener listener) {
        JButton btn = new JButton("<html><center>" + title + "</center></html>");
        btn.setFont(new Font("Arial", Font.PLAIN, 13));
        btn.setBackground(new Color(245, 245, 245));
        btn.setFocusPainted(false);
        btn.addActionListener(listener);
        return btn;
    }

    private void openRegisterCardModule() {
        RegisterReaderView7 regView = new RegisterReaderView7(this.reader);
        regView.showRegisterForm();
    }

    private void showUnderDevelopmentMessage(String featureName) {
        JOptionPane.showMessageDialog(
                this,
                "Chức năng \"" + featureName + "\" chưa hoàn thiện!\nVui lòng chọn chức năng khác.",
                "Thông báo",
                JOptionPane.INFORMATION_MESSAGE
        );
    }

    private void logout() {
        dispose();
        new LoginView7().setVisible(true);
    }
}
