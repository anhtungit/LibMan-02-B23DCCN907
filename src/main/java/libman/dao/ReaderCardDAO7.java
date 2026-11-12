package libman.dao;

import libman.entity.Reader7;
import libman.entity.ReaderCard7;
import java.sql.*;

public class ReaderCardDAO7 extends DAO7 {

    public ReaderCardDAO7() {
        super();
    }

    public ReaderCardDAO7(Connection con) {
        super(con);
    }

    public boolean existsByCardCode(String cardCode) {
        String sql = "SELECT cardCode FROM tblReaderCard7 WHERE cardCode = ?";
        try (PreparedStatement ps = getConnection().prepareStatement(sql)) {
            ps.setString(1, cardCode);
            try (ResultSet rs = ps.executeQuery()) {
                return rs.next();
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean addCard(ReaderCard7 card) {
        if (card == null || card.getReader() == null) return false;
        String sql = "INSERT INTO tblReaderCard7 (cardCode, userId, registerDate, expiryDate) VALUES (?, ?, ?, ?)";
        try (PreparedStatement ps = getConnection().prepareStatement(sql)) {
            ps.setString(1, card.getCardCode());
            ps.setInt(2, card.getReader().getUserId());
            ps.setDate(3, new java.sql.Date(card.getRegisterDate() != null ? card.getRegisterDate().getTime() : System.currentTimeMillis()));
            ps.setDate(4, new java.sql.Date(card.getExpiryDate() != null ? card.getExpiryDate().getTime() : System.currentTimeMillis() + 365L * 24 * 3600 * 1000));
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public ReaderCard7 getCardByReaderId(int userId) {
        String sql = "SELECT cardCode, registerDate, expiryDate FROM tblReaderCard7 WHERE userId = ?";
        try (PreparedStatement ps = getConnection().prepareStatement(sql)) {
            ps.setInt(1, userId);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    ReaderCard7 card = new ReaderCard7();
                    card.setCardCode(rs.getString("cardCode"));
                    card.setRegisterDate(rs.getDate("registerDate"));
                    card.setExpiryDate(rs.getDate("expiryDate"));
                    return card;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public ReaderCard7 getCardByCode(String cardCode) {
        String sql = "SELECT cardCode, registerDate, expiryDate FROM tblReaderCard7 WHERE cardCode = ?";
        try (PreparedStatement ps = getConnection().prepareStatement(sql)) {
            ps.setString(1, cardCode);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    ReaderCard7 card = new ReaderCard7();
                    card.setCardCode(rs.getString("cardCode"));
                    card.setRegisterDate(rs.getDate("registerDate"));
                    card.setExpiryDate(rs.getDate("expiryDate"));
                    return card;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public String generateNextCardCode() {
        String sql = "SELECT cardCode FROM tblReaderCard7";
        int maxNumber = 0;
        try (PreparedStatement ps = getConnection().prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                String code = rs.getString("cardCode");
                if (code != null) {
                    // Trích xuất số cuối cùng
                    String numPart = code.replaceAll("\\D+", "");
                    if (!numPart.isEmpty()) {
                        try {
                            int num = Integer.parseInt(numPart);
                            if (num < 10000 && num > maxNumber) {
                                maxNumber = num;
                            }
                        } catch (NumberFormatException ignored) {}
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return String.format("CARD%03d", maxNumber + 1);
    }
}

