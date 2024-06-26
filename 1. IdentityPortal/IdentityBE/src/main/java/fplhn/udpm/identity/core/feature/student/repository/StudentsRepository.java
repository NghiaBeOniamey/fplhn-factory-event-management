package fplhn.udpm.identity.core.feature.student.repository;

import fplhn.udpm.identity.core.feature.student.model.request.StudentPaginationRequest;
import fplhn.udpm.identity.core.feature.student.model.response.ListStudentResponse;
import fplhn.udpm.identity.core.feature.student.model.response.StudentDetailResponse;
import fplhn.udpm.identity.core.feature.student.model.response.StudentResponse;
import fplhn.udpm.identity.entity.Student;
import fplhn.udpm.identity.repository.StudentRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface StudentsRepository extends StudentRepository {

    @Query(value = """
            SELECT
                    ROW_NUMBER() OVER(ORDER BY sv.id DESC) AS orderNumber,
                    sv.id as studentId,
                    sv.ma_sinh_vien as studentCode,
                    sv.ho_ten as studentName,
                    sv.xoa_mem as studentStatus,
                    sv.email_fpt as studentMail,
                    sv.so_dien_thoai as studentPhoneNumber,
                    sv.id_bo_mon_theo_co_so as departmentCampusId,
                    CONCAT(bm.ten, '-', cs.ten) as departmentNameAndCampusName
            FROM
                    sinh_vien sv
                    LEFT JOIN bo_mon_theo_co_so bmtcs
                    ON sv.id_bo_mon_theo_co_so = bmtcs.id
                    FULL JOIN bo_mon bm
                    ON bm.id = bmtcs.id_bo_mon
                    LEFT JOIN co_so cs
                    ON cs.id = bmtcs.id_co_so
            WHERE
                cs.ma = :#{#req.campusCode}
                AND ( :#{#req.studentCode} IS NULL OR sv.ma_sinh_vien LIKE CONCAT('%', :#{#req.studentCode}, '%') )
                AND ( :#{#req.studentName} IS NULL OR sv.ho_ten LIKE CONCAT('%', :#{#req.studentName}, '%') )
                AND ( :#{#req.studentMail} IS NULL OR sv.email_fpt LIKE CONCAT('%', :#{#req.studentMail}, '%') )
                AND ( :#{#req.listDepartmentId} IS NULL OR bm.id IN :#{#req.listDepartmentId} )
            """, countQuery = """
            SELECT
                	COUNT(sv.id)
            FROM
                    sinh_vien sv
                    LEFT JOIN bo_mon_theo_co_so bmtcs
                    ON sv.id_bo_mon_theo_co_so = bmtcs.id
                    FULL JOIN bo_mon bm
                    ON bm.id = bmtcs.id_bo_mon
                    LEFT JOIN co_so cs
                    ON cs.id = bmtcs.id_co_so
            WHERE
                    cs.ma = :#{#req.campusCode}
                    AND ( :#{#req.studentCode} IS NULL OR sv.ma_sinh_vien LIKE CONCAT('%', :#{#req.studentCode}, '%') )
                    AND ( :#{#req.studentName} IS NULL OR sv.ho_ten LIKE CONCAT('%', :#{#req.studentName}, '%') )
                    AND ( :#{#req.studentMail} IS NULL OR sv.email_fpt LIKE CONCAT('%', :#{#req.studentMail}, '%') )
                    AND ( :#{#req.listDepartmentId} IS NULL OR bm.id IN :#{#req.listDepartmentId} )
            """,
            nativeQuery = true
    )
    Page<List<StudentResponse>> findAllStudentWithDepartmentId(Pageable pageable, @Param("req") StudentPaginationRequest req);

    @Query(value = """
            SELECT
                    ROW_NUMBER() OVER(ORDER BY sv.id DESC) AS orderNumber,
                    sv.id as studentId,
                    sv.ma_sinh_vien as studentCode,
                    sv.ho_ten as studentName,
                    sv.xoa_mem as studentStatus,
                    sv.email_fpt as studentMail,
                    sv.so_dien_thoai as studentPhoneNumber,
                    sv.id_bo_mon_theo_co_so as departmentCampusId,
                    CONCAT(bm.ten, '-', cs.ten) as departmentNameAndCampusName
                  FROM
                      sinh_vien sv
                      LEFT JOIN bo_mon_theo_co_so bmtcs
                      ON sv.id_bo_mon_theo_co_so = bmtcs.id
                      FULL JOIN bo_mon bm
                      ON bm.id = bmtcs.id_bo_mon
                      LEFT JOIN co_so cs
                      ON cs.id = bmtcs.id_co_so
                    WHERE
                        cs.ma = :#{#req.campusCode}
                        AND ( :#{#req.studentCode} IS NULL OR sv.ma_sinh_vien LIKE CONCAT('%', :#{#req.studentCode}, '%') )
                        AND ( :#{#req.studentName} IS NULL OR sv.ho_ten LIKE CONCAT('%', :#{#req.studentName}, '%') )
                        AND ( :#{#req.studentMail} IS NULL OR sv.email_fpt LIKE CONCAT('%', :#{#req.studentMail}, '%') )
            """,
            countQuery = """
                    SELECT
                    	COUNT(sv.id)
                    FROM
                        sinh_vien sv
                        LEFT JOIN bo_mon_theo_co_so bmtcs
                        ON sv.id_bo_mon_theo_co_so = bmtcs.id
                        FULL JOIN bo_mon bm
                        ON bm.id = bmtcs.id_bo_mon
                        LEFT JOIN co_so cs
                        ON cs.id = bmtcs.id_co_so
                    WHERE
                        cs.ma = :#{#req.campusCode}
                        AND ( :#{#req.studentCode} IS NULL OR sv.ma_sinh_vien LIKE CONCAT('%', :#{#req.studentCode}, '%') )
                        AND ( :#{#req.studentName} IS NULL OR sv.ho_ten LIKE CONCAT('%', :#{#req.studentName}, '%') )
                        AND ( :#{#req.studentMail} IS NULL OR sv.email_fpt LIKE CONCAT('%', :#{#req.studentMail}, '%') )
                    """,
            nativeQuery = true
    )
    Page<List<StudentResponse>> findAllStudent(Pageable pageable, StudentPaginationRequest req);

    @Query(value = """
            SELECT id as studentId , ho_ten as studentName, ma_sinh_vien as studentCode
            FROM sinh_vien
            """, nativeQuery = true)
    List<ListStudentResponse> getListAllStudent();

    @Query(value = """
            SELECT
                  sv.id as studentId,
                  sv.ma_sinh_vien as studentCode,
                  sv.ho_ten as studentName,
                  sv.xoa_mem as studentStatus,
                  sv.email_fpt as studentMail,
                  sv.so_dien_thoai as studentPhoneNumber,
                  bm.id as departmentId,
                  cs.id as campusId
            FROM
                  sinh_vien sv
                  LEFT JOIN bo_mon_theo_co_so bmtcs
                  ON sv.id_bo_mon_theo_co_so = bmtcs.id
                  LEFT JOIN bo_mon bm
                  ON bm.id = bmtcs.id_bo_mon
                  LEFT JOIN co_so cs
                  ON cs.id = bmtcs.id_co_so
            where sv.id = :studentId
            """, nativeQuery = true)
    StudentDetailResponse getDetailStudent(@Param("studentId") Long studentId);

    Optional<Student> findByStudentCode(String studentCode);

}
