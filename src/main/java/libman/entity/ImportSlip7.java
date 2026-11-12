package libman.entity;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class ImportSlip7 {
    private int importSlipId;
    private Date importDate;
    private Supplier7 supplier;
    private Librarian7 librarian;
    private List<ImportDetail7> details = new ArrayList<>();

    public ImportSlip7() {}

    public ImportSlip7(int importSlipId, Date importDate, Supplier7 supplier, Librarian7 librarian) {
        this.importSlipId = importSlipId;
        this.importDate = importDate;
        this.supplier = supplier;
        this.librarian = librarian;
    }

    public void addDetail(ImportDetail7 detail) {
        if (detail != null) {
            detail.setImportSlip(this);
            this.details.add(detail);
        }
    }

    public double calcTotalAmount() {
        double total = 0.0;
        for (ImportDetail7 d : details) {
            total += d.getSubTotal();
        }
        return total;
    }

    public int getImportSlipId() { return importSlipId; }
    public void setImportSlipId(int importSlipId) { this.importSlipId = importSlipId; }

    public Date getImportDate() { return importDate; }
    public void setImportDate(Date importDate) { this.importDate = importDate; }

    public Supplier7 getSupplier() { return supplier; }
    public void setSupplier(Supplier7 supplier) { this.supplier = supplier; }

    public Librarian7 getLibrarian() { return librarian; }
    public void setLibrarian(Librarian7 librarian) { this.librarian = librarian; }

    public List<ImportDetail7> getDetails() { return details; }
    public void setDetails(List<ImportDetail7> details) { this.details = details; }
}

