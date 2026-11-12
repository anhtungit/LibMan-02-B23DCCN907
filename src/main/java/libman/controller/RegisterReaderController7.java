package libman.controller;

import libman.dao.ReaderCardDAO7;
import libman.dao.ReaderDAO7;
import libman.dto.CardDTO;
import libman.dto.ReaderDTO;
import libman.entity.Reader7;
import libman.entity.ReaderCard7;
import libman.view.reader.RegisterReaderView7;

import java.text.SimpleDateFormat;

public class RegisterReaderController7 {
    private RegisterReaderView7 view;
    private ReaderDAO7 readerDAO;
    private ReaderCardDAO7 readerCardDAO;

    public RegisterReaderController7(RegisterReaderView7 view) {
        this.view = view;
        this.readerDAO = new ReaderDAO7();
        this.readerCardDAO = new ReaderCardDAO7();
    }

    public RegisterReaderController7(RegisterReaderView7 view, ReaderDAO7 readerDAO, ReaderCardDAO7 readerCardDAO) {
        this.view = view;
        this.readerDAO = readerDAO;
        this.readerCardDAO = readerCardDAO;
    }

    public String getNextCardCode() {
        return readerCardDAO.generateNextCardCode();
    }

    public void registerReaderCard(ReaderDTO readerInfo, CardDTO cardInfo) {
        if (cardInfo != null && (cardInfo.getCardCode() == null || cardInfo.getCardCode().trim().isEmpty())) {
            cardInfo.setCardCode(getNextCardCode());
        }

        // Bước 5: validate
        if (!validate(readerInfo, cardInfo)) {
            view.showError("Thông tin bạn đọc hoặc thẻ không hợp lệ hoặc bị để trống!");
            return;
        }

        // Bước 6 & 7: new Reader7, new ReaderCard7
        Reader7 reader = new Reader7(readerInfo);
        ReaderCard7 card = new ReaderCard7(cardInfo, reader);

        // Kiểm tra xem độc giả đã tồn tại trong CSDL chưa (trường hợp bạn đọc đã đăng nhập)
        Reader7 existingReader = readerDAO.getReaderByCode(reader.getReaderCode());
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");

        if (existingReader != null) {
            // Bạn đọc đã có tài khoản: Kiểm tra xem đã có thẻ chưa
            ReaderCard7 existingCard = readerCardDAO.getCardByReaderId(existingReader.getUserId());
            if (existingCard != null) {
                String expStr = existingCard.getExpiryDate() != null ? sdf.format(existingCard.getExpiryDate()) : "Không xác định";
                if (existingCard.isValid()) {
                    view.showError("Bạn đọc hiện đã sở hữu thẻ '" + existingCard.getCardCode() + "' còn hiệu lực (Hạn sử dụng: " + expStr + ")!");
                } else {
                    view.showError("Bạn đọc đã có thẻ '" + existingCard.getCardCode() + "' nhưng đã hết hạn vào ngày " + expStr + "!");
                }
                return;
            }

            // Kiểm tra mã thẻ mới có bị trùng không
            ReaderCard7 dupCard = readerCardDAO.getCardByCode(card.getCardCode());
            if (dupCard != null) {
                String expStr = dupCard.getExpiryDate() != null ? sdf.format(dupCard.getExpiryDate()) : "Không xác định";
                view.showError("Mã thẻ '" + dupCard.getCardCode() + "' đã tồn tại trên hệ thống (Hạn dùng: " + expStr + ")!");
                return;
            }

            card.setReader(existingReader);
            boolean addedCard = readerCardDAO.addCard(card);
            if (!addedCard) {
                view.showError("Lỗi hệ thống: Không thể cấp thẻ cho bạn đọc!");
                return;
            }

            view.showSuccess(card.getCardCode(), card.getExpiryDate());
            return;
        }

        // Trường hợp bạn đọc chưa có trong CSDL: Tạo mới cả bạn đọc và thẻ
        if (readerDAO.existsByUsernameOrReaderCode(reader.getUsername(), reader.getReaderCode())) {
            view.showError("Mã bạn đọc hoặc tên tài khoản đã tồn tại!");
            return;
        }

        // Bước 10 & 11: kiểm tra tồn tại cardCode
        ReaderCard7 dupCard = readerCardDAO.getCardByCode(card.getCardCode());
        if (dupCard != null) {
            String expStr = dupCard.getExpiryDate() != null ? sdf.format(dupCard.getExpiryDate()) : "Không xác định";
            view.showError("Mã thẻ '" + dupCard.getCardCode() + "' đã tồn tại trên hệ thống (Hạn dùng: " + expStr + ")!");
            return;
        }

        // Bước 12 & 13: addReader
        boolean addedReader = readerDAO.addReader(reader);
        if (!addedReader) {
            view.showError("Lỗi hệ thống: Không thể lưu thông tin bạn đọc!");
            return;
        }

        // Bước 14 & 15: addCard
        boolean addedCard = readerCardDAO.addCard(card);
        if (!addedCard) {
            view.showError("Lỗi hệ thống: Không thể cấp thẻ bạn đọc!");
            return;
        }

        // Bước 16 & 17: showSuccess
        view.showSuccess(card.getCardCode(), card.getExpiryDate());
    }

    private boolean validate(ReaderDTO readerInfo, CardDTO cardInfo) {
        if (readerInfo == null || cardInfo == null) return false;
        if (readerInfo.getFullName() == null || readerInfo.getFullName().trim().isEmpty()) return false;
        if (readerInfo.getReaderCode() == null || readerInfo.getReaderCode().trim().isEmpty()) return false;
        if (cardInfo.getCardCode() == null || cardInfo.getCardCode().trim().isEmpty()) return false;
        if (cardInfo.getExpiryDate() == null) return false;
        return true;
    }
}

