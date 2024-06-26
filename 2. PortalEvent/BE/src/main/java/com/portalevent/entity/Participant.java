package com.portalevent.entity;

import com.portalevent.entity.base.PrimaryEntity;
import com.portalevent.infrastructure.constant.EntityProperties;
import com.portalevent.infrastructure.constant.ParticipantType;
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
 * Object: Participant (Người tham gia)
 * Description: Lưu toàn bộ người tham gia của các sự kiện.
 * - 1 email(1 người) có thể có mặt trong nhiều sự kiện khác nhau
 * - Question (câu hỏi) chỉ lưu câu hỏi thắc mắc trước khi sự kiện được tổ chức
 * - AttendenceTime: Lưu thời gian bấm điểm danh
 * - Rate: Đánh giá sự kiện (1-5 sao) chỉ lưu khi người tham gia điền form điểm danh
 * - Feedback: Phản hồi về sự kiện -> chỉ lưu khi người tham gia điền form điểm danh
 */

@Getter
@Setter
@Table(name = "participant")
@Entity
public class Participant extends PrimaryEntity implements Serializable {

    @Column(length = EntityProperties.LENGTH_CODE)
    private String participantCode;

    @Column(length = EntityProperties.LENGTH_CODE)
    private String subjectCode;

    @Column(length = EntityProperties.LENGTH_CODE)
    private String trainingFacilityCode;

    @Column(length = EntityProperties.LENGTH_ID,nullable = false)
    private String eventId;

    @Column(length = EntityProperties.LENGTH_ID)
//    private String idUser; // Mới bổ sung: Sử dụng lưu idUser lấy từ module identity về
    private String userCode; // Mới bổ sung: Sử dụng lưu idUser lấy từ module identity về

    @Column(length = EntityProperties.LENGTH_EMAIL)
    private String email;

    @Nationalized
    @Column(length = EntityProperties.LENGTH_QUESTION)
    private String question;

    @Column
    private Long attendanceTime;

    @Column
    private Byte rate; // Đánh giá - từ 1 -> 5 (1 sao đến 5 sao)

    @Column(length = EntityProperties.LENGTH_NAME)
    private String className;

    @Column(length = EntityProperties.LENGTH_NAME)
    private String lecturerName; //Tên của giảng viên

    @Column(length = EntityProperties.LENGTH_NAME)
    private String participantName; //Tên của người tham gia

    @Nationalized
    @Column(length = EntityProperties.LENGTH_FEEDBACK)
    private String feedback;

    @Column(name = "participant_type")
    private ParticipantType participantType;

    @Column(name = "role")
    private String role;
}
