package com.portalevent.core.admin.eventwaitingapprovalmanagement.repository;

import com.portalevent.core.admin.eventwaitingapprovalmanagement.model.request.AdminerEventListRequest;
import com.portalevent.core.admin.eventwaitingapprovalmanagement.model.respone.*;
import com.portalevent.core.common.TokenFindRequest;
import com.portalevent.repository.EventRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface AdminerEWAEventRepository extends EventRepository {

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
             where ( :#{#req.name} is null or e.name like :#{'%'+#req.name+'%'} )
               and (:#{#req.endTime} is null or  e.end_time <= :#{#req.endTime} )
               and ( :#{#req.startTime} is null or e.start_time >= :#{#req.startTime } )
               and ( :#{#req.status} is null or FIND_IN_SET(e.status,:#{#req.status} ))
               and ( :#{#req.categoryId} is null or FIND_IN_SET(e.category_id,:#{#req.categoryId} ))
               and (:#{#request.currentTrainingFacilityCode} is null or ca.code = :#{#request.currentTrainingFacilityCode} )
               and ( :#{#req.departmentId} is null or FIND_IN_SET(d.id,:#{#req.departmentId} ))
               and e.status IN :listStatus
               GROUP BY e.id, e.name, e.start_time, e.end_time, c.name, e.status, e.created_date
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
            where ( :#{#req.name} is null or e.name like :#{'%'+#req.name+'%'} )
               and (:#{#req.endTime} is null or  e.end_time <= :#{#req.endTime} )
               and ( :#{#req.startTime} is null or e.start_time >= :#{#req.startTime} )
               and ( :#{#req.status} is null or FIND_IN_SET(e.status,:#{#req.status} ))
               and ( :#{#req.categoryId} is null or FIND_IN_SET(e.category_id,:#{#req.categoryId} ))
               and (:#{#request.currentTrainingFacilityCode} is null or ca.code = :#{#request.currentTrainingFacilityCode} )
               and ( :#{#req.departmentId} is null or FIND_IN_SET(d.id,:#{#req.departmentId} ))
               and e.status IN :listStatus
               GROUP BY e.id, e.name, e.start_time, e.end_time, c.name, e.status, e.created_date
               ORDER BY e.created_date DESC
            """, nativeQuery = true)
    Page<AdminerEventListResponse> getEventList(Pageable pageable, AdminerEventListRequest req, TokenFindRequest request, List<Integer> listStatus);

    @Query(value = """
            select DISTINCT e.id as id
            , e.name as name
            , e.start_time as startTime
            , e.end_time  as endTime
            , e.formality as formality
            , c.name as categoryName
            , m.name as majorName
            , b.name as blockName
            , e.location as location
            , e.description  as description
            , a.name  as approverName
            , e.reason  as reason
            , e.expected_participants as expectedParticipant
            , e.status as status
            from event as e
            join category c on c.id = e.category_id
            join approver a on e.approver_id = a.id
            join block b on e.block_id = b.id
            join major m on e.major_id = m.id
            where e.id = :id
            and e.status in (2,1) """, nativeQuery = true)
    Optional<AdminerEventDetailApprovedResponse> getDetailApprovedById(@Param("id") String id);

    @Query(value = """
               select c.id as id,c.name as name from category as c
            """, nativeQuery = true)
    List<AdminerEventCategoryResponse> getListEventCategory();

    @Query(value = """
                select m.id as id, m.name as name from major as m
                where m.name is not null
            """, nativeQuery = true)
    List<AdminerEventGroupResponse> getEventMajor();

    @Query(value = """
                SELECT 	d.id as id,
                       	d.name as name
                FROM department d
                JOIN department_campus dc on d.department_id = dc.department_id
                JOIN campus c on dc.campus_id = c.campus_id
                WHERE c.code = :#{#request.currentTrainingFacilityCode}
                """,nativeQuery = true)
    List<AdminerListDepartmentResponse> getEventDepartment(TokenFindRequest request);

    @Query(value = """
               select e.id as idEvent, c.id as idComment, c.comment as comment, p.email as email
               from event e join comment c on e.id = c.event_id
               join participant p on c.participant_id = p.id
               where e.id = :idevent 
            """, countQuery = """
                select count(*)
                from event e join comment c on e.id = c.event_id
                join participant p on c.participant_id = p.id
                where e.id = :idevent
            """, nativeQuery = true)
    Page<AdminerCommentEventDetailResponse> getCommentEventById(Pageable pageable, @Param("idevent") String idEvent);

}



