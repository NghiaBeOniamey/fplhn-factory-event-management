package com.portalevent.core.adminh.eventwaitingapprovalmanagement.repository;

import com.portalevent.core.admin.eventwaitingapprovalmanagement.model.respone.AdminerListDepartmentResponse;
import com.portalevent.core.adminh.eventwaitingapprovalmanagement.model.request.AdminHewEventListRequest;
import com.portalevent.core.adminh.eventwaitingapprovalmanagement.model.respone.AdminHewCommentEventDetailResponse;
import com.portalevent.core.adminh.eventwaitingapprovalmanagement.model.respone.AdminHewEventCategoryResponse;
import com.portalevent.core.adminh.eventwaitingapprovalmanagement.model.respone.AdminHewEventDetailApprovedResponse;
import com.portalevent.core.adminh.eventwaitingapprovalmanagement.model.respone.AdminHewEventGroupResponse;
import com.portalevent.core.adminh.eventwaitingapprovalmanagement.model.respone.AdminHewEventListResponse;
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
public interface AdminHewaEventRepository extends EventRepository {

    @Query(value = """
            SELECT ROW_NUMBER() OVER(ORDER BY e.created_date DESC) AS indexs,
                    e.id AS eventId,
                    e.name AS eventName,
                    e.start_time AS eventStartTime,
                    e.end_time AS eventEndTime,
                    c.name AS categoryName,
                    e.status AS status
            FROM event e
            JOIN category c ON c.id = e.category_id
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
            WHERE ( :#{#req.name} IS NULL OR e.name like :#{'%'+#req.name+'%'} )
                AND (:#{#req.endTime} IS NULL OR  e.end_time <= :#{#req.endTime} )
                AND ( :#{#req.startTime} IS NULL OR e.start_time >= :#{#req.startTime } )
                AND ( :#{#req.status} IS NULL OR FIND_IN_SET(e.status,:#{#req.status} ))
                AND ( :#{#req.categoryId} IS NULL OR FIND_IN_SET(e.category_id,:#{#req.categoryId} ))
                AND (:#{#request.currentTrainingFacilityCode} IS NULL OR ca.code = :#{#request.currentTrainingFacilityCode} )
                AND ( :#{#req.departmentId} IS NULL OR FIND_IN_SET(d.id,:#{#req.departmentId} ))
                AND e.status IN :listStatus
                GROUP BY e.id, e.name, e.start_time, e.end_time, c.name, e.status, e.created_date
                ORDER BY e.created_date DESC
            """, countQuery = """
            SELECT count(*)
            FROM event e
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
            WHERE ( :#{#req.name} IS NULL OR e.name like :#{'%'+#req.name+'%'} )
                AND (:#{#req.endTime} IS NULL OR  e.end_time <= :#{#req.endTime} )
                AND ( :#{#req.startTime} IS NULL OR e.start_time >= :#{#req.startTime} )
                AND ( :#{#req.status} IS NULL OR FIND_IN_SET(e.status,:#{#req.status} ))
                AND ( :#{#req.categoryId} IS NULL OR FIND_IN_SET(e.category_id,:#{#req.categoryId} ))
                AND (:#{#request.currentTrainingFacilityCode} IS NULL OR ca.code = :#{#request.currentTrainingFacilityCode} )
                AND ( :#{#req.departmentId} IS NULL OR FIND_IN_SET(d.id,:#{#req.departmentId} ))
                AND e.status IN :listStatus
                GROUP BY e.id, e.name, e.start_time, e.end_time, c.name, e.status, e.created_date
                ORDER BY e.created_date DESC
            """, nativeQuery = true)
    Page<AdminHewEventListResponse> getEventList(Pageable pageable, AdminHewEventListRequest req, TokenFindRequest request, List<Integer> listStatus);

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
    Optional<AdminHewEventDetailApprovedResponse> getDetailApprovedById(@Param("id") String id);

    @Query(value = """
               select c.id as id,c.name as name from category as c
            """, nativeQuery = true)
    List<AdminHewEventCategoryResponse> getListEventCategory();

    @Query(value = """
                select m.id as id, m.name as name from major as m
                where m.name is not null
            """, nativeQuery = true)
    List<AdminHewEventGroupResponse> getEventMajor();

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
    Page<AdminHewCommentEventDetailResponse> getCommentEventById(Pageable pageable, @Param("idevent") String idEvent);

}



