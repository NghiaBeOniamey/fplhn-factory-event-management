package fplhn.udpm.identity.infrastructure.security.repository;

import fplhn.udpm.identity.entity.Campus;
import fplhn.udpm.identity.infrastructure.security.response.ModuleAvailableResponse;
import fplhn.udpm.identity.repository.StaffRoleModuleRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface StaffModuleRoleAuthRepository extends StaffRoleModuleRepository {

    @Query(
            value = """
                    SELECT
                    	r.ma
                    FROM
                    	[nhan_vien-chuc_vu] smr
                    LEFT JOIN chuc_vu r ON
                    	smr.id_chuc_vu = r.id
                    LEFT JOIN nhan_vien nv on smr.id_nhan_vien = nv.id
                    LEFT JOIN module m ON smr.id_modules = m.id
                    WHERE
                    	nv.id = :userId AND m.id = :moduleId
                    """,
            nativeQuery = true
    )
    List<String> getListRoleCodeByUserIdAndModuleId(Long userId, Long moduleId);

    @Query(
            value = """
                    SELECT
                    	r.ten
                    FROM
                    	[nhan_vien-chuc_vu] smr
                    LEFT JOIN chuc_vu r ON
                    	smr.id_chuc_vu = r.id
                    LEFT JOIN nhan_vien nv on smr.id_nhan_vien = nv.id 	
                    LEFT JOIN module m ON smr.id_modules = m.id
                    WHERE
                    	nv.id = :userId AND m.id = :moduleId
                    """,
            nativeQuery = true
    )
    List<String> getListRoleNameByUserId(Long userId, Long moduleId);

    @Modifying
    @Query("""
            DELETE StaffRoleModule srm
            WHERE srm.staff.id = :staffId
            """)
    void deleteByStaffId(Long staffId);

    @Query("""
            SELECT DISTINCT new fplhn.udpm.identity.infrastructure.security.response.ModuleAvailableResponse(m.ma, m.ten)
            FROM StaffRoleModule srm
            LEFT JOIN srm.module m
            LEFT JOIN srm.staff s
            WHERE s.id = :userId
            """)
    List<ModuleAvailableResponse> findModuleAvailableByUserId(Long userId);

    @Query("""
            SELECT c FROM Campus c
            WHERE c.id = :id
            """)
    Optional<Campus> findCampusById(Long id);

}
