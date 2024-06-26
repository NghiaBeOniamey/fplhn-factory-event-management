package com.portalevent.core.organizer.eventDetail.repository;

import com.portalevent.core.organizer.eventDetail.model.response.OedMajorCampusResponse;
import com.portalevent.core.organizer.eventDetail.model.response.OedMajorResponse;
import com.portalevent.entity.Major;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 * @author HoangDV
 */
@Repository
public interface OedMajorRepository extends JpaRepository<Major, String> {
    @Query(value = "SELECT id, code, name FROM major", nativeQuery = true)
    List<OedMajorResponse> getAll();

//    @Query(value = "SELECT m.id, code, name FROM major AS m " +
//            "JOIN event_major AS em " +
//            "ON em.major_id = m.id " +
//            "WHERE em.event_id = :idEvent" , nativeQuery = true)
    @Query(value = """
            SELECT mc.id, m.name, m.code FROM major_campus mc
            LEFT JOIN major m ON m.major_id = mc.major_id
            LEFT JOIN event_major em ON em.major_id = mc.id
            WHERE em.event_id = :idEvent
            """ , nativeQuery = true)
    List<OedMajorResponse> getMajorByIdEvent(@Param("idEvent") String id);

    @Query(value = """
            select
            	mc.id,
            	mc.department_campus_id,
            	m.name
            from
            	event e
            left join event_major em on
            	em.event_id = e.id
            left join major_campus mc on
            	em.major_id = mc.id
            left join major m on
            	mc.major_id = m.major_id
            where e.id = :idEvent
            """, nativeQuery = true)
    List<OedMajorCampusResponse> getAllMajorCampusByIdEvent(String idEvent);
}
