package libman.dao;

import libman.entity.ReturnDetail7;
import libman.entity.ReturnSlip7;
import java.sql.*;

public class ReturnSlipDAO7 extends DAO7 {

    public ReturnSlipDAO7() {
        super();
    }

    public ReturnSlipDAO7(Connection con) {
        super(con);
    }

    public boolean addReturnSlip(ReturnSlip7 slip) {
        if (slip == null || slip.getReader() == null || slip.getLibrarian() == null) return false;

        String insertSlip = "INSERT INTO tblReturnSlip7 (readerId, librarianId, returnDate, paymentStatus, paymentDate) " +
                            "VALUES (?, ?, ?, ?, ?)";
        String insertDetail = "INSERT INTO tblReturnDetail7 (returnSlipId, borrowDetailId, quantityReturned, lateFine, damageFine, `condition`) " +
                             "VALUES (?, ?, ?, ?, ?, ?)";
        String updateDocQty = "UPDATE tblDocument7 SET totalQuantity = totalQuantity + ? WHERE documentId = ?";

        Connection conn = getConnection();
        boolean autoCommitOrig = true;
        try {
            autoCommitOrig = conn.getAutoCommit();
            conn.setAutoCommit(false);

            int generatedSlipId = -1;
            try (PreparedStatement psSlip = conn.prepareStatement(insertSlip, Statement.RETURN_GENERATED_KEYS)) {
                psSlip.setInt(1, slip.getReader().getUserId());
                psSlip.setInt(2, slip.getLibrarian().getUserId());
                psSlip.setDate(3, new java.sql.Date(slip.getReturnDate() != null ? slip.getReturnDate().getTime() : System.currentTimeMillis()));
                psSlip.setString(4, slip.getPaymentStatus());
                if (slip.getPaymentDate() != null) {
                    psSlip.setDate(5, new java.sql.Date(slip.getPaymentDate().getTime()));
                } else {
                    psSlip.setNull(5, Types.DATE);
                }
                int rows = psSlip.executeUpdate();
                if (rows == 0) {
                    conn.rollback();
                    return false;
                }
                try (ResultSet rs = psSlip.getGeneratedKeys()) {
                    if (rs.next()) {
                        generatedSlipId = rs.getInt(1);
                        slip.setReturnSlipId(generatedSlipId);
                    }
                }
            }

            if (generatedSlipId <= 0) {
                conn.rollback();
                return false;
            }

            try (PreparedStatement psDetail = conn.prepareStatement(insertDetail);
                 PreparedStatement psDoc = conn.prepareStatement(updateDocQty)) {

                for (ReturnDetail7 d : slip.getDetails()) {
                    psDetail.setInt(1, generatedSlipId);
                    psDetail.setInt(2, d.getBorrowDetail().getBorrowDetailId());
                    psDetail.setInt(3, d.getQuantityReturned());
                    psDetail.setDouble(4, d.getLateFine());
                    psDetail.setDouble(5, d.getDamageFine());
                    psDetail.setString(6, d.getCondition());
                    psDetail.addBatch();

                    // Nếu sách còn tốt thì hoàn trả kho
                    if ("GOOD".equalsIgnoreCase(d.getCondition()) && d.getBorrowDetail().getDocument() != null) {
                        psDoc.setInt(1, d.getQuantityReturned());
                        psDoc.setInt(2, d.getBorrowDetail().getDocument().getDocumentId());
                        psDoc.addBatch();
                    }
                }
                psDetail.executeBatch();
                psDoc.executeBatch();
            }

            conn.commit();
            return true;
        } catch (SQLException e) {
            try { conn.rollback(); } catch (SQLException ignored) {}
            e.printStackTrace();
            return false;
        } finally {
            try { conn.setAutoCommit(autoCommitOrig); } catch (SQLException ignored) {}
        }
    }

    public boolean updatePaymentStatus(int slipId, String status) {
        return updatePaymentStatus(slipId, status, new java.util.Date());
    }

    public boolean updatePaymentStatus(int slipId, String status, java.util.Date paymentDate) {
        String sql = "UPDATE tblReturnSlip7 SET paymentStatus = ?, paymentDate = ? WHERE returnSlipId = ?";
        try (PreparedStatement ps = getConnection().prepareStatement(sql)) {
            ps.setString(1, status);
            if (paymentDate != null) {
                ps.setDate(2, new java.sql.Date(paymentDate.getTime()));
            } else {
                ps.setNull(2, Types.DATE);
            }
            ps.setInt(3, slipId);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}

