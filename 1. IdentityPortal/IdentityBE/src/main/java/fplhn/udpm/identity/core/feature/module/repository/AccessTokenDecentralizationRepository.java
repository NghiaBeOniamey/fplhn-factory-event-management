package fplhn.udpm.identity.core.feature.module.repository;

import fplhn.udpm.identity.entity.AccessToken;
import fplhn.udpm.identity.infrastructure.constant.UserType;
import fplhn.udpm.identity.repository.AccessTokenRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AccessTokenDecentralizationRepository extends AccessTokenRepository {

    Optional<AccessToken> findByUserIdAndUserType(Long userId, UserType userType);

}
