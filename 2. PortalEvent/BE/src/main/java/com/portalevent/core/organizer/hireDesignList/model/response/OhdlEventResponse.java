package com.portalevent.core.organizer.hireDesignList.model.response;

import com.portalevent.entity.Event;
import com.portalevent.entity.base.IsIdentified;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.rest.core.config.Projection;

/**
 * Hứng data từ MySQL hiển thị Event Hire Design lên giao diện
 */

@Projection(types = Event.class)
public interface OhdlEventResponse extends IsIdentified {


    @Value("#{target.id}")
    String getId();

    @Value("#{target.name}")
    String getName();

    @Value("#{target.start_time}")
    Long getStartTime();

    @Value("#{target.end_time}")
    Long getEndTime();

    @Value("#{target.is_hire_design_banner}")
    Boolean getIsHireDesignBanner();

    @Value("#{target.is_hire_design_standee}")
    Boolean getIsHireDesignStandee();

    @Value("#{target.is_hire_design_bg}")
    Boolean getIsHireDesignBackground();

    @Value("#{target.is_hire_location}")
    Boolean getIsHireLocation();

    @Value("#{target.event_type}")
    Integer getEventType();

    @Value("#{target.semester}")
    String getSemester();

    @Value("#{target.organizer}")
    String getOrganizer();

    @Value("#{target.formality}")
    String getFormality();

    @Value("#{target.location}")
    String getLocation();

    @Value("#{target.major}")
    String getMajor();

    @Value("#{target.object}")
    String getObject();

    @Value("#{target.category}")
    String getCategory();

}
