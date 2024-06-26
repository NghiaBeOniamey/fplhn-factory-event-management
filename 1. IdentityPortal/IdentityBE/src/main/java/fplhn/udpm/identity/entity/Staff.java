package fplhn.udpm.identity.entity;

import fplhn.udpm.identity.entity.base.BaseEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
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
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "nhan_vien")
@DynamicUpdate
public class Staff extends BaseEntity {

    @Column(name = "ho_ten")
    @Nationalized
    private String fullName;

    @Column(name = "ma_nhan_vien")
    private String staffCode;

    @Column(name = "tai_khoan_fe")
    @Nationalized
    private String accountFE;

    @Column(name = "tai_khoan_fpt")
    @Nationalized
    private String accountFPT;

    @Column(name = "so_dien_thoai", unique = true, nullable = true)
    private String phoneNumber;

    @Column(name = "avatar", length = 2000)
    private String avatar;

    @ManyToOne
    @JoinColumn(name = "id_bo_mon_theo_co_so")
    private DepartmentCampus departmentCampus;

    @ManyToOne
    @JoinColumn(name = "id_co_so")
    private Campus campus;

}
