-- =====================================================================
-- HỆ THỐNG QUẢN LÝ THƯ VIỆN - LIBMAN7
-- DỮ LIỆU MẪU ĐA DẠNG (SEED DATA)
-- 1 người mượn nhiều quyển, đa dạng ngày trễ, hỏng hóc, đền bù
-- =====================================================================

USE libman7_db;
SET NAMES utf8mb4;

-- Tắt kiểm tra khóa ngoại tạm thời để dọn sạch dữ liệu cũ
SET FOREIGN_KEY_CHECKS = 0;
TRUNCATE TABLE tblReturnDetail7;
TRUNCATE TABLE tblReturnSlip7;
TRUNCATE TABLE tblBorrowDetail7;
TRUNCATE TABLE tblBorrowSlip7;
TRUNCATE TABLE tblImportDetail7;
TRUNCATE TABLE tblImportSlip7;
TRUNCATE TABLE tblReaderCard7;
TRUNCATE TABLE tblReader7;
TRUNCATE TABLE tblLibrarian7;
TRUNCATE TABLE tblManager7;
TRUNCATE TABLE tblUser7;
TRUNCATE TABLE tblDocument7;
TRUNCATE TABLE tblSupplier7;
SET FOREIGN_KEY_CHECKS = 1;

-- -----------------------------------------------------
-- 1. BẢNG NGƯỜI DÙNG (tblUser7)
-- -----------------------------------------------------
INSERT INTO tblUser7 (userId, username, password, fullName, dateOfBirth, address, email, phone, role) VALUES
-- Quản lý
(1, 'manager1', '123456', 'PGS.TS Trần Quang Đạt', '1975-03-12', 'Cầu Giấy, Hà Nội', 'dat.tq@libman.edu.vn', '0912334455', 'MANAGER'),
(2, 'manager2', '123456', 'ThS. Vũ Thị Bích Liên', '1980-09-25', 'Đống Đa, Hà Nội', 'lien.vtb@libman.edu.vn', '0912998877', 'MANAGER'),

-- Thủ thư
(3, 'librarian1', '123456', 'Nguyễn Văn Thủ Thư', '1990-05-10', 'Thanh Xuân, Hà Nội', 'lib1@gmail.com', '0912345678', 'LIBRARIAN'),
(4, 'librarian2', '123456', 'Hoàng Thị Thanh Tâm', '1994-11-18', 'Hai Bà Trưng, Hà Nội', 'tam.htt@gmail.com', '0933445566', 'LIBRARIAN'),
(5, 'librarian3', '123456', 'Đỗ Quang Dũng', '1992-07-04', 'Hoàng Mai, Hà Nội', 'dung.dq@gmail.com', '0944556677', 'LIBRARIAN'),

-- Bạn đọc
(6, 'reader1', '123456', 'Trần Văn Độc Giả', '2004-08-15', 'Hà Đông, Hà Nội', 'reader1@gmail.com', '0987654321', 'READER'),
(7, 'reader2', '123456', 'Lê Thị Mai', '2004-12-20', 'Nam Định', 'mai.lt@gmail.com', '0977112233', 'READER'),
(8, 'reader3', '123456', 'Phạm Quốc Anh', '2003-04-10', 'Bắc Ninh', 'anh.pq@gmail.com', '0966223344', 'READER'),
(9, 'reader4', '123456', 'TS. Hoàng Minh Tuấn', '1985-02-14', 'Ba Đình, Hà Nội', 'tuan.hm@ptit.edu.vn', '0903112233', 'READER'),
(10, 'reader5', '123456', 'Đỗ Thị Lan', '2001-06-30', 'Thái Bình', 'lan.dt@gmail.com', '0988776655', 'READER'),
(11, 'reader6', '123456', 'Nguyễn Hải Đăng', '2004-10-05', 'Hải Phòng', 'dang.nh@gmail.com', '0918273645', 'READER'),
(12, 'reader7', '123456', 'Vũ Thu Hương', '2005-01-22', 'Quảng Ninh', 'huong.vt@gmail.com', '0929384756', 'READER'),
(13, 'reader8', '123456', 'Trần Minh Thư', '2004-03-18', 'Hà Nội', 'thu.tm@gmail.com', '0938112233', 'READER'),
(14, 'reader9', '123456', 'Phạm Quang Minh', '2003-11-25', 'Hải Dương', 'minh.pq@gmail.com', '0948223344', 'READER'),
(15, 'reader10', '123456', 'Ngô Tiến Dũng', '2004-05-14', 'Hưng Yên', 'dung.nt@gmail.com', '0958334455', 'READER'),
(16, 'reader11', '123456', 'Bùi Hoàng Nam', '2000-09-08', 'Vĩnh Phúc', 'nam.bh@gmail.com', '0968445566', 'READER'),
(17, 'reader12', '123456', 'ThS. Nguyễn Văn Toàn', '1988-12-02', 'Hà Nội', 'toan.nv@ptit.edu.vn', '0978556677', 'READER');

