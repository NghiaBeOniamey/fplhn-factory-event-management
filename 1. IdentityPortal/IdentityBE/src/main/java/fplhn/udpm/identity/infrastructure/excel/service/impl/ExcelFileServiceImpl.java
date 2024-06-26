package fplhn.udpm.identity.infrastructure.excel.service.impl;

import fplhn.udpm.identity.entity.Campus;
import fplhn.udpm.identity.infrastructure.excel.model.StaffExportDTO;
import fplhn.udpm.identity.infrastructure.excel.repository.CourseCampusStaffRepository;
import fplhn.udpm.identity.infrastructure.excel.repository.MainCampusJobConfigRepository;
import fplhn.udpm.identity.infrastructure.excel.repository.RoleExcelRepository;
import fplhn.udpm.identity.infrastructure.excel.repository.StaffConfigJobRepository;
import fplhn.udpm.identity.infrastructure.excel.repository.StaffModuleRoleExcelRepository;
import fplhn.udpm.identity.infrastructure.excel.service.ExcelFileService;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.DataValidation;
import org.apache.poi.ss.usermodel.DataValidationConstraint;
import org.apache.poi.ss.usermodel.DataValidationHelper;
import org.apache.poi.ss.usermodel.FillPatternType;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.util.CellRangeAddressList;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.List;
import java.util.Optional;

@Service
@Slf4j
public class ExcelFileServiceImpl implements ExcelFileService {

    private final CourseCampusStaffRepository courseCampusStaffRepository;

    private final MainCampusJobConfigRepository mainCampusJobConfigRepository;

    private final RoleExcelRepository roleExcelRepository;

    private final StaffConfigJobRepository staffConfigJobRepository;

    private final StaffModuleRoleExcelRepository staffModuleRoleExcelRepository;

    @Autowired
    public ExcelFileServiceImpl(
            @Qualifier("CourseCampusStaffRepositoryExcel") CourseCampusStaffRepository courseCampusStaffRepository,
            MainCampusJobConfigRepository mainCampusJobConfigRepository,
            RoleExcelRepository roleExcelRepository,
            StaffConfigJobRepository staffConfigJobRepository,
            StaffModuleRoleExcelRepository staffModuleRoleExcelRepository
    ) {
        this.courseCampusStaffRepository = courseCampusStaffRepository;
        this.mainCampusJobConfigRepository = mainCampusJobConfigRepository;
        this.roleExcelRepository = roleExcelRepository;
        this.staffConfigJobRepository = staffConfigJobRepository;
        this.staffModuleRoleExcelRepository = staffModuleRoleExcelRepository;
    }

    /**
     * @param idNhanVien id của nhân viên
     * @return File excel mẫu thông tin giảng viên
     */
    @Override
    public ByteArrayInputStream downloadTemplate(Long idNhanVien) {
        try (Workbook workbook = new XSSFWorkbook()) {
            Optional<Campus> coSoOptional = mainCampusJobConfigRepository.findByIdNhanVien(idNhanVien);
            if (coSoOptional.isEmpty()) {
                return null;
            }
            Sheet sheet = createSheetWithHeader(workbook, "Template Thông Tin Giảng Viên", headerTitlesNotAdmin());
            addDataValidations(sheet, coSoOptional.get().getId());
            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            workbook.write(outputStream);
            return new ByteArrayInputStream(outputStream.toByteArray());
        } catch (IOException ex) {
            log.error("Error during export Excel file", ex);
            return null;
        }
    }

    @Override
    public ByteArrayInputStream downloadTemplate() {
        try (Workbook workbook = new XSSFWorkbook()) {
            List<Campus> listCampus = mainCampusJobConfigRepository.findAll();
            if (listCampus.isEmpty()) return null;
            Sheet sheet = createSheetWithHeader(workbook, "Template Thông Tin Giảng Viên", headerTitlesNotAdmin());
            addSampleData(sheet);
            addDataValidations(sheet);
            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            workbook.write(outputStream);
            return new ByteArrayInputStream(outputStream.toByteArray());
        } catch (IOException ex) {
            log.error("Error during export Excel file", ex);
            return null;
        }
    }

