package fplhn.udpm.identity.entity;

import fplhn.udpm.identity.entity.base.BaseEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.Nationalized;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Table(name = "hoc_ky")
@DynamicUpdate
public class Semester extends BaseEntity {

    @Nationalized
    @Column(length = 100, nullable = false, name = "ten_hoc_ky")
    private String name;

    @Column(nullable = false, name = "bat_dau")
    private Long startTime;

    @Column(nullable = false, name = "ket_thuc")
    private Long endTime;

    @Column(nullable = false, name = "bat_dau_block_1")
    private Long startTimeFirstBlock;

    @Column(nullable = false, name = "ket_thuc_block_1")
    private Long endTimeFirstBlock;

    @Column(nullable = false, name = "bat_dau_block_2")
    private Long startTimeSecondBlock;

    @Column(nullable = false, name = "ket_thuc_block_2")
    private Long endTimeSecondBlock;

}
