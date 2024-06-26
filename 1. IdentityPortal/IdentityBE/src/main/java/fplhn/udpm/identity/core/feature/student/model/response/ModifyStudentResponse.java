package fplhn.udpm.identity.core.feature.student.model.response;

public record ModifyStudentResponse(
        Long studentId,
        String studentCode,
        String studentName,
        String studentEmail
) {
}
