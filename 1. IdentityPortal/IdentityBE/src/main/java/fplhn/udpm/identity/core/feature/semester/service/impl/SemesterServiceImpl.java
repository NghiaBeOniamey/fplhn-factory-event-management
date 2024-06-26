package fplhn.udpm.identity.core.feature.semester.service.impl;

import fplhn.udpm.identity.core.common.PageableObject;
import fplhn.udpm.identity.core.common.ResponseObject;
import fplhn.udpm.identity.core.feature.semester.model.request.ModifySemesterRequest;
import fplhn.udpm.identity.core.feature.semester.model.request.SemesterPaginationRequest;
import fplhn.udpm.identity.core.feature.semester.repository.SemesterExtendRepository;
import fplhn.udpm.identity.core.feature.semester.service.SemesterService;
import fplhn.udpm.identity.entity.Semester;
import fplhn.udpm.identity.infrastructure.constant.EntityStatus;
import fplhn.udpm.identity.infrastructure.constant.ResponseMessage;
import fplhn.udpm.identity.util.Helper;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import java.util.Optional;

@Service
@RequiredArgsConstructor
@Validated
public class SemesterServiceImpl implements SemesterService {

    private final SemesterExtendRepository semesterExtendRepository;

    @Override
    public ResponseObject<?> getAllSemester(@Valid SemesterPaginationRequest request) {
        try {
            Pageable pageable = Helper.createPageable(request, "semesterId");
            return ResponseObject.successForward(
                    PageableObject.of(semesterExtendRepository.findAllSemester(pageable, request)),
                    ResponseMessage.SUCCESS.getMessage()
            );
        } catch (Exception e) {
            return ResponseObject.errorForward(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public ResponseObject<?> createSemester(@Valid ModifySemesterRequest request) {
        try {
            Object validationResponse = validateSemester(request);
            if (validationResponse instanceof ResponseObject) return (ResponseObject<?>) validationResponse;

            int overlappingSemesters = semesterExtendRepository.countOverlappingSemesters(request.getStartTime(), request.getEndTime());
            if (overlappingSemesters > 0) {
                return ResponseObject.errorForward(
                        ResponseMessage.OVERLAPPING_SEMESTERS.getMessage(),
                        HttpStatus.BAD_REQUEST
                );
            }

            Semester semester = new Semester();
            semester.setName(request.getName());
            semester.setStartTime(request.getStartTime());
            semester.setEndTime(request.getEndTime());
            semester.setStartTimeFirstBlock(request.getStartTimeFirstBlock());
            semester.setEndTimeFirstBlock(request.getEndTimeFirstBlock());
            semester.setStartTimeSecondBlock(request.getStartTimeSecondBlock());
            semester.setEndTimeSecondBlock(request.getEndTimeSecondBlock());
            semester.setEntityStatus(EntityStatus.NOT_DELETED);
            return new ResponseObject<>(
                    semesterExtendRepository.save(semester),
                    HttpStatus.CREATED,
                    ResponseMessage.CREATED.getMessage()
            );
        } catch (Exception e) {
            return ResponseObject.errorForward(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public ResponseObject<?> updateSemester(Long semesterId, ModifySemesterRequest request) {
        try {
            Object validationResponse = validateSemester(request);
            if (validationResponse instanceof ResponseObject) return (ResponseObject<?>) validationResponse;

            int overlappingSemesters = semesterExtendRepository.countOverlappingSemesters(
                    request.getStartTime(), request.getEndTime(), semesterId
            );
            if (overlappingSemesters > 0) {
                return ResponseObject.errorForward(
                        ResponseMessage.OVERLAPPING_SEMESTERS.getMessage(),
                        HttpStatus.BAD_REQUEST
                );
            }

            Optional<Semester> semester = semesterExtendRepository.findById(semesterId);
            if (semester.isEmpty()) {
                return ResponseObject.errorForward(
                        ResponseMessage.NOT_FOUND.getMessage(),
                        HttpStatus.NOT_FOUND
                );
            }
            semester.get().setName(request.getName());
            semester.get().setStartTime(request.getStartTime());
            semester.get().setEndTime(request.getEndTime());
            semester.get().setStartTimeFirstBlock(request.getStartTimeFirstBlock());
            semester.get().setEndTimeFirstBlock(request.getEndTimeFirstBlock());
            semester.get().setStartTimeSecondBlock(request.getStartTimeSecondBlock());
            semester.get().setEndTimeSecondBlock(request.getEndTimeSecondBlock());
            return new ResponseObject<>(
                    semesterExtendRepository.save(semester.get()),
                    HttpStatus.OK,
                    ResponseMessage.UPDATED.getMessage()
            );
        } catch (Exception e) {
            return ResponseObject.errorForward(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public ResponseObject<?> getDetailSemester(Long semesterId) {
        try {
            Optional<Semester> semester = semesterExtendRepository.findById(semesterId);
            if (semester.isEmpty()) {
                return ResponseObject.errorForward(
                        ResponseMessage.NOT_FOUND.getMessage(),
                        HttpStatus.NOT_FOUND
                );
            }
            return ResponseObject.successForward(
                    semesterExtendRepository.findDetailSemesterById(semesterId),
                    ResponseMessage.SUCCESS.getMessage()
            );
        } catch (Exception e) {
            return ResponseObject.errorForward(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    private Object validateSemester(ModifySemesterRequest request) {
        if (request.getStartTime() >= request.getEndTime()) {
            return ResponseObject.errorForward(
                    "Start time must be less than end time",
                    HttpStatus.BAD_REQUEST
            );
        }
        if (request.getStartTimeFirstBlock() >= request.getEndTimeFirstBlock()) {
            return ResponseObject.errorForward(
                    "Start time of the first block must be less than its end time",
                    HttpStatus.BAD_REQUEST
            );
        }
        if (request.getStartTimeSecondBlock() >= request.getEndTimeSecondBlock()) {
            return ResponseObject.errorForward(
                    "Start time of the second block must be less than its end time",
                    HttpStatus.BAD_REQUEST
            );
        }
        if (request.getStartTimeFirstBlock() < request.getStartTime() || request.getEndTimeSecondBlock() > request.getEndTime()) {
            return ResponseObject.errorForward(
                    "Blocks must be within the semester duration",
                    HttpStatus.BAD_REQUEST
            );
        }
        return request;
    }

}
