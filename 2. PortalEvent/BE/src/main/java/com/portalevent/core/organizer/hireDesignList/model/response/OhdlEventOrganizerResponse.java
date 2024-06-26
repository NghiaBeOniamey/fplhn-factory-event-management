package com.portalevent.core.organizer.hireDesignList.model.response;

import com.portalevent.entity.base.IsIdentified;
import org.springframework.beans.factory.annotation.Value;

/**
 * @author thangncph26123
 */

/**
 * Hứng data MySQL hiển thị Organizer
 */
public interface OhdlEventOrganizerResponse extends IsIdentified {

    @Value("#{target.event_id}")
    String getEventId();

    @Value("#{target.organizer_id}")
    String getOrganizerId();

    @Value("#{target.event_role}")
    Integer getEventRole();

}
