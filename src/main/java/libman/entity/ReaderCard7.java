package libman.entity;

import libman.dto.CardDTO;
import java.util.Date;

public class ReaderCard7 {
    private String cardCode;
    private Date registerDate;
    private Date expiryDate;
    private Reader7 reader;

    public ReaderCard7() {}

    public ReaderCard7(String cardCode, Date registerDate, Date expiryDate, Reader7 reader) {
        this.cardCode = cardCode;
        this.registerDate = registerDate;
        this.expiryDate = expiryDate;
        this.reader = reader;
    }

    public ReaderCard7(CardDTO info, Reader7 reader) {
        this.reader = reader;
        if (info != null) {
            this.cardCode = info.getCardCode();
            this.registerDate = info.getRegisterDate();
            this.expiryDate = info.getExpiryDate();
        }
    }

    public boolean isValid() {
        if (expiryDate == null) return false;
        return expiryDate.after(new Date()) || expiryDate.equals(new Date());
    }

    public String getCardCode() { return cardCode; }
    public void setCardCode(String cardCode) { this.cardCode = cardCode; }

    public Date getRegisterDate() { return registerDate; }
    public void setRegisterDate(Date registerDate) { this.registerDate = registerDate; }

    public Date getExpiryDate() { return expiryDate; }
    public void setExpiryDate(Date expiryDate) { this.expiryDate = expiryDate; }

    public Reader7 getReader() { return reader; }
    public void setReader(Reader7 reader) { this.reader = reader; }
}

