package libman.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class DocumentDAO7 extends DAO7 {

    public DocumentDAO7() {
        super();
    }

    public DocumentDAO7(Connection con) {
        super(con);
    }

    public boolean increaseQuantity(int documentId, int qty) {
        if (qty <= 0) return true;
        String sql = "UPDATE tblDocument7 SET totalQuantity = totalQuantity + ? WHERE documentId = ?";
        try (PreparedStatement ps = getConnection().prepareStatement(sql)) {
            ps.setInt(1, qty);
            ps.setInt(2, documentId);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}

