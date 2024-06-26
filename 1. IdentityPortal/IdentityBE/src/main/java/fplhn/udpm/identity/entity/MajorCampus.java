package fplhn.udpm.identity.entity;

import fplhn.udpm.identity.entity.base.BaseEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.DynamicUpdate;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "chuyen_nganh_theo_co_so")
@DynamicUpdate
public class MajorCampus extends BaseEntity {

    @ManyToOne
    @JoinColumn(name = "id_bo_mon_theo_co_so")
    private DepartmentCampus departmentCampus;

    @ManyToOne
    @JoinColumn(name = "id_chuyen_nganh")
    private Major major;

    @ManyToOne
    @JoinColumn(name = "id_truong_mon")
    private Staff staff;

}