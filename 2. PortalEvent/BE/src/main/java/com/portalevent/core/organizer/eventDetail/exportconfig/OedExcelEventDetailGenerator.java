package com.portalevent.core.organizer.eventDetail.exportconfig;

import com.portalevent.core.organizer.eventDetail.model.response.OedEventOrganizerCustom;
import lombok.Getter;
import org.apache.poi.ss.usermodel.BorderStyle;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.DataValidation;
import org.apache.poi.ss.usermodel.DataValidationConstraint;
import org.apache.poi.ss.usermodel.DataValidationHelper;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.util.CellRangeAddressList;
import org.apache.poi.xssf.usermodel.XSSFDataValidation;
import org.apache.poi.xssf.usermodel.XSSFDataValidationConstraint;
import org.apache.poi.xssf.usermodel.XSSFDataValidationHelper;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.text.ParseException;
import java.util.List;

public class OedExcelEventDetailGenerator {

    @Getter
    private XSSFWorkbook workbook;
    private XSSFSheet sheet;

    public OedExcelEventDetailGenerator() {
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
        for (int i = 0; i < 3; i++) {
            headerRow.createCell(i).setCellStyle(headerStyle);
        }
        headerRow.getCell(0).setCellValue("STT");
        headerRow.getCell(1).setCellValue("Mail");
        headerRow.getCell(2).setCellValue("Số giờ F");
        for (int i = 0; i < 3; i++) {
            sheet.autoSizeColumn(i);
        }
    }

    public void writeValueDefault() {
        XSSFDataValidationHelper dvHelper = new XSSFDataValidationHelper(sheet);
        XSSFDataValidationConstraint dvConstraint = (XSSFDataValidationConstraint) dvHelper.createExplicitListConstraint(new String[]{"Co-Host", "Host"});
        // tao muôốn import ko có host ấy h chỉ cần đoạn này nx thôi
        CellRangeAddressList addressList = new CellRangeAddressList(1, 1, 2, 2);
        XSSFDataValidation validation = (XSSFDataValidation) dvHelper.createValidation(dvConstraint, addressList);

        validation.setShowErrorBox(true);
        sheet.addValidationData(validation);

        writeFooter();
    }

    public void writeFooter(){
        // Add notes
        String[] notes = {
                "Ghi chú:",
                "- Mail @fpt.edu.vn: Chọn list mail GV có sẵn trên hệ thống",
                "- Số giờ F: Chỉ được nhập định dạng số (nhập sai định dạng thì mặc định bằng 2)",
                "- Role: Chọn đúng ddingj dạng đã format (nhập sai định dạng thì mặc định bằng Co-Host)"
        };

        for (int i = 0; i < notes.length; i++) {
            Row noteRow = sheet.createRow(i+1);
            Cell noteCell = noteRow.createCell(5);
            noteCell.setCellValue(notes[i]);
        }
    }

    public void writeData(List<OedEventOrganizerCustom> organizerCustoms) throws ParseException {
        int rowCount = 1;
        for (OedEventOrganizerCustom organizerCustom : organizerCustoms) {
            var row = sheet.createRow(rowCount++);
            row.createCell(0).setCellValue(rowCount - 1);
            row.createCell(1).setCellValue(organizerCustom.getEmail());
            row.createCell(2).setCellValue(organizerCustom.getMeetingHour());
        }
        for (int i = 0; i < 3; i++) {
            sheet.autoSizeColumn(i);
        }
    }


//    private String statusDisplay(Integer statusSQL) {
//        switch (statusSQL) {
//            case 0:
//                return "Đóng";
//            case 1:
//                return "Dự kiến tổ chức";
//            case 2:
//                return "Cần sửa";
//            case 3:
//                return "Chờ phê duyệt";
//            case 4:
//                return "Đã phê duyệt";
//            case 5:
//                return "Đã tổ chức";
//            default:
//                return "";
//        }
//    }
//
//    private String formalityDisplay(Integer formalitySQL) {
//        if (formalitySQL == null) {
//            return "";
//        } else if (formalitySQL == 0) {
//            return "Online";
//        } else if (formalitySQL == 1) {
//            return "Offline";
//        } else {
//            return "";
//        }
//    }

}
