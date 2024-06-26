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
 * Object: Event Object (Đối tượng của sự kiện)
 * Description: Lưu các đối tượng của 1 sự kiện nhắm đến.
 * - VD: Sự kiên A nhắm tới cả SV kỳ 1 và SV kỳ 2
 */

@Getter
@Setter
@Table(name = "event_object")
@Entity
public class EventObject extends PrimaryEntity implements Serializable {

    @Column(length = EntityProperties.LENGTH_ID, nullable = false)
    private String objectId;

    @Column(length = EntityProperties.LENGTH_ID, nullable = false)
    private String eventId;

}
