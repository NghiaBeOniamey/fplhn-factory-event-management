package fplhn.udpm.identity.core.feature.semester.repository;

import fplhn.udpm.identity.core.feature.semester.model.request.SemesterPaginationRequest;
import fplhn.udpm.identity.core.feature.semester.model.response.DetailSemesterResponse;
import fplhn.udpm.identity.core.feature.semester.model.response.SemesterResponse;
import fplhn.udpm.identity.repository.SemesterRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SemesterExtendRepository extends SemesterRepository {

    @Query(
            value = """
                    SELECT
                        hk.id AS id,
                        ROW_NUMBER() over (ORDER BY hk.id DESC) AS orderNumber,
                        hk.ten_hoc_ky AS semesterName,
                        hk.bat_dau AS startTime,
                        hk.ket_thuc AS endTime,
                        hk.bat_dau_block_1 AS startTimeFirstBlock,
                        hk.ket_thuc_block_1 AS endTimeFirstBlock,
                        hk.bat_dau_block_2 AS startTimeSecondBlock,
                        hk.ket_thuc_block_2 AS endTimeSecondBlock
                    FROM
                        hoc_ky hk
                    WHERE
                         (:#{#request.semesterName} IS NULL OR hk.ten_hoc_ky LIKE %:#{#request.semesterName}%)
                        AND (:#{#request.startTime} IS NULL OR hk.bat_dau >= :#{#request.startTime})
                        AND (:#{#request.endTime} IS NULL OR hk.ket_thuc <= :#{#request.endTime})
                        AND (:#{#request.startTimeFirstBlock} IS NULL OR hk.bat_dau_block_1 >= :#{#request.startTimeFirstBlock})
                        AND (:#{#request.endTimeFirstBlock} IS NULL OR hk.ket_thuc_block_1 <= :#{#request.endTimeFirstBlock})
                        AND (:#{#request.startTimeSecondBlock} IS NULL OR hk.bat_dau_block_2 >= :#{#request.startTimeSecondBlock})
                        AND (:#{#request.endTimeSecondBlock} IS NULL OR hk.ket_thuc_block_2 <= :#{#request.endTimeSecondBlock})
                    """,
            countQuery = """
                        SELECT
                            COUNT(hk.id)
                        FROM
                            hoc_ky hk
                        WHERE
                             (:#{#request.semesterName} IS NULL OR hk.ten_hoc_ky LIKE %:#{#request.semesterName}%)
                            AND (:#{#request.startTime} IS NULL OR hk.bat_dau >= :#{#request.startTime})
                            AND (:#{#request.endTime} IS NULL OR hk.ket_thuc <= :#{#request.endTime})
                            AND (:#{#request.startTimeFirstBlock} IS NULL OR hk.bat_dau_block_1 >= :#{#request.startTimeFirstBlock})
                            AND (:#{#request.endTimeFirstBlock} IS NULL OR hk.ket_thuc_block_1 <= :#{#request.endTimeFirstBlock})
                            AND (:#{#request.startTimeSecondBlock} IS NULL OR hk.bat_dau_block_2 >= :#{#request.startTimeSecondBlock})
                            AND (:#{#request.endTimeSecondBlock} IS NULL OR hk.ket_thuc_block_2 <= :#{#request.endTimeSecondBlock})
                    """,
            nativeQuery = true
    )
    Page<List<SemesterResponse>> findAllSemester(Pageable pageable, SemesterPaginationRequest request);

    @Query(
            value = """
                        SELECT COUNT(hk.id)
                        FROM hoc_ky hk
                        WHERE hk.bat_dau < :endTime AND hk.ket_thuc > :startTime AND hk.id != :semesterId
                    """,
            nativeQuery = true
    )
    int countOverlappingSemesters(Long startTime, Long endTime, Long semesterId);

    @Query(
            value = """
                        SELECT COUNT(hk.id)
                        FROM hoc_ky hk
                        WHERE hk.bat_dau < :endTime AND hk.ket_thuc > :startTime
                    """,
            nativeQuery = true
    )
    int countOverlappingSemesters(Long startTime, Long endTime);

    @Query(
            value = """
                    SELECT
                        hk.id AS id,
                        hk.ten_hoc_ky AS semesterName,
                        hk.bat_dau AS startTime,
                        hk.ket_thuc AS endTime,
                        hk.bat_dau_block_1 AS startTimeFirstBlock,
                        hk.ket_thuc_block_1 AS endTimeFirstBlock,
                        hk.bat_dau_block_2 AS startTimeSecondBlock,
                        hk.ket_thuc_block_2 AS endTimeSecondBlock
                    FROM
                        hoc_ky hk
                    WHERE
                        hk.id = :id
                    """,
            nativeQuery = true
    )
    DetailSemesterResponse findDetailSemesterById(Long id);

}
