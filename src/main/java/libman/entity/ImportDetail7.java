package libman.entity;

public class ImportDetail7 {
    private int importDetailId;
    private ImportSlip7 importSlip;
    private Document7 document;
    private int quantityImported;
    private int unitPrice;

    public ImportDetail7() {}

    public ImportDetail7(int importDetailId, ImportSlip7 importSlip, Document7 document, int quantityImported, int unitPrice) {
        this.importDetailId = importDetailId;
        this.importSlip = importSlip;
        this.document = document;
        this.quantityImported = quantityImported;
        this.unitPrice = unitPrice;
    }

    public int getSubTotal() {
        return this.quantityImported * this.unitPrice;
    }

    public int getImportDetailId() { return importDetailId; }
    public void setImportDetailId(int importDetailId) { this.importDetailId = importDetailId; }

    public ImportSlip7 getImportSlip() { return importSlip; }
    public void setImportSlip(ImportSlip7 importSlip) { this.importSlip = importSlip; }

    public Document7 getDocument() { return document; }
    public void setDocument(Document7 document) { this.document = document; }

    public int getQuantityImported() { return quantityImported; }
    public void setQuantityImported(int quantityImported) { this.quantityImported = quantityImported; }

    public int getUnitPrice() { return unitPrice; }
    public void setUnitPrice(int unitPrice) { this.unitPrice = unitPrice; }
}

