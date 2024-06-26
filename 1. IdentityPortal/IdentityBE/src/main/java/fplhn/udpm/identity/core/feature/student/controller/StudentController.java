package fplhn.udpm.identity.core.feature.student.controller;

import fplhn.udpm.identity.core.feature.student.model.request.AddStudentRequest;
import fplhn.udpm.identity.core.feature.student.model.request.StudentPaginationRequest;
import fplhn.udpm.identity.core.feature.student.model.request.UpdateStudentRequest;
import fplhn.udpm.identity.core.feature.student.service.StudentService;
import fplhn.udpm.identity.infrastructure.constant.ApiConstant;
import fplhn.udpm.identity.util.Helper;
import io.swagger.v3.oas.annotations.Hidden;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(ApiConstant.API_STUDENT_PREFIX)
@CrossOrigin("*")
@RequiredArgsConstructor
@Slf4j
@Hidden
public class StudentController {

    private final StudentService studentService;

    @GetMapping
    public ResponseEntity<?> get(StudentPaginationRequest request) {
        return Helper.createResponseEntity(studentService.getAllStudentPagination(request));
    }

    @GetMapping("/search")
    public ResponseEntity<?> getStudentSearch() {
        return Helper.createResponseEntity(studentService.getListStudent());
    }

    @GetMapping("/{studentId}")
    public ResponseEntity<?> getDetail(@PathVariable Long studentId) {
        return Helper.createResponseEntity(studentService.getDetailStudent(studentId));
    }

    @PostMapping
    public ResponseEntity<?> addSinhVien(@Valid @RequestBody AddStudentRequest request) {
        return Helper.createResponseEntity(studentService.createStudent(request));
    }

    @PutMapping("/{studentId}")
    public ResponseEntity<?> updateProduct(@PathVariable Long studentId, @RequestBody @Valid UpdateStudentRequest request) {
        return Helper.createResponseEntity(studentService.updateStudent(studentId, request));
    }

    @PutMapping("/status/{studentId}")
    public ResponseEntity<?> updateStatus(@PathVariable Long studentId) {
        return Helper.createResponseEntity(studentService.updateStatus(studentId));
    }

    @GetMapping("/campus")
    public ResponseEntity<?> getListCampus() {
        return Helper.createResponseEntity(studentService.getListCampus());
    }

    @GetMapping("/department")
    public ResponseEntity<?> getListDepartment() {
        return Helper.createResponseEntity(studentService.getListDepartment());
    }

}
