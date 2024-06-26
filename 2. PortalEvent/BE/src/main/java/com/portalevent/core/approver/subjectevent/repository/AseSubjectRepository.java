package com.portalevent.core.approver.subjectevent.repository;

import com.portalevent.core.approver.subjectevent.model.request.AseSubjectListSubjectRequest;
import com.portalevent.core.approver.subjectevent.model.response.AseSubjectListSubjectResponse;
import com.portalevent.entity.Major;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface AseSubjectRepository extends JpaRepository<Major,String> {

    @Query(value = """
                SELECT  m.code AS code,
                        m.name AS name
                FROM major m
                JOIN department d ON m.department_id = d.department_id
                WHERE d.code = :#{#request.departmentCode} AND
                (
                    ( :#{#request.name} IS NULL OR m.name LIKE :#{ "%" + #request.name + "%" } ) AND
                    ( :#{#request.code} IS NULL OR m.code LIKE :#{ "%" + #request.code + "%" } )
                )
                """,nativeQuery = true)
    Page<AseSubjectListSubjectResponse> getListSubjectByDepartmentCode(Pageable pageable, AseSubjectListSubjectRequest request);

}
