package com.portalevent.core.admin.majormanagement.repository;

import com.portalevent.core.admin.majormanagement.model.response.AdminDepartmentResponse;
import com.portalevent.core.common.TokenFindRequest;
import com.portalevent.repository.DepartmentRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AdminDepartmentRepository extends DepartmentRepository {

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
            AND c.code = :#{#req.getCurrentTrainingFacilityCode()}
            """,countQuery = """
                SELECT COUNT(*) FROM department d
                LEFT JOIN department_campus dc
                ON d.department_id = dc.department_id
                LEFT JOIN campus c
                ON c.campus_id = dc.campus_id
                WHERE (d.code LIKE CONCAT('%', :value '%')
                OR d.name LIKE CONCAT('%', :value, '%')
                OR dc.mail_of_manager LIKE CONCAT('%', :value, '%'))
                AND c.code = :#{#req.getCurrentTrainingFacilityCode()}
            """, nativeQuery = true)
    List<AdminDepartmentResponse> getDepartmentList(@Param("value") String value, TokenFindRequest req);

}
