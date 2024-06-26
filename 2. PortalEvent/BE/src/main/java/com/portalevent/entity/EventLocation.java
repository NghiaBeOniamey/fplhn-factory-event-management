package com.portalevent.entity;

import com.portalevent.entity.base.PrimaryEntity;
import com.portalevent.infrastructure.constant.EntityProperties;
import com.portalevent.infrastructure.constant.Formality;
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
 * Object: Event Location (Địa điểm sự kiện)
 * Description: Lưu hình thức, địa điểm tổ chức sự kiện:
 * - 1 Sự kiện có thể tổ chức ONLINE hoặc OFFLINE. Hoặc có thể tổ chức cả OFFLINE và ONLINE
 * - VD: Sự kiện A (tổ chức ở với cả 2 hình thức: Offline và online)
 * 		-> EventLocation = {eventId: <idSuKienA>; formality: ONLINE; name: Google meets; path: https://meet.google.com/fod-weje-cxb}
 * 		-> EventLocation = {eventId: <idSuKienA>; formalyty: OFFLINE; name: Tòa P; path: P403}
 */

@Getter
@Setter
@Table(name = "event_location")
@Entity
public class EventLocation extends PrimaryEntity implements Serializable {

    @Column(length = EntityProperties.LENGTH_ID, nullable = false)
    private String eventId;

    @Column(nullable = false)
    private Formality formality;

    @Column(length = EntityProperties.LENGTH_NAME)
    private String name;

    @Column(length = EntityProperties.LENGTH_PATH)
    private String path;

}
