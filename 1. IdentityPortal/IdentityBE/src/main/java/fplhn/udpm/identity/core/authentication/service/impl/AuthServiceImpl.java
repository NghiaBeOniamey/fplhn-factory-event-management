package fplhn.udpm.identity.core.authentication.service.impl;

import fplhn.udpm.identity.core.authentication.model.request.LogOutRequest;
import fplhn.udpm.identity.core.authentication.model.request.RefreshTokenRequest;
import fplhn.udpm.identity.core.authentication.model.response.TokenRefreshResponse;
import fplhn.udpm.identity.core.authentication.repository.AccessTokenAuthEntryRepository;
import fplhn.udpm.identity.core.authentication.repository.ModuleAuthEntryRepository;
import fplhn.udpm.identity.core.authentication.repository.RefreshTokenAuthEntryRepository;
import fplhn.udpm.identity.core.authentication.service.AuthService;
import fplhn.udpm.identity.core.common.ResponseObject;
import fplhn.udpm.identity.entity.Module;
import fplhn.udpm.identity.entity.RefreshToken;
import fplhn.udpm.identity.infrastructure.constant.UserType;
import fplhn.udpm.identity.infrastructure.security.service.RefreshTokenService;
import fplhn.udpm.identity.infrastructure.security.service.TokenProvider;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final AccessTokenAuthEntryRepository accessTokenAuthEntryRepository;

    private final RefreshTokenAuthEntryRepository refreshTokenAuthEntryRepository;

    private final ModuleAuthEntryRepository moduleAuthEntryRepository;

    private final RefreshTokenService refreshTokenService;

    private final TokenProvider tokenProvider;

    @Override
    public ResponseObject<?> getRefreshToken(RefreshTokenRequest request) {
        try {
            String token = request.getRefreshToken();
            String currentHost = request.getCurrentHost();

            Optional<RefreshToken> refreshTokenOptional = refreshTokenAuthEntryRepository.findByRefreshToken(token);
            if (refreshTokenOptional.isEmpty()) {
                return new ResponseObject<>(null, HttpStatus.NOT_FOUND, "Token not found");
            }
            Optional<Module> moduleOptional = moduleAuthEntryRepository.findByUrl(currentHost);
            if (moduleOptional.isEmpty()) {
                return new ResponseObject<>(null, HttpStatus.NOT_FOUND, "Module not found");
            }

            RefreshToken refreshToken = refreshTokenService.verifyExpiration(refreshTokenOptional.get());
            if (refreshToken == null) return new ResponseObject<>(
                    null,
                    HttpStatus.NOT_ACCEPTABLE,
                    "Token is expired"
            );

            String newAccessToken = tokenProvider.createToken(
                    refreshTokenOptional.get().getUserId(),
                    moduleOptional.get().getUrl(),
                    refreshTokenOptional.get().getUserType().toString()
            );
            return new ResponseObject<>(
                    new TokenRefreshResponse(
                            newAccessToken,
                            refreshTokenOptional.get().getRefreshToken()
                    ),
                    HttpStatus.OK,
                    "Token refreshed");
        } catch (Exception e) {
            return ResponseObject.errorForward(
                    "Error while refreshing token",
                    HttpStatus.INTERNAL_SERVER_ERROR
            );
        }
    }

    @Transactional
    @Override
    public ResponseObject<?> logout(LogOutRequest request) {
        Long userId = request.getUserId();
        UserType userType = UserType.valueOf(request.getUserType());
        refreshTokenAuthEntryRepository.deleteByUserId(userId, userType);
        accessTokenAuthEntryRepository.deleteByUserId(userId, userType);
        return new ResponseObject<>(null, HttpStatus.OK, "Logout success");
    }

}
