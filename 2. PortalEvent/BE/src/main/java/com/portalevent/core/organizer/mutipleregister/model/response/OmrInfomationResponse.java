package com.portalevent.core.organizer.mutipleregister.model.response;

import com.portalevent.entity.Category;
import com.portalevent.entity.Event;
import com.portalevent.entity.Major;
import com.portalevent.entity.Semester;
import com.portalevent.entity.base.IsIdentified;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.rest.core.config.Projection;

import java.util.List;

/**
 * @author SonPT
 */

@Getter
@Setter
public class OmrInfomationResponse {

    private List<OmrSemesterResponse> listSemester;

    private List<OmrCategoryResponse> listCategory;

    private List<OmrObjectResponse> listObject;

    private List<OmrMajorResponse> listMajor;

    @Projection(types = {Semester.class})
    public interface OmrSemesterResponse extends IsIdentified {

        @Value("#{target.name}")
        String getName();
        @Value("#{target.start_time}")
        Long getStartTime();
        @Value("#{target.end_time}")
        Long getEndTime();
        @Value("#{target.start_time_first_block}")
        Long getStartTimeBlock1();
        @Value("#{target.end_time_first_block}")
        Long getEndTimeBlock1();
        @Value("#{target.start_time_second_block}")
        Long getStartTimeBlock2();
        @Value("#{target.end_time_second_block}")
        Long getEndTimeBlock2();

    }

    @Projection(types = {Category.class})
    public interface OmrCategoryResponse extends IsIdentified{

        @Value("#{target.name}")
        String getName();

    }

    @Projection(types = {Object.class})
    public interface OmrObjectResponse extends IsIdentified{

        @Value("#{target.name}")
        String getName();

    }

    @Projection(types = {Major.class})
    public interface OmrMajorResponse extends IsIdentified{

        @Value("#{target.code}")
        String getCode();

        @Value("#{target.name}")
        String getName();

    }

}
