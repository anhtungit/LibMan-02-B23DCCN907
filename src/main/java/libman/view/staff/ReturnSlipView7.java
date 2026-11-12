package libman.view.staff;

import libman.controller.ReturnController7;
import libman.entity.ReturnDetail7;
import libman.entity.ReturnSlip7;
import libman.util.PdfExportUtil7;

import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;

public class ReturnSlipView7 extends JFrame {
    private JLabel lblSlipPreview;
    private JEditorPane txtPreviewContent;
    private JButton btnBack;
    private JButton btnConfirmReturn;
    private JButton btnConfirmPayment;
    private JButton btnPrint;
    private JButton btnClose;
    private ReturnController7 controller;

    private ReturnSlip7 currentSlip;
    private double currentTotalFine;

    public ReturnSlipView7() {
        initComponents();
    }

    public void setController(ReturnController7 controller) {
        this.controller = controller;
    }

    private void initComponents() {
        setTitle("Phiếu trả tài liệu");
        setSize(680, 600);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout(10, 10));

        lblSlipPreview = new JLabel("Phiếu trả tài liệu", SwingConstants.CENTER);
        lblSlipPreview.setFont(new Font("Arial", Font.BOLD, 16));
        lblSlipPreview.setBorder(BorderFactory.createEmptyBorder(15, 10, 5, 10));
        add(lblSlipPreview, BorderLayout.NORTH);

        txtPreviewContent = new JEditorPane();
        txtPreviewContent.setContentType("text/html");
        txtPreviewContent.setEditable(false);
        txtPreviewContent.setFont(new Font("Monospaced", Font.PLAIN, 12));

        JScrollPane scrollPane = new JScrollPane(txtPreviewContent);
        scrollPane.setBorder(BorderFactory.createEmptyBorder(0, 15, 0, 15));
        add(scrollPane, BorderLayout.CENTER);

        JPanel pnlBottom = new JPanel(new FlowLayout(FlowLayout.RIGHT, 10, 12));
        btnBack = new JButton("Quay lại");
        btnConfirmReturn = new JButton("Xác nhận trả");
        btnConfirmReturn.setFont(new Font("Arial", Font.BOLD, 13));
        btnConfirmReturn.setBackground(new Color(220, 240, 255));

        btnPrint = new JButton("In phiếu trả");
        btnPrint.setEnabled(false);

        btnConfirmPayment = new JButton("Xác nhận đã thu tiền");
        btnConfirmPayment.setFont(new Font("Arial", Font.BOLD, 13));
        btnConfirmPayment.setBackground(new Color(255, 230, 230));
        btnConfirmPayment.setEnabled(false);

        btnClose = new JButton("Đóng");

        btnBack.addActionListener(e -> {
            setVisible(false);
            if (controller != null && controller.getListView() != null) {
                controller.getListView().setVisible(true);
            }
        });
        btnConfirmReturn.addActionListener(e -> onClickConfirmReturn());
        btnPrint.addActionListener(e -> printSlipToPdf());
        btnConfirmPayment.addActionListener(e -> onClickConfirmPayment());
        btnClose.addActionListener(e -> dispose());

