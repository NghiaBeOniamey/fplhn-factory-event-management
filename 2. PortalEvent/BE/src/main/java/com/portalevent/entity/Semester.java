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
 * Object: Semester (Học kỳ)
 * Description: Lưu các học kỳ, thời gian diễn ra học kỳ và block 1,2
 * - Một học kỳ có sẽ gồm 2 block
 * - Mỗi sự kiện chỉ thuộc 1 học kỳ
 */

@Entity
@Table(name = "semester")
@Getter
@Setter
public class Semester extends PrimaryEntity implements Serializable {

    private Long semesterId;

    @Nationalized
    @Column(length = EntityProperties.LENGTH_NAME, nullable = false)
    private String name;

    @Column(nullable = false)
    private Long startTime;

    @Column(nullable = false)
    private Long endTime;

    @Column(nullable = false)
    private Long startTimeFirstBlock;

    @Column(nullable = false)
    private Long endTimeFirstBlock;

    @Column(nullable = false)
    private Long startTimeSecondBlock;

    @Column(nullable = false)
    private Long endTimeSecondBlock;

}
