package libman.dto;

import java.util.Date;

public class CardDTO {
    private String cardCode;
    private Date registerDate;
    private Date expiryDate;

    public CardDTO() {}

    public CardDTO(String cardCode, Date registerDate, Date expiryDate) {
        this.cardCode = cardCode;
        this.registerDate = registerDate;
        this.expiryDate = expiryDate;
    }

    public String getCardCode() { return cardCode; }
    public void setCardCode(String cardCode) { this.cardCode = cardCode; }

    public Date getRegisterDate() { return registerDate; }
    public void setRegisterDate(Date registerDate) { this.registerDate = registerDate; }

    public Date getExpiryDate() { return expiryDate; }
    public void setExpiryDate(Date expiryDate) { this.expiryDate = expiryDate; }
}

