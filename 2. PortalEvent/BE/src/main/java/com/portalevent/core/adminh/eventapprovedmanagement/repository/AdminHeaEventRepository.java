package com.portalevent.core.adminh.eventapprovedmanagement.repository;

import com.portalevent.core.adminh.eventapprovedmanagement.model.request.AdminHeaEventApprovedRequest;
import com.portalevent.core.adminh.eventapprovedmanagement.model.response.AdminHeaEventApprovedResponse;
import com.portalevent.core.approver.eventwaitingapproval.model.respone.AewaEventCategoryResponse;
import com.portalevent.core.common.TokenFindRequest;
import com.portalevent.repository.EventRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AdminHeaEventRepository extends EventRepository {

    @Query(value = """
            select ROW_NUMBER() OVER(ORDER BY e.last_modified_date DESC) as indexs,
                                             e.id as eventId,
                                             e.name as eventName,
                                             e.start_time as eventStartTime,
                                             e.end_time as eventEndTime,
                                             c.name as categoryName,
                                             e.status as status
                                    from event e
                                    left join category c on c.id = e.category_id
                     where ( :#{#req.name} is null or e.name like :#{'%'+#req.name+'%'})
                       and (:#{#req.endTime} is null or  e.end_time <= :#{#req.endTime})
                       and ( :#{#req.startTime} is null or e.start_time >= :#{#req.startTime})
                       and ( :#{#req.status} is null or FIND_IN_SET(e.status,:#{#req.status}))
                       and ( :#{#req.categoryId} is null or FIND_IN_SET(e.category_id,:#{#req.categoryId}))
                       and e.status in (4, 5)
                       and (:#{#request.currentTrainingFacilityCode} is null or e.training_facility_code like :#{#request.currentTrainingFacilityCode})
            """, countQuery = """
                select count(e.id)
                     from event e
                     left join category c on c.id = e.category_id
                     where ( :#{#req.name} is null or e.name like :#{'%'+#req.name+'%'})
                       and (:#{#req.endTime} is null or  e.end_time <= :#{#req.endTime})
                       and ( :#{#req.startTime} is null or e.start_time >= :#{#req.startTime})
                       and ( :#{#req.status} is null or FIND_IN_SET(e.status,:#{#req.status}))
                       and ( :#{#req.categoryId} is null or FIND_IN_SET(e.category_id,:#{#req.categoryId}))
                       and e.status in (4, 5)
                       and (:#{#request.currentTrainingFacilityCode} is null or e.training_facility_code like :#{#request.currentTrainingFacilityCode})
            """, nativeQuery = true)
    Page<AdminHeaEventApprovedResponse> getListEventAppoved(Pageable pageable, AdminHeaEventApprovedRequest req, TokenFindRequest request);

    @Query(value = """
               select c.id as id,c.name as name from category as c
            """, nativeQuery = true)
    List<AewaEventCategoryResponse> getListEventCategory();
}
