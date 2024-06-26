package com.portalevent.core.organizer.mutipleregister.model.response;

import com.portalevent.entity.Event;
import com.portalevent.entity.base.IsIdentified;
import com.portalevent.infrastructure.constant.EventType;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.rest.core.config.Projection;

/**
 * @author SonPT
 */

@Getter
@Setter
public class OmrEventDetailResponse {

    private String id;

    private String name;

    private String time;

    private String status;

    private String categoryName;

    private String semesterName;

    private String blockName;

    private Short expectedParticipants;

    private String eventType;

    private String objects;

    private String majors;

    private String organizers;

    @Projection(types = {Event.class})
    public interface EventResponse extends IsIdentified {

        @Value("#{target.name}")
        String getName();

        @Value("#{target.start_time}")
        Long getStartTime();

        @Value("#{target.end_time}")
        Long getEndTime();

        @Value("#{target.status}")
        Short getStatus();

        @Value("#{target.category_name}")
        String getCategoryName();

        @Value("#{target.semester_name}")
        String getSemesterName();

        @Value("#{target.block_number}")
        String getBlockNumber();

        @Value("#{target.expected_participants}")
        Short getExpectedParticipants();

        @Value("#{target.event_type}")
        Short getEventType();

        @Value("#{target.object_name}")
        String getObjectName();

        @Value("#{target.major_name}")
        String getMajorName();

        @Value("#{target.organizer_id}")
        String getOranizerId();

    }

}
