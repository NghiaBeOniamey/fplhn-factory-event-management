package fplhn.udpm.identity.core.authentication.service;

import fplhn.udpm.identity.core.authentication.model.request.LogOutRequest;
import fplhn.udpm.identity.core.authentication.model.request.RefreshTokenRequest;
import fplhn.udpm.identity.core.common.ResponseObject;

public interface AuthService {

    ResponseObject<?> getRefreshToken(RefreshTokenRequest request);

    ResponseObject<?> logout(LogOutRequest request);

}
