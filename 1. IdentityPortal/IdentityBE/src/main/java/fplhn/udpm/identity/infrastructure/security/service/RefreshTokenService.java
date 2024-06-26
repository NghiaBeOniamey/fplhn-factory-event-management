package fplhn.udpm.identity.infrastructure.security.service;

import fplhn.udpm.identity.entity.RefreshToken;
import fplhn.udpm.identity.entity.Staff;
import fplhn.udpm.identity.infrastructure.constant.UserType;
import fplhn.udpm.identity.infrastructure.security.repository.RefreshTokenAuthRepository;
import fplhn.udpm.identity.infrastructure.security.repository.StaffAuthRepository;
import fplhn.udpm.identity.infrastructure.security.user.UserPrincipal;
import fplhn.udpm.identity.util.DateTimeUtil;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Optional;
import java.util.UUID;

@Service
public class RefreshTokenService {

    // 6 hours
    private final long REFRESH_EXPIRED_TIME = 6 * 60 * 60 * 1000;

    private final RefreshTokenAuthRepository refreshTokenRepository;

    private final StaffAuthRepository staffRepository;

    @Autowired
    public RefreshTokenService(
            RefreshTokenAuthRepository refreshTokenRepository,
            StaffAuthRepository staffRepository
    ) {
        this.refreshTokenRepository = refreshTokenRepository;
        this.staffRepository = staffRepository;
    }

    public Optional<RefreshToken> findByToken(String refreshToken) {
        return refreshTokenRepository.findByRefreshToken(refreshToken);
    }

    public RefreshToken createRefreshToken(Authentication authentication) {
        UserPrincipal userPrincipal = (UserPrincipal) authentication.getPrincipal();
        UserType userType = UserType.CAN_BO;

        Optional<Staff> staff = staffRepository.findByAccountFPT(userPrincipal.getEmail());
        Optional<Staff> staffFe = staffRepository.findByAccountFE(userPrincipal.getEmail());
        if (staff.isEmpty() && staffFe.isEmpty()) {
            userType = UserType.SINH_VIEN;
        }

        Optional<RefreshToken> optionalRefreshToken = refreshTokenRepository.findByUserIdAndUserType(userPrincipal.getId(), userType);

        if (optionalRefreshToken.isPresent()) {
            if (optionalRefreshToken.get().getRevokedAt() != null) {
                optionalRefreshToken.get().setRevokedAt(null);
                optionalRefreshToken.get().setUserType(userType);
                optionalRefreshToken.get().setUpdatedAt(DateTimeUtil.convertDateToTimeStampSecond(new Date()));
                optionalRefreshToken.get().setExpiredAt(REFRESH_EXPIRED_TIME);
                optionalRefreshToken.get().setRefreshToken(UUID.randomUUID().toString());
                return refreshTokenRepository.save(optionalRefreshToken.get());
            }
            RefreshToken refreshToken = optionalRefreshToken.get();
            refreshToken.setUpdatedAt(DateTimeUtil.convertDateToTimeStampSecond(new Date()));
            refreshToken.setExpiredAt(REFRESH_EXPIRED_TIME);
            refreshToken.setRefreshToken(UUID.randomUUID().toString());
            optionalRefreshToken.get().setUserType(userType);
            refreshToken = refreshTokenRepository.save(refreshToken);
            return refreshToken;
        }

        RefreshToken refreshToken = new RefreshToken();
        refreshToken.setCreatedAt(DateTimeUtil.convertDateToTimeStampSecond(new Date()));
        refreshToken.setUserId(userPrincipal.getId());
        refreshToken.setUserType(userType);
        refreshToken.setExpiredAt(REFRESH_EXPIRED_TIME);
        refreshToken.setRefreshToken(UUID.randomUUID().toString());
        refreshToken = refreshTokenRepository.save(refreshToken);
        return refreshToken;
    }

    public RefreshToken createRefreshToken(Long userId, UserType userType) {
        Optional<RefreshToken> optionalRefreshToken = refreshTokenRepository.findByUserIdAndUserType(userId, userType);
        if (optionalRefreshToken.isPresent()) {
            if (optionalRefreshToken.get().getRevokedAt() != null) {
                optionalRefreshToken.get().setRevokedAt(null);
                optionalRefreshToken.get().setUpdatedAt(DateTimeUtil.convertDateToTimeStampSecond(new Date()));
                optionalRefreshToken.get().setExpiredAt(REFRESH_EXPIRED_TIME);
                optionalRefreshToken.get().setRefreshToken(UUID.randomUUID().toString());
                return refreshTokenRepository.save(optionalRefreshToken.get());
            }
            RefreshToken refreshToken = optionalRefreshToken.get();
            refreshToken.setUpdatedAt(DateTimeUtil.convertDateToTimeStampSecond(new Date()));
            refreshToken.setExpiredAt(REFRESH_EXPIRED_TIME);
            refreshToken.setRefreshToken(UUID.randomUUID().toString());
            return refreshTokenRepository.save(refreshToken);
        }

        RefreshToken refreshToken = new RefreshToken();
        refreshToken.setCreatedAt(DateTimeUtil.convertDateToTimeStampSecond(new Date()));
        refreshToken.setUserId(userId);
        refreshToken.setExpiredAt(REFRESH_EXPIRED_TIME);
        refreshToken.setRefreshToken(UUID.randomUUID().toString());
        refreshToken = refreshTokenRepository.save(refreshToken);
        return refreshToken;
    }

    public RefreshToken verifyExpiration(RefreshToken token) {
        if (token.getExpiredAt() < DateTimeUtil.convertDateToTimeStampSecond(new Date())) {
            refreshTokenRepository.delete(token);
            return null;
        }
        return token;
    }

    @Transactional
    public int deleteByUserId(Long userId) {
        return refreshTokenRepository.deleteByUserId(userId);
    }

}
