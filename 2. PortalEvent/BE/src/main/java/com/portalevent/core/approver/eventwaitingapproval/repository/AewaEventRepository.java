package com.portalevent.core.approver.eventwaitingapproval.repository;

import com.portalevent.core.approver.eventwaitingapproval.model.request.AewaEventListRequest;
import com.portalevent.core.approver.eventwaitingapproval.model.respone.AewaCommentEventDetailResponse;
import com.portalevent.core.approver.eventwaitingapproval.model.respone.AewaEventCategoryResponse;
import com.portalevent.core.approver.eventwaitingapproval.model.respone.AewaEventDetailApprovedResponse;
import com.portalevent.core.approver.eventwaitingapproval.model.respone.AewaEventGroupResponse;
import com.portalevent.core.approver.eventwaitingapproval.model.respone.AewaEventListResponse;
import com.portalevent.core.common.TokenFindRequest;
import com.portalevent.repository.EventRepository;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface AewaEventRepository extends EventRepository {
    @Query(value = """
            SELECT  ROW_NUMBER() OVER(ORDER BY e.created_date DESC) as indexs,
                    e.id as eventId,
                    e.name as eventName,
                    e.start_time as eventStartTime,
                    e.end_time as eventEndTime,
                    ca.name as categoryName,
                    e.status as status
            FROM event e
            LEFT JOIN category ca on ca.id = e.category_id
            LEFT JOIN event_major em ON
                em.event_id = e.id
            LEFT JOIN major_campus mc ON
                em.major_id = mc.id
            LEFT JOIN major m ON
                m.major_id = mc.major_id
            LEFT JOIN department_campus dc ON
                mc.department_campus_id = dc.department_campus_id
            LEFT JOIN department d ON
                dc.department_id = d.department_id
            LEFT JOIN campus c ON
                dc.campus_id = c.campus_id
            where ( :#{#req.name} is null or e.name like :#{'%'+#req.name+'%'})
                and (:#{#req.endTime} is null or  e.end_time <= :#{#req.endTime})
                and ( :#{#req.startTime} is null or e.start_time >= :#{#req.startTime})
                and ( :#{#req.status} is null or FIND_IN_SET(e.status,:#{#req.status}))
                and ( :#{#req.categoryId} is null or FIND_IN_SET(e.category_id,:#{#req.categoryId}))
                AND (:#{#request.currentSubjectCode} IS NULL OR d.code LIKE :#{#request.currentSubjectCode})
                AND (:#{#request.currentTrainingFacilityCode} IS NULL OR c.code LIKE :#{#request.currentTrainingFacilityCode})
                and e.status IN :listStatus
            GROUP BY
                    e.created_date,
                    e.id,
                    e.name,
                    e.start_time,
                    e.end_time,
                    ca.name,
                    e.status
            ORDER BY e.created_date DESC
            """, countQuery = """
            SELECT count(*)
            FROM event e
            LEFT JOIN category ca on ca.id = e.category_id
            LEFT JOIN event_major em ON
                em.event_id = e.id
            LEFT JOIN major_campus mc ON
                em.major_id = mc.id
            LEFT JOIN major m ON
                m.major_id = mc.major_id
            LEFT JOIN department_campus dc ON
                mc.department_campus_id = dc.department_campus_id
            LEFT JOIN department d ON
                dc.department_id = d.department_id
            LEFT JOIN campus c ON
                dc.campus_id = c.campus_id
            where ( :#{#req.name} is null or e.name like :#{'%'+#req.name+'%'})
                and (:#{#req.endTime} is null or  e.end_time <= :#{#req.endTime})
                and ( :#{#req.startTime} is null or e.start_time >= :#{#req.startTime})
                and ( :#{#req.status} is null or FIND_IN_SET(e.status,:#{#req.status}))
                and ( :#{#req.categoryId} is null or FIND_IN_SET(e.category_id,:#{#req.categoryId}))
                AND (:#{#request.currentSubjectCode} IS NULL OR d.code LIKE :#{#request.currentSubjectCode})
                AND (:#{#request.currentTrainingFacilityCode} IS NULL OR c.code LIKE :#{#request.currentTrainingFacilityCode})
                and e.status IN :listStatus
                GROUP BY
                    e.created_date,
                    e.id,
                    e.name,
                    e.start_time,
                    e.end_time,
                    ca.name,
                    e.status
                ORDER BY e.created_date DESC
            """, nativeQuery = true)
    Page<AewaEventListResponse> getEventList(Pageable pageable, AewaEventListRequest req, TokenFindRequest request,List<Integer> listStatus);

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
                and e.status in (2,1) 
            """, nativeQuery = true)
    Optional<AewaEventDetailApprovedResponse> getDetailApprovedById(@Param("id") String id);

    @Query(value = """
               select c.id as id,c.name as name from category as c
            """, nativeQuery = true)
    List<AewaEventCategoryResponse> getListEventCategory();

    @Query(value = """
                select m.id as id, m.name as name from major as m
                where m.name is not null
            """, nativeQuery = true)
    List<AewaEventGroupResponse> getEventMajor();

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
    Page<AewaCommentEventDetailResponse> getCommentEventById(Pageable pageable, @Param("idevent") String idEvent);
}



