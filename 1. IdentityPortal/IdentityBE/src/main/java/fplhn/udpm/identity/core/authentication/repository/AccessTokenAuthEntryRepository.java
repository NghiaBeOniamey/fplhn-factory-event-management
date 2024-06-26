package fplhn.udpm.identity.core.authentication.repository;

import fplhn.udpm.identity.entity.AccessToken;
import fplhn.udpm.identity.infrastructure.constant.UserType;
import fplhn.udpm.identity.repository.AccessTokenRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AccessTokenAuthEntryRepository extends AccessTokenRepository {

    @Query(
            value = """
                    SELECT revoked_at
                    FROM access_token ac
                    WHERE ac.user_id = :userId
                    """,
            nativeQuery = true
    )
    Boolean isRevoked(Long userId);

    @Query(
            value = """
                    SELECT access_token
                    FROM access_token ac
                    WHERE ac.user_id = :userId
                    """,
            nativeQuery = true
    )
    String getAccessTokenByUserId(Long userId);

    Optional<AccessToken> findByUserId(Long userId);

    @Modifying
    @Query("""
            DELETE
            FROM AccessToken ac
            WHERE ac.userId = :userId AND ac.userType = :userType
            """)
    void deleteByUserId(Long userId, UserType userType);

}
