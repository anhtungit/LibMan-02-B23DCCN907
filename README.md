# HỆ THỐNG QUẢN LÝ THƯ VIỆN ĐẠI HỌC - LIBMAN7

> **Học phần:** Phân tích & Thiết kế Hệ thống Thông tin  
> **Mã sinh viên:** B23DCCN907  
> **Nhóm thực hiện:** Nhóm 02  
> **Repository:** [LibMan-02-B23DCCN907](https://github.com/anhtungit/LibMan-02-B23DCCN907)  

---

## 1. Giới thiệu tổng quan

**LibMan7** là ứng dụng desktop quản lý thư viện đại học được xây dựng theo chuẩn kiến trúc MVC / 3 lớp (Presentation - Business/Controller - Data Access Layer) kết nối cơ sở dữ liệu quan hệ MySQL. Ứng dụng đáp ứng các quy trình nghiệp vụ thư viện với hai module trọng tâm:
1. **Module 1 (Bạn đọc):** Đăng ký làm thẻ bạn đọc trực tuyến.
2. **Module 2 (Thủ thư):** Nhận trả tài liệu từ bạn đọc, xử lý phạt quá hạn/hỏng hóc và xuất hóa đơn phiếu trả ra định dạng PDF.

---

## 2. Công nghệ sử dụng

- **Ngôn ngữ:** Java (JDK 17)
- **Giao diện:** Java Swing (Desktop GUI)
- **Cơ sở dữ liệu:** MySQL 8.0 (chuẩn hóa 3NF/BCNF)
- **Kết nối CSDL:** JDBC (MySQL Connector/J 8.3.0)
- **Quản lý dự án & Build:** Apache Maven
- **Xuất hóa đơn / Báo cáo:** iText PDF 5.5.13.3 (Hỗ trợ font Unicode Tiếng Việt)

---

## 3. Kiến trúc mã nguồn

```
PTTK_Code/
├── src/main/
│   ├── java/libman/
│   │   ├── controller/               # Lớp điều khiển nghiệp vụ (Controller)
│   │   │   ├── RegisterReaderController7.java
│   │   │   └── ReturnController7.java
│   │   ├── dao/                      # Lớp truy xuất dữ liệu (Data Access Object)
│   │   │   ├── DAO7.java
│   │   │   ├── UserDAO7.java
│   │   │   ├── ReaderDAO7.java
│   │   │   ├── ReaderCardDAO7.java
│   │   │   ├── DocumentDAO7.java
│   │   │   ├── BorrowDetailDAO7.java
│   │   │   └── ReturnSlipDAO7.java
│   │   ├── dto/                      # Đối tượng truyền dữ liệu (Data Transfer Object)
│   │   │   ├── ReaderDTO.java
│   │   │   └── CardDTO.java
│   │   ├── entity/                   # Các thực thể dữ liệu (POJO)
│   │   │   ├── User7.java, Librarian7.java, Reader7.java, Manager7.java
│   │   │   ├── Document7.java, Supplier7.java, ReaderCard7.java
│   │   │   ├── BorrowSlip7.java, BorrowDetail7.java
│   │   │   └── ReturnSlip7.java, ReturnDetail7.java
│   │   ├── main/
│   │   │   └── MainApp7.java         # Điểm khởi chạy chính của ứng dụng
│   │   ├── util/
│   │   │   └── PdfExportUtil7.java   # Tiện ích tạo và xuất phiếu trả sang file PDF
│   │   └── view/                     # Giao diện người dùng (Swing Views)
│   │       ├── LoginView7.java
│   │       ├── reader/               # Giao diện bạn đọc (Dashboard, Đăng ký thẻ)
│   │       ├── staff/                # Giao diện thủ thư (Tìm kiếm, Trả tài liệu, Phiếu trả)
│   │       └── manager/              # Giao diện người quản lý (Thống kê, Báo cáo)
│   └── resources/
│       ├── db.properties             # Cấu hình chuỗi kết nối MySQL
│       └── sql/
│           ├── LibMan7_Schema.sql    # Kịch bản DDL tạo bảng CSDL
│           └── seed_data.sql         # Dữ liệu mẫu kiểm thử phong phú
├── pom.xml                           # Cấu hình phụ thuộc Maven
├── run_app.bat                       # Script 1-click khởi chạy ứng dụng
└── README.md
```

---

## 4. Các chức năng chính

### 4.1. Module 1: Đăng ký làm thẻ bạn đọc trực tuyến
- Độc giả đăng nhập hệ thống và chọn chức năng **Đăng ký làm thẻ bạn đọc**.
- Form đăng ký tự động nhận diện thông tin cá nhân của bạn đọc đang đăng nhập.
- Mã thẻ bạn đọc được hệ thống tự động sinh tăng dần theo cơ sở dữ liệu (`CARD001`, `CARD002`, ...).
- Tự động kiểm tra thẻ hiện hữu: nếu bạn đọc đã có thẻ còn hạn, hệ thống sẽ cảnh báo kèm ngày hết hạn cụ thể của thẻ.

### 4.2. Module 2: Nhận trả tài liệu từ bạn đọc
- **Tìm kiếm:** Thủ thư quét mã thẻ hoặc nhập mã bạn đọc (`RD001`, `RD002`, ...).
- **Danh sách mượn:** Tải toàn bộ tài liệu đang mượn của bạn đọc kèm số ngày quá hạn tính đến ngày hiện tại.
- **Tính phạt thông minh thời gian thực:**
  * Tự động tính tiền phạt trễ hạn theo công thức: `Số ngày trễ × 2.000 VNĐ × Số lượng`.
  * Hỗ trợ chọn tình trạng tài liệu trả thực tế:
    - `GOOD`: Tài liệu tốt, phạt hỏng = 0 VNĐ.
    - `DAMAGED`: Tài liệu bị hư hỏng, tự động gợi ý phạt 50.000 VNĐ (có thể sửa).
    - `LOST`: Tài liệu bị mất, tự động gợi ý phạt 100.000 VNĐ (có thể sửa).
  * Tổng tiền phạt tự động cập nhật ngay trên giao diện theo từng thao tác tích chọn.
- **Lập & In phiếu trả:**
  * Hiển thị bảng tổng hợp chi phí, mã phiếu và tình trạng từng cuốn.
  * Xuất trực tiếp ra **file PDF** chuẩn A4 có hỗ trợ tiếng Việt có dấu, lưu vào máy và tự động mở file sau khi xuất.
  * Cập nhật số lượng tồn kho và ghi nhận trạng thái thanh toán tiền phạt (`PAID` / `UNPAID`).

### 4.3. Menu Bar & Dashboard đa vai trò
- Phân quyền theo vai trò (`LIBRARIAN`, `READER`, `MANAGER`) khi đăng nhập.
- Bố trí Menu Bar và Dashboard thẻ chức năng đầy đủ theo chuẩn tài liệu đặc tả nghiệp vụ thư viện. Các chức năng ngoài module trọng tâm được thông báo popup đang phát triển thân thiện.

---

## 5. Hướng dẫn cài đặt và chạy ứng dụng

### Bước 1: Chuẩn bị môi trường
- Đã cài đặt **Java JDK 17** trở lên.
- Đã cài đặt và khởi chạy **MySQL Server 8.0+** (mặc định cổng `3306`).

### Bước 2: Thiết lập Cơ sở dữ liệu
1. Mở MySQL Workbench hoặc cửa sổ dòng lệnh MySQL:
```bash
mysql -u root -p
```
2. Tạo CSDL và nạp dữ liệu từ các file trong thư mục `src/main/resources/sql/`:
```sql
CREATE DATABASE IF NOT EXISTS libman7_db CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
```
3. Chạy script tạo bảng và dữ liệu mẫu:
```bash
mysql -u root -p --default-character-set=utf8mb4 libman7_db < src/main/resources/sql/LibMan7_Schema.sql
mysql -u root -p --default-character-set=utf8mb4 libman7_db < src/main/resources/sql/seed_data.sql
```
4. Kiểm tra cấu hình kết nối trong file [src/main/resources/db.properties](src/main/resources/db.properties):
```properties
db.url=jdbc:mysql://localhost:3306/libman7_db?useSSL=false&allowPublicKeyRetrieval=true&serverTimezone=UTC&characterEncoding=UTF-8
db.user=root
db.password=12345
```
*(Thay đổi `db.password` và `db.user` cho phù hợp với máy của bạn nếu cần).*

### Bước 3: Khởi chạy ứng dụng
Có thể chọn một trong các cách sau:
- **Cách 1 (Nhanh nhất):** Nhấp đúp chuột vào file **`run_app.bat`** tại thư mục gốc của dự án.
- **Cách 2 (Maven CLI):** Mở terminal tại thư mục dự án và chạy:
  ```bash
  mvn exec:java -Dexec.mainClass="libman.main.MainApp7"
  ```
- **Cách 3 (IDE):** Mở dự án trong NetBeans / IntelliJ IDEA / Eclipse / VS Code, tìm file `libman.main.MainApp7.java` và chọn **Run File**.

---

## 6. Tài khoản kiểm thử có sẵn

| Vai trò | Tên đăng nhập | Mật khẩu | Mã người dùng | Ghi chú kiểm thử |
|---|---|---|---|---|
| **Thủ thư** | `librarian1` | `123456` | `LIB001` | Trực quầy, thực hiện nhận trả tài liệu |
| **Thủ thư** | `librarian2` | `123456` | `LIB002` | Nhân viên kiểm kê |
| **Người quản lý** | `manager1` | `123456` | `MGR001` | Xem báo cáo & thống kê |
| **Bạn đọc** | `reader1` | `123456` | `RD001` | Đang mượn 6 cuốn (trễ 6 - 33 ngày) |
| **Bạn đọc** | `reader2` | `123456` | `RD002` | Đang mượn 5 cuốn (trễ 4 - 18 ngày) |
| **Bạn đọc** | `reader3` | `123456` | `RD003` | Đang mượn 5 cuốn (trễ 5 - 23 ngày) |
| **Bạn đọc** | `reader4` | `123456` | `RD004` | Đang mượn 5 cuốn (trễ 7 - 28 ngày) |
| **Bạn đọc** | `reader5` | `123456` | `RD005` | Đang mượn 5 cuốn (trễ 3 - 25 ngày) |
| **Bạn đọc** | `reader6` -> `reader12` | `123456` | `RD006` -> `RD012` | **Chưa có thẻ**, sẵn sàng test đăng ký làm thẻ mới |

---
*Bản quyền học phần PT&TK HTTT - Học viện Công nghệ Bưu chính Viễn thông (PTIT)*
