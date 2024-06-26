package com.portalevent.entity;

import com.portalevent.entity.base.PrimaryEntity;
import com.portalevent.infrastructure.constant.EntityProperties;
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
 * Object: Agenda item (Các đầu mục trong timeline sự kiện)
 * Description:
 * - Lưu các phần được diễn ra trong 1 sự kiện
 * - VD: Từ 20h-20h15: Ổn định tổ chức, giới thiệu sự kiện - HangNT169
 */

@Entity
@Table(name = "agenda_item")
@Getter
@Setter
public class AgendaItem extends PrimaryEntity implements Serializable {

    @Column(length = EntityProperties.LENGTH_ID, nullable = false)
    private String eventId;

    @Column(length = EntityProperties.LENGTH_ID)
    private String organizerId;

    @Column
    private String startTime;

    @Column
    private String endTime;

    @Nationalized
    @Column(length = EntityProperties.LENGTH_DESCRIPTION)
    private String description;

}
