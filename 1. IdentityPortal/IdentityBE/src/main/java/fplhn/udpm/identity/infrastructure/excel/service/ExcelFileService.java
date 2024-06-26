package fplhn.udpm.identity.infrastructure.excel.service;

import java.io.ByteArrayInputStream;

public interface ExcelFileService {

    ByteArrayInputStream downloadTemplate(Long idNhanVien);

    ByteArrayInputStream downloadTemplate();

    ByteArrayInputStream downloadTemplateRole(String moduleCode);

    ByteArrayInputStream exportStaff(String campusCode);

}
