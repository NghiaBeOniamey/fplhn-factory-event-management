package com.portalevent.entity;

import com.portalevent.entity.base.PrimaryEntity;
import com.portalevent.infrastructure.constant.EntityProperties;
import com.portalevent.infrastructure.constant.EvidenceType;
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
 * Object: Evidence (Bằng chứng)
 * Description:
 * - Lưu lại các bằng chứng của sự kiện sau khi đã được tổ chức
 * - Chỉ sử dụng, thêm sửa xóa bởi NTC sau khi SK đã được tổ chức và ở trạng thái `ORGANIZED`
 * - Một sự kiện có thể có nhiều bằng chứng. VD: Ảnh chụp màn hình Zoom, hội trường khi sự kiện đang diễn ra,...
 */

@Entity
@Table(name = "evidence")
@Getter
@Setter
public class Evidence extends PrimaryEntity implements Serializable {

    @Column(length = EntityProperties.LENGTH_ID, nullable = false)
    private String eventId;

    @Nationalized
    @Column(length = EntityProperties.LENGTH_NAME, nullable = false)
    private String name;

    @Column(length = EntityProperties.LENGTH_PATH, nullable = false)
    private String path; // Đường dẫn đến file báo cáo/ ảnh

    @Nationalized
    @Column(length = EntityProperties.LENGTH_DESCRIPTION)
    private String description;

    @Column(nullable = false)
    private EvidenceType evidenceType;

}
