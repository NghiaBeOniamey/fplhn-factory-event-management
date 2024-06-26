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
 * Object: Periodic event object (Đối tượng sự kiện hằng kỳ)
 * Description: Lưu các đối tượng mà 1 sự kiện hằng kỳ nhắm tới
 */

@Entity
@Table(name = "periodic_event_object")
@Getter
@Setter
public class PeriodicEventObject extends PrimaryEntity implements Serializable {

    @Column(length = EntityProperties.LENGTH_ID, nullable = false)
    private String objectId;

    @Column(length = EntityProperties.LENGTH_ID, nullable = false)
    private String periodicEventId;

}
