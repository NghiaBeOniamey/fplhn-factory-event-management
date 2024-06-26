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
 * Object: Object (Đối tượng)
 * Description: Lưu các đối tượng mà các sự kiện nhắm đến. VD: Sinh viên, Giảng viên, Sinh viên học kỳ 1,...
 */

@Getter
@Setter
@Table(name = "object")
@Entity
public class Object extends PrimaryEntity implements Serializable {

    @Nationalized
    @Column(length = EntityProperties.LENGTH_NAME)
    private String name;

}
