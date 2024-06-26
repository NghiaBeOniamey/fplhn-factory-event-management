package com.portalevent.core.approver.eventwaitingapproval.exportconfig;

import com.portalevent.core.approver.eventwaitingapproval.model.respone.AewaEventExcelResponse;
import lombok.Getter;
import org.apache.poi.ss.usermodel.BorderStyle;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class ExcelEventGenerator {

    @Getter
    private XSSFWorkbook workbook;
    private XSSFSheet sheet;

    public ExcelEventGenerator() {
        workbook = new XSSFWorkbook();
        sheet = workbook.createSheet("Danh sách sự kiện");
    }

    public void writeHeader() {
        sheet = workbook.getSheetAt(0);
        Row headerRow = sheet.createRow(0);
        CellStyle headerStyle = workbook.createCellStyle();
        headerStyle.setFillBackgroundColor(IndexedColors.LIGHT_GREEN.getIndex());
        headerStyle.setBorderBottom(BorderStyle.THIN);
        headerStyle.setAlignment(HorizontalAlignment.CENTER);

        Font headerFont = workbook.createFont();
        headerFont.setBold(true);
        headerStyle.setFont(headerFont);

        for (int i = 0; i < 18; i++) {
            headerRow.createCell(i).setCellStyle(headerStyle);
        }

        headerRow.getCell(0).setCellValue("Chuyên ngành");
        headerRow.getCell(1).setCellValue("Tên sự kiện");
        headerRow.getCell(2).setCellValue("Mục tiêu sự kiện");
        headerRow.getCell(3).setCellValue("Loại sự kiện");
        headerRow.getCell(4).setCellValue("Ngày tổ chức");
        headerRow.getCell(5).setCellValue("Tên khách mời doanh nghiệp");
        headerRow.getCell(6).setCellValue("SLSV tham gia dự kiến");
        headerRow.getCell(7).setCellValue("SLGV tham gia tổ chức");
        headerRow.getCell(8).setCellValue("Tổng giờ giảng quy đổi");
        headerRow.getCell(9).setCellValue("Dự trù kinh phí");
        headerRow.getCell(10).setCellValue("SLSV tham gia");
        headerRow.getCell(11).setCellValue("Hình thức tổ chức");
        headerRow.getCell(12).setCellValue("Địa điểm tổ chức");
        headerRow.getCell(13).setCellValue("Link Zoom, Meet");
        headerRow.getCell(14).setCellValue("Link đăng sự kiện, Poster");
        headerRow.getCell(15).setCellValue("Link Feedback");
        headerRow.getCell(16).setCellValue("Ghi chú");
        headerRow.getCell(17).setCellValue("Trạng thái");

        for (int i = 0; i < 18; i++) {
            sheet.autoSizeColumn(i);
        }
    }

    public void writeData(List<AewaEventExcelResponse> events) throws ParseException {
        int rowCount = 1;
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("HH:mm dd/MM/yyyy");
        for (AewaEventExcelResponse event : events) {
            var row = sheet.createRow(rowCount++);
            Date startTime = new Date(event.getStartTime());
            Date endTime = new Date(event.getEndTime());
            String formattedStartTime = simpleDateFormat.format(startTime);
            String formattedEndTime = simpleDateFormat.format(endTime);
            long diffInMillies = Math.abs(endTime.getTime() - startTime.getTime());
//            long diffHours = diffInMillies / (60 * 60 * 1000);
//            long diffMinutes = diffInMillies / (60 * 1000) % 60;
//            String diffTimeFormatted = String.format("%02d" + "h" + "%02d" + "p", diffHours, diffMinutes);
            String description = event.getDescription().replaceAll("<[^>]*>", "");
            row.createCell(0).setCellValue(event.getMajorName());
            row.createCell(1).setCellValue(event.getEventName());
            row.createCell(2).setCellValue(description);
            row.createCell(3).setCellValue(event.getTypeOfEvent());
            row.createCell(4).setCellValue(formattedStartTime + " - " + formattedEndTime);
            row.createCell(5).setCellValue("");
            row.createCell(6).setCellValue(event.getExpectedNumberOfStudents());
            row.createCell(7).setCellValue(event.getNumberOfLectures());
//            row.createCell(10).setCellValue(diffTimeFormatted);
            row.createCell(8).setCellValue(event.getMeetingHours());
            row.createCell(9).setCellValue("");
            row.createCell(10).setCellValue(event.getNumberOfStudents());
            row.createCell(11).setCellValue(formalityDisplay(event.getFormality()));
            row.createCell(12).setCellValue(event.getPathOffline());
            row.createCell(13).setCellValue(event.getPathOnline());
            row.createCell(14).setCellValue("");
            row.createCell(15).setCellValue("");
            row.createCell(16).setCellValue("");
            row.createCell(17).setCellValue(statusDisplay(event.getStatus()));
        }
        for (int i = 0; i < 18; i++) {
            if (i == 12 || i == 13) sheet.setColumnWidth(i, 10000);
            else sheet.autoSizeColumn(i);
        }
    }

    private String statusDisplay(Integer statusSQL) {
        switch (statusSQL) {
            case 0:
                return "Đóng";
            case 1:
                return "Dự kiến tổ chức";
            case 2:
                return "Cần sửa";
            case 3:
                return "Chờ phê duyệt";
            case 4:
                return "Đã phê duyệt";
            case 5:
                return "Đã tổ chức";
            case 6:
                return "Đã được bộ môn thông qua";
            default:
                return "";
        }
    }

    private String formalityDisplay(Integer formalitySQL) {
        if (formalitySQL == null) {
            return "";
        } else if (formalitySQL == 0) {
            return "Online";
        } else if (formalitySQL == 1) {
            return "Offline";
        } else {
            return "Kết hợp";
        }
    }

}
