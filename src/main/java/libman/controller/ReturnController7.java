package libman.controller;

import libman.dao.BorrowDetailDAO7;
import libman.dao.ReaderDAO7;
import libman.dao.ReturnSlipDAO7;
import libman.entity.BorrowDetail7;
import libman.entity.Librarian7;
import libman.entity.Reader7;
import libman.entity.ReturnDetail7;
import libman.entity.ReturnSlip7;
import libman.view.staff.BorrowingListView7;
import libman.view.staff.ReaderSearchView7;
import libman.view.staff.ReturnSlipView7;

import java.util.Date;
import java.util.List;

public class ReturnController7 {
    private ReaderSearchView7 searchView;
    private BorrowingListView7 listView;
    private ReturnSlipView7 slipView;

    private ReaderDAO7 readerDAO;
    private BorrowDetailDAO7 borrowDetailDAO;
    private ReturnSlipDAO7 returnSlipDAO;

    private Reader7 currentReader;
    private Librarian7 currentLibrarian;
    private ReturnSlip7 currentSlip;

    public ReturnController7() {
        this(null);
    }

    public ReturnController7(Librarian7 librarian) {
        this.searchView = new ReaderSearchView7();
        this.listView = new BorrowingListView7();
        this.slipView = new ReturnSlipView7();

        this.searchView.setController(this);
        this.listView.setController(this);
        this.slipView.setController(this);

        this.readerDAO = new ReaderDAO7();
        this.borrowDetailDAO = new BorrowDetailDAO7();
        this.returnSlipDAO = new ReturnSlipDAO7();

        this.currentLibrarian = librarian;
    }

    public ReturnController7(ReaderSearchView7 searchView, BorrowingListView7 listView, ReturnSlipView7 slipView,
                             ReaderDAO7 readerDAO, BorrowDetailDAO7 borrowDetailDAO, ReturnSlipDAO7 returnSlipDAO) {
        this.searchView = searchView;
        this.listView = listView;
        this.slipView = slipView;
        this.readerDAO = readerDAO;
        this.borrowDetailDAO = borrowDetailDAO;
        this.returnSlipDAO = returnSlipDAO;
        this.currentLibrarian = null;
    }

    public void start() {
        if (searchView != null) {
            searchView.showSearchForm();
        }
    }

    public void searchReader(String code) {
        // Bước 5 & 6: getReaderByCode
        Reader7 reader = readerDAO.getReaderByCode(code);
        if (reader == null) {
            searchView.showError("Không tìm thấy bạn đọc với mã: " + code);
            return;
        }

        this.currentReader = reader;

        // Bước 7 & 8: getBorrowingByReader
        List<BorrowDetail7> borrowList = borrowDetailDAO.getBorrowingByReader(reader.getUserId());
        if (borrowList == null || borrowList.isEmpty()) {
            searchView.showError("Bạn đọc " + reader.getFullName() + " hiện không có tài liệu nào đang mượn!");
            return;
        }

        // Bước 9: showReaderAndBorrowings
        searchView.setVisible(false);
        listView.showReaderAndBorrowings(reader, borrowList);
    }

    public void createReturnSlip(List selectedItems) {
        if (currentLibrarian == null) {
            listView.showError("Chưa xác định thủ thư đang đăng nhập hệ thống!");
            return;
        }

        if (currentReader == null) {
            listView.showError("Chưa xác định độc giả trả tài liệu!");
            return;
        }

        // Bước 12: new ReturnSlip(reader, librarian, selectedItems)
        ReturnSlip7 slip = new ReturnSlip7(currentReader, currentLibrarian, (List<ReturnDetail7>) selectedItems);
        this.currentSlip = slip;

        // Bước 13 & 14: calcTotalFine()
        double totalFine = slip.calcTotalFine();

        // Bước 15: showReturnSlipPreview
        listView.setVisible(false);
        slipView.showReturnSlipPreview(slip, totalFine);
    }

    public void confirmReturn(ReturnSlip7 slip) {
        // Bước 18 & 19: addReturnSlip(slip)
        boolean success = returnSlipDAO.addReturnSlip(slip);
        if (!success) {
            slipView.showError("Lỗi hệ thống: Không thể lưu phiếu trả vào CSDL!");
            return;
        }

        // Bước 20 & 21: showPrintReturnSlip
        slipView.showPrintReturnSlip(slip);
    }

    public void confirmPayment(int slipId) {
        // Bước 24 & 25: updatePaymentStatus
        Date paymentDate = new Date();
        boolean success = returnSlipDAO.updatePaymentStatus(slipId, "PAID", paymentDate);
        if (!success) {
            slipView.showError("Lỗi hệ thống: Không thể cập nhật trạng thái thanh toán!");
            return;
        }

        if (currentSlip != null && currentSlip.getReturnSlipId() == slipId) {
            currentSlip.setPaymentStatus("PAID");
            currentSlip.setPaymentDate(paymentDate);
        }

        // Bước 26: showMessage("Thanh toán thành công")
        slipView.showMessage("Thanh toán tiền phạt thành công!");

        // Bước 27: showMessage("Hoàn tất quy trình nhận trả")
        slipView.showMessage("Hoàn tất quy trình nhận trả tài liệu!");
    }

    public ReaderSearchView7 getSearchView() { return searchView; }
    public BorrowingListView7 getListView() { return listView; }
    public ReturnSlipView7 getSlipView() { return slipView; }
    public void setCurrentLibrarian(Librarian7 librarian) { this.currentLibrarian = librarian; }
}

