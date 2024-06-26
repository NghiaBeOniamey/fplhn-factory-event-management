package com.portalevent.core.organizer.hireDesignList.repository;

import com.portalevent.core.organizer.hireDesignList.model.response.OhdlEventMajorResponse;
import com.portalevent.repository.MajorRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OhdlEventMajorRepository extends MajorRepository {

    //Hiển thị major
    @Query(value = "SELECT id, code, name FROM major"
            , countQuery = """
                    SELECT COUNT(*) FROM major
                    """, nativeQuery = true)
    List<OhdlEventMajorResponse> getListMajorOfHireDesign();

    //Lấy 1 major trong list major
    @Query(value = """
            SELECT m.id, code, name FROM major AS m 
            JOIN event_major AS em 
            ON em.major_id = m.id 
            WHERE em.event_id = :idEvent
            """, countQuery = """
            SELECT COUNT(*)
                        JOIN event_major AS em 
                        ON em.major_id = m.id 
                        WHERE em.event_id = :idEvent
            """, nativeQuery = true)
    List<OhdlEventMajorResponse> getMajorByIdEvent(@Param("idEvent") String id);

}
