package libman.view.manager;

import libman.entity.Manager7;
import libman.view.LoginView7;

import javax.swing.*;
import java.awt.*;

public class ManagerHomeView7 extends JFrame {
    private Manager7 manager;

    public ManagerHomeView7(Manager7 manager) {
        this.manager = manager;
        initComponents();
    }

    private void initComponents() {
        setTitle("LibMan7 - Bàn làm việc Người quản lý");
        setSize(700, 480);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout(15, 15));

        // Menu Bar
        JMenuBar menuBar = new JMenuBar();

        JMenu mnuSystem = new JMenu("Hệ thống");
        JMenuItem mniLogout = new JMenuItem("Đăng xuất");
        JMenuItem mniExit = new JMenuItem("Thoát");
        mniLogout.addActionListener(e -> logout());
        mniExit.addActionListener(e -> System.exit(0));
        mnuSystem.add(mniLogout);
        mnuSystem.addSeparator();
        mnuSystem.add(mniExit);

        JMenu mnuReports = new JMenu("Báo cáo thống kê");
        JMenuItem mniDocStats = new JMenuItem("Thống kê tài liệu theo số lần mượn");
        JMenuItem mniReaderStats = new JMenuItem("Thống kê độc giả theo số lần mượn");
        JMenuItem mniSupplierStats = new JMenuItem("Thống kê nhà cung cấp theo số lượng nhập");

        mniDocStats.addActionListener(e -> showUnderDevelopmentMessage("Thống kê tài liệu theo số lần mượn"));
        mniReaderStats.addActionListener(e -> showUnderDevelopmentMessage("Thống kê độc giả theo số lần mượn"));
        mniSupplierStats.addActionListener(e -> showUnderDevelopmentMessage("Thống kê nhà cung cấp theo số lượng tài liệu nhập"));

        mnuReports.add(mniDocStats);
        mnuReports.add(mniReaderStats);
        mnuReports.add(mniSupplierStats);

        menuBar.add(mnuSystem);
        menuBar.add(mnuReports);
        setJMenuBar(menuBar);

        // Header Panel
        JPanel pnlHeader = new JPanel(new GridLayout(2, 1, 5, 5));
        pnlHeader.setBorder(BorderFactory.createEmptyBorder(20, 20, 10, 20));

        JLabel lblTitle = new JLabel("HỆ THỐNG QUẢN LÝ - LIBMAN7", SwingConstants.CENTER);
        lblTitle.setFont(new Font("Arial", Font.BOLD, 18));
        lblTitle.setForeground(new Color(130, 40, 30));

        String welcomeText = manager != null ?
                String.format("Người quản lý: %s (Mã QL: %s)", manager.getFullName(), manager.getManagerCode()) :
                "Người quản lý";
        JLabel lblWelcome = new JLabel(welcomeText, SwingConstants.CENTER);
        lblWelcome.setFont(new Font("Arial", Font.PLAIN, 13));

        pnlHeader.add(lblTitle);
        pnlHeader.add(lblWelcome);
        add(pnlHeader, BorderLayout.NORTH);

        // Center Dashboard Grid
        JPanel pnlCenter = new JPanel(new GridLayout(2, 2, 20, 20));
        pnlCenter.setBorder(BorderFactory.createEmptyBorder(15, 40, 15, 40));

        JButton btnDocStats = createMenuButton("Thống kê tài liệu theo số lần mượn", e ->
                showUnderDevelopmentMessage("Thống kê tài liệu theo số lần mượn"));

        JButton btnReaderStats = createMenuButton("Thống kê độc giả theo số lần mượn", e ->
                showUnderDevelopmentMessage("Thống kê độc giả theo số lần mượn"));

        JButton btnSupplierStats = createMenuButton("Thống kê NCC theo số lượng nhập", e ->
                showUnderDevelopmentMessage("Thống kê nhà cung cấp theo số lượng tài liệu nhập"));

        JButton btnOverviewStats = createMenuButton("Báo cáo tổng hợp hoạt động thư viện", e ->
                showUnderDevelopmentMessage("Báo cáo tổng hợp hoạt động thư viện"));

        pnlCenter.add(btnDocStats);
        pnlCenter.add(btnReaderStats);
        pnlCenter.add(btnSupplierStats);
        pnlCenter.add(btnOverviewStats);
        add(pnlCenter, BorderLayout.CENTER);

        // Footer
        JPanel pnlFooter = new JPanel(new FlowLayout(FlowLayout.RIGHT, 15, 10));
        JButton btnLogout = new JButton("Đăng xuất");
        btnLogout.addActionListener(e -> logout());
        pnlFooter.add(btnLogout);
        add(pnlFooter, BorderLayout.SOUTH);
    }

    private JButton createMenuButton(String title, java.awt.event.ActionListener listener) {
        JButton btn = new JButton("<html><center>" + title + "</center></html>");
        btn.setFont(new Font("Arial", Font.BOLD, 14));
        btn.setBackground(new Color(250, 245, 240));
        btn.setFocusPainted(false);
        btn.addActionListener(listener);
        return btn;
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

