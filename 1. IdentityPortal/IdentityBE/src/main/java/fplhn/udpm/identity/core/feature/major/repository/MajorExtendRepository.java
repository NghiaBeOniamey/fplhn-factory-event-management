package fplhn.udpm.identity.core.feature.major.repository;

import fplhn.udpm.identity.core.feature.major.model.request.MajorRequest;
import fplhn.udpm.identity.core.feature.major.model.response.DetailMajorResponse;
import fplhn.udpm.identity.core.feature.major.model.response.MajorResponse;
import fplhn.udpm.identity.entity.Major;
import fplhn.udpm.identity.repository.MajorRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface MajorExtendRepository extends MajorRepository {

    @Query(
            value = """
                    SELECT
                        ROW_NUMBER() OVER(ORDER BY cn.id DESC) AS orderNumber,
                        cn.id AS majorId,
                        cn.ten AS majorName,
                        cn.ma AS majorCode,
                        cn.xoa_mem AS majorStatus
                    FROM
                        chuyen_nganh cn
                    WHERE
                        cn.id_bo_mon = :#{#request.departmentId}
                    AND (:#{#request.majorName} IS NULL OR cn.ten LIKE %:#{#request.majorName}% )
                    AND (:#{#request.majorCode} IS NULL OR cn.ma LIKE %:#{#request.majorCode}% )
                    """,
            countQuery = """
                    SELECT
                        COUNT(cn.id)
                    FROM
                        chuyen_nganh cn
                    WHERE
                        cn.id_bo_mon = :#{#request.departmentId}
                    AND (:#{#request.majorName} IS NULL OR cn.ten LIKE %:#{#request.majorName}% )
                    AND (:#{#request.majorCode} IS NULL OR cn.ma LIKE %:#{#request.majorCode}% )
                    """,
            nativeQuery = true
    )
    Page<List<MajorResponse>> findAllMajor(Pageable pageable, MajorRequest request);

    Optional<Major> findByCode(String mạjorCode);

    @Query(
            value = """
                    SELECT
                        cn.id AS majorId,
                        cn.ten AS majorName,
                        cn.ma AS majorCode,
                        cn.xoa_mem AS majorStatus
                    FROM
                        chuyen_nganh cn
                    WHERE
                        cn.id = :majorId
                    """,
            nativeQuery = true
    )
    DetailMajorResponse findByMajorId(Long majorId);

}
