package fplhn.udpm.identity.infrastructure.upload.controller;

import fplhn.udpm.identity.infrastructure.constant.ApiConstant;
import fplhn.udpm.identity.infrastructure.excel.jobconfig.role.RoleJobLauncher;
import fplhn.udpm.identity.infrastructure.excel.jobconfig.staff.StaffJobLauncher;
import fplhn.udpm.identity.infrastructure.excel.service.ExcelFileService;
import fplhn.udpm.identity.infrastructure.upload.service.FileUploadService;
import fplhn.udpm.identity.util.ValidateFileExtensionExcel;
import io.swagger.v3.oas.annotations.Hidden;
import org.apache.poi.util.IOUtils;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.Optional;


@RestController
@RequestMapping(ApiConstant.API_FILE_PREFIX)
@CrossOrigin("*")
@Hidden
public class FileExcelController {

    private final FileUploadService storageService;

    private final FileUploadService storageServiceRole;

    private final StaffJobLauncher staffJobLauncher;

    private final RoleJobLauncher roleJobLauncher;

    private final ExcelFileService excelFileService;


    public FileExcelController(
            @Qualifier("excelService") FileUploadService storageService,
            @Qualifier("fileUploadRoleServiceImpl") FileUploadService storageServiceRole,
            StaffJobLauncher staffJobLauncher,
            ExcelFileService excelFileService,
            RoleJobLauncher roleJobLauncher
    ) {
        this.storageService = storageService;
        this.storageServiceRole = storageServiceRole;
        this.staffJobLauncher = staffJobLauncher;
        this.excelFileService = excelFileService;
        this.roleJobLauncher = roleJobLauncher;
    }

    /**
     * @param file MultipartFile
     * @return ResponseEntity<?> message
     */
    @PostMapping("/upload")
    public ResponseEntity<?> uploadFile(@RequestParam("file") MultipartFile file) {
        String message;
        try {
            Optional<String> extension = ValidateFileExtensionExcel.getExtensionByStringHandling(file.getOriginalFilename());
            if (extension.isEmpty() || !extension.get().equals("xlsx")) {
                message = "File Không Đúng Định Dạng";
                return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(message);
            }
            storageService.init();
            String fileName = storageService.save(file);
            message = "Tải File Excel Thành Công: " + file.getOriginalFilename();
            staffJobLauncher.setFullPathFileName(fileName);
            staffJobLauncher.enable();
            return ResponseEntity.status(HttpStatus.OK).body(message);
        } catch (Exception e) {
            message = "Đã Xảy Ra Lỗi: " + file.getOriginalFilename() + ". Error: " + e.getMessage();
            return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(message);
        }
    }

    @PostMapping("/upload-role")
    public ResponseEntity<?> uploadFileRole(@RequestParam("file") MultipartFile file) {
        String message;
        try {
            Optional<String> extension = ValidateFileExtensionExcel.getExtensionByStringHandling(file.getOriginalFilename());
            if (extension.isEmpty() || !extension.get().equals("xlsx")) {
                message = "File Không Đúng Định Dạng";
                return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(message);
            }
            storageServiceRole.init();
            String fileName = storageServiceRole.save(file);
            message = "Tải File Excel Thành Công: " + file.getOriginalFilename();
            roleJobLauncher.setFullPathFileNameRole(fileName);
            roleJobLauncher.enable();
            return ResponseEntity.status(HttpStatus.OK).body(message);
        } catch (Exception e) {
            message = "Đã Xảy Ra Lỗi: " + file.getOriginalFilename() + ". Error: " + e.getMessage();
            return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(message);
        }
    }

    /**
     * @param staffId ID of staff
     * @return MultiPartFile
     * @throws IOException IOException
     */
    @GetMapping(value = "/download-template/{staffId}")
    public ResponseEntity<?> downloadTemplate(@PathVariable Long staffId) throws IOException {
        HttpHeaders header = new HttpHeaders();
        header.setContentType(new MediaType("application", "force-download"));
        header.set(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=template-staff.xlsx");
        ByteArrayInputStream byteArrayInputStream = excelFileService.downloadTemplate(staffId);
        return new ResponseEntity<>(IOUtils.toByteArray(byteArrayInputStream), header, HttpStatus.CREATED);
    }

    @GetMapping(value = "/download-template")
    public ResponseEntity<?> downloadTemplate() throws IOException {
        HttpHeaders header = new HttpHeaders();
        header.setContentType(new MediaType("application", "force-download"));
        header.set(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=template-staff.xlsx");
        ByteArrayInputStream byteArrayInputStream = excelFileService.downloadTemplate();
        return new ResponseEntity<>(IOUtils.toByteArray(byteArrayInputStream), header, HttpStatus.CREATED);
    }

    @GetMapping(value = "/download-template-role/{moduleCode}")
    public ResponseEntity<?> downloadTemplateRole(@PathVariable String moduleCode) throws IOException {
        HttpHeaders header = new HttpHeaders();
        header.setContentType(new MediaType("application", "force-download"));
        header.set(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=template-role.xlsx");
        ByteArrayInputStream byteArrayInputStream = excelFileService.downloadTemplateRole(moduleCode);
        return new ResponseEntity<>(IOUtils.toByteArray(byteArrayInputStream), header, HttpStatus.CREATED);
    }

    @GetMapping(value = "/export-staff/{campusCode}")
    public ResponseEntity<?> exportStaff(@PathVariable String campusCode) throws IOException {
        HttpHeaders header = new HttpHeaders();
        header.setContentType(new MediaType("application", "force-download"));
        header.set(
                HttpHeaders.CONTENT_DISPOSITION,
                "attachment; filename=DanhSachNhanVien-" + campusCode + new java.util.Date().getTime() + ".xlsx"
        );
        ByteArrayInputStream byteArrayInputStream = excelFileService.exportStaff(campusCode);
        return new ResponseEntity<>(IOUtils.toByteArray(byteArrayInputStream), header, HttpStatus.CREATED);
    }

}
