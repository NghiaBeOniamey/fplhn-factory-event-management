package com.portalevent.core.approver.eventattendancelist.exportconfig;

import com.portalevent.core.approver.eventattendancelist.model.response.ArlAttendanceResponse;
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
import java.util.List;

public class ExcelAttendanceGenerator {

    @Getter
    private XSSFWorkbook workbook;
    private XSSFSheet sheet;

    public ExcelAttendanceGenerator() {
        workbook = new XSSFWorkbook();
        sheet = workbook.createSheet("Danh sách tham gia");
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

        for (int i = 0; i < 3; i++) {
            headerRow.createCell(i).setCellStyle(headerStyle);
        }

        headerRow.getCell(0).setCellValue("Mã");
        headerRow.getCell(1).setCellValue("Họ và tên");
        headerRow.getCell(2).setCellValue("Tên sự kiện");

        for (int i = 0; i < 3; i++) {
            sheet.autoSizeColumn(i);
        }
    }

    public void writeData(List<ArlAttendanceResponse> responseList) throws ParseException {
        int rowCount = 1;
        for (ArlAttendanceResponse response : responseList) {
            var row = sheet.createRow(rowCount++);
            row.createCell(0).setCellValue(response.getCode());
            row.createCell(1).setCellValue(response.getName());
            row.createCell(2).setCellValue(response.getEventName());
        }
        for (int i = 0; i < 3; i++) {
            sheet.autoSizeColumn(i);
        }
    }

}
