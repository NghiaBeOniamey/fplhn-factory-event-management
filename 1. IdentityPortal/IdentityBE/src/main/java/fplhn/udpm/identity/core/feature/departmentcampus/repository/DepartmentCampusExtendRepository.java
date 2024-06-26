package fplhn.udpm.identity.core.feature.departmentcampus.repository;


import fplhn.udpm.identity.core.feature.departmentcampus.model.request.DepartmentCampusDetailRequest;
import fplhn.udpm.identity.core.feature.departmentcampus.model.request.ModifyDepartmentCampusRequest;
import fplhn.udpm.identity.core.feature.departmentcampus.model.response.DepartmentCampusResponse;
import fplhn.udpm.identity.core.feature.departmentcampus.model.response.ListCampusResponse;
import fplhn.udpm.identity.core.feature.departmentcampus.model.response.ListDepartmentCampusResponse;
import fplhn.udpm.identity.core.feature.departmentcampus.model.response.ListHeadDepartmentCampusResponse;
import fplhn.udpm.identity.entity.Campus;
import fplhn.udpm.identity.entity.Department;
import fplhn.udpm.identity.entity.DepartmentCampus;
import fplhn.udpm.identity.repository.DepartmentCampusRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface DepartmentCampusExtendRepository extends DepartmentCampusRepository {

    @Query(value = """
               SELECT
               	ROW_NUMBER() OVER(
               	ORDER BY bmtcs.id DESC) as orderNumber,
               	bmtcs.id as departmentCampusId,
               	bmtcs.id_co_so as campusId,
                cs.ma as campusCode,
               	bmtcs.id_chu_nhiem_bo_mon as headDepartmentCampusId,
               	bmtcs.xoa_mem as departmentCampusStatus,
               	cs.ten as campusName,
               	nv.ho_ten as headDepartmentCampusName,
               	nv.ma_nhan_vien as headDepartmentCampusCode
               FROM
               	bo_mon_theo_co_so bmtcs
               LEFT JOIN co_so cs ON
               	cs.id = bmtcs.id_co_so
               LEFT JOIN bo_mon bm ON
               	bm.id = bmtcs.id_bo_mon
               LEFT JOIN nhan_vien nv ON
               	nv.id = bmtcs.id_chu_nhiem_bo_mon
               WHERE
               	(bmtcs.id_bo_mon = :id)
               	AND (:#{#req.searchValues} IS NULL OR :#{#req.searchValues} = '' OR (cs.id IN (CONCAT('%', :#{#req.searchValues}, '%'))))
            """,
            countQuery = """
                    SELECT
                    	COUNT(bmtcs.id)
                    FROM
                    	bo_mon_theo_co_so bmtcs
                    LEFT JOIN co_so cs ON
                    	cs.id = bmtcs.id_co_so
                    LEFT JOIN bo_mon bm ON
                    	bm.id = bmtcs.id_bo_mon
                    LEFT JOIN nhan_vien nv ON
                    	nv.id = bmtcs.id_chu_nhiem_bo_mon
                    WHERE
                    	(bmtcs.id_bo_mon = :id)
                    	AND (:#{#req.searchValues} IS NULL OR :#{#req.searchValues} = '' OR (cs.id IN (CONCAT('%', :#{#req.searchValues}, '%'))))
                    """,
            nativeQuery = true
    )
    Page<DepartmentCampusResponse> getAllDepartmentCampus(Long id, Pageable pageable, @Param("req") DepartmentCampusDetailRequest req);

    @Query(value = """
               SELECT
               	ROW_NUMBER() OVER(
               	ORDER BY bmtcs.id DESC) as orderNumber,
               	bmtcs.id as departmentCampusId,
               	bmtcs.id_co_so as campusId,
               	cs.ma as campusCode,
               	bmtcs.id_chu_nhiem_bo_mon as headDepartmentCampusId,
               	bmtcs.xoa_mem as departmentCampusStatus,
               	cs.ten as campusName,
               	nv.ho_ten as headDepartmentCampusName,
               	nv.ma_nhan_vien as headDepartmentCampusCode
               FROM
               	bo_mon_theo_co_so bmtcs
               LEFT JOIN co_so cs ON
               	cs.id = bmtcs.id_co_so
               LEFT JOIN bo_mon bm ON
               	bm.id = bmtcs.id_bo_mon
               LEFT JOIN nhan_vien nv ON
               	nv.id = bmtcs.id_chu_nhiem_bo_mon
               WHERE
               	(bmtcs.id_bo_mon = :id)
            """,
            countQuery = """
                    SELECT
                    	COUNT(bmtcs.id)
                    FROM
                    	bo_mon_theo_co_so bmtcs
                    LEFT JOIN co_so cs ON
                    	cs.id = bmtcs.id_co_so
                    LEFT JOIN bo_mon bm ON
                    	bm.id = bmtcs.id_bo_mon
                    LEFT JOIN nhan_vien nv ON
                    	nv.id = bmtcs.id_chu_nhiem_bo_mon
                    WHERE
                    	(bmtcs.id_bo_mon = :id)
                    """,
            nativeQuery = true
    )
    Page<DepartmentCampusResponse> getAllDepartmentCampus(Long id, Pageable pageable);


    @Query(value = """
                SELECT
                    ten as tenBoMon
                FROM 
                    bo_mon
                WHERE
                    id = :id
            """, nativeQuery = true)
    String getTenBoMon(Long id);

    @Query(value = """
                SELECT
                	bmtcs
                FROM
                	DepartmentCampus bmtcs
                WHERE
                	(:#{#req.departmentId} IS NULL
                		OR :#{#req.departmentId} LIKE ''
                		OR bmtcs.department.id = :#{#req.departmentId})
                	AND (:#{#req.campusId} IS NULL
                		OR :#{#req.campusId} LIKE ''
                		OR bmtcs.campus.id = :#{#req.campusId})
            """
    )
    Optional<DepartmentCampus> findByIdBoMonAndIdCoSoAndIdAdd(@Param("req") ModifyDepartmentCampusRequest req);

    boolean existsByCampusAndDepartment(Campus campus, Department department);

    @Query(value = """
                SELECT
                	id as campusId,
                	ten as campusName
                FROM
                	co_so
                WHERE
                	xoa_mem LIKE 'NOT_DELETED'
            """, nativeQuery = true)
    List<ListCampusResponse> getListCoSo();

    @Query(value = """
                    SELECT
                        id as staffId,
                        ho_ten as staffName,
                        ma_nhan_vien as staffCode
                     FROM
                        nhan_vien
                    WHERE
                        xoa_mem LIKE 'NOT_DELETED'
            """, nativeQuery = true)
    List<ListHeadDepartmentCampusResponse> getListNhanVien();

    @Query(value = """
                        SELECT
                        	bmtcs.id_co_so as campusId,
                        	nv.ho_ten as staffName,
                        	cs.ten as campusName,
                        	nv.ma_nhan_vien as staffCode
                        FROM
                        	bo_mon_theo_co_so bmtcs
                        LEFT JOIN nhan_vien nv ON
                        	nv.id = bmtcs.id_chu_nhiem_bo_mon
                        LEFT JOIN co_so cs ON
                        	cs.id = bmtcs.id_co_so
                        WHERE
                            bmtcs.id_bo_mon = :id
            """, nativeQuery = true)
    List<ListDepartmentCampusResponse> getListBoMonTheoCoSo(Long id);

}
