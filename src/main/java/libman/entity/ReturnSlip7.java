package libman.entity;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class ReturnSlip7 {
    private int returnSlipId;
    private Date returnDate;
    private String paymentStatus = "NO_FINE";
    private Date paymentDate;
    private Reader7 reader;
    private Librarian7 librarian;
    private List<ReturnDetail7> details = new ArrayList<>();

    public ReturnSlip7() {
        this.returnDate = new Date();
    }

    public ReturnSlip7(Reader7 reader, Librarian7 librarian, List<ReturnDetail7> items) {
        this.reader = reader;
        this.librarian = librarian;
        this.returnDate = new Date();
        if (items != null) {
            for (ReturnDetail7 d : items) {
                addDetail(d);
            }
        }
        double totalFine = calcTotalFine();
        if (totalFine > 0) {
            this.paymentStatus = "UNPAID";
        } else {
            this.paymentStatus = "NO_FINE";
        }
    }

    public void addDetail(ReturnDetail7 detail) {
        if (detail != null) {
            detail.setReturnSlip(this);
            this.details.add(detail);
        }
    }

    public double calcTotalFine() {
        double total = 0.0;
        for (ReturnDetail7 d : details) {
            total += d.getSubTotalFine();
        }
        return total;
    }

    public void confirmPayment() {
        this.paymentStatus = "PAID";
        this.paymentDate = new Date();
    }

    public int getReturnSlipId() { return returnSlipId; }
    public void setReturnSlipId(int returnSlipId) { this.returnSlipId = returnSlipId; }

    public Date getReturnDate() { return returnDate; }
    public void setReturnDate(Date returnDate) { this.returnDate = returnDate; }

    public String getPaymentStatus() { return paymentStatus; }
    public void setPaymentStatus(String paymentStatus) { this.paymentStatus = paymentStatus; }

    public Date getPaymentDate() { return paymentDate; }
    public void setPaymentDate(Date paymentDate) { this.paymentDate = paymentDate; }

    public Reader7 getReader() { return reader; }
    public void setReader(Reader7 reader) { this.reader = reader; }

    public Librarian7 getLibrarian() { return librarian; }
    public void setLibrarian(Librarian7 librarian) { this.librarian = librarian; }

    public List<ReturnDetail7> getDetails() { return details; }
    public void setDetails(List<ReturnDetail7> details) { this.details = details; }
}

