package libman.entity;

import java.util.Date;

public class BorrowDetail7 {
    private int borrowDetailId;
    private BorrowSlip7 borrowSlip;
    private Document7 document;
    private int quantityBorrowed;

    public BorrowDetail7() {}

    public BorrowDetail7(int borrowDetailId, BorrowSlip7 borrowSlip, Document7 document, int quantityBorrowed) {
        this.borrowDetailId = borrowDetailId;
        this.borrowSlip = borrowSlip;
        this.document = document;
        this.quantityBorrowed = quantityBorrowed;
    }

    public int getOverdueDays(Date currentDate) {
        if (borrowSlip == null || borrowSlip.getDueDate() == null || currentDate == null) {
            return 0;
        }
        long diff = currentDate.getTime() - borrowSlip.getDueDate().getTime();
        if (diff <= 0) return 0;
        return (int) (diff / (1000L * 60 * 60 * 24));
    }

    public int getBorrowDetailId() { return borrowDetailId; }
    public void setBorrowDetailId(int borrowDetailId) { this.borrowDetailId = borrowDetailId; }

    public BorrowSlip7 getBorrowSlip() { return borrowSlip; }
    public void setBorrowSlip(BorrowSlip7 borrowSlip) { this.borrowSlip = borrowSlip; }

    public Document7 getDocument() { return document; }
    public void setDocument(Document7 document) { this.document = document; }

    public int getQuantityBorrowed() { return quantityBorrowed; }
    public void setQuantityBorrowed(int quantityBorrowed) { this.quantityBorrowed = quantityBorrowed; }
}