    @Override
    public ByteArrayInputStream downloadTemplateRole(String moduleCode) {
        try {
            List<String> roleCodes = roleExcelRepository.findAllRoleCode();
            List<String> staffCodes = staffConfigJobRepository.findAllStaffCode();
            if (roleCodes.isEmpty() || staffCodes.isEmpty()) {
                return null;
            }
            Workbook workbook = new XSSFWorkbook();
            Sheet sheet = workbook.createSheet("Template Phân Quyền");
            CellStyle headerCellStyle = createHeaderCellStyle(workbook);
            Row row = sheet.createRow(0);
            Cell firstCell = row.createCell(0);
            firstCell.setCellValue("Thông tin nhân viên");
            firstCell.setCellStyle(headerCellStyle);
            sheet.autoSizeColumn(0);
            for (int i = 0; i < roleCodes.size(); i++) {
                Cell cell = row.createCell(i + 1);
                cell.setCellValue(roleCodes.get(i));
                cell.setCellStyle(headerCellStyle);
                sheet.autoSizeColumn(i + 1);
            }
            for (int i = 0; i < staffCodes.size(); i++) {
                Row employeeRow = sheet.createRow(i + 1);
                Cell cell = employeeRow.createCell(0);
                cell.setCellValue(staffCodes.get(i) + "-" + moduleCode);
                for (int j = 0; j < roleCodes.size(); j++) {
                    Cell roleCell = employeeRow.createCell(j + 1);
                    if (checkEmployeeRole(staffCodes.get(i), roleCodes.get(j), moduleCode)) {
                        roleCell.setCellValue("x");
                    } else {
                        roleCell.setCellValue("n");
                    }
                }
            }
            DataValidationHelper dvHelper = sheet.getDataValidationHelper();
            DataValidationConstraint dvConstraint = dvHelper.createExplicitListConstraint(new String[]{"x", "n"});
            CellRangeAddressList addressList = new CellRangeAddressList(1, staffCodes.size(), 1, roleCodes.size());
            DataValidation validation = dvHelper.createValidation(dvConstraint, addressList);
            validation.createErrorBox("Sai Dữ Liệu", "Hãy Chọn Dữ Liệu Cho Sẵn");
            validation.createPromptBox("Chọn Dữ Liệu", "Chọn Dữ Liệu Cho Sẵn");
            validation.setShowErrorBox(true);
            validation.setSuppressDropDownArrow(true);
            sheet.addValidationData(validation);

            CellStyle style = workbook.createCellStyle();

            style.setFillForegroundColor(IndexedColors.YELLOW.getIndex());
            style.setFillPattern(FillPatternType.SOLID_FOREGROUND);

            style.setAlignment(HorizontalAlignment.CENTER);

            Sheet legendSheet = workbook.createSheet("Chú thích");

            Row legendRow = legendSheet.createRow(0);
            Cell legendCell = legendRow.createCell(0);
            legendCell.setCellValue("x: Chọn");
            legendCell.setCellStyle(style);

            legendRow = legendSheet.createRow(1);
            legendCell = legendRow.createCell(0);
            legendCell.setCellValue("n: Không chọn");
            legendCell.setCellStyle(style);

            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            workbook.write(outputStream);
            return new ByteArrayInputStream(outputStream.toByteArray());
        } catch (Exception e) {
            log.error("Error during export Excel file", e);
            return null;
        }
    }

    @Override
    public ByteArrayInputStream exportStaff(String campusCode) {
        try (Workbook workbook = new XSSFWorkbook()) {
            List<StaffExportDTO> staffExportDTOS = staffConfigJobRepository.findByCampusCode(campusCode);
            if (staffExportDTOS.isEmpty()) return null;
            Sheet sheet = workbook.createSheet("Danh Sách Giảng Viên");
            CellStyle headerCellStyle = createHeaderCellStyle(workbook);
            Row row = sheet.createRow(0);
            String[] headers = headerTitlesNotAdmin();
            for (int i = 0; i < headers.length; i++) {
                Cell cell = row.createCell(i);
                cell.setCellValue(headers[i]);
                cell.setCellStyle(headerCellStyle);
                sheet.autoSizeColumn(i);
            }
            addDataStaff(sheet, staffExportDTOS);
            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            workbook.write(outputStream);
            return new ByteArrayInputStream(outputStream.toByteArray());
        } catch (IOException ex) {
            log.error("Error during export Excel file", ex);
            return null;
        }
    }

    private boolean checkEmployeeRole(String staffCode, String roleCode, String moduleCode) {
        return staffModuleRoleExcelRepository.findBy(staffCode, roleCode, moduleCode).isPresent();
    }

    private void createRowWithCell(Sheet sheet, int rowIndex, String cellValue, CellStyle cellStyle) {
        Row row = sheet.createRow(rowIndex);
        Cell cell = row.createCell(0);
        if (cellStyle != null) {
            cell.setCellStyle(cellStyle);
        }
        cell.setCellValue(cellValue);
    }

