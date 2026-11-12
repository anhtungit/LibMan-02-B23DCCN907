-- =====================================================================
-- HỆ THỐNG QUẢN LÝ THƯ VIỆN - LIBMAN7
-- DDL SCRIPT CSDL (MySQL 8.0+)
-- Chuẩn hóa toàn bộ: Bảng, Cột, Khóa chính (PK), Khóa ngoại (FK),
-- Ràng buộc toàn vẹn (CHECK), Ràng buộc duy nhất (UNIQUE), Chỉ mục (INDEX).
-- Các thông tin này đã được lược giản trên biểu đồ DrawIO để tránh rối.
-- =====================================================================

DROP DATABASE IF EXISTS libman7_db;
CREATE DATABASE libman7_db CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
USE libman7_db;

-- -----------------------------------------------------
-- 1. BẢNG NGƯỜI DÙNG CƠ SỞ (tblUser7)
-- -----------------------------------------------------
CREATE TABLE tblUser7 (
    userId INT AUTO_INCREMENT PRIMARY KEY,
    username VARCHAR(255) NOT NULL UNIQUE,
    password VARCHAR(100) NOT NULL,
    fullName VARCHAR(100) NOT NULL,
    dateOfBirth DATE NULL,
    address VARCHAR(255) NULL,
    email VARCHAR(100) NULL,
    phone VARCHAR(20) NULL,
    role VARCHAR(20) NOT NULL,
    CONSTRAINT chk_user_role CHECK (role IN ('MANAGER', 'LIBRARIAN', 'READER'))
) ENGINE=InnoDB;

