package com.portalevent.entity;

import com.portalevent.entity.base.PrimaryEntity;
import com.portalevent.infrastructure.constant.EntityProperties;
import com.portalevent.infrastructure.constant.EventType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.Nationalized;

import java.io.Serializable;

/**
 * @author SonPT
 */

/**
 * Object: Periodic event (Sự kiện hằng kỳ)
 * Description: 1 Sự kiện sau khi tổ chức có thể xin sự phê duyệt của NPD để
 * 				xác nhận SK đó có thể tổ chức hằng kỳ. Trong các kỳ tiếp theo
 * 				những NTC khác có thể xem danh sách và một số thông tin cơ bản của
 * 				các sự kiện hằng kỳ và thực hiện lên plan tổ chức.
 */

@Entity
@Table(name = "periodic_event")
@Getter
@Setter
public class PeriodicEvent extends PrimaryEntity implements Serializable {

    @Column(length = EntityProperties.LENGTH_CODE)
    private String approverCode;

    @Column(length = EntityProperties.LENGTH_CODE)
    private String subjectCode;

    @Column(length = EntityProperties.LENGTH_CODE)
    private String trainingFacilityCode;

    @Column(length = EntityProperties.LENGTH_ID)
    private String categoryId;

    @Nationalized
    @Column(length = EntityProperties.LENGTH_NAME, nullable = false)
    private String name;

    @Column
    private EventType eventType;

    @Column
    private Short expectedParticipants; // Số người dự kiến tham gia

    @Nationalized
    @Column(length = EntityProperties.LENGTH_DESCRIPTION)
    private String description;

}
