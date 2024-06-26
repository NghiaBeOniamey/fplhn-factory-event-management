package fplhn.udpm.identity.infrastructure.excel.repository;

import fplhn.udpm.identity.entity.Role;
import fplhn.udpm.identity.repository.RoleRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface RoleExcelRepository extends RoleRepository {

    @Query("SELECT r FROM Role r WHERE r.code = :roleCode")
    Optional<Role> findRoleByRoleCode(String roleCode);

    @Query("SELECT r.code FROM Role r")
    List<String> findAllRoleCode();

}
