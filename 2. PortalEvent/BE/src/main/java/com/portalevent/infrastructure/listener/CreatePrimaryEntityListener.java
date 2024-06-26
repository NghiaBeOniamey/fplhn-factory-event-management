package com.portalevent.infrastructure.listener;

import com.portalevent.entity.base.PrimaryEntity;
import jakarta.persistence.PrePersist;

import java.util.UUID;

/**
 * @author SonPT
 */
public class CreatePrimaryEntityListener {

    @PrePersist
    private void onCreate(PrimaryEntity entity) {
        entity.setId(UUID.randomUUID().toString());
    }

}
