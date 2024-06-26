package com.portalevent.entity;

import com.portalevent.entity.base.PrimaryEntity;
import com.portalevent.infrastructure.constant.EntityProperties;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

/**
 * @author SonPT
 */

/**
 * Object: Periodic event major (Bộ môn sự kiện hằng kỳ)
 * Description: Lưu các bộ môn mà 1 sự kiện hằng kỳ nhắm tới
 */

@Entity
@Table(name = "periodic_event_major")
@Getter
@Setter
public class PeriodicEventMajor extends PrimaryEntity implements Serializable {

    @Column(length = EntityProperties.LENGTH_ID, nullable = false)
    private String majorId;

    @Column(length = EntityProperties.LENGTH_ID, nullable = false)
    private String periodicEventId;

}
