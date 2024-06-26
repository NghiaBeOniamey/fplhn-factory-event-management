package fplhn.udpm.identity.core.feature.module.repository;

import fplhn.udpm.identity.entity.RefreshToken;
import fplhn.udpm.identity.infrastructure.constant.UserType;
import fplhn.udpm.identity.repository.RefreshTokenRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RefreshTokenDecentralizationRepository extends RefreshTokenRepository {

    Optional<RefreshToken> findByUserIdAndUserType(Long userId, UserType userType);

}