-- -----------------------------------------------------
-- 2. BẢNG CHI TIẾT VAI TRÒ
-- -----------------------------------------------------
INSERT INTO tblManager7 (userId, managerCode) VALUES
(1, 'MGR001'),
(2, 'MGR002');

INSERT INTO tblLibrarian7 (userId, staffCode, position) VALUES
(3, 'LIB001', 'Thủ thư trưởng quầy'),
(4, 'LIB002', 'Thủ thư kiểm kê'),
(5, 'LIB003', 'Thủ thư nhập kho');

INSERT INTO tblReader7 (userId, readerCode, faculty, readerType) VALUES
(6, 'RD001', 'Công nghệ Thông tin', 'Sinh viên'),
(7, 'RD002', 'Kinh tế & Quản trị', 'Sinh viên'),
(8, 'RD003', 'Điện tử Viễn thông', 'Sinh viên'),
(9, 'RD004', 'Công nghệ Thông tin', 'Giảng viên'),
(10, 'RD005', 'Khoa học Máy tính', 'Học viên cao học'),
(11, 'RD006', 'Ngoại ngữ', 'Sinh viên'),               -- Chưa có thẻ
(12, 'RD007', 'Kỹ thuật Đa phương tiện', 'Sinh viên'), -- Chưa có thẻ
(13, 'RD008', 'Kinh tế & Quản trị', 'Sinh viên'),      -- Chưa có thẻ (test Khoa Kinh tế & Quản trị)
(14, 'RD009', 'Kinh tế & Quản trị', 'Sinh viên'),      -- Chưa có thẻ
(15, 'RD010', 'Công nghệ Thông tin', 'Sinh viên'),     -- Chưa có thẻ
(16, 'RD011', 'Điện tử Viễn thông', 'Học viên cao học'), -- Chưa có thẻ
(17, 'RD012', 'Cơ bản 1', 'Giảng viên');               -- Chưa có thẻ

-- -----------------------------------------------------
-- 3. BẢNG THẺ BẠN ĐỌC (tblReaderCard7)
-- -----------------------------------------------------
INSERT INTO tblReaderCard7 (cardCode, userId, registerDate, expiryDate) VALUES
('CARD001', 6, '2026-01-01', '2027-01-01'),
('CARD002', 7, '2026-02-01', '2027-02-01'),
('CARD003', 8, '2026-03-01', '2027-03-01'),
('CARD004', 9, '2025-09-01', '2028-09-01'),
('CARD005', 10, '2026-04-15', '2027-04-15');

