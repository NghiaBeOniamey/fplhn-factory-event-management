package fplhn.udpm.identity.core.feature.staff.model.response;

public record ModifyStaffResponse(
        String staffName,
        String staffCode,
        String emailFe,
        String phoneNumber,
        String emailFpt
) {
}
