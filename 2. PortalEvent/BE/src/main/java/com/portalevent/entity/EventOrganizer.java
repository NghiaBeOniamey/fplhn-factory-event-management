package com.portalevent.entity;

import com.portalevent.entity.base.PrimaryEntity;
import com.portalevent.infrastructure.constant.EntityProperties;
import com.portalevent.infrastructure.constant.EventRole;
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
 * Object: Event Organizer (Người tổ chức sự kiện)
 * Description: Lưu người tham gia tổ chức sự kiện
 * - 1 sự kiện có thể có nhiều người tổ chức
 * - Mỗi người tổ chức trong 1 sự kiện sẽ có 1 chức vụ. Và 1 sự kiện chỉ có 1 HOST(người tổ chức chính),
 * và nhiều CO_HOST(người đồng tổ chức)
 */

@Getter
@Setter
@Table(name = "event_organizer")
@Entity
public class EventOrganizer extends PrimaryEntity implements Serializable {

    @Column(length = EntityProperties.LENGTH_ID, nullable = false)
    private String eventId;

    @Column(length = EntityProperties.LENGTH_ID, nullable = false)
    private String organizerId;

    @Column(length = EntityProperties.LENGTH_CODE)
    private String organizerCode;

    @Column(nullable = false)
    private EventRole eventRole;

    private Double meetingHour;

}
