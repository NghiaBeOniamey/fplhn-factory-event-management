package com.portalevent.entity;

import com.portalevent.entity.base.PrimaryEntity;
import com.portalevent.infrastructure.constant.EntityProperties;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

@Entity
@Table(name = "major_campus")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class MajorCampus extends PrimaryEntity implements Serializable {

    private Long majorCampusId;

    private Long majorId;

    private Long departmentCampusId;

    @Column(length = EntityProperties.LENGTH_EMAIL)
    private String mail_of_manager;

}
