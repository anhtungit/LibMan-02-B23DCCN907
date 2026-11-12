package libman.view.staff;

import libman.controller.ReturnController7;

import javax.swing.*;
import java.awt.*;

public class ReaderSearchView7 extends JFrame {
    private JTextField txtReaderCode;
    private JButton btnSearch;
    private ReturnController7 controller;

    public ReaderSearchView7() {
        initComponents();
    }

    public void setController(ReturnController7 controller) {
        this.controller = controller;
    }

    private void initComponents() {
        setTitle("Nhận trả tài liệu");
        setSize(450, 180);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout(10, 10));

        JLabel lblTitle = new JLabel("Nhận trả tài liệu", SwingConstants.CENTER);
        lblTitle.setFont(new Font("Arial", Font.BOLD, 16));
        lblTitle.setBorder(BorderFactory.createEmptyBorder(15, 10, 5, 10));
        add(lblTitle, BorderLayout.NORTH);

        JPanel pnlCenter = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 15));
        pnlCenter.add(new JLabel("Mã thẻ / Mã bạn đọc:"));
        txtReaderCode = new JTextField(15);
        txtReaderCode.setFont(new Font("Arial", Font.PLAIN, 14));
        pnlCenter.add(txtReaderCode);

        btnSearch = new JButton("Tìm kiếm");
        btnSearch.setFont(new Font("Arial", Font.BOLD, 13));
        btnSearch.addActionListener(e -> onClickSearch());
        pnlCenter.add(btnSearch);

        add(pnlCenter, BorderLayout.CENTER);

        txtReaderCode.addActionListener(e -> onClickSearch());
    }

    public void showSearchForm() {
        setVisible(true);
    }

    public String inputCode() {
        return txtReaderCode.getText().trim();
    }

    public String scanCard() {
        return inputCode();
    }

    public void onClickSearch() {
        String code = inputCode();
        if (code.isEmpty()) {
            showError("Vui lòng nhập hoặc quét mã thẻ / mã bạn đọc!");
            return;
        }
        if (controller != null) {
            controller.searchReader(code);
        }
    }

    public void showError(String msg) {
        JOptionPane.showMessageDialog(this, msg, "Thông báo lỗi", JOptionPane.ERROR_MESSAGE);
    }
}

