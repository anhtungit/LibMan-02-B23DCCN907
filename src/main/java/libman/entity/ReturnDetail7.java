package libman.entity;

public class ReturnDetail7 {
    private int returnDetailId;
    private ReturnSlip7 returnSlip;
    private BorrowDetail7 borrowDetail;
    private int quantityReturned;
    private double lateFine;
    private double damageFine;
    private String condition = "GOOD";

    public ReturnDetail7() {}

    public ReturnDetail7(int returnDetailId, ReturnSlip7 returnSlip, BorrowDetail7 borrowDetail,
                         int quantityReturned, double lateFine, double damageFine, String condition) {
        this.returnDetailId = returnDetailId;
        this.returnSlip = returnSlip;
        this.borrowDetail = borrowDetail;
        this.quantityReturned = quantityReturned;
        this.lateFine = lateFine;
        this.damageFine = damageFine;
        this.condition = condition != null ? condition : "GOOD";
    }

    public double calcLateFine(int overdueDays, double rate) {
        if (overdueDays > 0 && rate > 0) {
            this.lateFine = overdueDays * rate * quantityReturned;
        } else {
            this.lateFine = 0.0;
        }
        return this.lateFine;
    }

    public void setDamageFine(double fine) {
        this.damageFine = Math.max(0, fine);
    }

    public double getSubTotalFine() {
        return this.lateFine + this.damageFine;
    }

    public int getReturnDetailId() { return returnDetailId; }
    public void setReturnDetailId(int returnDetailId) { this.returnDetailId = returnDetailId; }

    public ReturnSlip7 getReturnSlip() { return returnSlip; }
    public void setReturnSlip(ReturnSlip7 returnSlip) { this.returnSlip = returnSlip; }

    public BorrowDetail7 getBorrowDetail() { return borrowDetail; }
    public void setBorrowDetail(BorrowDetail7 borrowDetail) { this.borrowDetail = borrowDetail; }

    public int getQuantityReturned() { return quantityReturned; }
    public void setQuantityReturned(int quantityReturned) { this.quantityReturned = quantityReturned; }

    public double getLateFine() { return lateFine; }
    public void setLateFine(double lateFine) { this.lateFine = lateFine; }

    public double getDamageFine() { return damageFine; }

    public String getCondition() { return condition; }
    public void setCondition(String condition) { this.condition = condition; }
}

