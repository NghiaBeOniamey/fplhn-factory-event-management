package fplhn.udpm.identity.entity;

import fplhn.udpm.identity.entity.base.BaseTokenEntity;
import fplhn.udpm.identity.infrastructure.constant.UserType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.DynamicUpdate;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "access_token")
@DynamicUpdate
public class AccessToken extends BaseTokenEntity {

    @Column(name = "access_token", length = 8000)
    private String accessToken;

    @Column(name = "expired_at")
    private Long expiredAt;

    @Column(name = "user_id")
    private Long userId;

    @Column(name = "user_type")
    @Enumerated(EnumType.STRING)
    private UserType userType;

    @Column(name = "revoked_at")
    private Long revokedAt;

}
