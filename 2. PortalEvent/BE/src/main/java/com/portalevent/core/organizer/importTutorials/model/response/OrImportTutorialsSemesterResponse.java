package com.portalevent.core.organizer.importTutorials.model.response;

import com.portalevent.entity.Semester;
import com.portalevent.entity.base.IsIdentified;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.rest.core.config.Projection;

@Projection(types = {Semester.class})
public interface OrImportTutorialsSemesterResponse extends IsIdentified {

    @Value("#{target.name}")
    String getName();

    @Value("#{target.start_time}")
    Long getStartTime();

    @Value("#{target.end_time}")
    Long getEndTime();

    @Value("#{target.start_time_first_block}")
    Long getStartTimeFirstBlock();

    @Value("#{target.end_time_first_block}")
    Long getEndTimeFirstBlock();

    @Value("#{target.start_time_second_block}")
    Long getStartTimeSecondBlock();

    @Value("#{target.end_time_second_block}")
    Long getEndTimeSecondBlock();
}
