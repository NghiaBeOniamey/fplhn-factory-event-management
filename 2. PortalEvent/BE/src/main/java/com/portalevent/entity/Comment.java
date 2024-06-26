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
 * Object: Comment
 * Description: Lưu các comment của NTG, NTC về sự kiện
 * - Lưu theo đệ quy: 1 bản ghi comment có thể là 1 comment riêng lẻ hoặc là 1 comment để trả lời 1 comment khác
 * - UserID: được lấy từ module phân quyền. Có thể là NTG, NTC hoặc NPD
 */

@Getter
@Setter
@Table(name = "comment")
@Entity
public class Comment extends PrimaryEntity implements Serializable {

    @Column(length = EntityProperties.LENGTH_ID, nullable = false)
    private String eventId;

    @Column(length = EntityProperties.LENGTH_ID, nullable = false)
    private String userId;

    @Nationalized
    @Column(length = EntityProperties.LENGTH_COMMENT)
    private String comment;

    @Column(length = EntityProperties.LENGTH_ID)
    private String replyId;

}
