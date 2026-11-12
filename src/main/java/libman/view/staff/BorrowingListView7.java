package libman.view.staff;

import libman.controller.ReturnController7;
import libman.entity.BorrowDetail7;
import libman.entity.Reader7;
import libman.entity.ReturnDetail7;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class BorrowingListView7 extends JFrame {
    private JLabel lblReaderInfo;
    private JLabel lblTotalFine;
    private JTable tblBorrowings;
    private DefaultTableModel tableModel;
    private JButton btnCreateSlip;
    private JButton btnBack;
    private ReturnController7 controller;

    private Reader7 currentReader;
    private List<BorrowDetail7> currentBorrowList;
    private boolean isUpdatingTable = false;

    public BorrowingListView7() {
        initComponents();
    }

    public void setController(ReturnController7 controller) {
        this.controller = controller;
    }

    private void initComponents() {
        setTitle("Danh sách tài liệu đang mượn");
        setSize(980, 520);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout(10, 10));

        JPanel pnlTop = new JPanel(new BorderLayout(5, 5));
        pnlTop.setBorder(BorderFactory.createEmptyBorder(10, 15, 5, 15));

        JLabel lblHeader = new JLabel("Danh sách tài liệu đang mượn", SwingConstants.LEFT);
        lblHeader.setFont(new Font("Arial", Font.BOLD, 16));
        pnlTop.add(lblHeader, BorderLayout.NORTH);

        lblReaderInfo = new JLabel("Bạn đọc: ...");
        lblReaderInfo.setFont(new Font("Arial", Font.PLAIN, 13));
        lblReaderInfo.setForeground(new Color(0, 70, 150));
        pnlTop.add(lblReaderInfo, BorderLayout.CENTER);

        add(pnlTop, BorderLayout.NORTH);

        String[] columns = {
            "Chọn", "Mã mượn", "Nhan đề tài liệu", "Loại", "Hạn trả",
            "Đang mượn", "SL Trả", "Tình trạng", "Quá hạn (ngày)",
            "Phạt trễ (VNĐ)", "Phạt hỏng (VNĐ)", "Thành tiền (VNĐ)"
        };

        tableModel = new DefaultTableModel(columns, 0) {
            @Override
            public Class<?> getColumnClass(int columnIndex) {
                if (columnIndex == 0) return Boolean.class;
                return Object.class;
            }

            @Override
            public boolean isCellEditable(int row, int column) {
                return column == 0 || column == 6 || column == 7 || column == 10;
            }
        };

        tblBorrowings = new JTable(tableModel);
        tblBorrowings.setRowHeight(28);

        // ComboBox editor cho cột Tình trạng (cột 7)
        JComboBox<String> cbCondition = new JComboBox<>(new String[]{"GOOD", "DAMAGED", "LOST"});
        tblBorrowings.getColumnModel().getColumn(7).setCellEditor(new DefaultCellEditor(cbCondition));

        // Lắng nghe thay đổi dữ liệu để tính tiền phạt thời gian thực
        tableModel.addTableModelListener(e -> {
            if (isUpdatingTable) return;
            int col = e.getColumn();
            int row = e.getFirstRow();
            if (row >= 0) {
                if (col == 7) {
                    // Tự động gợi ý mức phạt khi đổi tình trạng
                    String cond = String.valueOf(tableModel.getValueAt(row, 7));
                    if ("DAMAGED".equalsIgnoreCase(cond)) {
                        tableModel.setValueAt(50000.0, row, 10);
                    } else if ("LOST".equalsIgnoreCase(cond)) {
                        tableModel.setValueAt(100000.0, row, 10);
                    } else if ("GOOD".equalsIgnoreCase(cond)) {
                        tableModel.setValueAt(0.0, row, 10);
                    }
                }
                updateRowFinesAndTotal();
            }
        });

        JScrollPane scrollPane = new JScrollPane(tblBorrowings);
        scrollPane.setBorder(BorderFactory.createEmptyBorder(0, 15, 0, 15));
        add(scrollPane, BorderLayout.CENTER);

        // Thanh công cụ phía dưới kèm Tổng tiền phạt hiển thị trực tiếp
        JPanel pnlBottom = new JPanel(new BorderLayout(15, 5));
        pnlBottom.setBorder(BorderFactory.createEmptyBorder(8, 15, 12, 15));

        lblTotalFine = new JLabel("Tổng tiền phạt: 0 VNĐ");
        lblTotalFine.setFont(new Font("Arial", Font.BOLD, 15));
        lblTotalFine.setForeground(new Color(200, 0, 0));
        pnlBottom.add(lblTotalFine, BorderLayout.WEST);

        JPanel pnlButtons = new JPanel(new FlowLayout(FlowLayout.RIGHT, 10, 0));
        btnBack = new JButton("Quay lại");
        btnCreateSlip = new JButton("Tạo phiếu");
        btnCreateSlip.setFont(new Font("Arial", Font.BOLD, 13));

        btnBack.addActionListener(e -> {
            setVisible(false);
            if (controller != null && controller.getSearchView() != null) {
                controller.getSearchView().setVisible(true);
            }
        });

        btnCreateSlip.addActionListener(e -> onClickCreateSlip());

        pnlButtons.add(btnBack);
        pnlButtons.add(btnCreateSlip);
        pnlBottom.add(pnlButtons, BorderLayout.EAST);

        add(pnlBottom, BorderLayout.SOUTH);
    }

    public void showReaderAndBorrowings(Reader7 r, List list) {
        this.currentReader = r;
        this.currentBorrowList = (List<BorrowDetail7>) list;

        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        lblReaderInfo.setText(String.format("<html>Bạn đọc: <b>%s</b> (%s) | Khoa: <b>%s</b> | Đối tượng: <b>%s</b></html>",
                r.getFullName(), r.getReaderCode(), r.getFaculty() != null ? r.getFaculty() : "-", r.getReaderType() != null ? r.getReaderType() : "-"));

        isUpdatingTable = true;
        tableModel.setRowCount(0);
        Date today = new Date();

        if (currentBorrowList != null) {
            for (BorrowDetail7 bd : currentBorrowList) {
                int overdue = bd.getOverdueDays(today);
                String dueDateStr = bd.getBorrowSlip() != null && bd.getBorrowSlip().getDueDate() != null ?
                        sdf.format(bd.getBorrowSlip().getDueDate()) : "-";
                String title = bd.getDocument() != null ? bd.getDocument().getTitle() : "Tài liệu #" + bd.getDocument().getDocumentId();
                String docType = bd.getDocument() != null ? bd.getDocument().getDocType() : "-";

                int qty = bd.getQuantityBorrowed();
                double lateFine = overdue > 0 ? (overdue * 2000.0 * qty) : 0.0;
                double damageFine = 0.0;
                double subTotal = lateFine + damageFine;

                tableModel.addRow(new Object[]{
                    true, // Mặc định chọn trả
                    bd.getBorrowDetailId(),
                    title,
                    docType,
                    dueDateStr,
                    qty,
                    qty, // Mặc định trả hết số lượng
                    "GOOD",
                    overdue,
                    lateFine,
                    damageFine,
                    subTotal
                });
            }
        }
        isUpdatingTable = false;

        updateRowFinesAndTotal();
        setVisible(true);
    }

    private void updateRowFinesAndTotal() {
        if (isUpdatingTable) return;
        isUpdatingTable = true;
        try {
            double grandTotal = 0.0;
            DecimalFormat df = new DecimalFormat("#,### VNĐ");

            for (int i = 0; i < tableModel.getRowCount(); i++) {
                Boolean checked = (Boolean) tableModel.getValueAt(i, 0);
                boolean isChecked = checked != null && checked;

                int overdue = 0;
                try {
                    overdue = Integer.parseInt(String.valueOf(tableModel.getValueAt(i, 8)));
                } catch (Exception ignored) {}

                int qty = 1;
                try {
                    qty = Integer.parseInt(String.valueOf(tableModel.getValueAt(i, 6)));
                } catch (Exception ignored) {}

                double damageFine = 0.0;
                try {
                    damageFine = Double.parseDouble(String.valueOf(tableModel.getValueAt(i, 10)));
                } catch (Exception ignored) {}

                double lateFine = 0.0;
                if (isChecked && overdue > 0) {
                    lateFine = overdue * 2000.0 * qty;
                }

                double subTotal = 0.0;
                if (isChecked) {
                    subTotal = lateFine + damageFine;
                    grandTotal += subTotal;
                }

                tableModel.setValueAt(lateFine, i, 9);
                tableModel.setValueAt(subTotal, i, 11);
            }
            lblTotalFine.setText("Tổng tiền phạt: " + df.format(grandTotal));
        } finally {
            isUpdatingTable = false;
        }
    }

    public void selectItem(int index, int qty) {
        if (index >= 0 && index < tableModel.getRowCount()) {
            tableModel.setValueAt(true, index, 0);
            tableModel.setValueAt(qty, index, 6);
            updateRowFinesAndTotal();
        }
    }

    public void inputFineAndCondition(String condition, double fine) {
        int selected = tblBorrowings.getSelectedRow();
        if (selected >= 0) {
            tableModel.setValueAt(condition, selected, 7);
            tableModel.setValueAt(fine, selected, 10);
            updateRowFinesAndTotal();
        }
    }

    public void onClickCreateSlip() {
        if (tblBorrowings.isEditing()) {
            tblBorrowings.getCellEditor().stopCellEditing();
        }

        List<ReturnDetail7> selectedItems = new ArrayList<>();
        Date today = new Date();
        double lateFeeRatePerDay = 2000.0;

        for (int i = 0; i < tableModel.getRowCount(); i++) {
            Boolean checked = (Boolean) tableModel.getValueAt(i, 0);
            if (checked != null && checked) {
                BorrowDetail7 bd = currentBorrowList.get(i);
                int qty = 1;
                try {
                    qty = Integer.parseInt(tableModel.getValueAt(i, 6).toString());
                } catch (Exception ex) {
                    qty = bd.getQuantityBorrowed();
                }

                if (qty <= 0 || qty > bd.getQuantityBorrowed()) {
                    showError("Số lượng trả tài liệu '" + bd.getDocument().getTitle() + "' không hợp lệ (1 - " + bd.getQuantityBorrowed() + ")!");
                    return;
                }

                String condition = (String) tableModel.getValueAt(i, 7);
                double damageFine = 0.0;
                try {
                    damageFine = Double.parseDouble(tableModel.getValueAt(i, 10).toString());
                } catch (Exception ignored) {}

                ReturnDetail7 detail = new ReturnDetail7();
                detail.setBorrowDetail(bd);
                detail.setQuantityReturned(qty);
                detail.setCondition(condition != null ? condition : "GOOD");
                detail.setDamageFine(damageFine);

                int overdue = bd.getOverdueDays(today);
                detail.calcLateFine(overdue, lateFeeRatePerDay);

                selectedItems.add(detail);
            }
        }

        if (selectedItems.isEmpty()) {
            showError("Vui lòng tích chọn ít nhất 1 tài liệu để nhận trả!");
            return;
        }

        if (controller != null) {
            controller.createReturnSlip(selectedItems);
        }
    }

    public void showError(String msg) {
        JOptionPane.showMessageDialog(this, msg, "Lỗi", JOptionPane.ERROR_MESSAGE);
    }
}
