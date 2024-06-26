package fplhn.udpm.identity.infrastructure.security.repository;

import fplhn.udpm.identity.entity.Role;
import fplhn.udpm.identity.repository.RoleRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface RoleAuthRepository extends RoleRepository {

    @Query("SELECT r FROM Role r WHERE r.code = 'GIAO_VIEN'")
    Optional<Role> findRoleStaff();

    @Query(
            value = """
                    SELECT c.ma
                    FROM chuc_vu c
                    LEFT JOIN dbo.[nhan_vien-chuc_vu] smr on c.id = smr.id_chuc_vu
                    LEFT JOIN nhan_vien nv on smr.id_nhan_vien = nv.id
                    WHERE nv.id = :id
                    """,
            nativeQuery = true
    )
    List<String> findRoleByStaffId(Long id);

}
