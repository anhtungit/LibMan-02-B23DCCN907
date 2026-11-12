package libman.view.reader;

import libman.controller.RegisterReaderController7;
import libman.dto.CardDTO;
import libman.dto.ReaderDTO;
import libman.entity.Reader7;

import javax.swing.*;
import java.awt.*;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class RegisterReaderView7 extends JFrame {
    private JTextField txtReaderCode;
    private JTextField txtFullName;
    private JTextField txtDob;
    private JTextField txtAddress;
    private JTextField txtEmail;
    private JTextField txtPhone;
    private JTextField txtFaculty;
    private JComboBox<String> cbReaderType;

    private JTextField txtCardCode;
    private JTextField txtExpiryMonths;

    private JButton btnSubmit;
    private JButton btnCancel;
    private RegisterReaderController7 controller;
    private Reader7 currentReader;

    public RegisterReaderView7() {
        this(null);
    }

    public RegisterReaderView7(Reader7 loggedInReader) {
        this.currentReader = loggedInReader;
        this.controller = new RegisterReaderController7(this);
        initComponents();
        bindReaderData();
    }

    private void initComponents() {
        setTitle("Đăng ký làm thẻ bạn đọc");
        setSize(520, 500);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout(10, 10));

        JLabel lblTitle = new JLabel("Đăng ký làm thẻ bạn đọc", SwingConstants.CENTER);
        lblTitle.setFont(new Font("Arial", Font.BOLD, 17));
        lblTitle.setBorder(BorderFactory.createEmptyBorder(15, 10, 5, 10));
        add(lblTitle, BorderLayout.NORTH);

        JPanel pnlForm = new JPanel(new GridLayout(10, 2, 8, 8));
        pnlForm.setBorder(BorderFactory.createEmptyBorder(10, 25, 10, 25));

        txtReaderCode = new JTextField();
        txtFullName = new JTextField();
        txtDob = new JTextField("2004-01-01");
        txtAddress = new JTextField("Hà Nội");
        txtEmail = new JTextField();
        txtPhone = new JTextField();
        txtFaculty = new JTextField("Công nghệ Thông tin");
        cbReaderType = new JComboBox<>(new String[]{"Sinh viên", "Giảng viên", "Học viên cao học", "Khác"});

        txtCardCode = new JTextField();
        txtCardCode.setEditable(false);
        txtCardCode.setBackground(new Color(245, 245, 245));
        txtExpiryMonths = new JTextField("12");

        pnlForm.add(new JLabel("Mã bạn đọc (*):"));
        pnlForm.add(txtReaderCode);

        pnlForm.add(new JLabel("Họ và tên bạn đọc (*):"));
        pnlForm.add(txtFullName);

        pnlForm.add(new JLabel("Khoa / Đơn vị:"));
        pnlForm.add(txtFaculty);

        pnlForm.add(new JLabel("Loại bạn đọc:"));
        pnlForm.add(cbReaderType);

        pnlForm.add(new JLabel("Ngày sinh (yyyy-MM-dd):"));
        pnlForm.add(txtDob);

        pnlForm.add(new JLabel("Số điện thoại:"));
        pnlForm.add(txtPhone);

        pnlForm.add(new JLabel("Email:"));
        pnlForm.add(txtEmail);

        pnlForm.add(new JLabel("Địa chỉ:"));
        pnlForm.add(txtAddress);

        pnlForm.add(new JLabel("Mã thẻ:"));
        pnlForm.add(txtCardCode);

        pnlForm.add(new JLabel("Thời hạn thẻ (tháng):"));
        pnlForm.add(txtExpiryMonths);

        add(pnlForm, BorderLayout.CENTER);

        JPanel pnlButtons = new JPanel(new FlowLayout(FlowLayout.RIGHT, 15, 15));
        btnSubmit = new JButton("Thêm");
        btnCancel = new JButton("Đóng");

        btnSubmit.setFont(new Font("Arial", Font.BOLD, 13));
        btnSubmit.addActionListener(e -> onClickSubmit());
        btnCancel.addActionListener(e -> dispose());

        pnlButtons.add(btnSubmit);
        pnlButtons.add(btnCancel);
        add(pnlButtons, BorderLayout.SOUTH);
    }

    private void bindReaderData() {
        if (currentReader != null) {
            txtReaderCode.setText(currentReader.getReaderCode());
            txtReaderCode.setEditable(false); // Mã bạn đọc đã đăng nhập không cho sửa
            txtFullName.setText(currentReader.getFullName());
            if (currentReader.getFaculty() != null) txtFaculty.setText(currentReader.getFaculty());
            if (currentReader.getPhone() != null) txtPhone.setText(currentReader.getPhone());
            if (currentReader.getEmail() != null) txtEmail.setText(currentReader.getEmail());
            if (currentReader.getAddress() != null) txtAddress.setText(currentReader.getAddress());
            if (currentReader.getReaderType() != null) cbReaderType.setSelectedItem(currentReader.getReaderType());
            if (currentReader.getDateOfBirth() != null) {
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                txtDob.setText(sdf.format(currentReader.getDateOfBirth()));
            }
        } else {
            txtReaderCode.setText("RD" + System.currentTimeMillis() % 10000);
        }
        if (controller != null) {
            txtCardCode.setText(controller.getNextCardCode());
        }
    }

    public void showRegisterForm() {
        setVisible(true);
    }

    public void onClickSubmit() {
        try {
            ReaderDTO readerInfo = new ReaderDTO();
            readerInfo.setReaderCode(txtReaderCode.getText().trim());
            readerInfo.setFullName(txtFullName.getText().trim());

            if (currentReader != null) {
                readerInfo.setUsername(currentReader.getUsername());
                readerInfo.setPassword(currentReader.getPassword());
            } else {
                readerInfo.setUsername(readerInfo.getReaderCode().toLowerCase());
                readerInfo.setPassword("123456");
            }

            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            try {
                readerInfo.setDateOfBirth(sdf.parse(txtDob.getText().trim()));
            } catch (Exception ex) {
                readerInfo.setDateOfBirth(new Date());
            }

            readerInfo.setAddress(txtAddress.getText().trim());
            readerInfo.setEmail(txtEmail.getText().trim());
            readerInfo.setPhone(txtPhone.getText().trim());
            readerInfo.setFaculty(txtFaculty.getText().trim());
            readerInfo.setReaderType((String) cbReaderType.getSelectedItem());

            CardDTO cardInfo = new CardDTO();
            cardInfo.setCardCode(txtCardCode.getText().trim());
            cardInfo.setRegisterDate(new Date());

            int months = 12;
            try {
                months = Integer.parseInt(txtExpiryMonths.getText().trim());
            } catch (NumberFormatException ignored) {}

            Calendar cal = Calendar.getInstance();
            cal.add(Calendar.MONTH, months);
            cardInfo.setExpiryDate(cal.getTime());

            controller.registerReaderCard(readerInfo, cardInfo);
        } catch (Exception e) {
            showError("Lỗi dữ liệu: " + e.getMessage());
        }
    }

    public void showSuccess(String cardCode, Date expiryDate) {
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        String msg = String.format("<html><b style='color:green;font-size:14px'>Đăng ký làm thẻ bạn đọc thành công!</b><br/><br/>" +
                "Mã thẻ: <b>%s</b><br/>" +
                "Ngày hết hạn: <b>%s</b></html>", cardCode, sdf.format(expiryDate));
        JOptionPane.showMessageDialog(this, msg, "Thành công", JOptionPane.INFORMATION_MESSAGE);
        dispose();
    }

    public void showError(String message) {
        JOptionPane.showMessageDialog(this, message, "Lỗi", JOptionPane.ERROR_MESSAGE);
    }
}