    private CellStyle createHeaderCellStyle(Workbook workbook) {
        CellStyle headerCellStyle = workbook.createCellStyle();
        headerCellStyle.setLocked(true);
        headerCellStyle.setFillForegroundColor(IndexedColors.LIGHT_GREEN.getIndex());
        headerCellStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
        return headerCellStyle;
    }

    private String[] headerTitlesNotAdmin() {
        return new String[]{"STT", "ID Giảng Viên", "Họ và Tên", "Email FE", "Email FPT", "Bộ Môn - Cơ Sở"};
    }

    private Sheet createSheetWithHeader(Workbook workbook, String sheetName, String[] headers) {
        Sheet sheet = workbook.createSheet(sheetName);
        CellStyle headerCellStyle = createHeaderCellStyle(workbook);
        Row row = sheet.createRow(0);
        for (int i = 0; i < headers.length; i++) {
            Cell cell = row.createCell(i);
            cell.setCellValue(headers[i]);
            cell.setCellStyle(headerCellStyle);
            sheet.autoSizeColumn(i);
        }
        return sheet;
    }

    private void addDataValidations(Sheet sheet, Long coSoId) {
        List<String> validBoMon = courseCampusStaffRepository.findAllByCoSo(coSoId);
        addDataValidation(sheet, new CellRangeAddressList(1, 1000, 6, 6), validBoMon.toArray(new String[0]));
    }

    private void addDataValidations(Sheet sheet) {
        List<String> validDepartmentName = courseCampusStaffRepository.findAllDepartmentName();
        List<String> validCampusName = courseCampusStaffRepository.findAllCampusName();
        addDataValidation(sheet, new CellRangeAddressList(1, 1000, 6, 6), validDepartmentName.toArray(new String[0]));
        addDataValidation(sheet, new CellRangeAddressList(1, 1000, 7, 7), validCampusName.toArray(new String[0]));
    }

    private void addSampleData(Sheet sheet) {
        Row row = sheet.createRow(1);
        row.createCell(0).setCellValue("1");
        row.createCell(1).setCellValue("vana21");
        row.createCell(2).setCellValue("Nguyễn Văn A");
        row.createCell(3).setCellValue("vana21@fe.edu.vn");
        row.createCell(4).setCellValue("vana21@fpt.edu.vn");
        row.createCell(5).setCellValue("Ứng dụng phần mềm-Hà Nội");
        for (int i = 0; i < headerTitlesNotAdmin().length; i++) {
            sheet.autoSizeColumn(i);
        }
    }

    private void addDataStaff(Sheet sheet, List<StaffExportDTO> staffExportDTOS) {
        for (int i = 0; i < staffExportDTOS.size(); i++) {
            Row row = sheet.createRow(i + 1);
            StaffExportDTO staffExportDTO = staffExportDTOS.get(i);
            row.createCell(0).setCellValue(staffExportDTO.getOrderNumber());
            row.createCell(1).setCellValue(staffExportDTO.getStaffCode());
            row.createCell(2).setCellValue(staffExportDTO.getFullName());
            row.createCell(3).setCellValue(staffExportDTO.getMailFpt());
            row.createCell(4).setCellValue(staffExportDTO.getMailFe());
            row.createCell(5).setCellValue(staffExportDTO.getDepartmentCampusInfo());
        }
        for (int i = 0; i < headerTitlesNotAdmin().length; i++) {
            sheet.autoSizeColumn(i);
        }
    }

    private void addDataValidation(Sheet sheet, CellRangeAddressList addressList, String[] explicitListValues) {
//        DataValidationHelper validationHelper = sheet.getDataValidationHelper();
//        DataValidationConstraint constraint;
//        if (explicitListValues != null) {
//            constraint = validationHelper.createExplicitListConstraint(explicitListValues);
//        } else {
//            constraint = validationHelper.createNumericConstraint(DataValidationConstraint.ValidationType.INTEGER, DataValidationConstraint.OperatorType.BETWEEN, "1950", "2023");
//        }
//        DataValidation dataValidation = validationHelper.createValidation(constraint, addressList);
//        dataValidation.setShowErrorBox(true);
//        dataValidation.setSuppressDropDownArrow(true);
//        dataValidation.createErrorBox("Sai Dữ Liệu", "Hãy Chọn Dữ Liệu Cho Sẵn");
//        dataValidation.createPromptBox("Chọn Dữ Liệu", "Chọn Dữ Liệu Cho Sẵn");
//        sheet.addValidationData(dataValidation);
    }

}

