package com.portalevent.core.adminho.eventwaitingapprovalmanagement.repository;

import com.portalevent.core.adminho.eventwaitingapprovalmanagement.model.request.AdminHOewEventListRequest;
import com.portalevent.core.adminho.eventwaitingapprovalmanagement.model.respone.AdminHOewCommentEventDetailResponse;
import com.portalevent.core.adminho.eventwaitingapprovalmanagement.model.respone.AdminHOewEventCategoryResponse;
import com.portalevent.core.adminho.eventwaitingapprovalmanagement.model.respone.AdminHOewEventDetailApprovedResponse;
import com.portalevent.core.adminho.eventwaitingapprovalmanagement.model.respone.AdminHOewEventGroupResponse;
import com.portalevent.core.adminho.eventwaitingapprovalmanagement.model.respone.AdminHOewEventListResponse;
import com.portalevent.repository.EventRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface AdminHOewaEventRepository extends EventRepository {

    @Query(value = """
            SELECT ROW_NUMBER() OVER(ORDER BY e.created_date DESC) AS indexs,
                    e.id as eventId,
                    e.name as eventName,
                    e.start_time as eventStartTime,
                    e.end_time as eventEndTime,
                    c.name as categoryName,
                    e.status as status
            FROM event e
            LEFT JOIN category c on c.id = e.category_id
            LEFT JOIN event_major em ON
                e.id = em.event_id
            LEFT JOIN major_campus mc ON
                em.major_id = mc.id
            LEFT JOIN major m ON
                m.major_id = mc.major_id
            LEFT JOIN department_campus dc ON
                mc.department_campus_id = dc.department_campus_id
            LEFT JOIN department d ON
                dc.department_id = d.department_id
            LEFT JOIN campus ca ON
                dc.campus_id = ca.campus_id
            WHERE ( :#{#req.name} IS NULL OR e.name like :#{'%'+#req.name+'%'})
                AND (:#{#req.endTime} IS NULL OR  e.end_time <= :#{#req.endTime})
                AND (:#{#req.startTime} IS NULL OR e.start_time >= :#{#req.startTime})
                AND (:#{#req.status} IS NULL OR FIND_IN_SET(e.status,:#{#req.status}))
                AND (:#{#req.categoryId} IS NULL OR FIND_IN_SET(e.category_id,:#{#req.categoryId}))
                AND (:#{#req.campusId} IS NULL OR ca.campus_id = :#{#req.campusId})
                AND (:#{#req.departmentId} IS NULL OR d.department_id = :#{#req.departmentId})
                AND e.status IN :listStatus
                GROUP BY e.id, e.name, e.start_time, e.end_time, c.name, e.status, e.created_date
                ORDER BY e.created_date DESC
            """, countQuery = """
            SELECT count(*)
            FROM event e
            LEFT JOIN category c on c.id = e.category_id
            LEFT JOIN event_major em ON
                e.id = em.event_id
            LEFT JOIN major_campus mc ON
                em.major_id = mc.id
            LEFT JOIN major m ON
                m.major_id = mc.major_id
            LEFT JOIN department_campus dc ON
                mc.department_campus_id = dc.department_campus_id
            LEFT JOIN department d ON
                dc.department_id = d.department_id
            LEFT JOIN campus ca ON
                dc.campus_id = ca.campus_id
            WHERE ( :#{#req.name} IS NULL OR e.name like :#{'%'+#req.name+'%'})
                AND (:#{#req.endTime} IS NULL OR  e.end_time <= :#{#req.endTime})
                AND ( :#{#req.startTime} IS NULL OR e.start_time >= :#{#req.startTime})
                AND ( :#{#req.status} IS NULL OR FIND_IN_SET(e.status,:#{#req.status}))
                AND ( :#{#req.categoryId} IS NULL OR FIND_IN_SET(e.category_id,:#{#req.categoryId}))
                AND (:#{#req.campusId} IS NULL OR ca.campus_id = :#{#req.campusId})
                AND (:#{#req.departmentId} IS NULL OR d.department_id = :#{#req.departmentId})
                AND e.status IN :listStatus
                GROUP BY e.id, e.name, e.start_time, e.end_time, c.name, e.status, e.created_date
                ORDER BY e.created_date DESC
            """, nativeQuery = true)
    Page<AdminHOewEventListResponse> getEventList(Pageable pageable, AdminHOewEventListRequest req, List<Integer> listStatus);

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
    Optional<AdminHOewEventDetailApprovedResponse> getDetailApprovedById(@Param("id") String id);

    @Query(value = """
               select c.id as id,c.name as name from category as c
            """, nativeQuery = true)
    List<AdminHOewEventCategoryResponse> getListEventCategory();

    @Query(value = """
                select m.id as id, m.name as name from major as m
                where m.name is not null
            """, nativeQuery = true)
    List<AdminHOewEventGroupResponse> getEventMajor();

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
    Page<AdminHOewCommentEventDetailResponse> getCommentEventById(Pageable pageable, @Param("idevent") String idEvent);

}



