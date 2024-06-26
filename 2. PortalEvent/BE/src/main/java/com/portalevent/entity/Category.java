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
 * Object: Category (danh mục)
 * Description: Lưu danh mục của sự kiện.VD: Seminar, workshop, talkshow,....
 */

@Getter
@Setter
@Table(name = "category")
@Entity
public class Category extends PrimaryEntity implements Serializable {

    @Nationalized
    @Column(length = EntityProperties.LENGTH_NAME)
    private String name;
}