-- -----------------------------------------------------
-- 4. BẢNG TÀI LIỆU (tblDocument7)
-- -----------------------------------------------------
INSERT INTO tblDocument7 (documentId, title, author, publisher, publishYear, docType, totalQuantity, location, description) VALUES
(1, 'Lập trình Java Căn bản & Nâng cao', 'Nguyễn Hữu', 'NXB Khoa học & Kỹ thuật', 2023, 'Sách', 12, 'Kệ A1-01', 'Thực hành Java từ cơ bản, Swing đến JDBC'),
(2, 'Clean Code - Mã Sạch', 'Robert C. Martin', 'NXB Thông tin & Truyền thông', 2022, 'Sách', 8, 'Kệ A1-02', 'Cẩm nang tối ưu cấu trúc code và refactoring'),
(3, 'Design Patterns - Các Mẫu Thiết Kế Hướng Đối Tượng', 'Erich Gamma, GoF', 'NXB Khoa học Tự nhiên', 2021, 'Sách', 6, 'Kệ A1-03', '23 mẫu thiết kế phần mềm kinh điển'),
(4, 'Trí Tuệ Nhân Tạo Trong Kỷ Nguyên Số', 'Stuart Russell', 'NXB Đại học Quốc gia', 2024, 'Sách', 15, 'Kệ A2-01', 'Machine Learning, Deep Learning và Agent AI'),
(5, 'Cơ Sở Dữ Liệu Nâng Cao & Tối Ưu Hóa Truy Vấn', 'Lê Tiến Dũng', 'NXB Bách Khoa', 2023, 'Sách', 10, 'Kệ A2-02', 'Tối ưu chỉ mục, Transaction và chuẩn 3NF/BCNF'),
(6, 'Nhập môn Công nghệ Phần mềm', 'Trần Minh', 'NXB Đại học Quốc gia', 2024, 'Giáo trình', 25, 'Kệ B1-01', 'Quy trình phát triển phần mềm Agile và Waterfall'),
(7, 'Giáo trình Cấu trúc Dữ liệu và Giải thuật', 'Đỗ Xuân Lôi', 'NXB Giáo Dục', 2022, 'Giáo trình', 30, 'Kệ B1-02', 'Danh sách liên kết, cây nhị phân, đồ thị và sắp xếp'),
(8, 'Giáo trình Mạng Máy tính & An toàn Mạng', 'Nguyễn Kim Khánh', 'NXB Giáo Dục', 2023, 'Giáo trình', 20, 'Kệ B2-01', 'Mô hình OSI, TCP/IP, Định tuyến và An ninh mạng'),
(9, 'Giáo trình Phân tích và Thiết kế Hệ thống Thông tin', 'Nguyễn Văn Ba', 'NXB Đại học Quốc gia', 2024, 'Giáo trình', 18, 'Kệ B2-02', 'Biểu đồ Use case, Sequence, Activity và Lớp'),
(10, 'Tạp chí Tin học & Đời sống - Số AI 2026', 'Hội Tin học Việt Nam', 'NXB Tin học', 2026, 'Tạp chí', 5, 'Kệ C1-01', 'Số chuyên đề về ứng dụng Trí tuệ nhân tạo'),
(11, 'Tạp chí Khoa học & Công nghệ Việt Nam Số 05/2026', 'Bộ KH&CN', 'NXB Khoa học', 2026, 'Tạp chí', 7, 'Kệ C1-02', 'Các nghiên cứu công nghệ mới nổi'),
(12, 'Tạp chí An toàn Thông tin Số 02/2026', 'Ban Cơ yếu Chính phủ', 'NXB Công an', 2026, 'Tạp chí', 4, 'Kệ C1-03', 'Phòng chống mã độc và rò rỉ dữ liệu');

-- -----------------------------------------------------
-- 5. BẢNG NHÀ CUNG CẤP (tblSupplier7)
-- -----------------------------------------------------
INSERT INTO tblSupplier7 (supplierId, name, address, phone, email, taxCode) VALUES
(1, 'Nhà xuất bản Giáo Dục Việt Nam', '81 Trần Hưng Đạo, Hoàn Kiếm, Hà Nội', '02438220801', 'nxbgd@moet.edu.vn', '0100115212'),
(2, 'Công ty Cổ phần Sách Fahasa', '60-62 Lê Lợi, Quận 1, TP.HCM', '02838225446', 'info@fahasa.com', '0304132047'),
(3, 'Nhà xuất bản Thông tin và Truyền thông', 'Cục Viễn thông, Cầu Giấy, Hà Nội', '02435772143', 'nxb.tttt@mic.gov.vn', '0101684347'),
(4, 'Công ty CP Sách Alpha (Alpha Books)', '176 Thái Hà, Đống Đa, Hà Nội', '02437226234', 'contact@alphabooks.vn', '0101608466');

-- -----------------------------------------------------
-- 6. BẢNG PHIẾU NHẬP & CHI TIẾT NHẬP (tblImportSlip7, tblImportDetail7)
-- -----------------------------------------------------
INSERT INTO tblImportSlip7 (importSlipId, supplierId, librarianId, importDate) VALUES
(1, 1, 5, '2026-08-10'),
(2, 2, 5, '2026-09-05'),
(3, 4, 5, '2026-10-01');

INSERT INTO tblImportDetail7 (importDetailId, importSlipId, documentId, quantityImported, unitPrice) VALUES
(1, 1, 6, 30, 45000),
(2, 1, 7, 35, 50000),
(3, 2, 1, 15, 85000),
(4, 2, 2, 10, 120000),
(5, 3, 4, 20, 150000),
(6, 3, 10, 10, 35000);

-- -----------------------------------------------------
-- 7. BẢNG PHIẾU MƯỢN & CHI TIẾT MƯỢN (tblBorrowSlip7, tblBorrowDetail7)
-- CẢ 5 BẠN ĐỌC ĐỀU ĐANG MƯỢN NHIỀU QUYỂN VÀ ĐỀU CÓ HẠN TRẢ BỊ TRỄ NGÀY
-- -----------------------------------------------------

