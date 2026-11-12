package libman.entity;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class BorrowSlip7 {
    private int borrowSlipId;
    private Date borrowDate;
    private Date dueDate;
    private Reader7 reader;
    private Librarian7 librarian;
    private List<BorrowDetail7> details = new ArrayList<>();

    public BorrowSlip7() {}

    public BorrowSlip7(int borrowSlipId, Date borrowDate, Date dueDate, Reader7 reader, Librarian7 librarian) {
        this.borrowSlipId = borrowSlipId;
        this.borrowDate = borrowDate;
        this.dueDate = dueDate;
        this.reader = reader;
        this.librarian = librarian;
    }

    public void addDetail(BorrowDetail7 detail) {
        if (detail != null) {
            detail.setBorrowSlip(this);
            this.details.add(detail);
        }
    }

    public int getBorrowSlipId() { return borrowSlipId; }
    public void setBorrowSlipId(int borrowSlipId) { this.borrowSlipId = borrowSlipId; }

    public Date getBorrowDate() { return borrowDate; }
    public void setBorrowDate(Date borrowDate) { this.borrowDate = borrowDate; }

    public Date getDueDate() { return dueDate; }
    public void setDueDate(Date dueDate) { this.dueDate = dueDate; }

    public Reader7 getReader() { return reader; }
    public void setReader(Reader7 reader) { this.reader = reader; }

    public Librarian7 getLibrarian() { return librarian; }
    public void setLibrarian(Librarian7 librarian) { this.librarian = librarian; }

    public List<BorrowDetail7> getDetails() { return details; }
    public void setDetails(List<BorrowDetail7> details) { this.details = details; }
}

