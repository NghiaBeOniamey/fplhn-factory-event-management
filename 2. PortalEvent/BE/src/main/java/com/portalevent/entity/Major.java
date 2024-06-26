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

/**
 * @author SonPT
 */

/**
 * Object: Major (bộ môn)
 * Description: Lưu các bộ môn, bộ môn hẹp
 * - Lưu theo đệ quy. VD: bộ môn hẹp(bộ môn) JAVA thuộc bộ môn PTPM thuộc ngành UDPM (không lưu ngành UDPM)
 * -> ptpm = {id: <UUID1>; mainMajorId: null; code: "PTPM"; name: "Phát triển phần mềm"; mailOfManager: null}
 *   java = {id: <UUID2>; mainMajorId: <UUID1>; code: "JAVA"; name: "Bộ môn Java"; mailOfManager: <email trưởng bộ môn Java>}
 *   cSharp = {id: <UUID2>; mainMajorId: <UUID1>; code: "C#"; name: "Bộ môn C Sharp"; mailOfManager: <email trưởng bộ môn C#}
 */

@Entity
@Table(name = "major")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Major extends PrimaryEntity implements Serializable {

//    private Long idLong;

    private Long majorId;

    private Long departmentId;

    @Column(length = EntityProperties.LENGTH_CODE)
    private String code;

    @Column(length = EntityProperties.LENGTH_NAME)
    private String name;

//    @Column(length = EntityProperties.LENGTH_EMAIL)
//    private String mailOfManager;

//    private Long campusId;
//
//    @Column(length = EntityProperties.LENGTH_CODE)
//    private String campusCode;

}
