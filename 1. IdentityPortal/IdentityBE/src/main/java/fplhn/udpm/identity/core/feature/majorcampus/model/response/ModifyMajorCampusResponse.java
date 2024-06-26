package fplhn.udpm.identity.core.feature.majorcampus.model.response;

public record ModifyMajorCampusResponse(
        String majorName,
        String campusName,
        String departmentName,
        String staffName
) {
}
