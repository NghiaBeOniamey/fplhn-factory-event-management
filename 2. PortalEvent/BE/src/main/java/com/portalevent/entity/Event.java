package com.portalevent.entity;

import com.portalevent.entity.base.PrimaryEntity;
import com.portalevent.infrastructure.constant.ApprovalPeriodicallyStatus;
import com.portalevent.infrastructure.constant.EntityProperties;
import com.portalevent.infrastructure.constant.EventStatus;
import com.portalevent.infrastructure.constant.EventType;
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

@Getter
@Setter
@Table(name = "event")
@Entity
public class Event extends PrimaryEntity implements Serializable {

    @Column(length = EntityProperties.LENGTH_ID)
    private String approverId;

    @Column(length = EntityProperties.LENGTH_CODE)
    private String approverCode;

    @Column(length = EntityProperties.LENGTH_ID)
    private String categoryId;

    @Column(length = EntityProperties.LENGTH_ID)
    private String semesterId;

    @Column(length = EntityProperties.LENGTH_CODE)
    private String subjectCode;

    @Column(length = EntityProperties.LENGTH_CODE)
    private String trainingFacilityCode;

    @Column(length = EntityProperties.LENGTH_EVENT_NAME)
    @Nationalized
    private String name;

    @Column
    private Long startTime;

    @Column
    private Long endTime;

    @Column
    private Boolean blockNumber; // false - BLOCK 1; true - BLOCK 2

    @Column
    private Short expectedParticipants; // Số lượng người dự kiến tham gia

    @Column
    private Short numberParticipants; // Số lượng người tham gia thực tế -> do NTC điền sau khi SK được tổ chức

    @Column(nullable = false)
    private Boolean isAttendance = false; // Biến check mở điểm danh -> Luôn false khi vừa tạo sự kiện

    @Column(nullable = false)
    private Boolean isOpenRegistration = false; // Biến check mở đăng ký cho người tham gia -> luôn false khi tạo sk. chỉ sửa khi sk ở trạng thái đã phê duyệt

    @Column
    private EventType eventType; // Kiểu của sự kiện

    @Column(length = EntityProperties.LENGTH_REASON)
    private String reason; // Lý do từ chối phê duyệt của Người phê duyệt (nếu có)

    @Column(nullable = false)
    private EventStatus status = EventStatus.SCHEDULED_HELD; // Trạng thái của sự kiện

    @Column(nullable = false)
    private Boolean isHireDesignBanner = false; // Xác nhận cần thuê thiết kế ảnh Banner, True - có false - không

    @Column(nullable = false)
    private Boolean isHireDesignBg = false; // Xác nhận cần thuê thiết kế ảnh background, True - có false - không

    @Column(nullable = false)
    private Boolean isHireDesignStandee = false; // Xác nhận cần thuê thiết kế ảnh standee, True - có false - không

    @Column(nullable = false)
    private Boolean isHireLocation = false; // Xác nhận cần book địa điểm, True - có false - không

    @Column(length = EntityProperties.LENGTH_PATH)
    private String backgroundPath; //Ảnh backgroud ZOOM, Google meets

    @Column(length = EntityProperties.LENGTH_PATH)
    private String bannerPath; //Ảnh banner để sử dụng cho các bài đăng FB, Classroom

    @Column(length = EntityProperties.LENGTH_PATH)
    private String standeePath; //Ảnh sử dụng để quảng cáo tại các biển quảng cáo

    @Column
    private Integer orderApprovalPriority; //Độ ưu tiên yêu cầu phê duyệt

    @Column
    private Boolean isNotEnoughTimeApproval = false; //Không đủ thời gian phê duyệt

    @Column
    private Boolean isConversionHoneyRequest = false; //Không đủ thời gian phê duyệt

    @Column
    private ApprovalPeriodicallyStatus isWaitApprovalPeriodically = ApprovalPeriodicallyStatus.CHUA_GUI_YEU_CAU; // Chờ phê duệt thành sự kiện hằng kỳ. mặc định 0: Chưa gửi yêu cầu, 1: Đã gửi yêu cầu, 2: Đã phê duyệt

    @Column(length = EntityProperties.LENGTH_REASON)
    private String approvalPeriociallyReason; // Lý do từ chối phê duyệt sự kiện hằng kỳ

    @Nationalized
    @Column(length = EntityProperties.LENGTH_DESCRIPTION)
    private String description;

}