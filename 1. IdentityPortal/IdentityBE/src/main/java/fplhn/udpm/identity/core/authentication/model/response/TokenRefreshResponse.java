package fplhn.udpm.identity.core.authentication.model.response;

public record TokenRefreshResponse(
        String accessToken,
        String refreshToken
) { }
