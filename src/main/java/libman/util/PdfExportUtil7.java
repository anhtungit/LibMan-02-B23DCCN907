package libman.util;

import com.itextpdf.text.*;
import com.itextpdf.text.pdf.*;
import libman.entity.ReturnDetail7;
import libman.entity.ReturnSlip7;

import java.io.File;
import java.io.FileOutputStream;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;

public class PdfExportUtil7 {

    public static boolean exportReturnSlipToPdf(ReturnSlip7 slip, File targetFile) {
        if (slip == null || targetFile == null) return false;

        Document document = new Document(PageSize.A4, 36, 36, 40, 40);
        try (FileOutputStream fos = new FileOutputStream(targetFile)) {
            PdfWriter.getInstance(document, fos);
            document.open();

            // Cấu hình Font chữ Tiếng Việt có dấu từ Windows Arial
            BaseFont bf;
            try {
                bf = BaseFont.createFont("C:/Windows/Fonts/arial.ttf", BaseFont.IDENTITY_H, BaseFont.EMBEDDED);
            } catch (Exception e) {
                bf = BaseFont.createFont(BaseFont.HELVETICA, BaseFont.WINANSI, BaseFont.NOT_EMBEDDED);
            }

            Font fontTitle = new Font(bf, 16, Font.BOLD, new BaseColor(20, 110, 50));
            Font fontSubTitle = new Font(bf, 13, Font.BOLD, BaseColor.DARK_GRAY);
            Font fontHeaderTable = new Font(bf, 11, Font.BOLD, BaseColor.BLACK);
            Font fontNormal = new Font(bf, 10, Font.NORMAL, BaseColor.BLACK);
            Font fontBold = new Font(bf, 10, Font.BOLD, BaseColor.BLACK);
            Font fontTotal = new Font(bf, 12, Font.BOLD, new BaseColor(180, 0, 0));
            Font fontItalic = new Font(bf, 10, Font.ITALIC, BaseColor.GRAY);

            // Tiêu đề
            Paragraph pHeader = new Paragraph("THƯ VIỆN ĐẠI HỌC - LIBMAN7\n", fontSubTitle);
            pHeader.setAlignment(Element.ALIGN_CENTER);
            document.add(pHeader);

            Paragraph pTitle = new Paragraph("PHIẾU TRẢ TÀI LIỆU VÀ PHẠT VI PHẠM\n\n", fontTitle);
            pTitle.setAlignment(Element.ALIGN_CENTER);
            document.add(pTitle);

            // Bảng Thông tin phiếu
            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm");
            DecimalFormat df = new DecimalFormat("#,### VNĐ");

            PdfPTable tblMeta = new PdfPTable(2);
            tblMeta.setWidthPercentage(100);
            tblMeta.setWidths(new float[]{55, 45});

            addMetaCell(tblMeta, "Mã phiếu trả: #" + slip.getReturnSlipId(), fontBold);
            addMetaCell(tblMeta, "Ngày trả: " + (slip.getReturnDate() != null ? sdf.format(slip.getReturnDate()) : sdf.format(new java.util.Date())), fontNormal);

            String readerName = slip.getReader() != null ? slip.getReader().getFullName() + " (" + slip.getReader().getReaderCode() + ")" : "-";
            addMetaCell(tblMeta, "Bạn đọc: " + readerName, fontNormal);

            String libName = slip.getLibrarian() != null ? slip.getLibrarian().getFullName() : "Thủ thư trực quầy";
            addMetaCell(tblMeta, "Thủ thư tiếp nhận: " + libName, fontNormal);

            String statusStr = "PAID".equalsIgnoreCase(slip.getPaymentStatus()) ? "ĐÃ THANH TOÁN" :
                    ("UNPAID".equalsIgnoreCase(slip.getPaymentStatus()) ? "CHƯA THANH TOÁN (GHI NỢ)" : "KHÔNG PHẠT");
            addMetaCell(tblMeta, "Trạng thái phạt: " + statusStr, fontBold);
            addMetaCell(tblMeta, " ", fontNormal);

            document.add(tblMeta);
            document.add(new Paragraph("\n"));

            // Bảng Chi tiết trả
            PdfPTable table = new PdfPTable(7);
            table.setWidthPercentage(100);
            table.setWidths(new float[]{6, 36, 12, 8, 13, 12, 13});

            // Tiêu đề cột
            String[] headers = {"STT", "Nhan đề tài liệu", "Tình trạng", "SL", "Phạt trễ", "Phạt hỏng/mất", "Thành tiền"};
            for (String h : headers) {
                PdfPCell cell = new PdfPCell(new Phrase(h, fontHeaderTable));
                cell.setBackgroundColor(new BaseColor(240, 240, 240));
                cell.setHorizontalAlignment(Element.ALIGN_CENTER);
                cell.setPadding(6);
                table.addCell(cell);
            }

            int stt = 1;
            double grandTotal = 0;
            if (slip.getDetails() != null) {
                for (ReturnDetail7 d : slip.getDetails()) {
                    String title = d.getBorrowDetail() != null && d.getBorrowDetail().getDocument() != null ?
                            d.getBorrowDetail().getDocument().getTitle() : "-";

                    table.addCell(createCell(String.valueOf(stt++), fontNormal, Element.ALIGN_CENTER));
                    table.addCell(createCell(title, fontNormal, Element.ALIGN_LEFT));
                    table.addCell(createCell(d.getCondition() != null ? d.getCondition() : "GOOD", fontNormal, Element.ALIGN_CENTER));
                    table.addCell(createCell(String.valueOf(d.getQuantityReturned()), fontNormal, Element.ALIGN_CENTER));
                    table.addCell(createCell(df.format(d.getLateFine()), fontNormal, Element.ALIGN_RIGHT));
                    table.addCell(createCell(df.format(d.getDamageFine()), fontNormal, Element.ALIGN_RIGHT));
                    table.addCell(createCell(df.format(d.getSubTotalFine()), fontBold, Element.ALIGN_RIGHT));

                    grandTotal += d.getSubTotalFine();
                }
            }

            // Dòng Tổng tiền phạt
            PdfPCell cTotalLabel = new PdfPCell(new Phrase("TỔNG TIỀN PHẠT:", fontBold));
            cTotalLabel.setColspan(6);
            cTotalLabel.setHorizontalAlignment(Element.ALIGN_RIGHT);
            cTotalLabel.setPadding(6);
            table.addCell(cTotalLabel);

            PdfPCell cTotalValue = new PdfPCell(new Phrase(df.format(grandTotal), fontTotal));
            cTotalValue.setHorizontalAlignment(Element.ALIGN_RIGHT);
            cTotalValue.setPadding(6);
            table.addCell(cTotalValue);

            document.add(table);

            // Chữ ký xác nhận
            document.add(new Paragraph("\n\n"));
            PdfPTable tblSigns = new PdfPTable(2);
            tblSigns.setWidthPercentage(100);

            PdfPCell cReaderSign = new PdfPCell(new Phrase("NGƯỜI TRẢ SÁCH\n(Ký và ghi rõ họ tên)\n\n\n\n", fontBold));
            cReaderSign.setBorder(Rectangle.NO_BORDER);
            cReaderSign.setHorizontalAlignment(Element.ALIGN_CENTER);

            PdfPCell cLibSign = new PdfPCell(new Phrase("THỦ THƯ TIẾP NHẬN\n(Ký và ghi rõ họ tên)\n\n\n\n", fontBold));
            cLibSign.setBorder(Rectangle.NO_BORDER);
            cLibSign.setHorizontalAlignment(Element.ALIGN_CENTER);

            tblSigns.addCell(cReaderSign);
            tblSigns.addCell(cLibSign);
            document.add(tblSigns);

            // Lời cảm ơn
            Paragraph pFooter = new Paragraph("\nXin cảm ơn quý độc giả đã sử dụng dịch vụ thư viện!", fontItalic);
            pFooter.setAlignment(Element.ALIGN_CENTER);
            document.add(pFooter);

            document.close();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    private static void addMetaCell(PdfPTable table, String text, Font font) {
        PdfPCell cell = new PdfPCell(new Phrase(text, font));
        cell.setBorder(Rectangle.NO_BORDER);
        cell.setPadding(3);
        table.addCell(cell);
    }

    private static PdfPCell createCell(String text, Font font, int align) {
        PdfPCell cell = new PdfPCell(new Phrase(text, font));
        cell.setHorizontalAlignment(align);
        cell.setPadding(5);
        return cell;
    }
}

