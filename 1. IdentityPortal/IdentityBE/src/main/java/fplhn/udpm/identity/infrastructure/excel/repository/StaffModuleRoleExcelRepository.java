package fplhn.udpm.identity.infrastructure.excel.repository;

import fplhn.udpm.identity.entity.StaffRoleModule;
import fplhn.udpm.identity.repository.StaffRoleModuleRepository;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface StaffModuleRoleExcelRepository extends StaffRoleModuleRepository {

    @Query("""
            SELECT srm
            FROM StaffRoleModule srm
            WHERE srm.staff.staffCode = :staffCode
            AND srm.role.code = :roleCode
            AND srm.module.ma = :moduleCode
            """)
    Optional<StaffRoleModule> findBy(String staffCode, String roleCode, String moduleCode);

    @Modifying
    @Transactional
    @Query("""
            DELETE
            FROM StaffRoleModule srm
            WHERE srm.module.ma = :moduleCode
            """)
    void deleteAllByModuleCode(String moduleCode);

    @Query("""
            SELECT COUNT(srm)
            FROM StaffRoleModule srm
            WHERE srm.module.ma = :moduleCode
            """)
    int countByModuleMa(String moduleCode);



}
