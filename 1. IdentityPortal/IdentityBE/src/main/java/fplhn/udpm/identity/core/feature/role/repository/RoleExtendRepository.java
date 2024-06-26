package fplhn.udpm.identity.core.feature.role.repository;

import fplhn.udpm.identity.core.feature.role.model.request.RolePaginationRequest;
import fplhn.udpm.identity.core.feature.role.model.response.DetailRoleResponse;
import fplhn.udpm.identity.core.feature.role.model.response.ListRoleResponse;
import fplhn.udpm.identity.core.feature.role.model.response.RoleResponse;
import fplhn.udpm.identity.entity.Role;
import fplhn.udpm.identity.repository.RoleRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface RoleExtendRepository extends RoleRepository {

    @Query("""
                SELECT
                    ROW_NUMBER() OVER(ORDER BY x.id DESC) AS orderNumber,
                    x.id as id,
                    x.code as roleCode,
                    x.name as roleName,
                    x.entityStatus as roleStatus
                FROM Role x
                WHERE
                (:#{#request.roleName} IS NULL OR :#{#request.roleName} LIKE '' OR x.name LIKE  CONCAT('%', :#{#request.roleName}, '%'))
                AND
                ( :#{#request.searchValues} IS NULL OR :#{#request.searchValues == null ? true : #request.searchValues.empty} = true OR x.id IN :#{#request.searchValues} )
                AND
                (:#{#request.roleCode} IS NULL OR :#{#request.roleCode} LIKE '' OR x.code LIKE  CONCAT('%', :#{#request.roleCode}, '%'))
                AND
                ((:#{#request.q} IS NULL OR :#{#request.q} LIKE '' OR x.code LIKE  CONCAT('%', :#{#request.q}, '%')) OR
                (:#{#request.q} IS NULL OR :#{#request.q} LIKE '' OR x.name LIKE  CONCAT('%', :#{#request.q}, '%')))
            """)
    Page<RoleResponse> findAllRole(@Param("request") RolePaginationRequest request, Pageable pageable);


    Optional<Role> findByCode(String ma);

    Boolean existsByCode(String ma);

    @Query(
            value = """
                    SELECT x.id as roleId, x.ma as roleCode, x.ten as roleName, x.xoa_mem as roleStatus FROM chuc_vu x WHERE x.id = :id
                    """,
            nativeQuery = true
    )
    DetailRoleResponse findDetailById(Long id);

    @Query(
            value = """
                    SELECT x.id as id, x.ma as roleCode, x.ten as roleName FROM chuc_vu x
                    """,
            nativeQuery = true
    )
    List<ListRoleResponse> getAll();

}
