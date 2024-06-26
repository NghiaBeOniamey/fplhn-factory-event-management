package com.portalevent.core.approver.eventapproved.repository;

import com.portalevent.core.approver.eventapproved.model.request.AeaEventApprovedRequest;
import com.portalevent.core.approver.eventapproved.model.response.AeaEventApprovedResponse;
import com.portalevent.core.approver.eventwaitingapproval.model.respone.AewaEventCategoryResponse;
import com.portalevent.core.common.TokenFindRequest;
import com.portalevent.repository.EventRepository;
import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface AeaEventRepository extends EventRepository {

    @Query(value = """
             select ROW_NUMBER() OVER(ORDER BY e.created_date DESC) as indexs,
                    e.id as eventId,
                    e.name as eventName,
                    e.start_time as eventStartTime,
                    e.end_time as eventEndTime,
                    c.name as categoryName,
                    e.status as status
             from event e
            JOIN category c on c.id = e.category_id
              JOIN event_major em ON
             e.id = em.event_id
             JOIN major_campus mc ON
             em.major_id = mc.id
             JOIN major m ON
             m.major_id = mc.major_id
             JOIN department_campus dc ON
             mc.department_campus_id = dc.department_campus_id
             JOIN department d ON
             dc.department_id = d.department_id
             JOIN campus ca ON
             dc.campus_id = ca.campus_id
             where ( :#{#req.name} is null or e.name like :#{'%'+#req.name+'%'})
               and (:#{#req.endTime} is null or  e.end_time <= :#{#req.endTime})
               and ( :#{#req.startTime} is null or e.start_time >= :#{#req.startTime})
               and ( :#{#req.status} is null or FIND_IN_SET(e.status,:#{#req.status}))
               and ( :#{#req.categoryId} is null or FIND_IN_SET(e.category_id,:#{#req.categoryId}))
               and (:#{#request.currentTrainingFacilityCode} is null or ca.campus_id = :#{#req.currentTrainingFacilityCode})
               and (:#{#request.currentSubjectCode} is null or d.department_id = :#{#req.currentSubjectCode})
               and e.status IN :listStatus
               GROUP BY e.id, e.name, e.start_time, e.end_time, c.name, e.status
             ORDER BY e.created_date DESC
            """, countQuery = """
            select count(*)
            from event e
             JOIN category c on c.id = e.category_id
              JOIN event_major em ON
             e.id = em.event_id
             JOIN major_campus mc ON
             em.major_id = mc.id
             JOIN major m ON
             m.major_id = mc.major_id
             JOIN department_campus dc ON
             mc.department_campus_id = dc.department_campus_id
             JOIN department d ON
             dc.department_id = d.department_id
             JOIN campus ca ON
             dc.campus_id = ca.campus_id
            where ( :#{#req.name} is null or e.name like :#{'%'+#req.name+'%'})
               and (:#{#req.endTime} is null or  e.end_time <= :#{#req.endTime})
               and ( :#{#req.startTime} is null or e.start_time >= :#{#req.startTime})
               and ( :#{#req.status} is null or FIND_IN_SET(e.status,:#{#req.status}))
               and ( :#{#req.categoryId} is null or FIND_IN_SET(e.category_id,:#{#req.categoryId}))
               and (:#{#request.currentTrainingFacilityCode} is null or ca.campus_id = :#{#req.currentTrainingFacilityCode})
               and (:#{#request.currentSubjectCode} is null or d.department_id = :#{#req.currentSubjectCode})
               and e.status IN :listStatus
               GROUP BY e.id, e.name, e.start_time, e.end_time, c.name, e.status
               ORDER BY e.created_date DESC
            """, nativeQuery = true)
    Page<AeaEventApprovedResponse> getListEventAppoved(Pageable pageable, AeaEventApprovedRequest req, TokenFindRequest request);

    @Query(value = """
               select c.id as id,c.name as name from category as c
            """, nativeQuery = true)
    List<AewaEventCategoryResponse> getListEventCategory();
}