-- [BẠN ĐỌC 1 - RD001]: Trần Văn Độc Giả (userId: 6)
-- Đợt 1: Hạn 2026-10-10 -> Trễ 33 ngày
INSERT INTO tblBorrowSlip7 (borrowSlipId, readerId, librarianId, borrowDate, dueDate) VALUES
(1, 6, 3, '2026-09-20', '2026-10-10');

INSERT INTO tblBorrowDetail7 (borrowDetailId, borrowSlipId, documentId, quantityBorrowed) VALUES
(1, 1, 6, 1), -- Nhập môn Công nghệ Phần mềm
(2, 1, 1, 2), -- Lập trình Java Căn bản & Nâng cao (2 cuốn)
(3, 1, 3, 1); -- Design Patterns

-- Đợt 2 của RD001: Hạn 2026-11-06 -> Trễ 6 ngày
INSERT INTO tblBorrowSlip7 (borrowSlipId, readerId, librarianId, borrowDate, dueDate) VALUES
(2, 6, 3, '2026-10-20', '2026-11-06');

INSERT INTO tblBorrowDetail7 (borrowDetailId, borrowSlipId, documentId, quantityBorrowed) VALUES
(4, 2, 2, 1), -- Clean Code
(5, 2, 5, 1), -- Cơ Sở Dữ Liệu Nâng Cao
(6, 2, 10, 1); -- Tạp chí Tin học & Đời sống

-- [BẠN ĐỌC 2 - RD002]: Lê Thị Mai (userId: 7)
-- Đợt 1: Hạn 2026-10-25 -> Trễ 18 ngày
INSERT INTO tblBorrowSlip7 (borrowSlipId, readerId, librarianId, borrowDate, dueDate) VALUES
(3, 7, 4, '2026-10-05', '2026-10-25');

INSERT INTO tblBorrowDetail7 (borrowDetailId, borrowSlipId, documentId, quantityBorrowed) VALUES
(7, 3, 7, 2), -- Cấu trúc Dữ liệu & Giải thuật (2 cuốn)
(8, 3, 8, 1), -- Mạng Máy tính
(9, 3, 11, 1); -- Tạp chí KH&CN Việt Nam

-- Đợt 2 của RD002: Hạn 2026-11-08 -> Trễ 4 ngày
INSERT INTO tblBorrowSlip7 (borrowSlipId, readerId, librarianId, borrowDate, dueDate) VALUES
(4, 7, 4, '2026-10-25', '2026-11-08');

INSERT INTO tblBorrowDetail7 (borrowDetailId, borrowSlipId, documentId, quantityBorrowed) VALUES
(10, 4, 4, 1), -- Trí Tuệ Nhân Tạo Trong Kỷ Nguyên Số
(11, 4, 9, 1); -- Phân tích & Thiết kế HTTT

-- [BẠN ĐỌC 3 - RD003]: Phạm Quốc Anh (userId: 8)
-- Đợt 1: Hạn 2026-10-20 -> Trễ 23 ngày
INSERT INTO tblBorrowSlip7 (borrowSlipId, readerId, librarianId, borrowDate, dueDate) VALUES
(5, 8, 4, '2026-10-01', '2026-10-20');

INSERT INTO tblBorrowDetail7 (borrowDetailId, borrowSlipId, documentId, quantityBorrowed) VALUES
(12, 5, 4, 1), -- Trí Tuệ Nhân Tạo Trong Kỷ Nguyên Số
(13, 5, 9, 2), -- Phân tích & Thiết kế HTTT (2 cuốn)
(14, 5, 12, 1); -- Tạp chí An toàn Thông tin

-- Đợt 2 của RD003: Hạn 2026-11-07 -> Trễ 5 ngày
INSERT INTO tblBorrowSlip7 (borrowSlipId, readerId, librarianId, borrowDate, dueDate) VALUES
(6, 8, 4, '2026-10-22', '2026-11-07');

INSERT INTO tblBorrowDetail7 (borrowDetailId, borrowSlipId, documentId, quantityBorrowed) VALUES
(15, 6, 1, 1), -- Lập trình Java
(16, 6, 2, 1); -- Clean Code

