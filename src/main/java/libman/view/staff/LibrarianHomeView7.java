package libman.view.staff;

import libman.controller.ReturnController7;
import libman.entity.Librarian7;
import libman.view.LoginView7;

import javax.swing.*;
import java.awt.*;

public class LibrarianHomeView7 extends JFrame {
    private Librarian7 librarian;

    public LibrarianHomeView7(Librarian7 librarian) {
        this.librarian = librarian;
        initComponents();
    }

    private void initComponents() {
        setTitle("LibMan7 - Bàn làm việc Thủ thư");
        setSize(780, 520);
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

        // 2. Nghiệp vụ quầy
        JMenu mnuCounter = new JMenu("Nghiệp vụ quầy");
        JMenuItem mniReturnDocs = new JMenuItem("Nhận trả tài liệu từ bạn đọc");
        JMenuItem mniLendDocs = new JMenuItem("Cho mượn tài liệu");
        mniReturnDocs.addActionListener(e -> openReturnModule());
        mniLendDocs.addActionListener(e -> showUnderDevelopmentMessage("Cho mượn tài liệu"));
        mnuCounter.add(mniReturnDocs);
        mnuCounter.add(mniLendDocs);

        // 3. Quản lý danh mục
        JMenu mnuCatalog = new JMenu("Quản lý danh mục");
        JMenuItem mniManageDocs = new JMenuItem("Cập nhật tài liệu (Sách, Giáo trình, Tạp chí)");
        JMenuItem mniManageReaders = new JMenuItem("Cập nhật thông tin bạn đọc");
        JMenuItem mniManageSuppliers = new JMenuItem("Cập nhật nhà cung cấp");
        mniManageDocs.addActionListener(e -> showUnderDevelopmentMessage("Cập nhật tài liệu"));
        mniManageReaders.addActionListener(e -> showUnderDevelopmentMessage("Cập nhật thông tin bạn đọc"));
        mniManageSuppliers.addActionListener(e -> showUnderDevelopmentMessage("Cập nhật nhà cung cấp"));
        mnuCatalog.add(mniManageDocs);
        mnuCatalog.add(mniManageReaders);
        mnuCatalog.add(mniManageSuppliers);

        // 4. Nhập kho & Tra cứu
        JMenu mnuWarehouse = new JMenu("Nhập kho & Tra cứu");
        JMenuItem mniImportDocs = new JMenuItem("Nhập tài liệu từ nhà cung cấp");
        JMenuItem mniSearchDocs = new JMenuItem("Tra cứu & Tìm kiếm thông tin");
        mniImportDocs.addActionListener(e -> showUnderDevelopmentMessage("Nhập tài liệu từ nhà cung cấp"));
        mniSearchDocs.addActionListener(e -> showUnderDevelopmentMessage("Tra cứu & Tìm kiếm thông tin"));
        mnuWarehouse.add(mniImportDocs);
        mnuWarehouse.add(mniSearchDocs);

        menuBar.add(mnuSystem);
        menuBar.add(mnuCounter);
        menuBar.add(mnuCatalog);
        menuBar.add(mnuWarehouse);
        setJMenuBar(menuBar);

        // Header Panel
        JPanel pnlHeader = new JPanel(new GridLayout(2, 1, 5, 5));
        pnlHeader.setBorder(BorderFactory.createEmptyBorder(15, 20, 5, 20));

        JLabel lblTitle = new JLabel("HỆ THỐNG THỦ THƯ - LIBMAN7", SwingConstants.CENTER);
        lblTitle.setFont(new Font("Arial", Font.BOLD, 18));
        lblTitle.setForeground(new Color(20, 110, 50));

        String welcomeText = librarian != null ?
                String.format("Thủ thư: %s (Mã NV: %s | Chức vụ: %s)", librarian.getFullName(), librarian.getStaffCode(), librarian.getPosition() != null ? librarian.getPosition() : "Thủ thư chính") :
                "Thủ thư trực quầy";
        JLabel lblWelcome = new JLabel(welcomeText, SwingConstants.CENTER);
        lblWelcome.setFont(new Font("Arial", Font.PLAIN, 13));

        pnlHeader.add(lblTitle);
        pnlHeader.add(lblWelcome);
        add(pnlHeader, BorderLayout.NORTH);

        // Center Dashboard Grid
        JPanel pnlCenter = new JPanel(new GridLayout(3, 2, 15, 15));
        pnlCenter.setBorder(BorderFactory.createEmptyBorder(10, 30, 10, 30));

        JButton btnReturnDocs = createMenuButton("Nhận trả tài liệu từ bạn đọc", e -> openReturnModule());

        // Các nút chức năng khác
        JButton btnLendDocs = createMenuButton("Cho mượn tài liệu", e ->
                showUnderDevelopmentMessage("Cho mượn tài liệu"));

        JButton btnManageDocs = createMenuButton("Cập nhật tài liệu (Sách, GT, TC)", e ->
                showUnderDevelopmentMessage("Cập nhật tài liệu"));

        JButton btnManageReaders = createMenuButton("Cập nhật thông tin bạn đọc", e ->
                showUnderDevelopmentMessage("Cập nhật thông tin bạn đọc"));

        JButton btnManageSuppliers = createMenuButton("Cập nhật nhà cung cấp", e ->
                showUnderDevelopmentMessage("Cập nhật nhà cung cấp"));

        JButton btnImportDocs = createMenuButton("Nhập tài liệu từ nhà cung cấp", e ->
                showUnderDevelopmentMessage("Nhập tài liệu từ nhà cung cấp"));

        pnlCenter.add(btnReturnDocs);
        pnlCenter.add(btnLendDocs);
        pnlCenter.add(btnManageDocs);
        pnlCenter.add(btnManageReaders);
        pnlCenter.add(btnManageSuppliers);
        pnlCenter.add(btnImportDocs);

        add(pnlCenter, BorderLayout.CENTER);

        // Footer Panel
        JPanel pnlFooter = new JPanel(new BorderLayout());
        pnlFooter.setBorder(BorderFactory.createEmptyBorder(5, 30, 15, 30));

        JButton btnSearch = new JButton("Tra cứu & Tìm kiếm thông tin");
        btnSearch.setFont(new Font("Arial", Font.PLAIN, 12));
        btnSearch.addActionListener(e -> showUnderDevelopmentMessage("Tra cứu & Tìm kiếm thông tin"));

        JButton btnLogout = new JButton("Đăng xuất");
        btnLogout.addActionListener(e -> logout());

        pnlFooter.add(btnSearch, BorderLayout.WEST);
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

    private void openReturnModule() {
        ReturnController7 ctrl = new ReturnController7(this.librarian);
        ctrl.start();
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
