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
 * Object: Major Organizer (Bộ môn của người tổ chức)
 * Description: Lưu bộ môn của người tổ chức
 * 				`organizerId` được lấy từ Module đăng nhập, phân quyền
 */

@Entity
@Table(name = "organizer_major")
@Getter
@Setter
public class OrganizerMajor extends PrimaryEntity implements Serializable {

    @Column(length = EntityProperties.LENGTH_ID, nullable = false)
    private String majorId;

    @Column(length = EntityProperties.LENGTH_ID, nullable = false)
    private String organizerId;

}
