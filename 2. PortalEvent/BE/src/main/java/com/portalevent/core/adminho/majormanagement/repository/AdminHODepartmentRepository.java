package com.portalevent.core.adminho.majormanagement.repository;

import com.portalevent.core.adminho.majormanagement.model.response.AdminHODepartmentResponse;
import com.portalevent.repository.DepartmentRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AdminHODepartmentRepository extends DepartmentRepository {

//    @Query(
//            value = """
//                        SELECT * FROM agenda_item
//                    """,
//            nativeQuery = true
//    )
//    List<AdminHODepartmentResponse> getDepartments(AdminHOSearchDepartmentsRequest request);

//    @Query(value = """
//            SELECT ROW_NUMBER() OVER(ORDER BY m.last_modified_date DESC) as indexs,
//            m.id AS id,
//            m.code AS code,
//            m.name AS name
//            FROM major m
//            WHERE (m.code LIKE CONCAT('%', :value '%')
//            OR m.name LIKE CONCAT('%', :value, '%')
//            OR m.mail_of_manager LIKE CONCAT('%', :value, '%'))
//            AND m.campus_code = :campusCode
//            """,countQuery = """
//            SELECT COUNT(*) FROM major m
//            WHERE (m.code LIKE CONCAT('%', :value '%')
//            OR m.name LIKE CONCAT('%', :value, '%')
//            OR m.mail_of_manager LIKE CONCAT('%', :value, '%'))
//            AND m.campus_code = :campusCode
//            """, nativeQuery = true)
//    List<AdminerHOMajorResponse> getMajorList(@Param("value") String value, String campusCode);

//    @Query(value = """
//            SELECT ROW_NUMBER() OVER(ORDER BY m.last_modified_date DESC) as indexs,
//            m.id AS id,
//            m.code AS code,
//            m.name AS name,
//            m.mail_of_manager AS mailOfManager
//            FROM major m
//            WHERE m.campus_code = :campusCode
//            """, nativeQuery = true)
//    List<AdminerHOMajorResponse> getAllMajors(String campusCode);

//    @Query(value = """
//        SELECT COUNT(*) FROM major
//        """, nativeQuery = true)
//    Integer count(@Param("idMajor") String idMajor);

//    Optional<Major> findByIdLongAndCampusId(Long idLong, Long campusId);

        @Query(value = """
            SELECT ROW_NUMBER() OVER(ORDER BY d.last_modified_date DESC) as indexs,
            d.code AS code,
            d.name AS name,
            dc.mail_of_manager as mailOfManager
            FROM department d
            LEFT JOIN department_campus dc
            ON d.department_id = dc.department_id
            LEFT JOIN campus c
            ON c.campus_id = dc.campus_id
            WHERE (d.code LIKE CONCAT('%', :value '%')
            OR d.name LIKE CONCAT('%', :value, '%')
            OR dc.mail_of_manager LIKE CONCAT('%', :value, '%'))
            AND c.code = :campusCode
            """,countQuery = """
                SELECT COUNT(*) FROM department d
                LEFT JOIN department_campus dc
                ON d.department_id = dc.department_id
                LEFT JOIN campus c
                ON c.campus_id = dc.campus_id
                WHERE (d.code LIKE CONCAT('%', :value '%')
                OR d.name LIKE CONCAT('%', :value, '%')
                OR dc.mail_of_manager LIKE CONCAT('%', :value, '%'))
                AND c.code = :campusCode
                        """, nativeQuery = true)
    List<AdminHODepartmentResponse> getDepartmentList(@Param("value") String value, @Param("campusCode") String campusCode);

}
