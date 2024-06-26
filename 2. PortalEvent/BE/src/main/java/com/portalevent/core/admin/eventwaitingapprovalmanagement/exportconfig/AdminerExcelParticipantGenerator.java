package com.portalevent.core.admin.eventwaitingapprovalmanagement.exportconfig;

import com.portalevent.core.admin.eventwaitingapprovalmanagement.model.respone.AdminerParticipantResponse;
import lombok.Getter;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.util.List;

public class AdminerExcelParticipantGenerator {

    @Getter
    private XSSFWorkbook workbook;
    private XSSFSheet sheet;

    public AdminerExcelParticipantGenerator() {
        workbook = new XSSFWorkbook();
        sheet = workbook.createSheet("Danh sách sinh viên");
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

    public void writeData(List<AdminerParticipantResponse> participants) {
        int rowCount = 1;

        for (AdminerParticipantResponse participant : participants) {
            var row = sheet.createRow(rowCount++);
            row.createCell(0).setCellValue(participant.getSemesterName());
            row.createCell(1).setCellValue(participant.getEventName());
            row.createCell(2).setCellValue(participant.getParticipantEmail());
            row.createCell(3).setCellValue(participant.getParticipantName());
        }

        for (int i = 0; i < 16; i++) {
            sheet.autoSizeColumn(i);
        }

    }

}
