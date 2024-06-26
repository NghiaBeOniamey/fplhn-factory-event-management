package com.portalevent.core.participant.home.repository;

import com.portalevent.core.common.TokenFindRequest;
import com.portalevent.entity.Participant;
import com.portalevent.infrastructure.constant.ParticipantType;
import com.portalevent.repository.ParticipantRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * @author SonPT
 */

@Repository
public interface PhHomeParticipantRepository extends ParticipantRepository {
    @Query(value = """
        select * from participant as p
        where p.event_id = :idEvent
        and p.user_code = :userCode
        AND (:#{#request.currentTrainingFacilityCode} IS NULL OR p.training_facility_code LIKE :#{#request.currentTrainingFacilityCode})
        AND (:#{#request.currentSubjectCode} IS NULL OR p.subject_code LIKE :#{#request.currentSubjectCode})
    """, nativeQuery = true)
    Optional<Participant> getByIdEventAndIdUser(@Param("idEvent") String idEvent, @Param("userCode") String userCode, TokenFindRequest request);

    List<Participant> getByEventIdAndUserCode(String eventId, String userCode);

    Optional<Participant> findByParticipantCodeAndEventIdAndParticipantType(String participantCode, String eventId, ParticipantType participantType);
}
