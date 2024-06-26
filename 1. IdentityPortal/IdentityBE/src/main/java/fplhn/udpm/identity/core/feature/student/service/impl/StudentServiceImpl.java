package fplhn.udpm.identity.core.feature.student.service.impl;

import fplhn.udpm.identity.core.common.PageableObject;
import fplhn.udpm.identity.core.common.ResponseObject;
import fplhn.udpm.identity.core.feature.student.model.request.AddStudentRequest;
import fplhn.udpm.identity.core.feature.student.model.request.StudentPaginationRequest;
import fplhn.udpm.identity.core.feature.student.model.request.UpdateStudentRequest;
import fplhn.udpm.identity.core.feature.student.model.response.ModifyStudentResponse;
import fplhn.udpm.identity.core.feature.student.model.response.StudentResponse;
import fplhn.udpm.identity.core.feature.student.repository.DepartmentCampusStudentRepository;
import fplhn.udpm.identity.core.feature.student.repository.StudentsRepository;
import fplhn.udpm.identity.core.feature.student.service.StudentService;
import fplhn.udpm.identity.entity.DepartmentCampus;
import fplhn.udpm.identity.entity.Student;
import fplhn.udpm.identity.infrastructure.constant.EntityStatus;
import fplhn.udpm.identity.infrastructure.constant.ResponseMessage;
import fplhn.udpm.identity.util.Helper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class StudentServiceImpl implements StudentService {

    private final StudentsRepository studentsRepository;

    private final DepartmentCampusStudentRepository departmentCampusRepository;

    @Override
    public ResponseObject<?> getAllStudentPagination(StudentPaginationRequest request) {
        try {
            Pageable pageable = Helper.createPageable(request, "studentId");
            if (request.getListDepartmentId() == null || request.getListDepartmentId().length == 0) {
                Page<List<StudentResponse>> responses = studentsRepository.findAllStudent(pageable, request);
                return new ResponseObject<>(
                        PageableObject.of(responses),
                        HttpStatus.OK,
                        ResponseMessage.SUCCESS.getMessage()
                );
            }
            Page<List<StudentResponse>> responses = studentsRepository.findAllStudentWithDepartmentId(pageable, request);
            return new ResponseObject<>(
                    PageableObject.of(responses),
                    HttpStatus.OK,
                    ResponseMessage.SUCCESS.getMessage()
            );
        } catch (Exception e) {
            e.printStackTrace(System.out);
            return ResponseObject.errorForward(
                    ResponseMessage.INTERNAL_SERVER_ERROR.getMessage(),
                    HttpStatus.INTERNAL_SERVER_ERROR
            );
        }
    }

    @Override
    public ResponseObject<?> getListStudent() {
        try {
            return new ResponseObject<>(
                    studentsRepository.getListAllStudent(),
                    HttpStatus.OK,
                    ResponseMessage.SUCCESS.getMessage()
            );
        } catch (Exception e) {
            return ResponseObject.errorForward(
                    ResponseMessage.INTERNAL_SERVER_ERROR.getMessage(),
                    HttpStatus.INTERNAL_SERVER_ERROR
            );
        }
    }

    @Override
    public ResponseObject<?> createStudent(AddStudentRequest request) {
        try {
            if (request.getDepartmentId() == null || request.getCampusId() == null) {
                Student student = Student.builder()
                        .studentCode(request.getStudentCode())
                        .fullName(request.getStudentName())
                        .emailFpt(request.getStudentMail())
                        .phoneNumber(request.getStudentPhoneNumber())
                        .build();
                student.setEntityStatus(EntityStatus.NOT_DELETED);
                studentsRepository.save(student);
                return ResponseObject.successForward(
                        student,
                        ResponseMessage.CREATED.getMessage()
                );
            }
            Optional<DepartmentCampus> departmentCampus = departmentCampusRepository
                    .findDepartmentCampusByCampus_IdAndDepartment_Id(
                            request.getCampusId(),
                            request.getDepartmentId()
                    );
            if (departmentCampus.isEmpty()) {
                return ResponseObject.errorForward(
                        ResponseMessage.NOT_FOUND.getMessage(),
                        HttpStatus.NOT_FOUND);
            }
            Student student = Student.builder()
                    .studentCode(request.getStudentCode())
                    .fullName(request.getStudentName())
                    .emailFpt(request.getStudentMail())
                    .phoneNumber(request.getStudentPhoneNumber())
                    .departmentCampus(departmentCampus.get())
                    .build();
            student.setEntityStatus(EntityStatus.NOT_DELETED);
            studentsRepository.save(student);
            return new ResponseObject<>(
                    new ModifyStudentResponse(
                            student.getId(),
                            student.getStudentCode(),
                            student.getFullName(),
                            student.getEmailFpt()
                    ),
                    HttpStatus.CREATED,
                    ResponseMessage.CREATED.getMessage()
            );
        } catch (Exception e) {
            return ResponseObject.errorForward(
                    ResponseMessage.INTERNAL_SERVER_ERROR.getMessage(),
                    HttpStatus.INTERNAL_SERVER_ERROR
            );
        }
    }

    @Override
    public ResponseObject<?> getDetailStudent(Long id) {
        try {
            Optional<Student> student = studentsRepository.findById(id);
            if (student.isEmpty()) {
                return ResponseObject.errorForward(
                        ResponseMessage.NOT_FOUND.getMessage(),
                        HttpStatus.NOT_FOUND
                );
            }
            return ResponseObject.successForward(
                    studentsRepository.getDetailStudent(id),
                    ResponseMessage.SUCCESS.getMessage()
            );
        } catch (Exception e) {
            return ResponseObject.errorForward(
                    ResponseMessage.INTERNAL_SERVER_ERROR.getMessage(),
                    HttpStatus.INTERNAL_SERVER_ERROR
            );
        }
    }

    @Override
    public ResponseObject<?> updateStudent(Long id, UpdateStudentRequest request) {
        try {

            Optional<Student> studentWithSameCode = studentsRepository.findByStudentCode(request.getStudentCode());
            if (studentWithSameCode.isPresent() && !studentWithSameCode.get().getId().equals(id)) {
                return ResponseObject.errorForward(
                        ResponseMessage.CODE_EXIST.getMessage(),
                        HttpStatus.CONFLICT);
            }

            if (request.getDepartmentId() == null || request.getCampusId() == null) {
                Student studentUpdate = Student.builder()
                        .studentCode(request.getStudentCode())
                        .fullName(request.getStudentName())
                        .emailFpt(request.getStudentMail() != null ? request.getStudentMail() : null)
                        .phoneNumber(request.getStudentPhoneNumber() != null ? request.getStudentPhoneNumber() : null)
                        .build();
                studentUpdate.setId(id);
                studentUpdate.setEntityStatus(EntityStatus.NOT_DELETED);
                studentsRepository.save(studentUpdate);
                return new ResponseObject<>(
                        new ModifyStudentResponse(
                                studentUpdate.getId(),
                                studentUpdate.getStudentCode(),
                                studentUpdate.getFullName(),
                                studentUpdate.getEmailFpt()
                        ),
                        HttpStatus.OK,
                        ResponseMessage.SUCCESS.getMessage()
                );
            }
            Optional<DepartmentCampus> departmentCampus = departmentCampusRepository
                    .findDepartmentCampusByCampus_IdAndDepartment_Id(
                            request.getCampusId(),
                            request.getDepartmentId()
                    );
            if (departmentCampus.isEmpty()) {
                return ResponseObject.errorForward(
                        ResponseMessage.NOT_FOUND.getMessage(),
                        HttpStatus.NOT_FOUND
                );
            }
            Student studentUpdate = Student.builder()
                    .studentCode(request.getStudentCode())
                    .fullName(request.getStudentName())
                    .emailFpt(request.getStudentMail() != null ? request.getStudentMail() : null)
                    .phoneNumber(request.getStudentPhoneNumber() != null ? request.getStudentPhoneNumber() : null)
                    .departmentCampus(departmentCampus.get())
                    .build();
            studentUpdate.setId(id);
            studentUpdate.setEntityStatus(EntityStatus.NOT_DELETED);
            studentsRepository.save(studentUpdate);
            return new ResponseObject<>(
                    new ModifyStudentResponse(
                            studentUpdate.getId(),
                            studentUpdate.getStudentCode(),
                            studentUpdate.getFullName(),
                            studentUpdate.getEmailFpt()
                    ),
                    HttpStatus.OK,
                    ResponseMessage.SUCCESS.getMessage()
            );
        } catch (Exception e) {
            e.printStackTrace(System.out);
            return ResponseObject.errorForward(
                    ResponseMessage.INTERNAL_SERVER_ERROR.getMessage(),
                    HttpStatus.INTERNAL_SERVER_ERROR
            );
        }
    }

    @Override
    public ResponseObject<?> updateStatus(Long studentId) {
        try {
            Optional<Student> student = studentsRepository.findById(studentId);
            if (student.isEmpty()) {
                return ResponseObject.errorForward(
                        ResponseMessage.NOT_FOUND.getMessage(),
                        HttpStatus.NOT_FOUND
                );
            }
            Student studentUpdate = student.get();
            if (studentUpdate.getEntityStatus().equals(EntityStatus.NOT_DELETED)) {
                studentUpdate.setEntityStatus(EntityStatus.DELETED);
            } else {
                studentUpdate.setEntityStatus(EntityStatus.NOT_DELETED);
            }
            studentsRepository.save(studentUpdate);
            return ResponseObject.successForward(
                    new ModifyStudentResponse(
                            studentUpdate.getId(),
                            studentUpdate.getStudentCode(),
                            studentUpdate.getFullName(),
                            studentUpdate.getEmailFpt()
                    ),
                    ResponseMessage.SUCCESS.getMessage()
            );
        } catch (Exception e) {
            return ResponseObject.errorForward(
                    ResponseMessage.INTERNAL_SERVER_ERROR.getMessage(),
                    HttpStatus.INTERNAL_SERVER_ERROR
            );
        }
    }

    @Override
    public ResponseObject<?> getListCampus() {
        try {
            return new ResponseObject<>(
                    departmentCampusRepository.getListCampus(),
                    HttpStatus.OK,
                    ResponseMessage.SUCCESS.getMessage()
            );
        } catch (Exception e) {
            return ResponseObject.errorForward(
                    ResponseMessage.INTERNAL_SERVER_ERROR.getMessage(),
                    HttpStatus.INTERNAL_SERVER_ERROR
            );
        }
    }

    @Override
    public ResponseObject<?> getListDepartment() {
        try {
            return new ResponseObject<>(
                    departmentCampusRepository.getListDepartment(),
                    HttpStatus.OK,
                    ResponseMessage.SUCCESS.getMessage()
            );
        } catch (Exception e) {
            return ResponseObject.errorForward(
                    ResponseMessage.INTERNAL_SERVER_ERROR.getMessage(),
                    HttpStatus.INTERNAL_SERVER_ERROR
            );
        }
    }

}
