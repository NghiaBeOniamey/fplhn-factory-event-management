package com.portalevent.core.organizer.registrationList.exportconfig;

import com.portalevent.core.organizer.registrationList.model.response.OrlRegistrationResponse;
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

public class ExcelRegistrationGenerator {

    @Getter
    private XSSFWorkbook workbook;
    private XSSFSheet sheet;

    public ExcelRegistrationGenerator() {
        workbook = new XSSFWorkbook();
        sheet = workbook.createSheet("Danh sách đăng ký");
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

        for (int i = 0; i < 2; i++) {
            headerRow.createCell(i).setCellStyle(headerStyle);
        }

        headerRow.getCell(0).setCellValue("Mã");
        headerRow.getCell(1).setCellValue("Họ và tên");

        for (int i = 0; i < 2; i++) {
            sheet.autoSizeColumn(i);
        }
    }

    public void writeData(List<OrlRegistrationResponse> responseList) throws ParseException {
        int rowCount = 1;
        for (OrlRegistrationResponse response : responseList) {
            var row = sheet.createRow(rowCount++);
            row.createCell(0).setCellValue(response.getCode());
            row.createCell(1).setCellValue(response.getName());
        }
        for (int i = 0; i < 2; i++) {
            sheet.autoSizeColumn(i);
        }
    }

}
