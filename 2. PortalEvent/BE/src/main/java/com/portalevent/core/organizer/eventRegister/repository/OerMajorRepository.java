package com.portalevent.core.organizer.eventRegister.repository;

import com.portalevent.core.organizer.eventRegister.model.response.OerCategoryResponse;
import com.portalevent.core.organizer.eventRegister.model.response.OerMajorResponse;
import com.portalevent.repository.CategoryRepository;
import com.portalevent.repository.MajorRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author SonPT
 */

@Repository
public interface OerMajorRepository extends MajorRepository {

//    @Query(value = "SELECT id, code, name, mail_of_manager FROM major " +
//            "ORDER BY created_date DESC", nativeQuery = true)
//    List<OerMajorResponse> getAll();

//    @Query(value = """
//            SELECT
//            	m.id,
//            	m.code,
//            	m.name
//            FROM major m
//            	JOIN department d ON m.department_id = d.department_id
//            WHERE
//            	d.code = :currentSubject
//            ORDER BY
//            	m.created_date DESC
//            """, nativeQuery = true)
//    List<OerMajorResponse> getAll(String currentSubject);
    @Query(value = """
            SELECT
            	m.id,
             	m.code,
             	m.name,
             	mc.mail_of_manager,
             	m.department_id
            FROM major m
            	LEFT JOIN major_campus mc ON m.major_id = mc.major_id
            ORDER BY
            	m.created_date DESC
            """, nativeQuery = true)
    List<OerMajorResponse> getAll();
}
//
//
//LEFT JOIN
//department_campus dc on dc.id = mc.department_campus_id
//LEFT JOIN
//campus c on c.id = dc.campus_id
//            WHERE
//            	c.code = :campusCode
