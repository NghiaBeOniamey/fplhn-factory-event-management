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
 * Object: Resource (Tài nguyên)
 * Description: Lưu lại các tài nguyên dưới dạng đường dẫn.
 * - VD: Link source code, video record của SK, project mẫu,...
 * - 1 Sự kiện có thể có nhiều tài nguyên
 */

@Getter
@Setter
@Table(name = "resource")
@Entity
public class Resource extends PrimaryEntity implements Serializable {

    @Column(length = EntityProperties.LENGTH_ID, nullable = false)
    private String eventId;

    @Column(length = EntityProperties.LENGTH_NAME, nullable = false)
    @Nationalized
    private String name;

    @Column(length = EntityProperties.LENGTH_PATH, nullable = false)
    private String path;

    @Column(length = EntityProperties.LENGTH_DESCRIPTION)
    @Nationalized
    private String Description;

}