        pnlBottom.add(btnBack);
        pnlBottom.add(btnConfirmReturn);
        pnlBottom.add(btnPrint);
        pnlBottom.add(btnConfirmPayment);
        pnlBottom.add(btnClose);
        add(pnlBottom, BorderLayout.SOUTH);
    }

    public void showReturnSlipPreview(ReturnSlip7 slip, double totalFine) {
        this.currentSlip = slip;
        this.currentTotalFine = totalFine;

        renderSlipHtml(false);

        btnBack.setEnabled(true);
        btnConfirmReturn.setEnabled(true);
        btnPrint.setEnabled(false);
        btnConfirmPayment.setEnabled(false);

        setVisible(true);
    }

    private void renderSlipHtml(boolean isSaved) {
        if (currentSlip == null) return;
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm");
        DecimalFormat df = new DecimalFormat("#,### VNĐ");

        StringBuilder sb = new StringBuilder();
        sb.append("<html><body style='font-family:Arial;padding:10px;'>");
        sb.append("<div style='text-align:center;'>");
        sb.append("<h2>THƯ VIỆN ĐẠI HỌC - LIBMAN7</h2>");
        sb.append("<h3>PHIẾU TRẢ TÀI LIỆU VÀ PHẠT VI PHẠM</h3>");
        sb.append("</div>");

        sb.append("<table style='width:100%;margin-bottom:10px;'>");
        sb.append("<tr><td><b>Bạn đọc:</b> ").append(currentSlip.getReader().getFullName()).append(" (").append(currentSlip.getReader().getReaderCode()).append(")</td>");
        sb.append("<td><b>Ngày trả:</b> ").append(sdf.format(currentSlip.getReturnDate())).append("</td></tr>");
        sb.append("<tr><td><b>Thủ thư:</b> ").append(currentSlip.getLibrarian() != null ? currentSlip.getLibrarian().getFullName() : "Librarian").append("</td>");
        String statusColor = "PAID".equalsIgnoreCase(currentSlip.getPaymentStatus()) ? "green" :
                ("UNPAID".equalsIgnoreCase(currentSlip.getPaymentStatus()) ? "red" : "blue");
        sb.append("<td><b>Trạng thái phạt:</b> <span style='color:").append(statusColor).append(";'><b>")
          .append(currentSlip.getPaymentStatus()).append("</b></span></td></tr>");
        sb.append("</table>");

        sb.append("<table border='1' cellspacing='0' cellpadding='5' style='width:100%;border-collapse:collapse;'>");
        sb.append("<tr style='background-color:#f0f0f0;'><th>STT</th><th>Nhan đề tài liệu</th><th>Tình trạng</th><th>SL</th><th>Phạt trễ hạn</th><th>Phạt hỏng/mất</th><th>Thành tiền</th></tr>");

        int stt = 1;
        for (ReturnDetail7 d : currentSlip.getDetails()) {
            String title = d.getBorrowDetail().getDocument() != null ? d.getBorrowDetail().getDocument().getTitle() : "-";
            sb.append("<tr>");
            sb.append("<td style='text-align:center;'>").append(stt++).append("</td>");
            sb.append("<td>").append(title).append("</td>");
            sb.append("<td style='text-align:center;'>").append(d.getCondition()).append("</td>");
            sb.append("<td style='text-align:center;'>").append(d.getQuantityReturned()).append("</td>");
            sb.append("<td style='text-align:right;'>").append(df.format(d.getLateFine())).append("</td>");
            sb.append("<td style='text-align:right;'>").append(df.format(d.getDamageFine())).append("</td>");
            sb.append("<td style='text-align:right;'><b>").append(df.format(d.getSubTotalFine())).append("</b></td>");
            sb.append("</tr>");
        }

        sb.append("<tr style='background-color:#fafafa;'><td colspan='6' style='text-align:right;'><b>TỔNG TIỀN PHẠT:</b></td>");
        sb.append("<td style='text-align:right;color:red;font-size:14px;'><b>").append(df.format(currentTotalFine)).append("</b></td></tr>");
        sb.append("</table>");

        sb.append("<br/><div style='font-style:italic;text-align:center;'>Xin cảm ơn quý độc giả đã sử dụng dịch vụ thư viện!</div>");
        sb.append("</body></html>");

        txtPreviewContent.setText(sb.toString());
    }

    public void onClickConfirmReturn() {
        if (currentSlip == null) return;
        if (controller != null) {
            controller.confirmReturn(currentSlip);
        }
    }

    public void showPrintReturnSlip(ReturnSlip7 slip) {
        this.currentSlip = slip;
        renderSlipHtml(true);
        btnBack.setEnabled(false);
        btnConfirmReturn.setEnabled(false);
        btnPrint.setEnabled(true);

        if (currentTotalFine > 0 && "UNPAID".equalsIgnoreCase(currentSlip.getPaymentStatus())) {
            btnConfirmPayment.setEnabled(true);
        } else {
            btnConfirmPayment.setEnabled(false);
        }

        int choice = JOptionPane.showConfirmDialog(this,
                "Lập phiếu trả #" + currentSlip.getReturnSlipId() + " thành công!\n" +
                "Bạn có muốn in phiếu trả ra file PDF ngay bây giờ không?",
                "Xác nhận in phiếu PDF", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
        if (choice == JOptionPane.YES_OPTION) {
            printSlipToPdf();
        }
    }

    public void printSlipToPdf() {
        if (currentSlip == null) return;

        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setDialogTitle("Chọn nơi lưu Phiếu trả PDF");
        String defaultFileName = String.format("PhieuTra_#%d_%s.pdf",
                currentSlip.getReturnSlipId(),
                currentSlip.getReader() != null ? currentSlip.getReader().getReaderCode() : "doc");
        fileChooser.setSelectedFile(new File(defaultFileName));

        int userSelection = fileChooser.showSaveDialog(this);
        if (userSelection == JFileChooser.APPROVE_OPTION) {
            File fileToSave = fileChooser.getSelectedFile();
            if (!fileToSave.getName().toLowerCase().endsWith(".pdf")) {
                fileToSave = new File(fileToSave.getParentFile(), fileToSave.getName() + ".pdf");
            }

            boolean success = PdfExportUtil7.exportReturnSlipToPdf(currentSlip, fileToSave);
            if (success) {
                int choice = JOptionPane.showConfirmDialog(this,
                        "In phiếu ra file PDF thành công!\nĐường dẫn: " + fileToSave.getAbsolutePath() +
                        "\n\nBạn có muốn mở xem file PDF ngay không?",
                        "In PDF hoàn tất", JOptionPane.YES_NO_OPTION, JOptionPane.INFORMATION_MESSAGE);
                if (choice == JOptionPane.YES_OPTION) {
                    try {
                        if (Desktop.isDesktopSupported()) {
                            Desktop.getDesktop().open(fileToSave);
                        }
                    } catch (Exception ex) {
                        JOptionPane.showMessageDialog(this, "Không thể tự động mở file: " + ex.getMessage());
                    }
                }
            } else {
                showError("Lỗi xuất file PDF! Vui lòng thử lại.");
            }
        }
    }

    public void onClickConfirmPayment() {
        if (currentSlip == null) return;
        if (controller != null) {
            controller.confirmPayment(currentSlip.getReturnSlipId());
        }
    }

    public void showMessage(String msg) {
        if (currentSlip != null) {
            renderSlipHtml(true);
            if ("PAID".equalsIgnoreCase(currentSlip.getPaymentStatus())) {
                btnConfirmPayment.setEnabled(false);
            }
        }
        JOptionPane.showMessageDialog(this, msg, "Thông báo", JOptionPane.INFORMATION_MESSAGE);
    }

    public void showError(String msg) {
        JOptionPane.showMessageDialog(this, msg, "Lỗi", JOptionPane.ERROR_MESSAGE);
    }
}

