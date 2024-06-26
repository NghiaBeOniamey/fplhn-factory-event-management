package fplhn.udpm.identity.entity;

import fplhn.udpm.identity.entity.base.BaseEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.Nationalized;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
@Table(name = "sinh_vien")
@DynamicUpdate
public class Student extends BaseEntity {

    @Column(name = "ma_sinh_vien")
    private String studentCode;

    @Column(name = "email_fpt", unique = true)
    private String emailFpt;

    @Column(name = "ho_ten", nullable = false)
    @Nationalized
    private String fullName;

    @Column(name = "so_dien_thoai")
    private String phoneNumber;

    @ManyToOne
    @JoinColumn(name = "id_bo_mon_theo_co_so")
    private DepartmentCampus departmentCampus;

    @Column(name = "avatar", length = 2000)
    private String avatar;

}
