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
 * Description: Bảng lưu lại các mốc thời gian gửi mail thư mời của một sự kiện
 */

@Getter
@Setter
@Entity
@Table(name = "invitation_time")
public class InvitationTime extends PrimaryEntity implements Serializable {

    @Column(nullable = false, length = EntityProperties.LENGTH_ID)
    private String eventId;

	@Column
    private Long time;

    @Column(length = EntityProperties.LENGTH_ID)
    private String jobId;

    @Column(length = EntityProperties.LENGTH_ID)
    private String triggerId;

    @Column(nullable = false)
    private Boolean status = false;

}