-- -----------------------------------------------------
-- 2. BẢNG QUẢN LÝ (tblManager7)
-- -----------------------------------------------------
CREATE TABLE tblManager7 (
    userId INT PRIMARY KEY,
    managerCode VARCHAR(20) NOT NULL UNIQUE,
    CONSTRAINT fk_manager_user FOREIGN KEY (userId)
        REFERENCES tblUser7(userId) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB;

-- -----------------------------------------------------
-- 3. BẢNG THỦ THƯ / NHÂN VIÊN (tblLibrarian7)
-- -----------------------------------------------------
CREATE TABLE tblLibrarian7 (
    userId INT PRIMARY KEY,
    staffCode VARCHAR(20) NOT NULL UNIQUE,
    position VARCHAR(50) NULL,
    CONSTRAINT fk_librarian_user FOREIGN KEY (userId)
        REFERENCES tblUser7(userId) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB;

-- -----------------------------------------------------
-- 4. BẢNG BẠN ĐỌC (tblReader7)
-- -----------------------------------------------------
CREATE TABLE tblReader7 (
    userId INT PRIMARY KEY,
    readerCode VARCHAR(20) NOT NULL UNIQUE,
    faculty VARCHAR(100) NULL,
    readerType VARCHAR(50) NULL,
    CONSTRAINT fk_reader_user FOREIGN KEY (userId)
        REFERENCES tblUser7(userId) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB;

-- -----------------------------------------------------
-- 5. BẢNG THẺ BẠN ĐỌC (tblReaderCard7)
-- Quan hệ 1-1 với tblReader7 qua userId UNIQUE
-- -----------------------------------------------------
CREATE TABLE tblReaderCard7 (
    cardCode VARCHAR(20) PRIMARY KEY,
    userId INT NOT NULL UNIQUE,
    registerDate DATE NOT NULL,
    expiryDate DATE NOT NULL,
    CONSTRAINT fk_card_reader FOREIGN KEY (userId)
        REFERENCES tblReader7(userId) ON DELETE CASCADE ON UPDATE CASCADE,
    CONSTRAINT chk_card_dates CHECK (expiryDate > registerDate)
) ENGINE=InnoDB;

CREATE INDEX idx_card_expiry ON tblReaderCard7(expiryDate);

-- -----------------------------------------------------
-- 6. BẢNG TÀI LIỆU (tblDocument7)
-- Gộp Sách, Giáo trình, Tạp chí qua docType
-- -----------------------------------------------------
CREATE TABLE tblDocument7 (
    documentId INT AUTO_INCREMENT PRIMARY KEY,
    title VARCHAR(255) NOT NULL,
    author VARCHAR(100) NULL,
    publisher VARCHAR(100) NULL,
    publishYear INT NULL,
    docType VARCHAR(50) NOT NULL,
    totalQuantity INT NOT NULL DEFAULT 0,
    location VARCHAR(50) NULL,
    description VARCHAR(255) NULL,
    CONSTRAINT chk_doc_qty CHECK (totalQuantity >= 0)
) ENGINE=InnoDB;

CREATE INDEX idx_doc_search ON tblDocument7(title, author);

-- -----------------------------------------------------
-- 7. BẢNG NHÀ CUNG CẤP (tblSupplier7)
-- -----------------------------------------------------
CREATE TABLE tblSupplier7 (
    supplierId INT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(100) NOT NULL,
    address VARCHAR(255) NULL,
    phone VARCHAR(20) NULL,
    email VARCHAR(100) NULL,
    taxCode VARCHAR(20) NULL
) ENGINE=InnoDB;

-- -----------------------------------------------------
-- 8. BẢNG PHIẾU NHẬP (tblImportSlip7)
-- -----------------------------------------------------
CREATE TABLE tblImportSlip7 (
    importSlipId INT AUTO_INCREMENT PRIMARY KEY,
    supplierId INT NOT NULL,
    librarianId INT NOT NULL,
    importDate DATE NOT NULL,
    CONSTRAINT fk_import_supplier FOREIGN KEY (supplierId)
        REFERENCES tblSupplier7(supplierId) ON DELETE RESTRICT ON UPDATE CASCADE,
    CONSTRAINT fk_import_librarian FOREIGN KEY (librarianId)
        REFERENCES tblLibrarian7(userId) ON DELETE RESTRICT ON UPDATE CASCADE
) ENGINE=InnoDB;

-- -----------------------------------------------------
-- 9. BẢNG CHI TIẾT PHIẾU NHẬP (tblImportDetail7)
-- -----------------------------------------------------
CREATE TABLE tblImportDetail7 (
    importDetailId INT AUTO_INCREMENT PRIMARY KEY,
    importSlipId INT NOT NULL,
    documentId INT NOT NULL,
    quantityImported INT NOT NULL,
    unitPrice INT NOT NULL,
    CONSTRAINT fk_detail_importslip FOREIGN KEY (importSlipId)
        REFERENCES tblImportSlip7(importSlipId) ON DELETE CASCADE ON UPDATE CASCADE,
    CONSTRAINT fk_detail_importdoc FOREIGN KEY (documentId)
        REFERENCES tblDocument7(documentId) ON DELETE RESTRICT ON UPDATE CASCADE,
    CONSTRAINT uq_import_doc UNIQUE (importSlipId, documentId),
    CONSTRAINT chk_import_qty CHECK (quantityImported > 0),
    CONSTRAINT chk_import_price CHECK (unitPrice >= 0)
) ENGINE=InnoDB;

-- -----------------------------------------------------
-- 10. BẢNG PHIẾU MƯỢN (tblBorrowSlip7)
-- -----------------------------------------------------
CREATE TABLE tblBorrowSlip7 (
    borrowSlipId INT AUTO_INCREMENT PRIMARY KEY,
    readerId INT NOT NULL,
    librarianId INT NOT NULL,
    borrowDate DATE NOT NULL,
    dueDate DATE NOT NULL,
    CONSTRAINT fk_borrow_reader FOREIGN KEY (readerId)
        REFERENCES tblReader7(userId) ON DELETE RESTRICT ON UPDATE CASCADE,
    CONSTRAINT fk_borrow_librarian FOREIGN KEY (librarianId)
        REFERENCES tblLibrarian7(userId) ON DELETE RESTRICT ON UPDATE CASCADE,
    CONSTRAINT chk_borrow_dates CHECK (dueDate >= borrowDate)
) ENGINE=InnoDB;

CREATE INDEX idx_borrow_dates ON tblBorrowSlip7(borrowDate, dueDate);

-- -----------------------------------------------------
-- 11. BẢNG CHI TIẾT PHIẾU MƯỢN (tblBorrowDetail7)
-- -----------------------------------------------------
CREATE TABLE tblBorrowDetail7 (
    borrowDetailId INT AUTO_INCREMENT PRIMARY KEY,
    borrowSlipId INT NOT NULL,
    documentId INT NOT NULL,
    quantityBorrowed INT NOT NULL,
    CONSTRAINT fk_detail_borrowslip FOREIGN KEY (borrowSlipId)
        REFERENCES tblBorrowSlip7(borrowSlipId) ON DELETE CASCADE ON UPDATE CASCADE,
    CONSTRAINT fk_detail_borrowdoc FOREIGN KEY (documentId)
        REFERENCES tblDocument7(documentId) ON DELETE RESTRICT ON UPDATE CASCADE,
    CONSTRAINT uq_borrow_doc UNIQUE (borrowSlipId, documentId),
    CONSTRAINT chk_borrow_qty CHECK (quantityBorrowed > 0)
) ENGINE=InnoDB;

-- -----------------------------------------------------
-- 12. BẢNG PHIẾU TRẢ (tblReturnSlip7)
-- -----------------------------------------------------
CREATE TABLE tblReturnSlip7 (
    returnSlipId INT AUTO_INCREMENT PRIMARY KEY,
    readerId INT NOT NULL,
    librarianId INT NOT NULL,
    returnDate DATE NULL,
    paymentStatus VARCHAR(20) NOT NULL,
    paymentDate DATE NULL,
    CONSTRAINT fk_return_reader FOREIGN KEY (readerId)
        REFERENCES tblReader7(userId) ON DELETE RESTRICT ON UPDATE CASCADE,
    CONSTRAINT fk_return_librarian FOREIGN KEY (librarianId)
        REFERENCES tblLibrarian7(userId) ON DELETE RESTRICT ON UPDATE CASCADE,
    CONSTRAINT chk_payment_status CHECK (paymentStatus IN ('UNPAID', 'PAID', 'NO_FINE')),
    CONSTRAINT chk_payment_date CHECK (paymentStatus <> 'PAID' OR paymentDate IS NOT NULL)
) ENGINE=InnoDB;

-- -----------------------------------------------------
-- 13. BẢNG CHI TIẾT PHIẾU TRẢ (tblReturnDetail7)
-- -----------------------------------------------------
CREATE TABLE tblReturnDetail7 (
    returnDetailId INT AUTO_INCREMENT PRIMARY KEY,
    returnSlipId INT NOT NULL,
    borrowDetailId INT NOT NULL,
    quantityReturned INT NOT NULL,
    lateFine DECIMAL(12,2) NOT NULL DEFAULT 0.00,
    damageFine DECIMAL(12,2) NOT NULL DEFAULT 0.00,
    `condition` VARCHAR(50) NOT NULL DEFAULT 'GOOD',
    CONSTRAINT fk_detail_returnslip FOREIGN KEY (returnSlipId)
        REFERENCES tblReturnSlip7(returnSlipId) ON DELETE CASCADE ON UPDATE CASCADE,
    CONSTRAINT fk_detail_borrowdetail FOREIGN KEY (borrowDetailId)
        REFERENCES tblBorrowDetail7(borrowDetailId) ON DELETE RESTRICT ON UPDATE CASCADE,
    CONSTRAINT uq_return_borrowdetail UNIQUE (returnSlipId, borrowDetailId),
    CONSTRAINT chk_return_qty CHECK (quantityReturned > 0),
    CONSTRAINT chk_fines CHECK (lateFine >= 0 AND damageFine >= 0),
    CONSTRAINT chk_condition CHECK (`condition` IN ('GOOD', 'DAMAGED', 'LOST'))
) ENGINE=InnoDB;