-- [BẠN ĐỌC 4 - RD004]: TS. Hoàng Minh Tuấn (userId: 9)
-- Đợt 1: Hạn 2026-10-15 -> Trễ 28 ngày
INSERT INTO tblBorrowSlip7 (borrowSlipId, readerId, librarianId, borrowDate, dueDate) VALUES
(7, 9, 3, '2026-09-25', '2026-10-15');

INSERT INTO tblBorrowDetail7 (borrowDetailId, borrowSlipId, documentId, quantityBorrowed) VALUES
(17, 7, 2, 1), -- Clean Code
(18, 7, 3, 2), -- Design Patterns (2 cuốn)
(19, 7, 5, 1); -- Cơ Sở Dữ Liệu Nâng Cao

-- Đợt 2 của RD004: Hạn 2026-11-05 -> Trễ 7 ngày
INSERT INTO tblBorrowSlip7 (borrowSlipId, readerId, librarianId, borrowDate, dueDate) VALUES
(8, 9, 3, '2026-10-18', '2026-11-05');

INSERT INTO tblBorrowDetail7 (borrowDetailId, borrowSlipId, documentId, quantityBorrowed) VALUES
(20, 8, 8, 1), -- Mạng Máy tính
(21, 8, 10, 1); -- Tạp chí Tin học & Đời sống

-- [BẠN ĐỌC 5 - RD005]: Đỗ Thị Lan (userId: 10)
-- Đợt 1: Hạn 2026-10-18 -> Trễ 25 ngày
INSERT INTO tblBorrowSlip7 (borrowSlipId, readerId, librarianId, borrowDate, dueDate) VALUES
(9, 10, 4, '2026-09-28', '2026-10-18');

INSERT INTO tblBorrowDetail7 (borrowDetailId, borrowSlipId, documentId, quantityBorrowed) VALUES
(22, 9, 6, 1), -- Nhập môn CNPM
(23, 9, 7, 1), -- Cấu trúc Dữ liệu & Giải thuật
(24, 9, 4, 1); -- Trí Tuệ Nhân Tạo

-- Đợt 2 của RD005: Hạn 2026-11-09 -> Trễ 3 ngày
INSERT INTO tblBorrowSlip7 (borrowSlipId, readerId, librarianId, borrowDate, dueDate) VALUES
(10, 10, 4, '2026-10-24', '2026-11-09');

INSERT INTO tblBorrowDetail7 (borrowDetailId, borrowSlipId, documentId, quantityBorrowed) VALUES
(25, 10, 1, 2), -- Lập trình Java (2 cuốn)
(26, 10, 12, 1); -- Tạp chí An toàn Thông tin

-- -----------------------------------------------------
-- 8. BẢNG PHIẾU TRẢ & CHI TIẾT TRẢ LỊCH SỬ (tblReturnSlip7, tblReturnDetail7)
-- CẢ 5 BẠN ĐỌC ĐỀU CÓ LỊCH SỬ VỪA BỊ TRỄ HẠN VỪA BỊ HỎNG/MẤT ĐỒ
-- -----------------------------------------------------

-- Các đợt mượn trong quá khứ đã được hoàn tất việc trả:
INSERT INTO tblBorrowSlip7 (borrowSlipId, readerId, librarianId, borrowDate, dueDate) VALUES
(11, 6, 3, '2026-08-01', '2026-08-15'), -- RD001
(12, 7, 3, '2026-08-10', '2026-08-25'), -- RD002
(13, 8, 4, '2026-09-01', '2026-09-15'), -- RD003
(14, 9, 3, '2026-09-10', '2026-09-25'), -- RD004
(15, 10, 4, '2026-09-15', '2026-09-30'); -- RD005

INSERT INTO tblBorrowDetail7 (borrowDetailId, borrowSlipId, documentId, quantityBorrowed) VALUES
(27, 11, 6, 1), -- RD001: Nhập môn CNPM
(28, 11, 1, 1), -- RD001: Lập trình Java
(29, 12, 7, 1), -- RD002: Cấu trúc Dữ liệu
(30, 12, 8, 1), -- RD002: Mạng Máy tính
(31, 13, 2, 1), -- RD003: Clean Code
(32, 13, 5, 1), -- RD003: CSDL Nâng cao
(33, 14, 3, 1), -- RD004: Design Patterns
(34, 14, 4, 1), -- RD004: Trí Tuệ Nhân Tạo
(35, 15, 9, 1), -- RD005: PT&TK Hệ thống TT
(36, 15, 1, 1); -- RD005: Lập trình Java

