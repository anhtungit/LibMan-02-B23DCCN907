package libman.dao;

import libman.entity.BorrowDetail7;
import libman.entity.BorrowSlip7;
import libman.entity.Document7;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class BorrowDetailDAO7 extends DAO7 {

    public BorrowDetailDAO7() {
        super();
    }

    public BorrowDetailDAO7(Connection con) {
        super(con);
    }

    public List<BorrowDetail7> getBorrowingByReader(int userId) {
        List<BorrowDetail7> list = new ArrayList<>();
        String sql = "SELECT bd.borrowDetailId, bd.quantityBorrowed, " +
                     "bs.borrowSlipId, bs.borrowDate, bs.dueDate, " +
                     "d.documentId, d.title, d.author, d.publisher, d.publishYear, d.docType, d.totalQuantity, d.location, d.description, " +
                     "COALESCE(SUM(rd.quantityReturned), 0) AS totalReturned " +
                     "FROM tblBorrowDetail7 bd " +
                     "JOIN tblBorrowSlip7 bs ON bd.borrowSlipId = bs.borrowSlipId " +
                     "JOIN tblDocument7 d ON bd.documentId = d.documentId " +
                     "LEFT JOIN tblReturnDetail7 rd ON bd.borrowDetailId = rd.borrowDetailId " +
                     "WHERE bs.readerId = ? " +
                     "GROUP BY bd.borrowDetailId, bd.quantityBorrowed, bs.borrowSlipId, bs.borrowDate, bs.dueDate, d.documentId " +
                     "HAVING (bd.quantityBorrowed - totalReturned) > 0";

        try (PreparedStatement ps = getConnection().prepareStatement(sql)) {
            ps.setInt(1, userId);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    BorrowSlip7 slip = new BorrowSlip7();
                    slip.setBorrowSlipId(rs.getInt("borrowSlipId"));
                    slip.setBorrowDate(rs.getDate("borrowDate"));
                    slip.setDueDate(rs.getDate("dueDate"));

                    Document7 doc = new Document7();
                    doc.setDocumentId(rs.getInt("documentId"));
                    doc.setTitle(rs.getString("title"));
                    doc.setAuthor(rs.getString("author"));
                    doc.setPublisher(rs.getString("publisher"));
                    doc.setPublishYear(rs.getInt("publishYear"));
                    doc.setDocType(rs.getString("docType"));
                    doc.setTotalQuantity(rs.getInt("totalQuantity"));
                    doc.setLocation(rs.getString("location"));
                    doc.setDescription(rs.getString("description"));

                    int remainingQty = rs.getInt("quantityBorrowed") - rs.getInt("totalReturned");

                    BorrowDetail7 detail = new BorrowDetail7();
                    detail.setBorrowDetailId(rs.getInt("borrowDetailId"));
                    detail.setBorrowSlip(slip);
                    detail.setDocument(doc);
                    detail.setQuantityBorrowed(remainingQty); // Số lượng còn đang mượn

                    list.add(detail);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    public boolean updateReturnQuantity(int borrowDetailId, int qty) {
        // Hỗ trợ theo chuẩn giao diện biểu đồ lớp Module 2
        return true;
    }
}

