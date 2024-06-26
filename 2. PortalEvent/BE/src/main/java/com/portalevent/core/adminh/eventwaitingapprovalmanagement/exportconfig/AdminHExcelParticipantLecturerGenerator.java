package com.portalevent.core.adminh.eventwaitingapprovalmanagement.exportconfig;

import com.portalevent.core.adminh.eventwaitingapprovalmanagement.model.respone.AdminHpParticipantLecturerResponse;
import lombok.Getter;
import org.apache.poi.ss.usermodel.BorderStyle;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.util.List;

public class AdminHExcelParticipantLecturerGenerator {

    @Getter
    private XSSFWorkbook workbook;
    private XSSFSheet sheet;

    public AdminHExcelParticipantLecturerGenerator() {
        workbook = new XSSFWorkbook();
        sheet = workbook.createSheet("Danh sách giảng viên");
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

        for (int i = 0; i < 4; i++) {
            headerRow.createCell(i).setCellStyle(headerStyle);
        }
        headerRow.getCell(0).setCellValue("Học kỳ");
        headerRow.getCell(1).setCellValue("Tên sự kiện");
        headerRow.getCell(2).setCellValue("MSSV");
        headerRow.getCell(3).setCellValue("Họ tên");
        for (int i = 0; i < 4; i++) {
            sheet.autoSizeColumn(i);
        }

    }

    public void writeData(List<AdminHpParticipantLecturerResponse> participants) {
        int rowCount = 1;
        for (AdminHpParticipantLecturerResponse participantLecturerResponse : participants) {
            var row = sheet.createRow(rowCount++);
            row.createCell(0).setCellValue(participantLecturerResponse.getSemesterName());
            row.createCell(1).setCellValue(participantLecturerResponse.getEventName());
            row.createCell(2).setCellValue(participantLecturerResponse.getParticipantLecturerEmail());
            row.createCell(3).setCellValue(participantLecturerResponse.getParticipantLecturerName());
        }

        for (int i = 0; i < 16; i++) {
            sheet.autoSizeColumn(i);
        }

    }

}
