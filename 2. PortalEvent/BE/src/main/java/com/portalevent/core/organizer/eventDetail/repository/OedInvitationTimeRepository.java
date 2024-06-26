package com.portalevent.core.organizer.eventDetail.repository;

import com.portalevent.core.common.TokenFindRequest;
import com.portalevent.core.organizer.eventDetail.model.response.OedInvitationTimeResponse;
import com.portalevent.entity.InvitationTime;
import com.portalevent.repository.InvitationTimeRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OedInvitationTimeRepository extends InvitationTimeRepository {
    @Query(value = """
            SELECT a.id, 
            ROW_NUMBER() OVER(ORDER BY a.last_modified_date DESC) as indexs,
            a.event_id as eventId, a.time, a.status
            FROM invitation_time a
            WHERE a.event_id = :idEvent
            ORDER BY a.time ASC
            """, nativeQuery = true)
    List<OedInvitationTimeResponse> getAllInvitationTime(@Param("idEvent") String idEvent );

    List<InvitationTime> getAllByEventId(String eventId);

}
