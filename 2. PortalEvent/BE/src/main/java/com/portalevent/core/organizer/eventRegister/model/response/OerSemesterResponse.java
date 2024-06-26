package com.portalevent.core.organizer.eventRegister.model.response;

import com.portalevent.entity.Category;
import com.portalevent.entity.Semester;
import com.portalevent.entity.base.IsIdentified;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.rest.core.config.Projection;

/**
 * @author SonPT
 */
@Projection(types = {Semester.class})
public interface OerSemesterResponse extends IsIdentified {

    @Value("#{target.name}")
    String getName();

    @Value("#{target.start_time}")
    String getStartTime();

    @Value("#{target.end_time}")
    String getEndTime();

    @Value("#{target.start_time_first_block}")
    String getStartTimeFirstBlock();

    @Value("#{target.end_time_first_block}")
    String getEndTimeFirstBlock();

    @Value("#{target.start_time_second_block}")
    String getStartTimeSecondBlock();

    @Value("#{target.end_time_second_block}")
    String getEndTimeSecondBlock();
}
