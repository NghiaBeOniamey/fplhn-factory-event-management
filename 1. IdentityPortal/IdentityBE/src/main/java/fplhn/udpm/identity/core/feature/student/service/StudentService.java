package fplhn.udpm.identity.core.feature.student.service;

import fplhn.udpm.identity.core.common.ResponseObject;
import fplhn.udpm.identity.core.feature.student.model.request.AddStudentRequest;
import fplhn.udpm.identity.core.feature.student.model.request.StudentPaginationRequest;
import fplhn.udpm.identity.core.feature.student.model.request.UpdateStudentRequest;

public interface StudentService {

    ResponseObject<?> getAllStudentPagination(StudentPaginationRequest request);

    ResponseObject<?> getListStudent();

    ResponseObject<?> createStudent(AddStudentRequest request);

    ResponseObject<?> getDetailStudent(Long id);

    ResponseObject<?> updateStudent(Long id, UpdateStudentRequest request);

    ResponseObject<?> updateStatus(Long studentId);

    ResponseObject<?> getListCampus();

    ResponseObject<?> getListDepartment();

}
