package com.portalevent.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

/**
 * @author SonPT
 */

@Entity
@Table(name = "system_option")
@Getter
@Setter
public class SystemOption implements Serializable {

    @Id
    @Column(updatable = false)
    private Long id;

    private Byte mandatoryApprovalDays; //Số ngày cẩn thực hiện gửi yêu câu cầu phê duyệt trước khi sự kiện diễn ra

    private Boolean isAllowNotEnoughTimeApproval; // Trạng thái cho phép gửi yêu cầu phê duyệt không đủ thời gian quy định

    private Boolean isAllowCloseEvent; //Trạng thái cho phép đóng sự kiện khi đã phê duyệt

    private Long minimumCloseTime; //Thời gian tối thiểu cótherer đóng sự kiện trước khi diễn ra

}
