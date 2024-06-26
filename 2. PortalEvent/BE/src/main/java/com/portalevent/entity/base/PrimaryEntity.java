package com.portalevent.entity.base;

import com.portalevent.infrastructure.constant.EntityProperties;
import com.portalevent.infrastructure.listener.CreatePrimaryEntityListener;
import jakarta.persistence.Column;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.Id;
import jakarta.persistence.MappedSuperclass;
import lombok.Getter;
import lombok.Setter;

/**
 * @author SonPT
 */

@Getter
@Setter
@MappedSuperclass
@EntityListeners(CreatePrimaryEntityListener.class)
public abstract class PrimaryEntity extends AuditEntity implements IsIdentified{

    @Id
    @Column(length = EntityProperties.LENGTH_ID, updatable = false)
    private String id;

}