-- [PHIẾU TRẢ 1 - RD001]: VỪA TRỄ 36 NGÀY VỪA HỎNG GÁY -> ĐÃ THU PHẠT (PAID)
INSERT INTO tblReturnSlip7 (returnSlipId, readerId, librarianId, returnDate, paymentStatus, paymentDate) VALUES
(1, 6, 3, '2026-09-20', 'PAID', '2026-09-20');

INSERT INTO tblReturnDetail7 (returnDetailId, returnSlipId, borrowDetailId, quantityReturned, lateFine, damageFine, `condition`) VALUES
(1, 1, 27, 1, 72000.00, 50000.00, 'DAMAGED'), -- Trễ 36 ngày (72k) + Hỏng gáy (50k) = 122k
(2, 1, 28, 1, 72000.00, 0.00, 'GOOD');        -- Trễ 36 ngày (72k) + Sách tốt = 72k

-- [PHIẾU TRẢ 2 - RD002]: VỪA TRỄ 21 NGÀY VỪA RÁCH BÌA -> ĐÃ THU PHẠT (PAID)
INSERT INTO tblReturnSlip7 (returnSlipId, readerId, librarianId, returnDate, paymentStatus, paymentDate) VALUES
(2, 7, 4, '2026-09-15', 'PAID', '2026-09-15');

INSERT INTO tblReturnDetail7 (returnDetailId, returnSlipId, borrowDetailId, quantityReturned, lateFine, damageFine, `condition`) VALUES
(3, 2, 29, 1, 42000.00, 30000.00, 'DAMAGED'), -- Trễ 21 ngày (42k) + Rách bìa (30k) = 72k
(4, 2, 30, 1, 42000.00, 0.00, 'GOOD');        -- Trễ 21 ngày (42k) + Sách tốt = 42k

-- [PHIẾU TRẢ 3 - RD003]: VỪA TRỄ 25 NGÀY VỪA MẤT SÁCH + HỎNG ẨM -> CHƯA NỘP PHẠT (UNPAID)
INSERT INTO tblReturnSlip7 (returnSlipId, readerId, librarianId, returnDate, paymentStatus, paymentDate) VALUES
(3, 8, 4, '2026-10-10', 'UNPAID', NULL);

INSERT INTO tblReturnDetail7 (returnDetailId, returnSlipId, borrowDetailId, quantityReturned, lateFine, damageFine, `condition`) VALUES
(5, 3, 31, 1, 50000.00, 120000.00, 'LOST'),   -- Trễ 25 ngày (50k) + Mất sách đền bìa (120k) = 170k
(6, 3, 32, 1, 50000.00, 40000.00, 'DAMAGED'); -- Trễ 25 ngày (50k) + Ẩm ướt rách trang (40k) = 90k

-- [PHIẾU TRẢ 4 - RD004]: VỪA TRỄ 25 NGÀY VỪA BỊ RÁCH BÌA -> ĐÃ NỘP PHẠT (PAID)
INSERT INTO tblReturnSlip7 (returnSlipId, readerId, librarianId, returnDate, paymentStatus, paymentDate) VALUES
(4, 9, 3, '2026-10-20', 'PAID', '2026-10-20');

INSERT INTO tblReturnDetail7 (returnDetailId, returnSlipId, borrowDetailId, quantityReturned, lateFine, damageFine, `condition`) VALUES
(7, 4, 33, 1, 50000.00, 60000.00, 'DAMAGED'), -- Trễ 25 ngày (50k) + Hỏng bìa gáy (60k) = 110k
(8, 4, 34, 1, 50000.00, 0.00, 'GOOD');        -- Trễ 25 ngày (50k) + Sách tốt = 50k

-- [PHIẾU TRẢ 5 - RD005]: VỪA TRỄ 25 NGÀY VỪA LÀM MẤT SÁCH -> CHƯA NỘP PHẠT (UNPAID)
INSERT INTO tblReturnSlip7 (returnSlipId, readerId, librarianId, returnDate, paymentStatus, paymentDate) VALUES
(5, 10, 4, '2026-10-25', 'UNPAID', NULL);

INSERT INTO tblReturnDetail7 (returnDetailId, returnSlipId, borrowDetailId, quantityReturned, lateFine, damageFine, `condition`) VALUES
(9, 5, 35, 1, 50000.00, 50000.00, 'DAMAGED'), -- Trễ 25 ngày (50k) + Hỏng rách (50k) = 100k
(10, 5, 36, 1, 50000.00, 100000.00, 'LOST');  -- Trễ 25 ngày (50k) + Mất sách đền bù (100k) = 150k
