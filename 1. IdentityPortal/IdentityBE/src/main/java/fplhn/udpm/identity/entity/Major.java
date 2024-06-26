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
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.Nationalized;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "chuyen_nganh")
@DynamicUpdate
public class Major extends BaseEntity {

    @Column(name = "ma", length = 255)
    private String code;

    @Column(name = "ten", length = 255)
    @Nationalized
    private String name;

    @ManyToOne
    @JoinColumn(name = "id_bo_mon")
    private Department department;

}
