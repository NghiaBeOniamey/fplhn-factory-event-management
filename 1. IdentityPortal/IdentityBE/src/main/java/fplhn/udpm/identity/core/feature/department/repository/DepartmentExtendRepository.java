package fplhn.udpm.identity.core.feature.department.repository;

import fplhn.udpm.identity.core.feature.department.model.request.DepartmentPaginationRequest;
import fplhn.udpm.identity.core.feature.department.model.response.DepartmentResponse;
import fplhn.udpm.identity.core.feature.department.model.response.MultipleDepartmentResponse;
import fplhn.udpm.identity.repository.DepartmentRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DepartmentExtendRepository extends DepartmentRepository {

    @Query(value = """
            SELECT
            	ROW_NUMBER() OVER(ORDER BY bm.id DESC) AS orderNumber,
            	bm.id as departmentId,
            	bm.ma as departmentCode,
            	bm.ten as departmentName,
            	bm.xoa_mem as departmentStatus
            FROM
            	bo_mon bm
            WHERE
            	:#{#req.searchValues} IS NULL
            	OR :#{#req.searchValues} LIKE ''
            	OR bm.id IN :#{#req.searchValues}
            """, countQuery = """
            SELECT
                COUNT(bm.id) FROM bo_mon bm
            WHERE
                :#{#req.searchValues} IS NULL
            	OR :#{#req.searchValues} LIKE ''
            	OR bm.id IN :#{#req.searchValues}
            """, nativeQuery = true)
    Page<DepartmentResponse> getAllDepartmentFilter(Pageable pageable, DepartmentPaginationRequest req);

    @Query(value = """
            SELECT
            	ROW_NUMBER() OVER(
            	ORDER BY bm.id DESC) AS orderNumber,
            	bm.id as departmentId,
            	bm.ma as departmentCode,
            	bm.ten as departmentName,
            	bm.xoa_mem as departmentStatus
            FROM
            	bo_mon bm
            """, countQuery = """
            SELECT 
                COUNT(bm.id) FROM bo_mon bm 
            """, nativeQuery = true)
    Page<DepartmentResponse> getAllDepartment(Pageable pageable);

    Boolean existsByCode(String maBoMon);

    @Query(value = """
            SELECT
                id as departmentId,
                ma as departmentCode,
                ten as departmentName
            FROM
                bo_mon bm
            """, nativeQuery = true)
    List<MultipleDepartmentResponse> getListAllDepartment();

}
