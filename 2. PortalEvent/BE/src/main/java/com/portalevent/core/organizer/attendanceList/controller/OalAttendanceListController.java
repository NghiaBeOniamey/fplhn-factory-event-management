package com.portalevent.core.organizer.attendanceList.controller;

import com.portalevent.core.common.ResponseObject;
import com.portalevent.core.organizer.attendanceList.exportconfig.ExcelAttendanceGenerator;
import com.portalevent.core.organizer.attendanceList.model.request.OalFindAttendanceRequest;
import com.portalevent.core.organizer.attendanceList.service.OalAttendanceListService;
import com.portalevent.infrastructure.constant.Constants;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.text.ParseException;
import java.util.UUID;

/**
 * @author SonPT
 */

@RestController
@RequestMapping(Constants.UrlPath.URL_API_ORGANIZER_ATTENDANCE_LIST)

public class OalAttendanceListController {

    private final OalAttendanceListService attendanceListService;

    public OalAttendanceListController(OalAttendanceListService attendanceListService) {
        this.attendanceListService = attendanceListService;
    }

    @GetMapping("/event-detail/{id}")
    public ResponseObject eventDetail(@PathVariable("id") String id) {
        return new ResponseObject(attendanceListService.detail(id));
    }

    @PostMapping
    public ResponseObject getAllAttendance(@RequestBody OalFindAttendanceRequest request) {
        return new ResponseObject(attendanceListService.getAllAttendance(request));
    }

    @GetMapping("/count")
    public ResponseObject countAllSearch(final OalFindAttendanceRequest request) {
        return new ResponseObject(attendanceListService.countAllSearch(request));
    }

    @PostMapping("/export-attendance")
    public ResponseEntity<Resource> exportAttendance(@RequestParam String idEvent) throws ParseException {
        ExcelAttendanceGenerator excelRegistrationGenerator = new ExcelAttendanceGenerator();
        excelRegistrationGenerator.writeHeader();
        excelRegistrationGenerator.writeData(attendanceListService
                .getAttendanceList(idEvent));
        UUID uuid = UUID.randomUUID();
        String headerValues = "attachment;filename=" + uuid + ".xlsx";
        try (ByteArrayOutputStream outputStream = new ByteArrayOutputStream()) {
            excelRegistrationGenerator.getWorkbook().write(outputStream);
            ByteArrayResource resource = new ByteArrayResource(outputStream.toByteArray());
            return ResponseEntity.ok()
                    .header(HttpHeaders.CONTENT_DISPOSITION, headerValues)
                    .contentType(MediaType.parseMediaType("application/vnd.ms-excel"))
                    .body(resource);
        } catch (IOException e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}
