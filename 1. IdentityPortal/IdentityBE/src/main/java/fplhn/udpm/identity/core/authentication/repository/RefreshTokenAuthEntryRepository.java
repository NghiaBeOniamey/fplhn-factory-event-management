package fplhn.udpm.identity.core.authentication.repository;

import fplhn.udpm.identity.entity.RefreshToken;
import fplhn.udpm.identity.infrastructure.constant.UserType;
import fplhn.udpm.identity.repository.RefreshTokenRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RefreshTokenAuthEntryRepository extends RefreshTokenRepository {

    Optional<RefreshToken> findByRefreshToken(String refreshToken);

    @Modifying
    @Query(
            value = """
                    delete from  RefreshToken rt
                    where rt.userId = :userId and rt.userType = :userType
                    """
    )
    void deleteByUserId(Long userId, UserType userType);

    @Query(
            value = """
                            SELECT revoked_at
                            FROM refresh_token rt
                            WHERE rt.user_id = :userId
                    """,
            nativeQuery = true
    )
    Long isRevoked(Long userId);


    @Query(
            value = """
                    SELECT rt
                    FROM RefreshToken rt
                    WHERE rt.userId = :userId
                    """
    )
    RefreshToken getRefreshTokenByUserId(Long userId);

}
