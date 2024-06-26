package com.portalevent.core.admin.semestermanagement.repository;

import com.portalevent.core.admin.semestermanagement.model.respone.AdminerIdSemesterInEventResponse;
import com.portalevent.core.admin.semestermanagement.model.respone.AdminerNameSemesterResponse;
import com.portalevent.core.admin.semestermanagement.model.respone.AdminerSemesterResponse;
import com.portalevent.repository.SemesterRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AdminerSSemesterRepository extends SemesterRepository {

    @Query(value = """
            select ROW_NUMBER() OVER(ORDER BY s.created_date DESC) AS stt, s.id, s.name, s.start_time AS startTime, 
            s.end_time as endTime, s.start_time_first_block as startTimeFirstBlock , 
            s.end_time_first_block as endTimeFirstBlock, s.start_time_second_block as startTimeSecondBlock, s.end_time_second_block as endTimeSecondBlock
            from semester as s 
            WHERE (:searchName IS NULL OR s.name LIKE %:searchName%)
                """, countQuery = """
            select count(*)
            from semester as s 
            WHERE (:searchName IS NULL OR s.name LIKE %:searchName%)
            """, nativeQuery = true)
    Page<AdminerSemesterResponse> getList(Pageable pageable, String searchName);

    @Query(value = """
            SELECT s.id, s.name FROM semester AS s WHERE s.name LIKE ?1 
            """, countQuery = """
             select count(*)
             from semester AS s WHERE s.name LIKE ?1 
            """, nativeQuery = true)
    List<AdminerNameSemesterResponse> findNameSemester(@Param("nameSemester") String nameSemester);

    @Query(value = """
            SELECT e.id, e.name, e.semester_id 
            FROM event AS e 
            WHERE e.semester_id = ?1 
            """, countQuery = """
            SELECT count(*) FROM event 
            """, nativeQuery = true)
    List<AdminerIdSemesterInEventResponse> findIdSemester(@Param("idSemester") String idSemester);


}
