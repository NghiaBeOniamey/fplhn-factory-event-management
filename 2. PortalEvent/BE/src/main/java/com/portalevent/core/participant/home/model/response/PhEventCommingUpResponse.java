package com.portalevent.core.participant.home.model.response;

import com.portalevent.entity.Event;
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
public class PhEventCommingUpResponse {

    private String id;

    private String name;

    private String startTime;

    private String time;

    private String endTime;

    private String banner;

    private List<String> listOrganizerAccount;

	@Projection(types = {Event.class})
	public interface EventQueryResponse extends IsIdentified {
        @Value("#{target.name}")
        String getName();

        @Value("#{target.start_time}")
        Long getStartTime();

        @Value("#{target.end_time}")
        Long getEndTime();

        @Value("#{target.banner_path}")
        String getBannerPath();

        @Value("#{target.organizer_account}")
        String getOrganizerAccount();
    }

}
