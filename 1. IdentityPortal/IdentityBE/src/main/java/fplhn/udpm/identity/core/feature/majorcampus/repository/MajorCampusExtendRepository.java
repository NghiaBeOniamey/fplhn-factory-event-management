package fplhn.udpm.identity.core.feature.majorcampus.repository;

import fplhn.udpm.identity.core.feature.majorcampus.model.request.MajorCampusRequest;
import fplhn.udpm.identity.core.feature.majorcampus.model.response.DetailMajorCampusResponse;
import fplhn.udpm.identity.core.feature.majorcampus.model.response.MajorCampusResponse;
import fplhn.udpm.identity.entity.DepartmentCampus;
import fplhn.udpm.identity.entity.Major;
import fplhn.udpm.identity.entity.MajorCampus;
import fplhn.udpm.identity.repository.MajorCampusRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface MajorCampusExtendRepository extends MajorCampusRepository {

    @Query(
            value = """
                    SELECT
                    	ROW_NUMBER() OVER(
                    	ORDER BY cntcs.id DESC) AS orderNumber,
                    	cntcs.id as majorCampusId,
                    	cn.ma + ' - ' + cn.ten as majorCodeName,
                    	cntcs.xoa_mem as majorCampusStatus,
                    	nv.ho_ten + ' - ' + nv.ma_nhan_vien as headMajorCodeName,
                    	cs.ma as campusCode
                    FROM
                    	chuyen_nganh_theo_co_so cntcs
                    LEFT JOIN chuyen_nganh cn ON cntcs.id_chuyen_nganh = cn.id
                    LEFT JOIN nhan_vien nv ON cntcs.id_truong_mon = nv.id
                    LEFT JOIN bo_mon_theo_co_so bmtcs ON cntcs.id_bo_mon_theo_co_so = bmtcs.id
                    LEFT JOIN co_so cs ON bmtcs.id_co_so = cs.id
                    WHERE
                    bmtcs.id = :#{#request.departmentCampusId}
                    AND (:#{#request.majorCode} IS NULL OR cn.ma LIKE CONCAT('%', :#{#request.majorCode}, '%'))
                    AND (:#{#request.headMajorName} IS NULL OR nv.ho_ten LIKE CONCAT('%', :#{#request.headMajorName}, '%'))
                    AND (:#{#request.majorName} IS NULL OR cn.ten LIKE CONCAT('%', :#{#request.majorName}, '%'))
                    AND (:#{#request.headMajorCode} IS NULL OR nv.ma_nhan_vien LIKE CONCAT('%', :#{#request.headMajorCode}, '%'))
                    """,
            countQuery = """
                    SELECT
                    	COUNT(cntcs.id)
                    FROM
                        chuyen_nganh_theo_co_so cntcs
                    LEFT JOIN chuyen_nganh cn ON cntcs.id_chuyen_nganh = cn.id
                    LEFT JOIN nhan_vien nv ON cntcs.id_truong_mon = nv.id
                    LEFT JOIN bo_mon_theo_co_so bmtcs ON cntcs.id_bo_mon_theo_co_so = bmtcs.id
                    LEFT JOIN co_so cs ON bmtcs.id_co_so = cs.id
                    WHERE
                    bmtcs.id = :#{#request.departmentCampusId}
                    AND (:#{#request.majorCode} IS NULL OR cn.ma LIKE CONCAT('%', :#{#request.majorCode}, '%'))
                    AND (:#{#request.headMajorName} IS NULL OR nv.ho_ten LIKE CONCAT('%', :#{#request.headMajorName}, '%'))
                    AND (:#{#request.majorName} IS NULL OR cn.ten LIKE CONCAT('%', :#{#request.majorName}, '%'))
                    AND (:#{#request.headMajorCode} IS NULL OR nv.ma_nhan_vien LIKE CONCAT('%', :#{#request.headMajorCode}, '%'))
                    """,
            nativeQuery = true)
    Page<List<MajorCampusResponse>> getAllMajorCampus(MajorCampusRequest request, Pageable pageable);

    @Query(
            value = """
                    SELECT
                    	cn.id as majorId,
                    	cn.ten as majorName,
                    	nv.id as headMajorId,
                    	nv.ho_ten as headMajorCodeName,
                    	cs.ma as campusCode
                    FROM
                    	chuyen_nganh_theo_co_so cntcs
                    LEFT JOIN chuyen_nganh cn ON cntcs.id_chuyen_nganh = cn.id
                    LEFT JOIN nhan_vien nv ON cntcs.id_truong_mon = nv.id
                    LEFT JOIN bo_mon_theo_co_so bmtcs ON cntcs.id_bo_mon_theo_co_so = bmtcs.id
                    LEFT JOIN co_so cs ON bmtcs.id_co_so = cs.id
                    WHERE
                        cntcs.id = :majorCampusId
                    """,
            nativeQuery = true
    )
    DetailMajorCampusResponse getDetailMajorCampus(Long majorCampusId);

    Optional<MajorCampus> findByMajorAndDepartmentCampus(Major major, DepartmentCampus departmentCampus);

}
